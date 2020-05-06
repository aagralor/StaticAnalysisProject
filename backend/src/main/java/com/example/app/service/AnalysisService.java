package com.example.app.service;

import com.example.app.domain.AnalysisSAST;
import com.example.app.domain.sast.FindSecBugsAnalysis;

public interface AnalysisService {

	FindSecBugsAnalysis executeSAST(String pathToFolder, AnalysisSAST currentSast);

	AnalysisSAST createOrUpdateAnalysis(AnalysisSAST analysis);

	AnalysisSAST findLastAnalysisSast(String projectKey);

}