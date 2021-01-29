package com.ruoyi.common.utils.message.client;

public interface Request {

	public static RequestBuilder newHttpRequestBuilder() {
		return new HttpRequestBuilder();
	}

	public static final String POST = "POST";
	public static final String GET = "GET";

	public static enum ContentType {
		XML("application/xml"), JSON("application/json"), RAW("application/octet-stream"),
		FORM("application/x-www-form-urlencoded"), TEXT("text/plain; charset=UTF-8");
		private String value;

		ContentType(String type) {
			this.value = type;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return value;
		}
	}
	
	public RequestBuilder builder();

	public Response send(String data);

	public Response send(String url, String data);

	public void release();
}
