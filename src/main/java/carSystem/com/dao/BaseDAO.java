package carSystem.com.dao;

import carSystem.com.bean.BaseBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by rico on 2015/9/8.
 */
public class BaseDAO<T extends BaseBean> {

    public static final Log log = LogFactory.getLog(BaseDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Class<T> type;
    private String tableName;

    @SuppressWarnings("unchecked")
    public BaseDAO() {
        this.type = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.tableName = type.getSimpleName().toLowerCase();
    }

    public Integer count() {
        String sql = sql("SELECT COUNT(1) FROM ", tableName);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Integer count(String where, Object... params) {
        String sql = sql("SELECT COUNT(1) FROM ", tableName, " WHERE ", where);
        return jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public Integer insert(T entity) {
        if (entity == null || type != entity.getClass()) {
            log.error("Invalid entity type:" + entity.getClass());
            throw new RuntimeException("Invalid entity type:" + entity.getClass());
        }
        normalizeEntity(entity);
        SqlParameter param = new SqlParameter(entity);
        String sql = sql("INSERT INTO ", tableName, " (", param.getSqlFields(), ") VALUES (",
                param.getSqlPlaceholders(), ")");
        String[] fields = param.getFields();
        Object[] values = param.getValues();

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < fields.length; i++) {
                    ps.setObject(i + 1, values[i]);
                }
                return ps;
            }
        }, holder);

        int id = holder.getKey().intValue();
        entity.setId(id);
        return id;
    }

    public void batchInsert(List<T> entities) {
        if (entities != null && entities.size() > 0) {
            SqlParameter param = new SqlParameter(entities.get(0));
            String sql = sql("INSERT INTO ", tableName, " (",
                    param.getSqlFields(), ") VALUES (", param.getSqlPlaceholders(), ")");
            List<Object[]> objects = new ArrayList<Object[]>();
            for (T entity : entities) {
                normalizeEntity(entity);
                param = new SqlParameter(entity);
                objects.add(param.getValues());
            }
            jdbcTemplate.batchUpdate(sql, objects);
        } else {
            log.error("Invalid entitys insert");
        }
    }

    private Integer doUpdate(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }

    public boolean update(T entity, String... selectedFields) {
        if (entity == null || type != entity.getClass()) {
            log.error("Invalid entity type:" + entity.getClass());
            return false;
        }
        SqlParameter param = new SqlParameter(entity);
        String id = entity.getId().toString();
        String sql = sql("UPDATE ", tableName, " SET ", param.getSqlSetUpdate(selectedFields), " WHERE id = ", id);
        return doUpdate(sql, param.getValues(selectedFields)) != 0;
    }

    public boolean delete(T entity) {
        if (entity == null || type != entity.getClass()) {
            log.error("Invalid entity type:" + entity.getClass());
            return false;
        }
        T bean = findById(entity.getId());
        if (bean == null) {
            log.error("Delete Operation Error: Cannot found[" + entity.getClass() + "], where id is: " + entity.getId());
            return false;
        }

        try {
            final String sql = sql("delete from ", tableName, " where id=?");
            jdbcTemplate.update(sql, new Object[]{entity.getId()}, new int[]{Types.INTEGER});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAll(String where, Object... params) {
        try {
            final String sql = sql("DELETE FROM ", tableName, " WHERE ", where);
            jdbcTemplate.update(sql, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional(readOnly = true)
    public T findById(int id) {
        try {
            return jdbcTemplate.queryForObject(sql("SELECT * FROM ", tableName, " WHERE id = ?"),
                    new Object[]{id}, new BeanPropertyRowMapper<T>(type));
        } catch (DataAccessException e) {
            log.info(e.toString());
            return null;
        }
    }

    @Transactional(readOnly = true)
    public T find(String where, Object... params) {
        try {
            return jdbcTemplate.queryForObject(sql("SELECT * FROM ", tableName, " WHERE ", where, " LIMIT 1"), params,
                    new BeanPropertyRowMapper<T>(type));
        } catch (DataAccessException e) {
            log.info(e.toString());
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        try {
            return jdbcTemplate.query(sql("SELECT * FROM ", tableName), new BeanPropertyRowMapper<T>(type));
        } catch (DataAccessException e) {
            log.info(e.toString());
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<T> findAll(String where, Object... params) {
        try {
            return jdbcTemplate.query(sql("SELECT * FROM ", tableName, " WHERE ", where), params,
                    new BeanPropertyRowMapper<T>(type));
        } catch (DataAccessException e) {
            log.info(e.toString());
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<T> findPage(String where, Integer page, Integer pageSize, Object... params) {
        if (page < 0 || pageSize < 0) {
            throw new IllegalArgumentException("Illegal parameter of 'page' or 'pageSize', Must be positive.");
        }
        Integer offset = (page - 1) * pageSize;
        params = ArrayUtils.addAll(params, new Integer[]{offset, pageSize});
        where += " LIMIT ?,?";
        return findAll(where, params);
    }

    /*
    标准化Entity，属性为null的值都转化为对应类型的初始化。
     */
    protected void normalizeEntity(T entity) {
        try {
            Map entityMap = BeanUtils.describe(entity);
            for (Object entityKey : entityMap.keySet()) {
                if (!"id".equals(entityKey.toString())) {
                    if (null == BeanUtils.getProperty(entity, entityKey.toString())) {
                        ConvertUtils.register(new SqlTimestampConverter(null), Timestamp.class);
                        BeanUtils.setProperty(entity, entityKey.toString(), "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String sql(String... parts) {
        return join("", parts);
    }

    protected String join(String separator, String... parts) {
        return join(separator, Arrays.asList(parts));
    }

    protected String join(String separator, List<?> items) {
        StringBuilder result = new StringBuilder();
        for (Object item : items) {
            result.append(item);
            result.append(separator);
        }
        int length = result.length() - separator.length();
        return result.substring(0, length);
    }

    protected Map<String, String> listEntityAttributes(T entity) {
        try {
            Map<String, String> fields = BeanUtils.describe(entity);
            fields.remove("id");
            fields.remove("class");
            return fields;
        } catch (Exception e) {
            throw new RuntimeException("Exception when Parasing fields of " + entity);
        }
    }

    /*
    简单来说，这个内部类实现的是：分析实体对象的成员属性，解析出有效的字符串域sql, 并且进行插入操作
     */
    protected class SqlParameter {

        private Map<String, String> entityMap;
        private String[] fields;
        private String[] placeholders;
        private Object[] values;

        public SqlParameter(T entity) {
            setEntityMap(listEntityAttributes(entity));
        }

        protected void setEntityMap(Map<String, String> entityMap) {
            this.entityMap = entityMap;
            prepare();
        }

        protected void prepare() {
            fields = entityMap.keySet().toArray(new String[entityMap.size()]);
            placeholders = new String[entityMap.size()];
            Arrays.fill(placeholders, "?");
            values = entityMap.values().toArray();
        }

        public String getSqlFields() {
            return join(", ", fields);
        }

        public String getSqlPlaceholders() {
            return join(", ", placeholders);
        }

        public String[] getFields() {
            return fields;
        }

        public Object[] getValues(String... selectdFields) {
            if (null == selectdFields || 0 == selectdFields.length) {
                selectdFields = getFields();
            }
            Object[] result = new Object[selectdFields.length];
            for (int i = 0; i < selectdFields.length; i++) {
                for (int j = 0; j < fields.length; j++) {
                    if (fields[j].equals(selectdFields[i])) {
                        result[i] = values[j];
                        break;
                    }
                }
            }
            return result;
        }

        public String getSqlSetUpdate(String... selectFields) {
            if (selectFields == null || selectFields.length == 0) {
                selectFields = getFields();
            }
            List result = new ArrayList<String>();
            for (String field : selectFields) {
                if (ArrayUtils.contains(fields, field)) {
                    result.add(field + " = ?");
                }
            }
            return join(", ", result);
        }

    }

}
