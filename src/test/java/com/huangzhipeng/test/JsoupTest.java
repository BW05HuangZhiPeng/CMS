/**
 * 
 * @Title:         JsoupTest.java
 * @Package        com.huangzhipeng.test
 * @Description:   TODO
 * @author:        HuangZhiPeng
 * @date:          2019��10��16�� ����10:48:44
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
 * @date:          2019��10��16�� ����10:48:44     
 */
public class JsoupTest {
	
	@Test
	public void test_cean() throws IOException {
		//��¼������
		int count =0;
		
		//��ȡ���Ӷ���
		Connection connect = Jsoup.connect("https://www.cnblogs.com/cate/all/");
		
		
		//��ȡ�ĵ�����
		Document document = connect.get();
		
		
		//��ȡ��ǰ���ĵ������г�����
		Elements elements = document.select("a[href]");
		
		//����Ԫ�ض���
		for (Element element : elements) {
			//�����ӵ�url��ַ
			String url = element.attr("href");
			//������֤·����ʽ
			//https://www.cnblogs.com/ysocean/p/9143118.html
			//https://www.qu.la/book/101104/5305356.html
			///book/101104/5281139.html
			//https://www.cnblogs.com/zhixiang-org-cn/p/11730561.html
			String regex = "https://www.cnblogs.com/\\w+/p/\\d+.html$";
			
			if (url!=null && Pattern.matches(regex, url)) {
				//���ӵ��ı�����
				String str = element.text();
				System.out.println(url+"====="+str);
				count++;
				
				//��ȡ���µ��ĵ�����
				Document article = Jsoup.connect(url).get();
				//��ȡ���µ�����ԭ������
				Element elementById = article.getElementById("cnblogs_post_body");
				//�жϴ��ı��Ƿ�Ϊ��
				if(elementById !=null) {
					//��ȡ���ı�����
					String content = elementById.text();
					//ȥ�������е������ַ�
					str = str.replace("?","").replace("\"","").replace(":","").replace("/","").replace("*","");
					
					//д���ļ���
					FileUtil.writeFile("D:\\1705DJsoup3\\" + str + ".txt", content, "utf8");
				}
				
				
			}
		}
		System.out.println("��ҳ���ҵ��˸�����������ַ�У�" + count + "ƪ����");
	}

}
