package com.tonvchong.core.service;

import java.io.Serializable;

import com.tonvchong.core.dao.support.ModelSetup;
import com.tonvchong.core.dao.support.Page;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: IBaseEntityService.java 
 * @Project: RBM
 * @date: 2016年3月2日 下午9:09:14
 * @author: tonvchong
 * @Description: TODO
 */
public interface IBaseEntityService {
    public <T> void create(T entity);

    public <T> void update(T entity);

    public void removeById(Serializable id) ;

    public <T> T get(Serializable id);

    public Page pagedQuery(ModelSetup modelSetup, int pageNo, int pageSize);

    public String getIdName(Class clazz) ;
}
