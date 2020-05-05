package com.example.app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.app.domain.mongo.Project;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

}
