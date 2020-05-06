package com.example.app.service;

import com.example.app.domain.sast.FindSecBugsAnalysis;

public interface AnalysisService {

	FindSecBugsAnalysis executeSAST(String pathToFolder);

}
