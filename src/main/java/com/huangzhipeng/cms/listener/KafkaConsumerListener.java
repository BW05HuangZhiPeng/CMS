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

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
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
	
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		Gson gson = new Gson();
		Article article = gson.fromJson(data.value(),Article.class);
		arServie.add(article); 
	}

}
