package cn.sibat.warn.util;

import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Convernter {
	
	public static String getMd5(String s) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
//		String plainText = "人力资源局xxzx-barlzy0506-sszt2016-05-06 09:14:30";
		String plainText = s;
		byte[] btInput = plainText.getBytes();
		//对得到的字节数组进行处理
		md.update(btInput);
		//进行哈希计算并返回结果
		byte[] btResult = md.digest();
		//进行哈希计算后得到的数据的长度
		StringBuffer sb = new StringBuffer();
		for(byte b : btResult){
		int bt = b&0xff;
		if(bt<16){
		sb.append(0);
		}
		sb.append(Integer.toHexString(bt));
		}
		String result = sb.toString().toUpperCase();
		System.out.println(result);
		return result;
	}
	
	public static String calMd5(String s){
		
		return DigestUtils.md5Hex(s).toUpperCase();
	}
	
	public static void main(String[] args) throws Exception {
		MD5Convernter md = new MD5Convernter();
		md.getMd5("");
		md.calMd5("");
	}

}
