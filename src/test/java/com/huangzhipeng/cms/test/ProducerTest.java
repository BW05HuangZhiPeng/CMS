/**
 * 
 * @Title:         ProducerTest.java
 * @Package        com.huangzhipeng.cms.test
 * @Description:   TODO
 * @author:        HuangZhiPeng
 * @date:          2019年10月17日 上午9:17:16
 * @version:       V1.0
 */
package com.huangzhipeng.cms.test;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.huangzhipeng.cms.entity.Article;
import com.huangzhipeng.cms.entity.Category;
import com.huangzhipeng.cms.entity.Channel;
import com.huangzhipeng.cms.service.ArticleService;
import com.huangzhipeng.cms.service.CategoryService;
import com.huangzhipeng.cms.service.ChannelService;
import com.huangzhipeng.cms.service.RedisArticleService;
import com.huangzhipeng.cms.utils.RandomUtil;
import com.huangzhipeng.common.uitls.FileUtil;

/**   
 * @ClassName:     ProducerTest   
 * @Description:   TODO
 * @author:        HuangZhiPeng
 * @date:          2019年10月17日 上午9:17:16     
 */
@ContextConfiguration("classpath:spring.xml")
@RunWith(SpringRunner.class)
public class ProducerTest {
	
	@Resource
	private KafkaTemplate<String,String> kafkaTemplate;
	
	@Autowired
	private ChannelService channelservice;
	
	@Autowired
	private CategoryService categoryservice;
	
	@Autowired
	private RedisArticleService redisArticleService;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	@Test
	public void AddArticle() throws IOException {
		int count=0;
		int channelId[]={1,2,3,4,5,6,7,8};
		List<String> fileList = FileUtil.getFileList("D:\\1705DJsoup");
		Random random = new Random();
		for (String string : fileList) {
			Article article = new Article();
			String content;
			content = FileUtil.readFile(string);
			article.setContent(content);
			article.setTitle(string.substring(string.lastIndexOf('\\')+1 , string.lastIndexOf('.')));
			article.setHits(10 + random.nextInt(90));//
			article.setHot(random.nextInt(2));
			article.setUserId(44);
			article.setChannelId(channelId[random.nextInt(7)]);
			article.setCategoryId(channelId[random.nextInt(7)]);
			
			Gson gson = new Gson();
			String json = gson.toJson(article);
			count++;
			kafkaTemplate.sendDefault(json);
			
		}
		System.out.println("一共"+count+"条");
		
	}
	
	
	@Test
	public void JsoupArticle() throws IOException {
		int count=0;
		List<String> fileList = FileUtil.getFileList("D:\\1705DJsoup2");
		Random random = new Random();
		Gson gson = new Gson();
		for (String string : fileList) {
			Article article = new Article();
			String content;
			content = FileUtil.readFile(string);
			article.setContent(content);
			if (content.length()>140&&content!=null) {
				article.setPhrase(content.substring(0,140));
			}else {
				article.setPhrase(content);
			}
			article.setTitle(string.substring(string.lastIndexOf('\\')+1 , string.lastIndexOf('.')));
			article.setHits(random.nextInt(500));//
			article.setHot(random.nextInt(2));
			article.setStatus(random.nextInt(3));
			List<Channel> channels = channelservice.getChannels();
			Channel channel = channels.get(random.nextInt(channels.size()));
			article.setChannelId(channel.getId());
			List<Category> categoryByChId = categoryservice.getCategoryByChId(channel.getId());
			if (categoryByChId!=null && categoryByChId.size() > 0) {
				Category category = categoryByChId.get(random.nextInt(categoryByChId.size()));
				article.setCategoryId(category.getId());
			}
			article.setPicture("6.jpg");
			Date randomDate = RandomUtil.randomDate("2019-01-01","2019-10-24");
			article.setCreated(randomDate);
			article.setUpdated(randomDate);
			
			article.setDeleted(random.nextInt(2));
			article.setUserId(40);
			int i=0;
			i++;
			article.setId(856+i);
			
			String json = gson.toJson(article);
			redisArticleService.save(json);
			count++;
			
		}
		//把redis的key放送给消费端
		kafkaTemplate.sendDefault("article2");
		System.out.println("一共"+count+"条");
		
	}
	

}
