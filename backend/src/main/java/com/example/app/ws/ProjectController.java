package com.example.app.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.domain.AnalysisSAST;
import com.example.app.domain.Project;
import com.example.app.domain.sast.FindSecBugsAnalysis;
import com.example.app.mapper.AnalysisSASTMapper;
import com.example.app.service.AnalysisService;
import com.example.app.service.GithubService;
import com.example.app.service.ProjectService;

@RestController
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@Autowired
	GithubService githubService;

	@Autowired
	AnalysisService analysisService;

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
	public ResponseEntity<AnalysisSAST> startAnalysys(@RequestParam String key) {

		Project project = this.projectService.findByKey(key);
		AnalysisSAST  currentSast = new AnalysisSAST();
		currentSast.setCompletion("10");
		currentSast.setStatus("RUNNING");
		AnalysisSAST response = this.analysisService.createOrUpdateAnalysis(currentSast);
		Project updatedProject = this.projectService.addAnalysisSAST(project, response.getId());

		new Thread(() -> {
    		String downloadPath = this.githubService.downloadRepository(updatedProject.getRepositoryName(), updatedProject.getBranchName(),
    				updatedProject.getUsername(), (updatedProject.getAccessToken().isEmpty() ? null : updatedProject.getAccessToken()));

    		try {
    			FindSecBugsAnalysis analysis = this.analysisService.executeSAST(downloadPath, currentSast);

    			AnalysisSAST sast = this.analysisSASTMapper.toAnalysisSAST(analysis);
    			sast.setId(currentSast.getId());
    			sast.setCompletion("100");
    			sast.setStatus("COMPLETE");
    			this.analysisService.createOrUpdateAnalysis(sast);
    		} catch (RuntimeException e) {
    			e.printStackTrace();
    			currentSast.setCompletion("100");
    			currentSast.setStatus("CANCELLED");
    			this.analysisService.createOrUpdateAnalysis(currentSast);
    		}
		}).start();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(path = "/analysis")
	public ResponseEntity<AnalysisSAST> findLastAnalysisSast(@RequestParam String projectKey) {

		AnalysisSAST sast = this.analysisService.findLastAnalysisSast(projectKey);

		return new ResponseEntity<>(sast, HttpStatus.OK);
	}

}
