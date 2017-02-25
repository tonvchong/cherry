package com.tonvchong.core.dao.generic;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.Assert;

import com.tonvchong.core.dao.support.Page;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: IBatisGenericDao.java 
 * @Project: RBM
 * @date: 2016年3月2日 下午7:05:54
 * @author: tonvchong
 * @Description: 默认实现DAO的CURD
 */
@SuppressWarnings("unchecked")
abstract public class MyBatisGenericDao
        extends SqlSessionDaoSupport {

    /**
     * 根据ID获取对象 DOMAIN
     * @param statement
     * @param id
     * @param <T>
     * @return
     */
    @SuppressWarnings("hiding")
    public <T> T get(String statement, Serializable id) {
		return (T) getSqlSession().selectOne(
                statement, id);

    }


    /**
     * 获取全部对象
     * @param statement 配置的名称
     * @param <T> 方法级泛型
     * @return list
     */
    @SuppressWarnings("hiding")
    public <T> List<T> getAll(String statement) {
        return getSqlSession().selectList(statement, null);

    }

    /**
     * 新增对象
     * 先插入數據庫
     */
    public int insert(String statement, Object o) {
        int obj = getSqlSession().insert(statement, o);
        return obj;
    }

    /**
     * 保存对象
     */
    public int update(String statement, Object o) {

        int obj = getSqlSession().update(statement, o);
        return obj;
    }

    /**
     * 删除对象
     */
    public int remove(String statement, Object o) {
        int i = getSqlSession().delete(statement, o);
        return i;
    }

    /**
     * 根据ID删除对象
     */
    public int removeById(String statement, Serializable id) {
        int i = getSqlSession().delete(statement, id);
        return i;
    }

    /**
     * map查询.
     *
     * @param map 包含各种属性的查询
     */
    @SuppressWarnings("hiding")
    public <T> List<T> find(String statement, Map map) {
        if (map == null) {
            return this.getSqlSession().selectList(statement, null);
        } else {            
            return this.getSqlSession().selectList(statement, map);
        }
    }

    /**
     * 条件查询
     *
     * @param statement
     * @param o
     * @return
     */
    public <T> List<T> find(String statement, Object o) {
        return this.getSqlSession().selectList(statement, o);
    }

//    /**
//     * sql 查询.
//     *
//     * @param sql 直接sql的语句(需要防止注入式攻击)
//     */
//    @SuppressWarnings("hiding")
//    public <T> List<T> find(String statement, String sql) {
//        Assert.hasText(sql);
//
//        if (StringUtils.isEmpty(sql)) {
//            return this.getSqlSession().selectList(statement, null);
//        } else {
//            return this.getSqlSession().selectList(statement, sql);
//        }
//    }

    /**
     * 根据属性名和属性值查询对象.
     *
     * @return 符合条件的对象列表
     */
    @SuppressWarnings("hiding")
    public <T> List<T> findBy(String statement, String name, Object value) {
        Assert.hasText(name);

        Map map = new HashMap();
        map.put(name, value);

        return find(statement, map);
    }

    /**
     * 根据属性名和属性值查询对象.
     *
     * @return 符合条件的唯一对象
     */
    @SuppressWarnings("hiding")
    public <T> T findUniqueBy(String statement, String name, Object value) {
        Assert.hasText(name);

        Map map = new HashMap();
        //removed by ouyangke 2010.02.23
        //try {
        //	PropertyUtils.getProperty(clazz.newInstance(), name);
        map.put(name, value);
//        map.put("findUniqueBy", "True");

        return (T) getSqlSession().selectOne(statement, map);

    }
    /**
     * 根据属性名和属性值查询对象.
     *
     * @return 符合条件的唯一对象
     */
    @SuppressWarnings("hiding")
    public <T> T findUniqueBy(String statement, Map map) {

        return (T) getSqlSession().selectOne(statement, map);

    }

    /**
     * 根据属性名和属性值以Like AnyWhere方式查询对象.
     */
    @SuppressWarnings("hiding")
    public <T> List<T> findByLike(String statement, String name, String value) {
        Assert.hasText(name);

        Map map = new HashMap();
        map.put(name, value);
        map.put("findLikeBy", "True");

        return getSqlSession().selectList(statement, map);
    }

    /**
     * 判断对象某些属性的值在数据库中不存在重复
     *
     * @param tableName 数据表名字
     * @param names     在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
     *                  in different schema?
     */
    public boolean isNotUnique(String tableName, String names) {
        try {
            String primarykey;
            Connection con = getSqlSession().getConnection();
            ResultSet dbMetaData = con.getMetaData().getPrimaryKeys(
                    con.getCatalog(), null, tableName);
            dbMetaData.next();

            if (dbMetaData.getRow() > 0) {
                primarykey = dbMetaData.getString(4);

                if (names.indexOf(primarykey) > -1) {
                    return false;
                }
            } else {
                return true;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);

            return false;
        }

        return false;
    }

    @SuppressWarnings("hiding")
    public <T> List<T> list(String statement, Object parameter, int size) {
        return (List<T>) getSqlSession().selectList(statement, parameter, new RowBounds(0,
        		size));
    }

    /**
     * 分页查询函数，使用selectList（），select的Id使用用户自定义
     *
     * @param count     查询count的语句ID
     * @param statement 查询语句ID
     * @param parameter 参数
     * @param pageSize  每页行数
     * @param pageNo    页号,从1开始.
     * @return 含总记录数和当前页数据的Page对象.
     */
    public Page pagedQuery(String count, String statement, Object parameter,
                           int pageNo, int pageSize) {
        //Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
        if (pageNo < 1)
            pageNo = 1;

        // 计算总数
        Integer totalCount = (Integer) this.getSqlSession()
                .selectOne(count, parameter);

        // 如果没有数据则返回Empty Page
        Assert.notNull(totalCount, "totalCount Error");

        if (totalCount == null || totalCount.intValue() == 0) {
            return new Page();
        }

        List list;
        int totalPageCount = 0;
        int startIndex = 0;

        // 如果pageSize小于等于0,pageSize=Page.DEFAULT_PAGE_SIZE
        if (pageSize <= 0)
            pageSize = Page.DEFAULT_PAGE_SIZE;

        // 计算页数
        totalPageCount = (totalCount / pageSize);
        totalPageCount += (((totalCount % pageSize) > 0) ? 1 : 0);

        // 计算skip数量
        if (totalPageCount > pageNo) {
            startIndex = (pageNo - 1) * pageSize;
        } else {
            startIndex = (totalPageCount - 1) * pageSize;
        }

        list = getSqlSession().selectList(statement, parameter, new RowBounds(startIndex,
                pageSize));

        return new Page(startIndex, totalCount, pageSize, list);
    }

    /**
     * 提供过滤字段可排序字段的 分页查询 如要过滤，在sqlmapclient的doamin配置文件中需要加上
     * <code><isTrue name="filtable"></code> 需要排序,在sqlmapclient的doamin配置文件中需要加上
     * <code><isTrue name="sortable"></code>
     *
     * @param count
     * @param statement
     * @param parameter
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page pageQuery(String count, String statement, Map parameter, int pageNo, int pageSize) {

        for (Object entry : parameter.keySet()) {
            parameter.put(entry, parameter.get(entry));
        }
        return this.pagedQuery(count, statement, parameter, pageNo, pageSize);

    }

    /**
     * 获得某些条件下的行数
     *
     * @param statement
     * @param params
     * @return
     */
	public int getCount(String statement,Map params){
		return (Integer) getSqlSession().selectOne(statement, params);
	}
	
	public String getIdName() {
		return "id";
	}
}
