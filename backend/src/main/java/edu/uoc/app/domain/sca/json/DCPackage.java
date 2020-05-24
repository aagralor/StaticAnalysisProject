package edu.uoc.app.domain.sca.json;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "DCPackage")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DCPackage {

	@Field("Id")
	private String id;

	@Field("Confidence")
	private String confidence;

	@Field("URL")
	private String url;

}
