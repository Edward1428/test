package carSystem.com.service.report.baiRong.strategy;

import carSystem.com.bean.report.baiRong.strategy.SpecialList;
import carSystem.com.dao.report.baiRong.strategy.SpecialListDAO;
import carSystem.com.service.report.baiRong.BaiRongService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialListService extends BaiRongService{

    @Autowired
    private SpecialListDAO specialListDAO;

    public SpecialList getSpecialList(JSONObject jsonObject, Integer reportId) {
        SpecialList specialList = new SpecialList();
        specialList.setReportId(reportId);
        Integer flag = analyzeFlag(jsonObject.getString("flag_specialList_c"));
        specialList.setFlag(flag);
        if (flag == 1) {
            specialList.setSl_id_abnormal	(analyze(jsonObject.getString("sl_id_abnormal")));
            specialList.setSl_id_phone_overdue	(analyze(jsonObject.getString("sl_id_phone_overdue")));
            specialList.setSl_id_court_bad	(analyze(jsonObject.getString("sl_id_court_bad")));
            specialList.setSl_id_court_executed	(analyze(jsonObject.getString("sl_id_court_executed")));
            specialList.setSl_id_bank_bad	(analyze(jsonObject.getString("sl_id_bank_bad")));
            specialList.setSl_id_bank_overdue	(analyze(jsonObject.getString("sl_id_bank_overdue")));
            specialList.setSl_id_bank_fraud	(analyze(jsonObject.getString("sl_id_bank_fraud")));
            specialList.setSl_id_bank_lost	(analyze(jsonObject.getString("sl_id_bank_lost")));
            specialList.setSl_id_bank_refuse	(analyze(jsonObject.getString("sl_id_bank_refuse")));
            specialList.setSl_id_p2p_bad	(analyze(jsonObject.getString("sl_id_p2p_bad")));
            specialList.setSl_id_p2p_overdue	(analyze(jsonObject.getString("sl_id_p2p_overdue")));
            specialList.setSl_id_p2p_fraud	(analyze(jsonObject.getString("sl_id_p2p_fraud")));
            specialList.setSl_id_p2p_lost	(analyze(jsonObject.getString("sl_id_p2p_lost")));
            specialList.setSl_id_p2p_refuse	(analyze(jsonObject.getString("sl_id_p2p_refuse")));
            specialList.setSl_id_nbank_p2p_bad	(analyze(jsonObject.getString("sl_id_nbank_p2p_bad")));
            specialList.setSl_id_nbank_p2p_overdue	(analyze(jsonObject.getString("sl_id_nbank_p2p_overdue")));
            specialList.setSl_id_nbank_p2p_fraud	(analyze(jsonObject.getString("sl_id_nbank_p2p_fraud")));
            specialList.setSl_id_nbank_p2p_lost	(analyze(jsonObject.getString("sl_id_nbank_p2p_lost")));
            specialList.setSl_id_nbank_p2p_refuse	(analyze(jsonObject.getString("sl_id_nbank_p2p_refuse")));
            specialList.setSl_id_nbank_mc_bad	(analyze(jsonObject.getString("sl_id_nbank_mc_bad")));
            specialList.setSl_id_nbank_mc_overdue	(analyze(jsonObject.getString("sl_id_nbank_mc_overdue")));
            specialList.setSl_id_nbank_mc_fraud	(analyze(jsonObject.getString("sl_id_nbank_mc_fraud")));
            specialList.setSl_id_nbank_mc_lost	(analyze(jsonObject.getString("sl_id_nbank_mc_lost")));
            specialList.setSl_id_nbank_mc_refuse	(analyze(jsonObject.getString("sl_id_nbank_mc_refuse")));
            specialList.setSl_id_nbank_ca_bad	(analyze(jsonObject.getString("sl_id_nbank_ca_bad")));
            specialList.setSl_id_nbank_ca_overdue	(analyze(jsonObject.getString("sl_id_nbank_ca_overdue")));
            specialList.setSl_id_nbank_ca_fraud	(analyze(jsonObject.getString("sl_id_nbank_ca_fraud")));
            specialList.setSl_id_nbank_ca_lost	(analyze(jsonObject.getString("sl_id_nbank_ca_lost")));
            specialList.setSl_id_nbank_ca_refuse	(analyze(jsonObject.getString("sl_id_nbank_ca_refuse")));
            specialList.setSl_id_nbank_com_bad	(analyze(jsonObject.getString("sl_id_nbank_com_bad")));
            specialList.setSl_id_nbank_com_overdue	(analyze(jsonObject.getString("sl_id_nbank_com_overdue")));
            specialList.setSl_id_nbank_com_fraud	(analyze(jsonObject.getString("sl_id_nbank_com_fraud")));
            specialList.setSl_id_nbank_com_lost	(analyze(jsonObject.getString("sl_id_nbank_com_lost")));
            specialList.setSl_id_nbank_com_refuse	(analyze(jsonObject.getString("sl_id_nbank_com_refuse")));
            specialList.setSl_id_nbank_cf_bad	(analyze(jsonObject.getString("sl_id_nbank_cf_bad")));
            specialList.setSl_id_nbank_cf_overdue	(analyze(jsonObject.getString("sl_id_nbank_cf_overdue")));
            specialList.setSl_id_nbank_cf_fraud	(analyze(jsonObject.getString("sl_id_nbank_cf_fraud")));
            specialList.setSl_id_nbank_cf_lost	(analyze(jsonObject.getString("sl_id_nbank_cf_lost")));
            specialList.setSl_id_nbank_cf_refuse	(analyze(jsonObject.getString("sl_id_nbank_cf_refuse")));
            specialList.setSl_id_nbank_other_bad	(analyze(jsonObject.getString("sl_id_nbank_other_bad")));
            specialList.setSl_id_nbank_other_overdue	(analyze(jsonObject.getString("sl_id_nbank_other_overdue")));
            specialList.setSl_id_nbank_other_fraud	(analyze(jsonObject.getString("sl_id_nbank_other_fraud")));
            specialList.setSl_id_nbank_other_lost	(analyze(jsonObject.getString("sl_id_nbank_other_lost")));
            specialList.setSl_id_nbank_other_refuse	(analyze(jsonObject.getString("sl_id_nbank_other_refuse")));
            specialList.setSl_cell_abnormal	(analyze(jsonObject.getString("sl_cell_abnormal")));
            specialList.setSl_cell_phone_overdue	(analyze(jsonObject.getString("sl_cell_phone_overdue")));
            specialList.setSl_cell_bank_bad	(analyze(jsonObject.getString("sl_cell_bank_bad")));
            specialList.setSl_cell_bank_overdue	(analyze(jsonObject.getString("sl_cell_bank_overdue")));
            specialList.setSl_cell_bank_fraud	(analyze(jsonObject.getString("sl_cell_bank_fraud")));
            specialList.setSl_cell_bank_lost	(analyze(jsonObject.getString("sl_cell_bank_lost")));
            specialList.setSl_cell_bank_refuse	(analyze(jsonObject.getString("sl_cell_bank_refuse")));
            specialList.setSl_cell_p2p_bad	(analyze(jsonObject.getString("sl_cell_p2p_bad")));
            specialList.setSl_cell_p2p_overdue	(analyze(jsonObject.getString("sl_cell_p2p_overdue")));
            specialList.setSl_cell_p2p_fraud	(analyze(jsonObject.getString("sl_cell_p2p_fraud")));
            specialList.setSl_cell_p2p_lost	(analyze(jsonObject.getString("sl_cell_p2p_lost")));
            specialList.setSl_cell_p2p_refuse	(analyze(jsonObject.getString("sl_cell_p2p_refuse")));
            specialList.setSl_cell_nbank_p2p_bad	(analyze(jsonObject.getString("sl_cell_nbank_p2p_bad")));
            specialList.setSl_cell_nbank_p2p_overdue	(analyze(jsonObject.getString("sl_cell_nbank_p2p_overdue")));
            specialList.setSl_cell_nbank_p2p_fraud	(analyze(jsonObject.getString("sl_cell_nbank_p2p_fraud")));
            specialList.setSl_cell_nbank_p2p_lost	(analyze(jsonObject.getString("sl_cell_nbank_p2p_lost")));
            specialList.setSl_cell_nbank_p2p_refuse	(analyze(jsonObject.getString("sl_cell_nbank_p2p_refuse")));
            specialList.setSl_cell_nbank_mc_bad	(analyze(jsonObject.getString("sl_cell_nbank_mc_bad")));
            specialList.setSl_cell_nbank_mc_overdue	(analyze(jsonObject.getString("sl_cell_nbank_mc_overdue")));
            specialList.setSl_cell_nbank_mc_fraud	(analyze(jsonObject.getString("sl_cell_nbank_mc_fraud")));
            specialList.setSl_cell_nbank_mc_lost	(analyze(jsonObject.getString("sl_cell_nbank_mc_lost")));
            specialList.setSl_cell_nbank_mc_refuse	(analyze(jsonObject.getString("sl_cell_nbank_mc_refuse")));
            specialList.setSl_cell_nbank_ca_bad	(analyze(jsonObject.getString("sl_cell_nbank_ca_bad")));
            specialList.setSl_cell_nbank_ca_overdue	(analyze(jsonObject.getString("sl_cell_nbank_ca_overdue")));
            specialList.setSl_cell_nbank_ca_fraud	(analyze(jsonObject.getString("sl_cell_nbank_ca_fraud")));
            specialList.setSl_cell_nbank_ca_lost	(analyze(jsonObject.getString("sl_cell_nbank_ca_lost")));
            specialList.setSl_cell_nbank_ca_refuse	(analyze(jsonObject.getString("sl_cell_nbank_ca_refuse")));
            specialList.setSl_cell_nbank_com_bad	(analyze(jsonObject.getString("sl_cell_nbank_com_bad")));
            specialList.setSl_cell_nbank_com_overdue	(analyze(jsonObject.getString("sl_cell_nbank_com_overdue")));
            specialList.setSl_cell_nbank_com_fraud	(analyze(jsonObject.getString("sl_cell_nbank_com_fraud")));
            specialList.setSl_cell_nbank_com_lost	(analyze(jsonObject.getString("sl_cell_nbank_com_lost")));
            specialList.setSl_cell_nbank_com_refuse	(analyze(jsonObject.getString("sl_cell_nbank_com_refuse")));
            specialList.setSl_cell_nbank_cf_bad	(analyze(jsonObject.getString("sl_cell_nbank_cf_bad")));
            specialList.setSl_cell_nbank_cf_overdue	(analyze(jsonObject.getString("sl_cell_nbank_cf_overdue")));
            specialList.setSl_cell_nbank_cf_fraud	(analyze(jsonObject.getString("sl_cell_nbank_cf_fraud")));
            specialList.setSl_cell_nbank_cf_lost	(analyze(jsonObject.getString("sl_cell_nbank_cf_lost")));
            specialList.setSl_cell_nbank_cf_refuse	(analyze(jsonObject.getString("sl_cell_nbank_cf_refuse")));
            specialList.setSl_cell_nbank_other_bad	(analyze(jsonObject.getString("sl_cell_nbank_other_bad")));
            specialList.setSl_cell_nbank_other_overdue	(analyze(jsonObject.getString("sl_cell_nbank_other_overdue")));
            specialList.setSl_cell_nbank_other_fraud	(analyze(jsonObject.getString("sl_cell_nbank_other_fraud")));
            specialList.setSl_cell_nbank_other_lost	(analyze(jsonObject.getString("sl_cell_nbank_other_lost")));
            specialList.setSl_cell_nbank_other_refuse	(analyze(jsonObject.getString("sl_cell_nbank_other_refuse")));
            specialList.setSl_lm_cell_abnormal	(analyze(jsonObject.getString("sl_lm_cell_abnormal")));
            specialList.setSl_lm_cell_phone_overdue	(analyze(jsonObject.getString("sl_lm_cell_phone_overdue")));
            specialList.setSl_lm_cell_bank_bad	(analyze(jsonObject.getString("sl_lm_cell_bank_bad")));
            specialList.setSl_lm_cell_bank_overdue	(analyze(jsonObject.getString("sl_lm_cell_bank_overdue")));
            specialList.setSl_lm_cell_bank_fraud	(analyze(jsonObject.getString("sl_lm_cell_bank_fraud")));
            specialList.setSl_lm_cell_bank_lost	(analyze(jsonObject.getString("sl_lm_cell_bank_lost")));
            specialList.setSl_lm_cell_bank_refuse	(analyze(jsonObject.getString("sl_lm_cell_bank_refuse")));
            specialList.setSl_lm_cell_nbank_p2p_bad	(analyze(jsonObject.getString("sl_lm_cell_nbank_p2p_bad")));
            specialList.setSl_lm_cell_nbank_p2p_overdue	(analyze(jsonObject.getString("sl_lm_cell_nbank_p2p_overdue")));
            specialList.setSl_lm_cell_nbank_p2p_fraud	(analyze(jsonObject.getString("sl_lm_cell_nbank_p2p_fraud")));
            specialList.setSl_lm_cell_nbank_p2p_lost	(analyze(jsonObject.getString("sl_lm_cell_nbank_p2p_lost")));
            specialList.setSl_lm_cell_nbank_p2p_refuse	(analyze(jsonObject.getString("sl_lm_cell_nbank_p2p_refuse")));
            specialList.setSl_lm_cell_nbank_mc_bad	(analyze(jsonObject.getString("sl_lm_cell_nbank_mc_bad")));
            specialList.setSl_lm_cell_nbank_mc_overdue	(analyze(jsonObject.getString("sl_lm_cell_nbank_mc_overdue")));
            specialList.setSl_lm_cell_nbank_mc_fraud	(analyze(jsonObject.getString("sl_lm_cell_nbank_mc_fraud")));
            specialList.setSl_lm_cell_nbank_mc_lost	(analyze(jsonObject.getString("sl_lm_cell_nbank_mc_lost")));
            specialList.setSl_lm_cell_nbank_mc_refuse	(analyze(jsonObject.getString("sl_lm_cell_nbank_mc_refuse")));
            specialList.setSl_lm_cell_nbank_ca_bad	(analyze(jsonObject.getString("sl_lm_cell_nbank_ca_bad")));
            specialList.setSl_lm_cell_nbank_ca_overdue	(analyze(jsonObject.getString("sl_lm_cell_nbank_ca_overdue")));
            specialList.setSl_lm_cell_nbank_ca_fraud	(analyze(jsonObject.getString("sl_lm_cell_nbank_ca_fraud")));
            specialList.setSl_lm_cell_nbank_ca_lost	(analyze(jsonObject.getString("sl_lm_cell_nbank_ca_lost")));
            specialList.setSl_lm_cell_nbank_ca_refuse	(analyze(jsonObject.getString("sl_lm_cell_nbank_ca_refuse")));
            specialList.setSl_lm_cell_nbank_com_bad	(analyze(jsonObject.getString("sl_lm_cell_nbank_com_bad")));
            specialList.setSl_lm_cell_nbank_com_overdue	(analyze(jsonObject.getString("sl_lm_cell_nbank_com_overdue")));
            specialList.setSl_lm_cell_nbank_com_fraud	(analyze(jsonObject.getString("sl_lm_cell_nbank_com_fraud")));
            specialList.setSl_lm_cell_nbank_com_lost	(analyze(jsonObject.getString("sl_lm_cell_nbank_com_lost")));
            specialList.setSl_lm_cell_nbank_com_refuse	(analyze(jsonObject.getString("sl_lm_cell_nbank_com_refuse")));
            specialList.setSl_lm_cell_nbank_cf_bad	(analyze(jsonObject.getString("sl_lm_cell_nbank_cf_bad")));
            specialList.setSl_lm_cell_nbank_cf_overdue	(analyze(jsonObject.getString("sl_lm_cell_nbank_cf_overdue")));
            specialList.setSl_lm_cell_nbank_cf_fraud	(analyze(jsonObject.getString("sl_lm_cell_nbank_cf_fraud")));
            specialList.setSl_lm_cell_nbank_cf_lost	(analyze(jsonObject.getString("sl_lm_cell_nbank_cf_lost")));
            specialList.setSl_lm_cell_nbank_cf_refuse	(analyze(jsonObject.getString("sl_lm_cell_nbank_cf_refuse")));
            specialList.setSl_lm_cell_nbank_other_bad	(analyze(jsonObject.getString("sl_lm_cell_nbank_other_bad")));
            specialList.setSl_lm_cell_nbank_other_overdue	(analyze(jsonObject.getString("sl_lm_cell_nbank_other_overdue")));
            specialList.setSl_lm_cell_nbank_other_fraud	(analyze(jsonObject.getString("sl_lm_cell_nbank_other_fraud")));
            specialList.setSl_lm_cell_nbank_other_lost	(analyze(jsonObject.getString("sl_lm_cell_nbank_other_lost")));
            specialList.setSl_lm_cell_nbank_other_refuse	(analyze(jsonObject.getString("sl_lm_cell_nbank_other_refuse")));
        }
        return specialList;
    }

    public Integer insert(@NotNull SpecialList specialList) {
        return specialListDAO.insert(specialList);
    }

    public SpecialList findByReportId(@NotNull Integer reportId) {
        return specialListDAO.findByReportId(reportId);
    }

    private String analyze(String value) {
        String s = null;
        if (value != null) {
            switch (value) {
                case "": s = "未命中";
                    break;
                case "0" : s = "本人直接命中";
                    break;
                case "1" : s = "一度关系命中";
                    break;
                case "2" : s = "二度关系命中";
            }
        }
        return s;
    }
}
