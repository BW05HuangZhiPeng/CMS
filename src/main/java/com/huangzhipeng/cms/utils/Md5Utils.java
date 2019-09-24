package com.huangzhipeng.cms.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
*@author huangzhipeng
*@version 创建时间：2019年9月18日 下午7:40:56
*类功能说明
*/
public class Md5Utils {
	
	/**
	 * 加盐
	 */
	private static String  saltString="asdgsfdfdfsdfgssggfddgfdfg";
	
	/**
	 * 对密码进行加密
	 * @param pwd
	 * @return
	 */
	public static String md5(String pwd) {
		//对密码进行加密
		return DigestUtils.md5Hex(pwd + saltString);
	}
}

