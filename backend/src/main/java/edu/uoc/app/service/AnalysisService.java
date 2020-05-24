package edu.uoc.app.service;

import edu.uoc.app.domain.Analysis;
import edu.uoc.app.dto.sast.AnalysisStatusDTO;

public interface AnalysisService {

	Analysis execute(String pathToFolder, Analysis currentAnalysis);

	Analysis createOrUpdateAnalysis(Analysis analysis);

	Analysis findLastAnalysis(String projectKey);

	AnalysisStatusDTO checkStatus(String id);

}
