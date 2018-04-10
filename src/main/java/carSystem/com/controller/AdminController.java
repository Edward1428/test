package carSystem.com.controller;

import carSystem.com.annotation.LoginRequired;
import carSystem.com.bean.Customer;
import carSystem.com.bean.Report;
import carSystem.com.bean.Role;
import carSystem.com.bean.User;
import carSystem.com.bean.report.AdminLog;
import carSystem.com.dao.AdminLogDAO;
import carSystem.com.service.CustomerService;
import carSystem.com.service.IntegralService;
import carSystem.com.service.ReportService;
import carSystem.com.service.UserService;
import carSystem.com.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/admin")
public class AdminController {

    @Autowired
    private IntegralService integralService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private AdminLogDAO adminLogDAO;

    @Autowired
    private CustomerService customerService;

    @LoginRequired(role = Role.ADMIN)
    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    public @ResponseBody
    Result updateUser(@RequestAttribute User user, @RequestBody UserVO userVO) {
        User updateUser = userService.findById(userVO.getUserId());
        if (updateUser == null) {
            return Result.failed("用户不存在");
        }

        AdminLog adminLog = new AdminLog();
        adminLog.setAdminId(user.getId());
        adminLog.setAdminName(user.getNickName());
        adminLog.setUserId(updateUser.getId());
        adminLog.setUserName(updateUser.getNickName());
        adminLog.setStatus(AdminLog.UPDATE);
        String msg = "更新用户" + updateUser.getNickName() + ":";

        String nickName = userVO.getNickName();
        if (StringUtils.isNotBlank(nickName) && !nickName.equals(updateUser.getNickName())) {
            updateUser.setNickName(nickName);
            msg = msg + "修改昵称为：" + nickName + "；";
        }

        String password = userVO.getPassword();
        if (StringUtils.isNotBlank(password)) {
            if (password.length() >= 6) {
                updateUser.setSha512EncodedPassword(password);
                msg = msg + "修改密码；";
            } else {
                return Result.failed("密码长度不能小于6");
            }
        }

        Integer addIntegral = userVO.getAddIntegral();
        if (addIntegral != null) {
            updateUser.setIntegral(updateUser.getIntegral() + addIntegral);
            msg = msg + "增加积分" + addIntegral;
            adminLog.setIntegral(addIntegral);
        }
        adminLog.setMsg(msg);

        integralService.insertLog(adminLog);
        userService.update(updateUser);
        return Result.success();
    }

    @LoginRequired(role = Role.ADMIN)
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public @ResponseBody Result insertUser(@RequestAttribute User user, @RequestBody User newUser) {
        String email = newUser.getEmail();
        String password = newUser.getPassword();
        if (StringUtils.isNotBlank(email) && StringUtils.isNotBlank(password) && password.length()>5) {
            AdminLog adminLog = new AdminLog();
            adminLog.setStatus(AdminLog.INSERT);

            if (userService.findByEmail(email) != null) {
                return Result.failed("该账号名已存在");
            }

            User user1 = new User();
            user1.setEmail(email);
            user1.setSha512EncodedPassword(password);
            Integer integral = newUser.getIntegral();
            user1.setIntegral(integral != null ? integral : 0);
            user1.setNickName(newUser.getNickName());
            user1.setHeadUrl(newUser.getHeadUrl());
            user1.setRole(Role.CHANNEL.getRole());
            user1.setStatus(User.ABLE);
            String msg = "新增用户,初始积分为:" + integral;

            int userId = userService.insertUser(user1);
            adminLog.setAdminName(user.getNickName());
            adminLog.setAdminId(user.getId());
            adminLog.setUserName(newUser.getNickName());
            adminLog.setUserId(userId);
            adminLog.setMsg(msg);
            integralService.insertLog(adminLog);
            return Result.success();

        } else {
            return Result.failed("用户名密码不能为空,或密码长度过短");
        }
    }


    @LoginRequired(role = Role.ADMIN)
    @RequestMapping(method = RequestMethod.POST, value = "/user/listQuery")
    public @ResponseBody Result findAllUser(@RequestAttribute User user, @RequestBody(required = false) ListQuery listQuery) {
        List<UserTableVO> userTableVOList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        Integer total = 0;
        if (listQuery == null) {
            userList = userService.findAll();
        } else {
            userList = userService.userQuery(listQuery);
        }

        userTableVOList = integralService.toUserTableVO(userList);
        total = userTableVOList.size();
        Map<String, Object> map = new HashMap<>();
        map.put("list", userTableVOList);
        map.put("total", total);
        return Result.success(map);
    }

    @LoginRequired(role = Role.ADMIN)
    @RequestMapping(method = RequestMethod.POST, value = "/log/listQuery")
    public @ResponseBody Result findAllLog(@RequestAttribute User user, @RequestBody(required = false) ListQuery listQuery) {
        List<AdminLog> adminLogList = new ArrayList<>();
        Integer total = 0;
        if (listQuery == null) {
            adminLogList = integralService.findAllLog();
            total = adminLogList.size();
        } else {
            adminLogList = integralService.logQuery(listQuery);
            total = integralService.findAllLog().size();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("list", adminLogList);
        map.put("total", total);
        return Result.success(map);
    }

    @LoginRequired(role = Role.ADMIN)
    @RequestMapping(method = RequestMethod.DELETE, value = "/report/{reportId}")
    public @ResponseBody
    Result delete(@RequestAttribute User user, @PathVariable Integer reportId) {
        Report report = reportService.findById(reportId);
        if (report != null) {
            report.setStatus(Report.DisAble);
            reportService.update(report);

            AdminLog adminLog = new AdminLog();
            adminLog.setAdminId(user.getId());
            adminLog.setAdminName(user.getNickName());
            adminLog.setUserId(report.getUserId());
            adminLog.setUserName(userService.findById(report.getUserId()).getNickName());
            adminLog.setMsg("删除报告"+report.getName()+ ",如有需要，请手动返回积分:"+ report.getPayout() +"分");
            adminLogDAO.insert(adminLog);
            return Result.success();
        } else {
            return Result.failed("找不到这个报告");
        }
    }

    //用于前端导出，返回所有这个用户的所有
    @LoginRequired(role = Role.ADMIN)
    @RequestMapping(method = RequestMethod.GET, value = "/customer/{userId}")
    public @ResponseBody Result reportExcel(@PathVariable Integer userId) {
        List<ExcelVO> excelVOList = reportService.export(userId);
        return Result.success(excelVOList);
    }
}
