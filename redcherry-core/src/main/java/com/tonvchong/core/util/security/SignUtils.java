package com.tonvchong.core.util.security;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: SignUtils.java
 * @Project: RBM
 * @date: 2016年3月4日 下午6:04:22
 * @author: tonvchong
 * @Description: Sign
 */
@Slf4j
public class SignUtils {

    public static final String KEY = "86fdcec5a788933d4496afd2cc0a3a6m";

    /**
     * 签名算法sign
     * 
     * @param characterEncoding
     * @param parameters
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + KEY);

//        log.info("字符串拼接后是：" + sb.toString());
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    /**
     * 签名验证
     * 
     * @param payResult
     * @return
     */
    public static boolean checkResultSign(SortedMap<Object, Object> parameters, String mSign) {
        String sign = createSign("UTF-8", parameters);
        log.info("结果参数签名是：" + sign);
        if (sign.equals(mSign)) {
            return true;
        }
        return false;
    }

}
