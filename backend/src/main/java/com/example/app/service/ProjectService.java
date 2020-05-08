package com.example.app.service;

import java.util.List;

import com.example.app.domain.Project;

public interface ProjectService {

	Project createProject(Project project);

	List<Project> findAllProjects();

	Project findByKey(String key);

	Project addAnalysis(Project project, String analysisId);

}
