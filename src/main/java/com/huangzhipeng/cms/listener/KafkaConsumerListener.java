/**
 * 
 * @Title:         KafkaConsumerListener.java
 * @Package        com.huangzhipeng.cms.listener
 * @Description:   TODO
 * @author:        HuangZhiPeng
 * @date:          2019年10月17日 上午9:10:54
 * @version:       V1.0
 */
package com.huangzhipeng.cms.listener;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.huangzhipeng.cms.dao.ArticleMapper;
import com.huangzhipeng.cms.entity.Article;
import com.huangzhipeng.cms.service.ArticleService;

/**   
 * @ClassName:     KafkaConsumerListener   
 * @Description:   TODO
 * @author:        HuangZhiPeng
 * @date:          2019年10月17日 上午9:10:54     
 */
@Component("kafkaConsumerListener")
public class KafkaConsumerListener implements MessageListener<String,String>{

	/**   
	 * @Title:         onMessage
	 * @Description:   TODO
	 * @date:          2019年10月17日 上午9:11:57  
	 * @param data   
	 * @see org.springframework.kafka.listener.GenericMessageListener#onMessage(java.lang.Object)   
	 */
	@Autowired
	ArticleService arServie;
	
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	ArticleMapper articleMapper;
	
	
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		
	    String value = data.value();
	    Gson gson = new Gson();
	    Article findById = gson.fromJson(value,Article.class);
			IndexQuery build = new IndexQueryBuilder().withId(findById.getId().toString()).withObject(findById).build();
			String index = elasticsearchTemplate.index(build);
		
			
			//导入文章时利用
			/*
			 * ListOperations<String, String> opsForList = redisTemplate.opsForList();
			 * //获取所有redis的数据
			 * 
			 * List<String> range = opsForList.range(data.value(),0,-1); Gson gson = new
			 * Gson(); int count=0; for (String string : range) { count++; Article article =
			 * gson.fromJson(string,Article.class); arServie.add(article);
			 * System.out.println(article.getTitle()+"文章以导入完毕"); }
			 * System.out.println("一共"+count+"张");
			 * 
			 * redisTemplate.delete(data.value());
			 */
		
		

		
	
		
	}

}
