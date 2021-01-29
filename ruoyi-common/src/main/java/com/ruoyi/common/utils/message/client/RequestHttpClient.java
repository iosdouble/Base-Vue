package com.ruoyi.common.utils.message.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class RequestHttpClient implements Request {

	HttpRequestBuilder httpRequestBuilder;

	public RequestHttpClient(HttpRequestBuilder httpRequestBuilder) {
		// TODO Auto-generated constructor stub
		this.httpRequestBuilder = httpRequestBuilder;
	}

	@Override
	public RequestBuilder builder() {
		// TODO Auto-generated method stub
		return httpRequestBuilder;
	}

	@Override
	public Response send(String data) {
		// TODO Auto-generated method stub
		return send(httpRequestBuilder.uri(), data);
	}

	@Override
	public Response send(String url, String data) {
		// TODO Auto-generated method stub		
		ResponseEntity myResponse = ResponseEntity.create();
		myResponse.setRequestUri(url);
		if (httpRequestBuilder == null) {
			myResponse.error(new Exception("HttpRequestBuild failed"));
			return myResponse;
		}
		CloseableHttpResponse response = null;
		try {
			if (!url.equals(httpRequestBuilder.uri())) {
				httpRequestBuilder.resetUri(url);
			}
			if (POST.equals(httpRequestBuilder.method()) && data != null)
				setEntity(data);
			CloseableHttpClient httpClient = httpRequestBuilder.getConnection();
			response = httpClient.execute(httpRequestBuilder.httpRequest());
			myResponse.status(response.getStatusLine().getStatusCode());
			myResponse.header(response.getAllHeaders());
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				myResponse.content(EntityUtils.toByteArray(entity));
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			myResponse.error(e);
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					//no close httpClient, for reuse
					response.close();
				} catch (Exception e) {

				}
			}
		}
		return myResponse;
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		httpRequestBuilder.release();
	}

	private void setEntity(String body) throws UnsupportedEncodingException {
		if (StringUtils.isEmpty(body))
			return;
		HttpPost httpPost = (HttpPost) this.httpRequestBuilder.httpRequest();
		if (body.indexOf("&") > 0 || body.indexOf("=") > 0) {
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			String[] params = body.split("&");
			for (String str : params) {
				formParams.add(new BasicNameValuePair(str.substring(0, str.indexOf("=")),
						str.substring(str.indexOf("=") + 1)));
			}
			httpPost.setEntity(
					new StringEntity(URLEncodedUtils.format(formParams, httpRequestBuilder.charset())));
		} else {
			httpPost.setEntity(new StringEntity(body, httpRequestBuilder.charset()));
		}

	}

}
