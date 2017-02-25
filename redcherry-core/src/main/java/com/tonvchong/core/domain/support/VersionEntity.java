package com.tonvchong.core.domain.support;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: VersionEntity.java 
 * @Project: RBM
 * @date: 2016年3月2日 下午9:06:05
 * @author: tonvchong
 * @Description: TODO
 */
public abstract class VersionEntity extends Entity {
     //version control
    private Long version;
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
