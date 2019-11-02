/**
 * 
 * @Title:         RedisArticleServiceImpl.java
 * @Package        com.huangzhipeng.cms.service.impl
 * @Description:   TODO
 * @author:        HuangZhiPeng
 * @date:          2019年10月30日 上午8:55:14
 * @version:       V1.0
 */
package com.huangzhipeng.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.huangzhipeng.cms.service.ArticleService;
import com.huangzhipeng.cms.service.RedisArticleService;

/**   
 * @ClassName:     RedisArticleServiceImpl   
 * @Description:   TODO
 * @author:        HuangZhiPeng
 * @date:          2019年10月30日 上午8:55:14     
 */
@Service
public class RedisArticleServiceImpl implements RedisArticleService{
	  
	
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
	
	/**   
	 * @Title:         save
	 * @Description:   TODO
	 * @date:          2019年10月30日 上午8:57:00  
	 * @param article   
	 * @see com.huangzhipeng.cms.service.RedisArticleService#save(java.lang.String)   
	 */
	@Override
	public void save(String article) {
		
		//写入redis
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		opsForList.leftPush("article2",article);
		
	}
    
}
