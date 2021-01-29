package com.ruoyi.common.utils.message.client;



import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


/**
 * 简单封装，提供连接池模式请求

 *
 */
public class HttpRequestBuilder implements RequestBuilder {
	String uri;
	Map<String, String> header = new HashMap<>();
	private int timeout = 10000;
	private String method = Request.POST;
	private boolean ssl = false;

	private int defaultNum = 20;
	private int maxNum = 200;
	PoolingHttpClientConnectionManager connections;
	HttpRequestBase httpRequest;
	CloseableHttpClient httpClient = null;
	private RequestConfig requestConfig;
	HttpClientBuilder httpClientBuilder;
	private Charset charset = Charset.forName("utf-8");

	@Override
	public RequestBuilder uri(String uri) {
		// TODO Auto-generated method stub
		this.uri = uri;
		this.ssl = uri.startsWith("https://") ? true : false;
		return this;
	}

	@Override
	public RequestBuilder method(String method) {
		// TODO Auto-generated method stub
		this.method = method;
		return this;
	}

	@Override
	public RequestBuilder contentType(Request.ContentType contentType) {
		// TODO Auto-generated method stub
		header("Content-Type", contentType.toString());
		return this;
	}

	@Override
	public RequestBuilder header(String key, String value) {
		// TODO Auto-generated method stub
		header.put(key, value);
		if (httpClient != null) {
			//update header
			setHeader();
		}
		return this;
	}

	@Override
	public RequestBuilder timeout(int millisecond) {
		// TODO Auto-generated method stub
		timeout = millisecond;
		return this;
	}

	@Override
	public RequestBuilder charset(String charset) {
		// TODO Auto-generated method stub
		this.charset = Charset.forName(charset);
		return this;
	}

	@Override
	public RequestBuilder connections(int defaultNum, int maxNum) {
		// TODO Auto-generated method stub
		this.defaultNum = defaultNum;
		this.maxNum = maxNum;
		return this;
	}

	@Override
	public Request build() throws Exception {
		// TODO Auto-generated method stub
		initConnections();
		instanceClient();
		RequestHttpClient _client = new RequestHttpClient(this);
		return _client;
	}

	private void instanceClient() throws Exception {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(uri)) {
			throw new Exception("must set server url before build()");
		}
		if (httpRequest == null) {
			if (Request.GET.equalsIgnoreCase(method))
				httpRequest = new HttpGet(uri);
			else if (Request.POST.equalsIgnoreCase(method)) {
				httpRequest = new HttpPost(uri);
			}
		}
		setHeader();
		requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout)
				.setSocketTimeout(timeout).build();
	}

	private void setHeader() {
		// TODO Auto-generated method stub
		if (header != null && header.size() > 0) {
			for (Map.Entry<String, String> e : header.entrySet()) {
				httpRequest.setHeader(e.getKey(), e.getValue());
			}
		}
	}

	@SuppressWarnings("deprecation")
	private boolean initConnections() throws Exception {
		// TODO Auto-generated method stub
		if (connections != null)
			return true;
		Registry<ConnectionSocketFactory> socketFactoryRegistry = null;
		if (ssl) {
			SSLContext sslcontext;
			sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
			HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, hostnameVerifier);
			socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("https", sslsf)
					.build();
		} else {
			socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory()).build();
		}

		connections = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connections.setMaxTotal(maxNum);
		connections.setDefaultMaxPerRoute(defaultNum);
		SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(timeout).build();
		connections.setDefaultSocketConfig(socketConfig);
		return true;
	}

	public CloseableHttpClient getConnection() {
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connections)
				.setDefaultRequestConfig(requestConfig).build();
		if (connections != null && connections.getTotalStats() != null) {
			//Console.log("now client pool " + connections.getTotalStats().toString());
		}
		return httpClient;
	}

	public String uri() {
		// TODO Auto-generated method stub
		return this.uri;
	}

	public Charset charset() {
		return this.charset;
	}

	public HttpUriRequest httpRequest() {
		// TODO Auto-generated method stub
		return this.httpRequest;
	}

	public String method() {
		// TODO Auto-generated method stub
		return this.method;
	}

	public void resetUri(String url) {
		// TODO Auto-generated method stub
		this.uri = url;
		httpRequest.setURI(URI.create(url));
	}

	public void release() {
		// TODO Auto-generated method stub
		httpRequest = null;
		if (connections != null)
			connections.close();
	}

}
