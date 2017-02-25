package com.tonvchong.core.domain.support;


/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: PriorityEntity.java 
 * @Project: RBM
 * @date: 2016年3月2日 下午9:06:11
 * @author: tonvchong
 * @Description: TODO
 */
public abstract class PriorityEntity<T> extends Entity<T> {
     //priority control
    private int priority = 0;// the more the value is ,the smaller the priority is

    private long currentTime = System.currentTimeMillis();
    
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public long getCurrentTime(){
		return currentTime;
	}

}
