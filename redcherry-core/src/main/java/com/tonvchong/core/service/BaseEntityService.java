/**
 * com.kms.framework.core.service.BaseEntityService *
 * 社会渠道产品线项目-BCP1.0
 * @copyright 深圳新宇龙信息科技有限公司 版权所有
 * @licences:
 */
package com.tonvchong.core.service;

import java.io.Serializable;

import com.tonvchong.core.clazz.GenericsUtil;
import com.tonvchong.core.dao.IEntityDao;
import com.tonvchong.core.dao.support.ModelSetup;
import com.tonvchong.core.dao.support.Page;
import com.tonvchong.core.domain.support.Entity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unchecked")
public abstract class BaseEntityService<T extends Entity> implements IBaseEntityService{
    public Class<T> getEntityClass() {
        if (entityClass == null)
            entityClass = (Class<T>) GenericsUtil.getSuperClassGenricType(getClass());
        return entityClass;
    }

    protected Class<T> entityClass;

    protected String getEntityName() {
        return getEntityClass().getSimpleName();
    }
    abstract protected IEntityDao<T> getDao();
    
    public  void create(Object entity) {

        getDao().create(entity);
        log.info("create a {}:{}" ,new Object[]{ getEntityName(),((T)entity).getId()});
    }

    public void update(Object entity) {
        getDao().update(entity);
        log.info("update a {}:{}" ,new Object[]{  getEntityName(),((T)entity).getId()});
    }

    public void removeById(Serializable id) {
        getDao().removeById(id);
        log.info("delete a {}:{}" + new Object[]{  getEntityName(),id});

    }

    public T get(Serializable id) {
        return  getDao().get(id);
    }

    public Page pagedQuery(ModelSetup modelSetup, int pageNo, int pageSize) {
        return getDao().pagedQuery(modelSetup, pageNo, pageSize);
    }

    public String getIdName(Class clazz) {
        return getDao().getIdName(clazz);
    }
}
