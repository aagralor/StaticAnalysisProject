package edu.uoc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uoc.app.domain.Project;
import edu.uoc.app.repo.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectRepository repo;

	@Override
	public Project createProject(Project project) {

		Project response = this.repo.save(project);

		return response;
	}

	@Override
	public List<Project> findAllProjects() {

		List<Project> response = this.repo.findAll();

		return response;
	};

	@Override
	public Project findByKey(String key) {

		Project response = this.repo.findByKey(key);

		return response;
	}

	@Override
	public Project findByUserAndRepo(String username, String reponame) {

		Project response = this.repo.findByUserAndRepoName(username, reponame);

		return response;
	}

	@Override
	public Project addAnalysis(Project project, String analysisId) {

		project.addAnalysis(analysisId);

		Project response = this.repo.save(project);

		return response;
	}

	public static <T> void main(String[] args) {
		System.out.println("Bye World");
	}

}
