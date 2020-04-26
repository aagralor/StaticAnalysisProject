package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.Project;
import com.example.app.repo.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectRepository repo;

	public Project createProject(Project project) {

		Project response = this.repo.save(project);

		return response;
	}

	@Override
	public List<Project> findAllProjects() {

		List<Project> response = this.repo.findAll();

		return response;
	};

	public static <T> void main(String[] args) {
		System.out.println("Bye World");
	}

}
