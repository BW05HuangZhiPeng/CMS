/**   
 * Copyright © 2019 公司名. 八维
 * 
 * @Title: RandomUtil.java 
 * @Prject: demo-util
 * @Package: com.gejiahui.common.utils 
 * @Description: TODO

 * @date: 2019年8月8日 下午1:40:20 
 * @version: V1.0   
 */
package com.huangzhipeng.cms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/** 
 * @ClassName: RandomUtil 
 * @Description: TODO

 * @date: 2019年8月8日 下午1:40:20  
 */
public class RandomUtil {
	
	//方法1：返回min-max之间的随机整数（包含min和max值），例如返回1-3之间的随机数，那么返回1或2或3都是正确的，返回4就不对。 (5分)
	public static int random(int min, int max){
		Random ran = new Random();
		int i = ran.nextInt(max - min + 1)+min;
		return i;
		
	//TODO 实现代码
	}
	//方法2：在最小值min与最大值max之间截取subs个不重复的随机数。例如在1-10之间取3个不重复的随机数，那么[2,6,9]是对的，[3,5,5]则不对，因为5重复了。
//	应用场景：在100篇文章中随机取10篇文章，月考可能会使用到。 (8分)
	public static int[] subRandom (int min, int max, int subs){
		Set<Integer> set = new HashSet<Integer>();
		while(set.size()<subs) {
			set.add(random(min, max));
		}
		int[] arr = new int[subs];
		
		int j = 0;
		for (Integer i : set) {
			arr[j] = i;
			j++;
		}
		
		return arr;
	//TODO 实现代码
	}
	//方法3：返回1个1-9,a-Z之间的随机字符。 (8分)
	public static char randomCharacter (){
		String str="123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		
		int i = random(0, str.length()-1);
		
		return str.charAt(i);
	//TODO 实现代码
	}
	//方法4：a-z之间的随机字符。 (8分)
	public static char randomLetter(){
		String str="qwertyuiopasdfghjklzxcvbnm";
		
		int i = random(0, str.length()-1);
		
		return str.charAt(i);
		//TODO 实现代码
	}
	//方法5：返回参数length个字符串(5分)，方法内部要调用randomCharacter()方法 (4分)
	public static String randomString(int length){
		String str = "";
		for (int i = 0; i < length; i++) {
			str += randomCharacter();
		}
		return str;
	//TODO 实现代码
	}
	
	//给定时间 随机日期(字符串参数)
	public static Date randomDate(String stratDate,String endDate) {
		SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd");
		long date = 0L;
		try {
			Date d1 = st.parse(stratDate);
			Date d2 = st.parse(endDate);
			date = (long) (Math.random() * (d2.getTime() - d1.getTime() + 1) +d1.getTime());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date(date);
		
	}
	//给定时间 随机日期(日期参数)
	public static Date randomDate(Date stratDate,Date endDate) {
		SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd");
		String s1 = st.format(stratDate);
		String s2 = st.format(endDate);
		return randomDate(s1, s2);
	}
	
	
	//随机13开头的电话号
	public static String random13Phone() {
		long i = (long)(Math.ceil((Math.random()*(999999999-100000000.0) + 1)+100000000));
		String phone = i+"";
		return "13"+i;
	}
	
	

}
