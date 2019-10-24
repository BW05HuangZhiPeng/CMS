/**
 * 
 * @Title:         ConsumerTest.java
 * @Package        com.huangzhipeng.cms.test
 * @Description:   TODO
 * @author:        HuangZhiPeng
 * @date:          2019年10月17日 上午9:17:38
 * @version:       V1.0
 */
package com.huangzhipeng.cms.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**   
 * @ClassName:     ConsumerTest   
 * @Description:   TODO
 * @author:        HuangZhiPeng
 * @date:          2019年10月17日 上午9:17:38     
 */
public class ConsumerTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring-kafka-consumer.xml","classpath:spring.xml");
	}

}
