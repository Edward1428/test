package carSystem.com.vo;

import java.security.PublicKey;
import java.sql.Timestamp;

public class ReportDayCount {
    private Timestamp days;
    private long count;
    private long sum;

    public Timestamp getDays() {
        return days;
    }

    public void setDays(Timestamp days) {
        this.days = days;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }
}
