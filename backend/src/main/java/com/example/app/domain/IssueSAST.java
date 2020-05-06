package com.example.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "IssueSAST")
@Getter
@Setter
@NoArgsConstructor
public class IssueSAST {

	@Id
	private String id;

	@Field("Reference")
	private String reference;

	@Field("Name")
	private String name;

	@Field("Priority")
	private String priority;
	
	@Field("PriorityValue")
	private String priorityValue;
	
	@Field("Rank")
	private String rank;

	@Field("Text")
	private String text;

	@Field("TextHtml")
	private String textHtml;

	@Field("WarningHtml")
	private String warningHtml;

	@Field("FileName")
	private String fileName;

	@Field("LineNumber")
	private String lineNumber;
	
	@Field("ClassName")
	private String className;

	@Field("Message")
	private String message;
	
	@Field("Abbrev")
	private String abbrev;
	
	@Field("Category")
	private String category;

	@Field("MethodName")
	private String methodName;
}
