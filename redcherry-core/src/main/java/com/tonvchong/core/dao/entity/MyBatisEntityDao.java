package com.tonvchong.core.dao.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.Assert;

import com.tonvchong.core.clazz.GenericsUtil;
import com.tonvchong.core.dao.IEntityDao;
import com.tonvchong.core.dao.generic.MyBatisGenericDao;
import com.tonvchong.core.dao.support.ModelSetup;
import com.tonvchong.core.dao.support.Page;
import com.tonvchong.core.dao.support.mybatis.MyBatisModelSetup;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: IBatisEntityDao.java 
 * @Project: RBM
 * @date: 2016年3月2日 下午6:54:32
 * @author: tonvchong
 * @Description: TODO
 */
@SuppressWarnings("unchecked")
public abstract class MyBatisEntityDao<T> extends MyBatisGenericDao implements IEntityDao<T> {

    private static final String INSERT = ".insert";
    private static final String UPDATE = ".update";
    private static final String DELETE = ".delete";
    private static final String DELETEBYID = ".deleteById";
    private static final String GET = ".get";
    private static final String GETALL = ".getAll";

    public Class<T> getEntityClass() {
        if (entityClass == null)
            entityClass = (Class<T>) GenericsUtil.getSuperClassGenricType(getClass());
        return entityClass;
    }

    protected Class<T> entityClass;

    protected String getEntityName() {
        return getEntityClass().getName();
    }

    public T get(Serializable id) {
        String name = getEntityName() + GET;
        return (T) super.get(name, id);
    }

    public List<T> getAll() {

        String name = getEntityName() + GETALL;
        return this.getAll(name);
    }

    public List<T> getAll(ModelSetup modelSetup) {
        MyBatisModelSetup model = (MyBatisModelSetup) modelSetup;
        String name = model.getSqlName();
        return this.find(name, model.getParameters());
    }

    public void update(Object o) {
        String name = getEntityName() + UPDATE;
        this.update(name, o);
    }

    public void create(Object o) {
        String name = getEntityName() + INSERT;
        this.insert(name, o);
    }

    public void remove(Object o) {
        String name = getEntityName() + DELETE;
        this.remove(name, o);
    }

    public void removeById(Serializable id) {
        String name = getEntityName() + DELETEBYID;
        this.removeById(name, id);
    }

    /**
     * 请子类重写返回domain的ID的name
     *
     * @param clazz
     * @return
     */
    public String getIdName(Class clazz) {
        return "id";
    }

    /**
     * 通过ModelSetup查询count
     *
     * @param modelSetup
     * @return
     */
    public Integer getCount(ModelSetup modelSetup) {
        MyBatisModelSetup model = (MyBatisModelSetup) modelSetup;
        String countSqlName = model.getCountName();
        Assert.hasText(countSqlName, "count sql name not null");
        return super.getCount(countSqlName, model.getParameters());
    }

    /**
     * 通过ModelSetup  page
     *
     * @param modelSetup
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page pagedQuery(ModelSetup modelSetup, int pageNo, int pageSize) {
        MyBatisModelSetup model = (MyBatisModelSetup) modelSetup;
        String countSqlName = model.getCountName();
        Assert.hasText(countSqlName, "count sql name not null");
        String sqlName = model.getSqlName();
        Assert.hasText(sqlName, "sql name not null");
        return super.pagedQuery(countSqlName, sqlName, ((MyBatisModelSetup) modelSetup).getParameters(), pageNo, pageSize);
    }
}
