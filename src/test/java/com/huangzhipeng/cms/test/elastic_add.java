/**
 * 
 * @Title:         elastic_add.java
 * @Package        com.huangzhipeng.cms.test
 * @Description:   TODO
 * @author:        HuangZhiPeng
 * @date:          2019年10月23日 下午6:57:05
 * @version:       V1.0
 */
package com.huangzhipeng.cms.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.huangzhipeng.cms.entity.Article;
import com.huangzhipeng.cms.service.ArticleService;

/**   
 * @ClassName:     elastic_add   
 * @Description:   TODO
 * @author:        HuangZhiPeng
 * @date:          2019年10月23日 下午6:57:05     
 */
@ContextConfiguration("classpath:spring.xml")
@RunWith(SpringRunner.class)
public class elastic_add {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private ArticleService articleService;
	
	
	@Test
	public void esadd() {
		List<Article> articleAll = articleService.articleAll();
		for (Article article : articleAll) {
			IndexQuery build = new IndexQueryBuilder().withId(article.getId().toString()).withObject(article).build();
			String index = elasticsearchTemplate.index(build);
			
		}
		System.out.println("写入成功");
		
		
	}
	
      
}
