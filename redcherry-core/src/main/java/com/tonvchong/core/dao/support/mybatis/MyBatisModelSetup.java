package com.tonvchong.core.dao.support.mybatis;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tonvchong.core.dao.support.ModelSetup;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: MyBatisModelSetup.java 
 * @Project: RBM
 * @date: 2016年3月2日 下午7:36:56
 * @author: tonvchong
 * @Description: TODO
 */
public class MyBatisModelSetup implements ModelSetup {
    private String sqlName;//namedquery name  by query

    private String countName;// namedquery name by count
    private Map<String,Object> parameters=new HashMap<String,Object>();

    public void addParameter(String namedParameter,Object value){
        this.parameters.put(namedParameter,value);
    }
    public Map getParameters(){
        return this.parameters;
    }
    public void setup(Map<String,Object> params){
        Set<String> keys = params.keySet();
            for (String key : keys) {
                Object value = params.get(key);
                if (value!=null)
                    parameters.put(key,value);
            }

    }

    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }


    public String getCountName() {
        return countName;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }
	@Override
	public String toString() {
		return "MyBatisModelSetup [sqlName=" + sqlName + ", countName="
				+ countName + ", parameters=" + parameters + "]";
	}    
}