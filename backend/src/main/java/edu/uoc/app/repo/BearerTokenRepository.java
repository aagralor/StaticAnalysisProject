package edu.uoc.app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.uoc.app.domain.github.BearerToken;

@Repository
public interface BearerTokenRepository extends MongoRepository<BearerToken, String> {

	BearerToken findByUsername(String username);

//	@Query("{'GithubUsername' : ?0 , 'GithubRepositoryName' : ?1}")
//	Project findByUserAndRepoName(String user, String repositoryName);

}
