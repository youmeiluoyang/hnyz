package com.dg11185.hnyz.dao;


import com.dg11185.hnyz.bean.common.PageRequest;
import com.dg11185.hnyz.bean.common.PageWrap;
import com.dg11185.hnyz.util.SQLUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * JDBC DAO基类
 *
 */
public abstract class BaseDAO extends JdbcDaoSupport {

    public static final String TB_PREFIX = "tb_";

    @Resource
    public void setJb(JdbcTemplate jb) {
        super.setJdbcTemplate(jb);
    }


    public <T> PageWrap<T> queryForPage(String sql, Object[] args, PageRequest pageRequest,
                                        Class<T> clazz) {
        return queryForPage(sql, Arrays.asList(args), pageRequest, clazz);
    }

    /**
     * 分页查询
     * 
     * @param sql 查询sql
     * @param args 查询参数
     * @param pageRequest 分页参数
     * @return
     */
    public <T> PageWrap<T> queryForPage(String sql, List<? extends Object> args,
            PageRequest pageRequest, Class<T> clazz) {
        // 防止args传入不可变对象
        if (args == null) {
            args = Collections.emptyList();
        }
        List<Object> params = new ArrayList<>(args);
        // 总条目数
        int total = queryCount(sql, params.toArray());
        PageWrap<T> wrap = new PageWrap<>(pageRequest, total);
        if (total == 0) {
            return wrap;
        }
        if (pageRequest.isQueryAll()) {
            wrap.setList(queryForList(sql, params.toArray(), clazz));
        } else {
            // 判断分页条件中offset是否大于总 记录数量
            int offset = pageRequest.getOffset();
            if (offset > total) {
                return wrap;
            }
            // 添加分页条件
            params.add(offset);
            params.add(pageRequest.getPageSize());
            wrap.setList(
                    this.getJdbcTemplate().query( sql + " limit ?, ?",
                            params.toArray(), new BeanPropertyRowMapper<T>(clazz)));
        }

        // 查询结果
        return wrap;
    }

    /**
     * 列表查询
     *
     * @param sql
     * @param args
     * @param clazz
     * @return
     */
    public <T> List<T> queryForList(String sql, Object[] args, Class<T> clazz) {
        return this.getJdbcTemplate().query(sql, args, new BeanPropertyRowMapper<T>(clazz));
    }

    public <T> List<T> queryForList(String sql, List<? extends Object> args, Class<T> clazz) {
        return queryForList(sql, args.toArray(), clazz);
    }

    public <T> List<T> queryForList(String sql, Class<T> clazz) {
        return this.getJdbcTemplate().query(sql, (Object[]) null,
                new BeanPropertyRowMapper<T>(clazz));
    }

    /**
     * 数量查询
     *
     * @param sql
     * @return
     */
    public int queryCount(String sql, Object[] args) {
        return this.getJdbcTemplate().queryForObject(
                "SELECT count(*) as totalCount FROM (" + sql + ") w", Integer.class, args);
    }

    /**
     * 查询对象
     * @param sql
     * @param args
     * @param clazz
     * @return
     */
    public <T> T queryForBean(String sql, Object[] args, Class<T> clazz) {
        List<T> list = queryForList(sql, args, clazz);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询Map List
     * @param sql
     * @param args
     * @return
     */
    public List<Map<String, Object>> queryForMapList(String sql, Object[] args) {
        return this.getJdbcTemplate().queryForList(sql, args);
    }

    public <T> int saveOrUpdate(String sql, Collection<T> arg) {
        return this.getJdbcTemplate().update(sql, arg.toArray());
    }


    /**
     * 更新对象
     * @param obj       待更新的实体对象
     * @param keyColumn 更新对象的限定条件字段名称
     * @return 返回被更新的记录数
     */
    public <T> int update(T obj, String keyColumn) {
        String tableName = TB_PREFIX + obj.getClass().getSimpleName().toLowerCase();
        // 自动生成对象的更新操作的SQL语句及其参数值
        Object[] updateSql = SQLUtil.generateUpdate(obj, tableName, keyColumn, false);
        return this.getJdbcTemplate().update(updateSql[0].toString(), (Object[]) updateSql[1]);
    }


    /**
     * 更新
     * @param sql
     * @param args
     * @return
     */
    public int saveOrUpdate(String sql, Object... args) {
        return this.getJdbcTemplate().update(sql, args);
    }

    public Integer saveAndReturnPriKey(String sql, Object... args) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psCreator = new MySQLInsertStatementCreator(sql, args);
        this.getJdbcTemplate().update(psCreator, keyHolder);

        return keyHolder.getKey().intValue();
    }

    /**
     * 查找某一个属性
     * 
     * @param sql 执行的SQL
     * @param args 参数
     * @param elementType 查询的属性类型
     * @author zengxiangtao
     **/
    public <T> List<T> findProperty(String sql, Object[] args, Class<T> elementType) {
        return this.getJdbcTemplate().queryForList(sql, elementType, args);
    }

    /**
     * 查询结果封装成map
     * 
     * @param sql 执行的SQL
     * @param args 参数
     * @author zengxiangtao
     **/
    public Map<String, Object> queryForMap(String sql, Object[] args) {
        return this.getJdbcTemplate().queryForMap(sql, args);
    }

    /** 批处理 **/
    public void excuteBatchData(String sql, List<Object[]> param) {
        Connection conn = getConnection();
        PreparedStatement prest = null;
        try {
            prest = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            for (int x = 0; x < param.size(); x++) {
                Object[] arr = param.get(x);
                int k = 1;
                for (Object o : arr) {
                    prest.setObject(k, o);
                    k++;
                }
                prest.addBatch();
            }
            prest.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != prest) {
                    prest.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
