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
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.huangzhipeng.cms.entity.Article;
import com.huangzhipeng.cms.service.ArticleService;
import com.huangzhipeng.common.uitls.FileUtil;

/**   
 * @ClassName:     ProducerTest   
 * @Description:   TODO
 * @author:        HuangZhiPeng
 * @date:          2019年10月17日 上午9:17:16     
 */
@ContextConfiguration("classpath:spring-kafka-producer.xml")
@RunWith(SpringRunner.class)
public class ProducerTest {
	
	@Resource
	private KafkaTemplate<String,String> kafkaTemplate;
	
	
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
	

}
