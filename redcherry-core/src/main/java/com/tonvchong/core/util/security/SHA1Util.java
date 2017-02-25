/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package com.tonvchong.core.util.security;

import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: SHA1Util.java 
 * @Project: RBM
 * @date: 2016年3月4日 下午6:04:22
 * @author: tonvchong
 * @Description: sha1加密
 */
public class SHA1Util {

	/**
	 * sha1加密
	 * @param src
	 * @return
	 */
	public static String encrypt(String src) {
		try {
			String str = src;
			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();
				
			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取文件SHA1摘要值
	 * @param fileInputStream
	 * @return
	 */
	public static String getSHA1(InputStream fileInputStream){
		// 缓冲区大小
        int bufferSize = 256 * 1024;
        DigestInputStream digestInputStream = null;
        try{
           // 拿到一个SHA1转换器（这里可以换成MD5）
           MessageDigest messageDigest =MessageDigest.getInstance("SHA1");
           // 使用DigestInputStream
           digestInputStream = new DigestInputStream(fileInputStream,messageDigest);
           // read的过程中进行SHA1处理，直到读完文件
           byte[] buffer =new byte[bufferSize];
           while (digestInputStream.read(buffer) > 0);
           // 获取最终的MessageDigest
           messageDigest= digestInputStream.getMessageDigest();
           // 拿到结果，也是字节数组，包含16个元素
           byte[] resultByteArray = messageDigest.digest();
           // 把字节数组转换成字符串
           return byteArrayToHex(resultByteArray);
        }catch(Exception e) {
           return null;
        }finally{
           try{
              digestInputStream.close();
              fileInputStream.close();
           }catch (Exception e) {

           }
        }
	}
	
	/**
	 * 将字节数组换成成16进制的字符串
	 * @param byteArray
	 * @return
	 */
	public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray =new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
           resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
           resultCharArray[index++] = hexDigits[b& 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
	}
	
	/**
	 * SHA 256
	 * @param data
	 * @return
	 */
	public static String sha256Hex(String data) {  
        return DigestUtils.sha256Hex(data);  
    } 
	
	public static void main(String[] args) {
		String response = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><message><head><version>1.0.0</version><msgtype>0002</msgtype>"
				+ "<channelno>99</channelno><merchantno>CF3000036567</merchantno><trandate>20160405</trandate><trantime>151119</trantime>"
				+ "<bussflowno>CF300003656720160405000053</bussflowno><trancode>CP0001</trancode><respcode>C000000000</respcode>"
				+ "<respmsg>成功</respmsg></head><body><tranState>02</tranState></body></message>";
		String secretKey = "787a21420d0d1853c869635ca2a94cbe";
		System.out.println("加密后：" + SHA1Util.sha256Hex(response+secretKey));
	}
}
