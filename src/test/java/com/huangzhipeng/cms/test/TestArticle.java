package com.huangzhipeng.cms.test;

import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huangzhipeng.cms.entity.Article;
import com.huangzhipeng.cms.service.ArticleService;
import com.huangzhipeng.common.uitls.FileUtil;

/**
 * @author huangzhipeng
 *
 * 2019年9月23日
 */
public class TestArticle  extends TestBase{
	
	@Autowired
	ArticleService arServie;
	
	// D:\高级\复习\src\上传下载
	
	@Test
	public void TestImp(){
		
		int channelId[]={1,2,3,4,5,6,7,8};
		
		List<String> fileList = FileUtil.getFileList("D:\\学习文件\\web知识点");

		Random random = new Random();
		for (String string : fileList) {
			
			try {
				Article article = new Article();
				String content;
				content = FileUtil.readFile(string);
				System.out.println(content);
				article.setContent(content);
				article.setTitle(string.substring(string.lastIndexOf('\\')+1 , string.lastIndexOf('.')));
				article.setHits(10 + random.nextInt(90));//
				article.setHot(random.nextInt(2));
				article.setUserId(44);
				article.setChannelId(channelId[random.nextInt(7)]);
				article.setCategoryId(channelId[random.nextInt(7)]);
				arServie.add(article);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
			
			
		}
	}
	

}
