package com.huangzhipeng.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 
 * @author zhuzg
 *
 */
public class FileUtil {



	/***
	 * @Title: writeFile * @Description: 按照指定的编码把内容写入指定的文件中 * @param path * @param
	 *         content * @param charset * @throws IOException * @return: void
	 */
	public static void writeFile(String path, String content, String charset) throws IOException {
		// 创建写入的文件
		File file = new File(path);
		// 创建输出流对象
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
		bw.write(content);
		bw.flush();
		bw.close();

	}

}
