package com.tonvchong.core.util.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UriMatcher {
	/**
	 * URI匹配
	 * @param uri --请求URI
	 * @param uriTemplate --uri模板
	 * @return
	 */
	public static boolean match(String uri,String uriTemplate) {
		// "/project/investments/\\w+/check/\\w+";
		//转换uriTemplate为uri正则表达式
		String uriReg = uriTemplate.replaceAll("\\{\\w+\\}", "\\\\w+");
		uriReg = "^" + uriReg + "$";
		//System.out.println(uriReg);
		//返回uri是否匹配uri正则表达式
		Pattern p=Pattern.compile(uriReg);
		Matcher m=p.matcher(uri);
		return m.matches();
	}
	
	public static void main(String args[]) {
		String uriTemplate = "/project/investments/{id}/check/{status}"; 
		System.out.println(match("/project/investments/123/check/2",uriTemplate));
		System.out.println(match("/abc/project/investments/123/check/2",uriTemplate));
		System.out.println(match("/project/investments/123/check/2/",uriTemplate));
		System.out.println(match("/project1/investments/123/check/2",uriTemplate));
		System.out.println(match("/project/investment/123/check/2",uriTemplate));
		
		System.out.println("===================");
		String uriTemplate2 = "/project/investments";
		System.out.println(match("/project/investments",uriTemplate2));
		System.out.println(match("/project/investment/123",uriTemplate2));


	}
}
