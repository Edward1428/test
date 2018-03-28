package carSystem.com.utils;

import java.util.ArrayList;
import java.util.List;

public class SqlBuilder {

    protected StringBuilder sqlBuf = new StringBuilder();

    protected List values = new ArrayList();

    public SqlBuilder appendSql(String sql) {
        sqlBuf.append(sql);
        return this;
    }

    public SqlBuilder appendValue(Object value) {
        sqlBuf.append('?');
        values.add(value);
        return this;
    }

    public String getSql() {
        return sqlBuf.toString();
    }

    public Object[] getValues() {
        return values.toArray();
    }
}
