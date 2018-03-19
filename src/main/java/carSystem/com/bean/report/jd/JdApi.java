package carSystem.com.bean.report.jd;

import java.util.List;

public class JdApi {
    private BadRecord badRecord;
    private CellLong cellLong;
    private BlackName blackName;
    private Carshield carshield;
    private List<BlackNameList> blackNameLists;

    public BadRecord getBadRecord() {
        return badRecord;
    }

    public void setBadRecord(BadRecord badRecord) {
        this.badRecord = badRecord;
    }

    public CellLong getCellLong() {
        return cellLong;
    }

    public void setCellLong(CellLong cellLong) {
        this.cellLong = cellLong;
    }

    public BlackName getBlackName() {
        return blackName;
    }

    public void setBlackName(BlackName blackName) {
        this.blackName = blackName;
    }

    public Carshield getCarshield() {
        return carshield;
    }

    public void setCarshield(Carshield carshield) {
        this.carshield = carshield;
    }

    public List<BlackNameList> getBlackNameLists() {
        return blackNameLists;
    }

    public void setBlackNameLists(List<BlackNameList> blackNameLists) {
        this.blackNameLists = blackNameLists;
    }
}
