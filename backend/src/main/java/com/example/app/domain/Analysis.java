package com.example.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "Analysis")
@Getter
@Setter
@NoArgsConstructor
public class Analysis {

	@Id
	private String id;

	@Field("SAST")
	private AnalysisSAST sast;

	@Field("SCA")
	private AnalysisSCA sca;

	@Field("Completion")
	private String completion;

	@Field("Status")
	private String status;

}
