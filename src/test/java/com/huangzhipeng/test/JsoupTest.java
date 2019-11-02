/**
 * 
 * @Title:         JsoupTest.java
 * @Package        com.huangzhipeng.test
 * @Description:   TODO
 * @author:        HuangZhiPeng
 * @date:          2019年10月16日 上午10:48:44
 * @version:       V1.0
 */
package com.huangzhipeng.test;

import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/**   
 * @ClassName:     JsoupTest   
 * @Description:   TODO
 * @author:        HuangZhiPeng
 * @date:          2019年10月16日 上午10:48:44     
 */
public class JsoupTest {
	
	@Test
	public void test_cean() throws IOException {
		//记录文章数
		int count =0;
		
		//获取连接对象
		Connection connect = Jsoup.connect("https://www.cnblogs.com/cate/all/");
		
		
		//获取文档对象
		Document document = connect.get();
		
		
		//获取当前啊文档的所有超链接
		Elements elements = document.select("a[href]");
		
		//遍历元素对象
		for (Element element : elements) {
			//超链接的url地址
			String url = element.attr("href");
			//正则验证路径格式
			//https://www.cnblogs.com/ysocean/p/9143118.html
			//https://www.qu.la/book/101104/5305356.html
			///book/101104/5281139.html
			//https://www.cnblogs.com/zhixiang-org-cn/p/11730561.html
			String regex = "https://www.cnblogs.com/\\w+/p/\\d+.html$";
			
			if (url!=null && Pattern.matches(regex, url)) {
				//连接的文本内容
				String str = element.text();
				System.out.println(url+"====="+str);
				count++;
				
				//获取文章的文档对象
				Document article = Jsoup.connect(url).get();
				//获取文章的内容原属对象
				Element elementById = article.getElementById("cnblogs_post_body");
				//判断纯文本是否为空
				if(elementById !=null) {
					//获取纯文本内容
					String content = elementById.text();
					//去除标题中的特殊字符
					str = str.replace("?","").replace("\"","").replace(":","").replace("/","").replace("*","");
					
					//写道文件中
					FileUtil.writeFile("D:\\1705DJsoup3\\" + str + ".txt", content, "utf8");
				}
				
				
			}
		}
		System.out.println("首页中找到了复合条件的网址有：" + count + "篇文章");
	}

}
