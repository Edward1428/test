package carSystem.com.bean.report.ali;

import carSystem.com.bean.report.jd.BlackName;
import carSystem.com.bean.report.jd.BlackNameList;

import java.util.List;

public class AliApi {
    private BankCard bankCard;
    private Cell cell;
    private IdCard idCard;
    private CellCheck cellCheck;


    public BankCard getBankCard() {
        return bankCard;
    }

    public void setBankCard(BankCard bankCard) {
        this.bankCard = bankCard;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public IdCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCard idCard) {
        this.idCard = idCard;
    }

    public CellCheck getCellCheck() {
        return cellCheck;
    }

    public void setCellCheck(CellCheck cellCheck) {
        this.cellCheck = cellCheck;
    }
}
