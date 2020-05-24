package edu.uoc.app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import edu.uoc.app.domain.Project;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

	Project findByKey(String key);

	@Query("{'GithubUsername' : ?0 , 'GithubRepositoryName' : ?1}")
	Project findByUserAndRepoName(String user, String repositoryName);

}
