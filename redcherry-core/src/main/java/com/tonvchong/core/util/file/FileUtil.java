package com.tonvchong.core.util.file;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.tonvchong.core.util.config.CommonConfig;

public class FileUtil {
	
	/**
	 * 通过加密的文件名得到原始文件名
	 * @param encryptName
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getOriginalName(String encryptName) {
		if (encryptName == null) {
			return null;
		}
		encryptName = encryptName.replaceAll("_", "\\/");
		String sha = "";
		try {
			sha = new String(Base64.decodeBase64(encryptName.getBytes("UTF-8")),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!sha.contains("?")) {
			return null;
		}
		return sha.split("\\?")[1];
	}
	
	public static String getDownLoadName(String fileName){
		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		return CommonConfig.getString("FILE_HOST") + "/download/" + fileName;
	}
	
	public static String getImageName(String fileName){
		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		return CommonConfig.getString("FILE_HOST") + "/image/" + fileName;
	}
	
	public static String getVideoName(String fileName){
		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		return CommonConfig.getString("FILE_HOST") + "/video/" + fileName;
	}
	
	public static void main(String [] agrs) {
		String name = getOriginalName("MjA5QjBCMzdGMURFRTQwNDQ2OUY5ODMyNzU4MEQyQkU3QzRGQzg2MT_ml6Dnur_lhYUg5Y2B5LiALTIueGxzeA==");
		System.out.println("name:"+name);
	}

}
