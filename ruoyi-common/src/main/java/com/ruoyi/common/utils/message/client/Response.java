package com.ruoyi.common.utils.message.client;

import java.io.IOException;
import java.util.Map;

public interface Response {
	/**
	 * 根据字段获取响应头
	 * @param key
	 * @return
	 */
	public String header(String key);
	/**
	 * 获取整个响应头
	 * @return
	 */
	public Map<String,String> header();
	/**
	 * 获取响应字符串内容
	 * @return
	 */
	public String content();
	/**
	 * 获取响应字节内容
	 * @return
	 */
	public byte[] bytesContent();
	/**
	 * 内容长度
	 * @return
	 */
	public int contentLength();
	/**
	 * 响应状态
	 * @return
	 */
	public int status();
	/**
	 * 获取响应错误
	 * @return
	 */
	public String error();	
	/**
	 * 响应内容类型==header("content-Type")
	 * @return
	 */
	public String contentType();
	/**
	 * 获取响应字符集
	 * @return
	 */
	public String charset();
	/**
	 * 保存响应内容到目录，下载文件
	 * @param directory
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public Response saveTo(String directory, String filename) throws IOException;
}
