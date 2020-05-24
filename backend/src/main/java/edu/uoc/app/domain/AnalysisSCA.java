package edu.uoc.app.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "AnalysisSCA")
@Getter
@Setter
@NoArgsConstructor
public class AnalysisSCA {

	@Id
	private String id;

	@Field("Name")
	private String name;

	@Field("ReportDate")
	private String reportDate;

	@Field("DataSourceList")
	private List<String> dataSourceList;

	@Field("DependencyList")
	private List<DependencySCA> dependencyList;
}
