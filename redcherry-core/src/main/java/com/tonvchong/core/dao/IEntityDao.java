package com.tonvchong.core.dao;

import java.io.Serializable;
import java.util.List;

import com.tonvchong.core.dao.support.ModelSetup;
import com.tonvchong.core.dao.support.Page;

public interface IEntityDao<T> {
	T get(Serializable id);

    List<T> getAll();

    List<T> getAll(ModelSetup ModelSetup);

    void update(Object o);

    void create(Object o);

    void remove(Object o);

    void removeById(Serializable id);

    /**
     * 获取Entity对象的主键名.
     * @param clazz
     * @return
     */
    String getIdName(Class clazz);

    Integer getCount(ModelSetup ModelSetup);

    Page pagedQuery(ModelSetup modelSetup, int pageNo, int pageSize);
}
