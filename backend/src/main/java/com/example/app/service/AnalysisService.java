package com.example.app.service;

import com.example.app.domain.Analysis;
import com.example.app.dto.sast.AnalysisStatusDTO;

public interface AnalysisService {

	Analysis execute(String pathToFolder, Analysis currentAnalysis);

	Analysis createOrUpdateAnalysis(Analysis analysis);

	Analysis findLastAnalysis(String projectKey);

	AnalysisStatusDTO checkStatus(String id);

}
