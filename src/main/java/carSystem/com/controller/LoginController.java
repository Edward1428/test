package carSystem.com.controller;

import carSystem.com.annotation.LoginRequired;
import carSystem.com.bean.User;
import carSystem.com.service.UserService;
import carSystem.com.vo.Result;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@RequestMapping(value = "/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @Value("${cookie_timeout}")
    private String cookie_timeOut;

    @LoginRequired
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public @ResponseBody Result getUser(@RequestParam String token) {
        User user = userService.findSid(token);
        return Result.success(user);
    }

    @LoginRequired
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public @ResponseBody Result getUserInfo(@RequestAttribute User user) {
        if (user != null) {
            return Result.success();
        } else {
            return Result.failed("请登陆");
        }

    }


    //登录
    @RequestMapping(value = "/user/login", method = RequestMethod.PUT)
    public @ResponseBody Result userLogin(@RequestBody JSONObject authorized, HttpServletResponse response) {

        String email = authorized.getString("username");
        String password = authorized.getString("password");
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            return Result.failed("username or password can't be empty");
        }
        User user = userService.findUser(email, password);
        if (user != null) {
            response.addCookie(newCookie());
            user.setSid(newCookie().getValue());
            userService.update(user);
            return Result.success(user);
        } else {
            return Result.failed("email or password is incorrect");
        }

    }

    //登出
    @LoginRequired
    @RequestMapping(value = "/user/logout", method = RequestMethod.PUT)
    public @ResponseBody Result userLogout(@RequestAttribute User user, HttpServletResponse response) {
        if (user != null) {
            return userService.logout(user, response) ? Result.success() : Result.failed("logout fail");
        } else {
            return Result.failed("invalid user");
        }
    }

    private Cookie newCookie() {
        String sid = UUID.randomUUID().toString();
        Cookie newCookie = new Cookie("sid", sid);
        newCookie.setPath("/");
        int cookie_timeout;
        if (StringUtils.isBlank(cookie_timeOut)) {
            cookie_timeout = 31556926;
        } else {
            cookie_timeout = Integer.parseInt(cookie_timeOut);
        }
        newCookie.setMaxAge(cookie_timeout);
        return newCookie;
    }
}
