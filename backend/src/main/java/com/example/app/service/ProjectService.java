package com.example.app.service;

import java.util.List;

import com.example.app.domain.mongo.Project;

public interface ProjectService {

	Project createProject(Project project);

	List<Project> findAllProjects();

}
