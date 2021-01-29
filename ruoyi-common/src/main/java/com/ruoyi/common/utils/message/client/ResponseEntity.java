package com.ruoyi.common.utils.message.client;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ResponseEntity implements Response {
	Map<String, String> header = new HashMap<>();
	String stringContent;
	byte[] bytesContent;
	int status = 0;
	Exception ex;
	boolean done = false; //用来判断是否请求结束
	private String uri = "";

	@Override
	public String header(String key) {
		// TODO Auto-generated method stub
		return header.get(key);
	}

	@Override
	public Map<String, String> header() {
		// TODO Auto-generated method stub
		return header;
	}

	public void header(Header[] hs) {
		// TODO Auto-generated method stub
		if (hs != null && hs.length > 0) {
			for (Header h : hs) {
				header.put(h.getName(), h.getValue());
			}
		}
	}

	@Override
	public String charset() {
		// TODO Auto-generated method stub
		String contentType = contentType();
		String[] cc = contentType == null ? null : contentType.split(";");
		if (cc == null || cc.length == 0)
			return null;
		for (String c : cc) {
			if (c.trim().toLowerCase().startsWith("charset")) {
				return c.split("=")[1];
			}
		}
		return null;
	}

	@Override
	public String content() {
		// TODO Auto-generated method stub
		if (stringContent != null)
			return stringContent;
		else if (bytesContent != null) {
			try {
				String ch = charset();
				String charset = StringUtils.isEmpty(ch) ? "utf-8" : ch;
				stringContent = new String(bytesContent, charset);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return stringContent;
	}

	@Override
	public byte[] bytesContent() {
		// TODO Auto-generated method stub
		return bytesContent;
	}

	@Override
	public int contentLength() {
		// TODO Auto-generated method stub
		return bytesContent == null ? 0 : bytesContent.length;
	}

	@Override
	public int status() {
		// TODO Auto-generated method stub
		return status;
	}

	public void header(Map<String, String> head) {
		// TODO Auto-generated method stub
		header.putAll(head);
	}

	public void content(byte[] bytes) {
		// TODO Auto-generated method stub
		bytesContent = bytes;
	}

	public void status(int code) {
		// TODO Auto-generated method stub
		status = code;
	}

	public static ResponseEntity create() {
		// TODO Auto-generated method stub
		return new ResponseEntity();
	}

	public void error(Exception e) {
		// TODO Auto-generated method stub
		ex = e;
		status = -1;
	}

	@Override
	public String error() {
		// TODO Auto-generated method stub
		return ex == null ? "" : ex.getMessage();
	}

	public void done(boolean b) {
		// TODO Auto-generated method stub
		done = b;
	}

	public boolean isDone() {
		return done;
	}

	public void content(String value) {
		// TODO Auto-generated method stub
		stringContent = value;
	}

	public void clear() {
		// TODO Auto-generated method stub
		stringContent = null;
		header.clear();
		header = null;
		bytesContent = null;
	}

	public boolean isJson() {
		return header.entrySet().stream().filter(p -> {
			return p.getKey().toLowerCase().equals("content-type") && p.getValue().contains("json");
		}).findFirst().isPresent();
	}

	@Override
	public String contentType() {
		// TODO Auto-generated method stub
		return header.get("Content-Type");
	}

	@Override
	public Response saveTo(String directory, String filename) throws IOException {
		// TODO Auto-generated method stub				
		if (contentType().contains("stream")) {
			String fullPathName = "";
			if (StringUtils.isEmpty(filename)) {
				String _name = StringUtils.isEmpty(uri) ? "download" : uri.split("?")[0];
				filename = _name.substring(_name.lastIndexOf("/") + 1);
			}
			fullPathName = directory.endsWith("/") ? directory + filename : directory + "/" + filename;
			FileUtils.writeByteArrayToFile(new File(fullPathName), bytesContent());
		}
		return this;
	}

	public void setRequestUri(String uri) {
		// TODO Auto-generated method stub
		this.uri = uri;
	}

}
