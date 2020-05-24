package com.example.app.service;

import com.example.app.domain.Analysis;
import com.example.app.domain.Project;

public interface ReportService {

	byte[] generatePDF(Project project, Analysis analysis);

}
