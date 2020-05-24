package edu.uoc.app.service;

import java.util.List;

import edu.uoc.app.domain.Project;

public interface ProjectService {

	Project createProject(Project project);

	List<Project> findAllProjects();

	Project findByKey(String key);

	Project addAnalysis(Project project, String analysisId);

	Project findByUserAndRepo(String username, String reponame);

}
