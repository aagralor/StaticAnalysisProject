package com.example.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "Suppression")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Suppression {

	@Id
	private String id;

	@Field("Notes")
	private String notes;

	@Field("FilePath")
	private String filePath;

	@Field("FilePathIsRegex")
	private Boolean FilePathIsRegex;

	@Field("SHA1")
	private String sha1;

	@Field("CVE")
	private String cve;

}
