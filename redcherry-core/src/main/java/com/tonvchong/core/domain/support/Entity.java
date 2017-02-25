package com.tonvchong.core.domain.support;

import java.io.Serializable;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: Entity.java 
 * @Project: RBM
 * @date: 2016年3月2日 下午9:05:59
 * @author: tonvchong
 * @Description: TODO
 */
@SuppressWarnings("serial")
public abstract class Entity<T> implements Serializable{
	/**
	 * 统一的ID声明
	 * 
	 */
    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
