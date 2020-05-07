package com.example.app.service;

import com.example.app.domain.AnalysisSAST;
import com.example.app.domain.sast.FindSecBugsAnalysis;
import com.example.app.dto.sast.AnalysisStatusDTO;

public interface AnalysisService {

	FindSecBugsAnalysis executeSAST(String pathToFolder, AnalysisSAST currentSast);

	AnalysisSAST createOrUpdateAnalysis(AnalysisSAST analysis);

	AnalysisSAST findLastAnalysisSast(String projectKey);

	AnalysisStatusDTO checkStatus(String id);

}
