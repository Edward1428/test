package carSystem.com.service.report.baiRong.strategy;

import carSystem.com.bean.report.baiRong.strategy.InfoRelation;
import carSystem.com.dao.report.baiRong.strategy.InfoRelationDAO;
import carSystem.com.service.report.baiRong.BaiRongService;
import com.alibaba.fastjson.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoRelationService extends BaiRongService {

    @Autowired
    private InfoRelationDAO infoRelationDAO;

    public InfoRelation getInfoRelation(JSONObject jsonObject, Integer reportId) {
        InfoRelation infoRelation = new InfoRelation();
        infoRelation.setReportId(reportId);
        Integer flag = analyzeFlag(jsonObject.getString("flag_inforelation"));
        infoRelation.setFlag(flag);
        if (flag == 1) {
            infoRelation.setIr_id_x_cell_cnt(jsonObject.getString("ir_id_x_cell_cnt"));
            infoRelation.setIr_id_x_mail_cnt(jsonObject.getString("ir_id_x_mail_cnt"));
            infoRelation.setIr_cell_x_id_cnt(jsonObject.getString("ir_cell_x_id_cnt"));
            infoRelation.setIr_cell_x_mail_cnt(jsonObject.getString("ir_cell_x_mail_cnt"));
            infoRelation.setIr_allmatch_days(jsonObject.getString("ir_allmatch_days"));
            infoRelation.setIr_id_inlistwith_cell(analyze1(jsonObject.getString("ir_id_inlistwith_cell")));
            infoRelation.setIr_cell_inlistwith_id(analyze1(jsonObject.getString("ir_cell_inlistwith_id")));
            infoRelation.setIr_id_x_cell_notmat_days(analyze2(jsonObject.getString("ir_id_x_cell_notmat_days")));
            infoRelation.setIr_cell_x_id_notmat_days(analyze2(jsonObject.getString("ir_cell_x_id_notmat_days")));
            infoRelation.setIr_id_x_cell_lastchg_days(analyze3(jsonObject.getString("ir_id_x_cell_lastchg_days")));
            infoRelation.setIr_cell_x_id_lastchg_days(analyze3(jsonObject.getString("ir_cell_x_id_lastchg_days")));
            infoRelation.setIr_id_is_reabnormal(analyze4(jsonObject.getString("ir_id_is_reabnormal")));
            infoRelation.setIr_cell_is_reabnormal(analyze4(jsonObject.getString("ir_cell_is_reabnormal")));
            infoRelation.setIr_mail_is_reabnormal(analyze4(jsonObject.getString("ir_mail_is_reabnormal")));


            infoRelation.setIr_m1_id_x_cell_cnt(analyze5(jsonObject.getString	("ir_m1_id_x_cell_cnt")));
            infoRelation.setIr_m1_id_x_mail_cnt(analyze5(jsonObject.getString	("ir_m1_id_x_mail_cnt")));
            infoRelation.setIr_m1_cell_x_id_cnt(analyze5(jsonObject.getString	("ir_m1_cell_x_id_cnt")));
            infoRelation.setIr_m1_cell_x_mail_cnt(analyze5(jsonObject.getString	("ir_m1_cell_x_mail_cnt")));
            infoRelation.setIr_m1_id_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m1_id_x_tel_home_cnt")));
            infoRelation.setIr_m1_id_x_home_addr_cnt(analyze5(jsonObject.getString	("ir_m1_id_x_home_addr_cnt")));
            infoRelation.setIr_m1_id_x_biz_addr_cnt(analyze5(jsonObject.getString	("ir_m1_id_x_biz_addr_cnt")));
            infoRelation.setIr_m1_cell_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m1_cell_x_tel_home_cnt")));
            infoRelation.setIr_m1_cell_x_home_addr_cnt(analyze5(jsonObject.getString	("ir_m1_cell_x_home_addr_cnt")));
            infoRelation.setIr_m1_cell_x_biz_addr_cnt(analyze5(jsonObject.getString	("ir_m1_cell_x_biz_addr_cnt")));
            infoRelation.setIr_m1_linkman_cell_x_id_cnt(analyze5(jsonObject.getString	("ir_m1_linkman_cell_x_id_cnt")));
            infoRelation.setIr_m1_linkman_cell_x_cell_cnt(analyze5(jsonObject.getString	("ir_m1_linkman_cell_x_cell_cnt")));
            infoRelation.setIr_m1_linkman_cell_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m1_linkman_cell_x_tel_home_cnt")));
            infoRelation.setIr_m1_tel_home_x_cell_cnt(analyze5(jsonObject.getString	("ir_m1_tel_home_x_cell_cnt")));
            infoRelation.setIr_m1_tel_home_x_id_cnt(analyze5(jsonObject.getString	("ir_m1_tel_home_x_id_cnt")));
            infoRelation.setIr_m1_home_addr_x_cell_cnt(analyze5(jsonObject.getString	("ir_m1_home_addr_x_cell_cnt")));
            infoRelation.setIr_m1_home_addr_x_id_cnt(analyze5(jsonObject.getString	("ir_m1_home_addr_x_id_cnt")));
            infoRelation.setIr_m1_tel_home_x_home_addr_cnt(analyze5(jsonObject.getString	("ir_m1_tel_home_x_home_addr_cnt")));
            infoRelation.setIr_m1_home_addr_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m1_home_addr_x_tel_home_cnt")));
            infoRelation.setIr_m3_id_x_cell_cnt(analyze5(jsonObject.getString	("ir_m3_id_x_cell_cnt")));
            infoRelation.setIr_m3_id_x_mail_cnt(analyze5(jsonObject.getString	("ir_m3_id_x_mail_cnt")));
            infoRelation.setIr_m3_cell_x_id_cnt(analyze5(jsonObject.getString	("ir_m3_cell_x_id_cnt")));
            infoRelation.setIr_m3_cell_x_mail_cnt(analyze5(jsonObject.getString	("ir_m3_cell_x_mail_cnt")));
            infoRelation.setIr_m3_id_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m3_id_x_tel_home_cnt")));
            infoRelation.setIr_m3_id_x_home_addr_cnt(analyze5(jsonObject.getString	("ir_m3_id_x_home_addr_cnt")));
            infoRelation.setIr_m3_id_x_biz_addr_cnt(analyze5(jsonObject.getString	("ir_m3_id_x_biz_addr_cnt")));
            infoRelation.setIr_m3_cell_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m3_cell_x_tel_home_cnt")));
            infoRelation.setIr_m3_cell_x_home_addr_cnt(analyze5(jsonObject.getString	("ir_m3_cell_x_home_addr_cnt")));
            infoRelation.setIr_m3_cell_x_biz_addr_cnt(analyze5(jsonObject.getString	("ir_m3_cell_x_biz_addr_cnt")));
            infoRelation.setIr_m3_linkman_cell_x_id_cnt(analyze5(jsonObject.getString	("ir_m3_linkman_cell_x_id_cnt")));
            infoRelation.setIr_m3_linkman_cell_x_cell_cnt(analyze5(jsonObject.getString	("ir_m3_linkman_cell_x_cell_cnt")));
            infoRelation.setIr_m3_linkman_cell_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m3_linkman_cell_x_tel_home_cnt")));
            infoRelation.setIr_m3_tel_home_x_cell_cnt(analyze5(jsonObject.getString	("ir_m3_tel_home_x_cell_cnt")));
            infoRelation.setIr_m3_tel_home_x_id_cnt(analyze5(jsonObject.getString	("ir_m3_tel_home_x_id_cnt")));
            infoRelation.setIr_m3_home_addr_x_cell_cnt(analyze5(jsonObject.getString	("ir_m3_home_addr_x_cell_cnt")));
            infoRelation.setIr_m3_home_addr_x_id_cnt(analyze5(jsonObject.getString	("ir_m3_home_addr_x_id_cnt")));
            infoRelation.setIr_m3_tel_home_x_home_addr_cnt(analyze5(jsonObject.getString	("ir_m3_tel_home_x_home_addr_cnt")));
            infoRelation.setIr_m3_home_addr_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m3_home_addr_x_tel_home_cnt")));
            infoRelation.setIr_m6_id_x_cell_cnt(analyze5(jsonObject.getString	("ir_m6_id_x_cell_cnt")));
            infoRelation.setIr_m6_id_x_mail_cnt(analyze5(jsonObject.getString	("ir_m6_id_x_mail_cnt")));
            infoRelation.setIr_m6_cell_x_id_cnt(analyze5(jsonObject.getString	("ir_m6_cell_x_id_cnt")));
            infoRelation.setIr_m6_cell_x_mail_cnt(analyze5(jsonObject.getString	("ir_m6_cell_x_mail_cnt")));
            infoRelation.setIr_m6_id_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m6_id_x_tel_home_cnt")));
            infoRelation.setIr_m6_id_x_home_addr_cnt(analyze5(jsonObject.getString	("ir_m6_id_x_home_addr_cnt")));
            infoRelation.setIr_m6_id_x_biz_addr_cnt(analyze5(jsonObject.getString	("ir_m6_id_x_biz_addr_cnt")));
            infoRelation.setIr_m6_cell_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m6_cell_x_tel_home_cnt")));
            infoRelation.setIr_m6_cell_x_home_addr_cnt(analyze5(jsonObject.getString	("ir_m6_cell_x_home_addr_cnt")));
            infoRelation.setIr_m6_cell_x_biz_addr_cnt(analyze5(jsonObject.getString	("ir_m6_cell_x_biz_addr_cnt")));
            infoRelation.setIr_m6_linkman_cell_x_id_cnt(analyze5(jsonObject.getString	("ir_m6_linkman_cell_x_id_cnt")));
            infoRelation.setIr_m6_linkman_cell_x_cell_cnt(analyze5(jsonObject.getString	("ir_m6_linkman_cell_x_cell_cnt")));
            infoRelation.setIr_m6_linkman_cell_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m6_linkman_cell_x_tel_home_cnt")));
            infoRelation.setIr_m6_tel_home_x_cell_cnt(analyze5(jsonObject.getString	("ir_m6_tel_home_x_cell_cnt")));
            infoRelation.setIr_m6_tel_home_x_id_cnt(analyze5(jsonObject.getString	("ir_m6_tel_home_x_id_cnt")));
            infoRelation.setIr_m6_home_addr_x_cell_cnt(analyze5(jsonObject.getString	("ir_m6_home_addr_x_cell_cnt")));
            infoRelation.setIr_m6_home_addr_x_id_cnt(analyze5(jsonObject.getString	("ir_m6_home_addr_x_id_cnt")));
            infoRelation.setIr_m6_tel_home_x_home_addr_cnt(analyze5(jsonObject.getString	("ir_m6_tel_home_x_home_addr_cnt")));
            infoRelation.setIr_m6_home_addr_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m6_home_addr_x_tel_home_cnt")));
            infoRelation.setIr_m12_id_x_cell_cnt(analyze5(jsonObject.getString	("ir_m12_id_x_cell_cnt")));
            infoRelation.setIr_m12_id_x_mail_cnt(analyze5(jsonObject.getString	("ir_m12_id_x_mail_cnt")));
            infoRelation.setIr_m12_cell_x_id_cnt(analyze5(jsonObject.getString	("ir_m12_cell_x_id_cnt")));
            infoRelation.setIr_m12_cell_x_mail_cnt(analyze5(jsonObject.getString	("ir_m12_cell_x_mail_cnt")));
            infoRelation.setIr_m12_id_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m12_id_x_tel_home_cnt")));
            infoRelation.setIr_m12_id_x_home_addr_cnt(analyze5(jsonObject.getString	("ir_m12_id_x_home_addr_cnt")));
            infoRelation.setIr_m12_id_x_biz_addr_cnt(analyze5(jsonObject.getString	("ir_m12_id_x_biz_addr_cnt")));
            infoRelation.setIr_m12_cell_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m12_cell_x_tel_home_cnt")));
            infoRelation.setIr_m12_cell_x_home_addr_cnt(analyze5(jsonObject.getString	("ir_m12_cell_x_home_addr_cnt")));
            infoRelation.setIr_m12_cell_x_biz_addr_cnt(analyze5(jsonObject.getString	("ir_m12_cell_x_biz_addr_cnt")));
            infoRelation.setIr_m12_linkman_cell_x_id_cnt(analyze5(jsonObject.getString	("ir_m12_linkman_cell_x_id_cnt")));
            infoRelation.setIr_m12_linkman_cell_x_cell_cnt(analyze5(jsonObject.getString	("ir_m12_linkman_cell_x_cell_cnt")));
            infoRelation.setIr_m12_linkman_cell_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m12_linkman_cell_x_tel_home_cnt")));
            infoRelation.setIr_m12_tel_home_x_cell_cnt(analyze5(jsonObject.getString	("ir_m12_tel_home_x_cell_cnt")));
            infoRelation.setIr_m12_tel_home_x_id_cnt(analyze5(jsonObject.getString	("ir_m12_tel_home_x_id_cnt")));
            infoRelation.setIr_m12_home_addr_x_cell_cnt(analyze5(jsonObject.getString	("ir_m12_home_addr_x_cell_cnt")));
            infoRelation.setIr_m12_home_addr_x_id_cnt(analyze5(jsonObject.getString	("ir_m12_home_addr_x_id_cnt")));
            infoRelation.setIr_m12_tel_home_x_home_addr_cnt(analyze5(jsonObject.getString	("ir_m12_tel_home_x_home_addr_cnt")));
            infoRelation.setIr_m12_home_addr_x_tel_home_cnt(analyze5(jsonObject.getString	("ir_m12_home_addr_x_tel_home_cnt")));

        }

        return infoRelation;
    }

    public Integer insert(@NotNull InfoRelation infoRelation) {
        return infoRelationDAO.insert(infoRelation);
    }

    public InfoRelation findByReportId(Integer reportId) {
        return infoRelationDAO.findByReportId(reportId);
    }

    private String analyze1(String value) {
        String s = null;
        if (value != null) {
            switch (value) {
                case "1": s="是";
                    break;
                case "0": s="否";
                    break;
                case "": s="没有以往关联";
            }
        }
        return s;
    }

    private String analyze2(String value) {
        String s = null;
        if (value != null) {
            switch (value) {
                case "": s= "没有出现不一致";
                break;
                default: s = value;
            }
        }
        return s;
    }

    private String analyze3(String value) {
        String s = null;
        if (value != null) {
            switch (value) {
                case "": s= "从未变动过";
                    break;
                default: s = value;
            }
        }
        return s;
    }

    private String analyze4(String value) {
        String s = null;
        if (value != null) {
            switch (value) {
                case "1": s="是";
                    break;
                case "0": s="否";
            }
        }
        return s;
    }

    private String analyze5(String value) {
        String s = null;
        if (value != null) {
            switch (value) {
                case "": s="未能查到之前有关联";
                    break;
                default: s = value;
            }
        }
        return s;
    }


}
