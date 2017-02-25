package com.tonvchong.core.util.http;

import java.net.ConnectException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpUtil {

	/**
	 * 发送https请求(post)
	 * @param requestUrl
	 * @param params
	 * @return
	 */
	public static String httpsRequestPost(String requestUrl, Map<String,String> params) {
		log.debug("https requst, requestUrl:{}", requestUrl);
		//响应长度
		long responseLength = 0;
		//响应内容
		String responseContent = null;
		CloseableHttpClient httpClient = createSSLClientDefault();
		try {
			HttpPost httpPost = new HttpPost(requestUrl);
			// 构建POST请求的表单参数
			List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>(); 
			for (Map.Entry<String,String> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
			// 执行POST请求
			CloseableHttpResponse response = httpClient.execute(httpPost); 
			// 获取响应实体
			HttpEntity entity = response.getEntity(); 
			
			if (null != entity) {
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity); 
			}
			System.out.println("响应状态: " + response.getStatusLine());
		    System.out.println("响应长度: " + responseLength);
		    System.out.println("响应内容: " + responseContent);
		    
		    httpClient.close();
		} catch (ConnectException ce) {
			log.error("connect timeout：{}", ce);
		} catch (Exception e) {
			log.error("https request error：{}", e);
		} 
		return responseContent;
	}

	/**
	 * 发送http请求(post)
	 * @param requestUrl
	 * @param params
	 * @return
	 */
	public static String httpRequestPost(String requestUrl, Map<String,String> params) {
		log.debug("https requst, requestUrl:{}", requestUrl);
		//响应长度
		long responseLength = 0;
		//响应内容
		String responseContent = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(requestUrl);
			// 构建POST请求的表单参数
			List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>(); 
			for (Map.Entry<String,String> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
			// 执行POST请求
			CloseableHttpResponse response = httpClient.execute(httpPost); 
			// 获取响应实体
			HttpEntity entity = response.getEntity(); 
			
			if (null != entity) {
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity); 
			}
//			System.out.println("响应状态: " + response.getStatusLine());
//		    System.out.println("响应长度: " + responseLength);
//		    System.out.println("响应内容: " + responseContent);
		    httpClient.close();
		} catch (ConnectException ce) {
			log.error("connect timeout：{}", ce);
		} catch (Exception e) {
			log.error("https request error：{}", e);
		} 
		return responseContent;
	}
	
	/**
	 * 发送http请求(post)
	 * @param requestUrl
	 * @param params
	 * @return
	 */
	public static String httpRequest(String requestUrl, Object object, String method) {
		log.debug("https requst, requestUrl:{}", requestUrl);
		//响应内容
		String responseContent = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		try {
			
			CloseableHttpResponse response = null;
			
			if ("GET".equalsIgnoreCase(method)) {
				
				HttpGet request = new HttpGet(requestUrl);  
				response = httpClient.execute(request);
				
			} else if ("POST".equalsIgnoreCase(method)) {
				HttpPost httpPost = new HttpPost(requestUrl);
				
				ObjectMapper mapper = new ObjectMapper();  
				mapper.setSerializationInclusion(Include.NON_NULL);  
				String Json =  mapper.writeValueAsString(object);  
				
				StringEntity reqEntity = new StringEntity(Json,"utf-8");
				reqEntity.setContentEncoding("UTF-8");    
				reqEntity.setContentType("application/json");  
	            httpPost.setEntity(reqEntity);
	            
				// 执行POST请求
	            response = httpClient.execute(httpPost);
			}
			
			// 获取响应实体
			HttpEntity entity = response.getEntity(); 
			
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity); 
			}
//			System.out.println("响应状态: " + response.getStatusLine());
//		    System.out.println("响应内容: " + responseContent);
		    httpClient.close();
		} catch (ConnectException ce) {
			log.error("connect timeout：{}", ce);
		} catch (Exception e) {
			log.error("https request error：{}", e);
		} 
		return responseContent;
	}
	
	/**
	 * 封装SSL Client 
	 * @return
	 */
	private static CloseableHttpClient createSSLClientDefault() {
		try {
			X509TrustManager xtm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			TrustManager[] tm = { xtm };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();

		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}
	
}
