package com.example.app.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.domain.Analysis;
import com.example.app.domain.Project;
import com.example.app.domain.Suppression;
import com.example.app.dto.sast.AnalysisStatusDTO;
import com.example.app.mapper.AnalysisSASTMapper;
import com.example.app.service.AnalysisService;
import com.example.app.service.GithubService;
import com.example.app.service.ProjectService;
import com.example.app.service.ReportService;
import com.example.app.service.SuppressionService;

@RestController
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@Autowired
	GithubService githubService;

	@Autowired
	AnalysisService analysisService;

	@Autowired
	ReportService reportService;

	@Autowired
	SuppressionService suppressionService;

	@Autowired
	AnalysisSASTMapper analysisSASTMapper;

	@PostMapping(path = "/project")
	public ResponseEntity<Project> create(@RequestBody Project project) {

		Project response = this.projectService.createProject(project);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(path = "/project")
	public ResponseEntity<List<Project>> getAll() {

		List<Project> response = this.projectService.findAllProjects();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(path = "/project/analysis")
	public ResponseEntity<Analysis> startAnalysys(@RequestParam String key) {

		Project project = this.projectService.findByKey(key);
		Analysis  currentAnalysis = new Analysis();
		currentAnalysis.setCompletion("10");
		currentAnalysis.setStatus("RUNNING");
		Analysis response = this.analysisService.createOrUpdateAnalysis(currentAnalysis);
		Project updatedProject = this.projectService.addAnalysis(project, response.getId());

		new Thread(() -> {
    		String downloadPath = this.githubService.downloadRepository(updatedProject.getRepositoryName(), updatedProject.getBranchName(),
    				updatedProject.getUsername(), (updatedProject.getBearerToken() != null ? updatedProject.getBearerToken() : null));

    		this.suppressionService.generateSuppressionsFile(downloadPath);

    		try {
    			Analysis analysis = this.analysisService.execute(downloadPath, currentAnalysis);

    			analysis.setCompletion("100");
    			analysis.setStatus("COMPLETE");
    			this.analysisService.createOrUpdateAnalysis(analysis);
    		} catch (RuntimeException e) {
    			e.printStackTrace();
    			currentAnalysis.setCompletion("100");
    			currentAnalysis.setStatus("CANCELLED");
    			this.analysisService.createOrUpdateAnalysis(currentAnalysis);
    		}
		}).start();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(path = "/analysis")
	public ResponseEntity<Analysis> findLastAnalysisSast(@RequestParam String projectKey) {

		Analysis resp = this.analysisService.findLastAnalysis(projectKey);

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping(path = "/analysis/status")
	public ResponseEntity<AnalysisStatusDTO> checkStatusAnalysisSast(@RequestParam String id) {

		AnalysisStatusDTO sastCompletion = this.analysisService.checkStatus(id);

		return new ResponseEntity<>(sastCompletion, HttpStatus.OK);
	}

	@GetMapping(path = "/project/report")
	public ResponseEntity<byte[]> generateReport(@RequestParam String key) {

		Project project = this.projectService.findByKey(key);

		Analysis analysis = this.analysisService.findLastAnalysis(key);

		byte[] report = this.reportService.generatePDF(project, analysis);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
	    String filename = project.getName() + "_report.pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		return new ResponseEntity<>(report, headers, HttpStatus.OK);
	}

	@GetMapping(path = "/suppress")
	public ResponseEntity<List<Suppression>> getAllSuppressions() {

		List<Suppression> response = this.suppressionService.findAll();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
