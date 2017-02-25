package com.tonvchong.core.util.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

@Slf4j
public class JsonUtil {
	static ObjectMapper mapper = new ObjectMapper();
	
	static {
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		mapper.setDateFormat(sdf);
    }

	/**
     * 提供给elasticsearch使用，把bean转换成list map 集合类型，否则不能存入索引
     *
     * @param o
     * @return
     */
    public static Object beanToJsonObject(Object o) {
        return jsonStrToList(objectToJsonStr(o), Map.class);
    }

    public static String objectToJsonStr(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (IOException e) {
            log.error("object can not objectTranslate to json", e);
        }
        return null;
    }

    public static <T> T jsonStrToObject(String json, Class<T> cls) {
        try {
            return mapper.readValue(json, cls);
        } catch (IOException e) {
            log.error("json cant be objectTranslate to object", e);
            return null;
        }
    }

    public static <T> T jsonDataToObject(String jsonStr, Class<T> cls) {
        if (!StringUtils.isEmpty(jsonStr)) {
            T data = JsonUtil.jsonStrToObject(jsonStr, cls);
            return data;
        } else {
            return null;
        }
    }

    public static <T> List<T> jsonStrToList(String jsonStr, Class<?> clazz) {
        List<T> list = new ArrayList<T>();
        try {
            // 指定容器结构和类型（这里是ArrayList和clazz）
            TypeFactory t = TypeFactory.defaultInstance();
            list = mapper.readValue(jsonStr,
                    t.constructCollectionType(ArrayList.class, clazz));
        } catch (IOException e) {
            log.error("反序列化序列化attributes，从Json到List报错", e);
        }
        return list;
    }

    public static Map jsonStrToMap(String attributes) {
        try {
            return mapper.readValue(attributes, HashMap.class);
        } catch (IOException e) {
            log.error("反序列化序列化attributes，从Json到HashMap报错", e);
        }
        return new HashMap();
    }
    
    public static void main(String args[]) {
    
    	String jsonStr = "[{\"name\":\"paper tiger\",\"age\":12,\"birthday\":1393771949000,\"address\":{\"city\":\"sz\"},\"friends\":[{\"name\":\"cat\"},{\"name\":\"fish\"}]},{\"name\":\"doctor\",\"age\":12,\"birthday\":1393771949000,\"address\":{\"city\":\"gz\"},\"friends\":[{\"name\":\"bird\"},{\"name\":\"lion\"}]}]";
    }
}
