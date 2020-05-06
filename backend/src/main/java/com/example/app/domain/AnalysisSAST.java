package com.example.app.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "AnalysisSAST")
@Getter
@Setter
@NoArgsConstructor
public class AnalysisSAST {

	@Id
	private String id;

	@Field("Version")
	private String version;

	@Field("Sequence")
	private String sequence;

	@Field("Timestamp")
	private String timestamp;

	@Field("AnalysisTimestamp")
	private String analysisTimestamp;

	@Field("Release")
	private String release;

	@Field("ProjectName")
	private String projectName;

	@Field("JarList")
	private List<String> jarList;

	@Field("IssueList")
	private List<IssueSAST> issueList;

	@Field("Completion")
	private String completion;

	@Field("Status")
	private String status;

}
