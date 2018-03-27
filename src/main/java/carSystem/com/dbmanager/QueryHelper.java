package carSystem.com.dbmanager;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zhanzhenchao on 16/3/30.
 */
@Repository
public class QueryHelper {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public <T> T query(Class<T> type, String sql, Object... params) {
        try {
            return jdbcTemplate.queryForObject(sql + " limit 1 ", params,
                    new BeanPropertyRowMapper<T>(type));
        } catch (DataAccessException e) {
            logger.info(e.toString());
            e.printStackTrace();
            return null;
        }
    }

    public long stateLong(String sql, Object... params) {
        List<Long> longList = jdbcTemplate.query(sql, params, new RowMapper<Long>() {
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getLong(1);
            }
        });
        if ( longList.isEmpty() ){
            return 0;
        }else if ( longList.size() == 1 ) { // list contains exactly 1 element
            return longList.get(0);
        }else{  // list contains more than 1 elements
            logger.error("sql result: more then 1 elements!!");
            return longList.get(0);
        }
    }

    @Transactional(readOnly = true)
    public <T> List<T> queryAll(Class<T> type, String sql, Object... params) {
        try {
            return jdbcTemplate.query(sql, params,
                    new BeanPropertyRowMapper<T>(type));
        } catch (DataAccessException e) {
            logger.info(e.toString());
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = true)
    public <T> List<T> querySlice(Class<T> beanClass, String sql, int page, int count, Object... params) {
        if (page < 0 || count < 0)
            throw new IllegalArgumentException("Illegal parameter of 'page' or 'count', Must be positive.");
        int from = (page - 1) * count;
        count = (count > 0) ? count : Integer.MAX_VALUE;
        return queryAll(beanClass, sql + " LIMIT ?,?", ArrayUtils.addAll(params, new Integer[]{from, count}));
    }

//    /**
//     * 支持缓存的分页查询
//     *
//     * @param <T>
//     * @param beanClass
//     * @param cache
//     * @param cache_key
//     * @param cache_obj_count
//     * @param sql
//     * @param page
//     * @param count
//     * @param params
//     * @return
//     */
//    public static <T> List<T> querySliceCache(Class<T> beanClass,
//                                              String cache, Serializable cache_key, int cache_obj_count,
//                                              String sql, int page, int count, Object... params) {
//        List<T> objs = (List<T>) CacheManager.get(cache, cache_key);
//        if (objs == null) {
//            objs = querySlice(beanClass, sql, 1, cache_obj_count, params);
//            CacheManager.set(cache, cache_key, (Serializable) objs);
//        }
//        if (objs == null || objs.size() == 0)
//            return objs;
//        int from = (page - 1) * count;
//        if (from < 0)
//            return null;
//        if ((from + count) > cache_obj_count) {// 超出缓存的范围
//            return cacheQuerySlice("1d", cache + ':' + cache_key, beanClass, sql, page, count, params);
//        }
//        int end = Math.min(from + count, objs.size());
//        if (from >= end)
//            return null;
//        return objs.subList(from, end);
//    }
//
//    private static <T> List<T> cacheQuerySlice(String cache, Serializable cache_key, Class<T> cls, String sql, int page,
//                                               int count, Object... params) {
//        String key = cache_key + "#slice#" + page + "#" + count;
//        List<T> objs = (List<T>) CacheManager.get(cache, key);
//        if (objs == null) {
//            objs = querySlice(cls, sql, page, count, params);
//            CacheManager.set(cache, key, (Serializable) objs);
//        }
//        return objs;
//
//    }
//
//    /**
//     * 执行统计查询语句，语句的执行结果必须只返回一个数值
//     *
//     * @param sql
//     * @param params
//     * @return
//     */
//    public long stat(String sql, Object... params) {
//        Connection conn = getConnection();
//        try {
//            Number num = (Number) _g_runner.query(conn, sql, _g_scaleHandler, params);
//            return (num != null) ? num.longValue() : -1;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return -1;
//        } finally {
//            dbManager.closeConnection(conn);
//        }
//    }
//
//    /**
//     * 执行统计查询语句，语句的执行结果必须只返回一个数值
//     *
//     * @param cache_region
//     * @param key
//     * @param sql
//     * @param params
//     * @return
//     */
//    public static long statCache(String cache_region, Serializable key, String sql, Object... params) {
//        Number value = (Number) CacheManager.get(cache_region, key);
//        if (value == null) {
//            value = stat(sql, params);
//            CacheManager.set(cache_region, key, value);
//        }
//        return value.longValue();
//    }
//
//    /**
//     * 执行INSERT/UPDATE/DELETE语句
//     *
//     * @param sql
//     * @param params
//     * @return
//     */
//    public int update(String sql, Object... params) {
//        Connection conn = getConnection();
//        try {
//            return _g_runner.update(conn, sql, params);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return -1;
//        } finally {
//            dbManager.closeConnection(conn);
//        }
//    }
//
//    /**
//     * 批量执行指定的SQL语句
//     *
//     * @param sql
//     * @param params
//     * @return
//     */
//    public int[] batch(String sql, Object[][] params) {
//        try {
//            Connection conn = getConnection();
//            boolean automit = conn.getAutoCommit();
//            try {
//                conn.setAutoCommit(false);
//                int[] m = _g_runner.batch(conn, sql, params);
//                conn.commit();
//                return m;
//            } catch (SQLException e) {
//                conn.rollback();
//                e.printStackTrace();
//                return null;
//            } finally {
//                conn.setAutoCommit(automit);
//                dbManager.closeConnection(conn);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    public ResultSet sqlQuery(String sql, Object...params) {
//        Connection connection = null;
//        try {
//            connection = dbManager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(sql);
//            if (params != null) {
//                for (int i = 0; i < params.length; i++) {
//                    statement.setObject(i + 1, params[i]);
//                }
//            }
//            logger.info("SQL: " + sql + "has bean executed");
//            return statement.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            if (connection != null) {
//                dbManager.closeConnection(connection);
//            }
//        }
//    }
//
//    public int sqlUpdate(String sql, Object...params) {
//        Connection connection = null;
//        try {
//            connection = dbManager.getConnection();
//            PreparedStatement statement = connection.prepareStatement(sql);
//            if (params != null) {
//                for (int i = 0; i < params.length; i++) {
//                    statement.setObject(i + 1, params[i]);
//                }
//            }
////            logger.info("SQL: " + sql + "has bean executed");
//            return statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return -1;
//        } finally {
//            if (connection != null) {
//                dbManager.closeConnection(connection);
//            }
//        }
//    }

}
