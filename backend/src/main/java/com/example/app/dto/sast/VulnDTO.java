package com.example.app.dto.sast;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VulnDTO {

	@JsonProperty("access_token")
	private String value;

	@JsonProperty("tiken_type")
	private String type;

	private String scope;

	public VulnDTO() { }

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}