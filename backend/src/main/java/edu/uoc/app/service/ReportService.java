package edu.uoc.app.service;

import edu.uoc.app.domain.Analysis;
import edu.uoc.app.domain.Project;

public interface ReportService {

	byte[] generatePDF(Project project, Analysis analysis);

}
