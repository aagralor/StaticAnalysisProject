package com.example.app.domain.sca.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Evidence {

	private String type;

	private String confidence;

	private String source;

	private String name;

	private String value;

}
