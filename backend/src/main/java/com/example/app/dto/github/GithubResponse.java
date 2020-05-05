package com.example.app.dto.github;

public class GithubResponse {

	private GithubIdentity body;
	private String statusCode;
	private Integer statusCodeValue;

	public GithubResponse() { }

	public GithubIdentity getBody() {
		return this.body;
	}

	public void setBody(GithubIdentity body) {
		this.body = body;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getStatusCodeValue() {
		return this.statusCodeValue;
	}

	public void setStatusCodeValue(Integer statusCodeValue) {
		this.statusCodeValue = statusCodeValue;
	}

}
