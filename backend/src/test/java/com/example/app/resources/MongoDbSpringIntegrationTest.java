package com.example.app.resources;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.example.app.domain.Project;
import com.example.app.service.ProjectService;
import com.example.app.service.ProjectServiceImpl;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@DataMongoTest
@ContextConfiguration(classes = { MongoDbSpringIntegrationTest.MDBIntegrationTestConfiguration.class })
public class MongoDbSpringIntegrationTest extends AbstractBaseServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MongoDbSpringIntegrationTest.class);

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	ProjectService projectService;
	
	@Test
	public void contextLoads() {
		LOGGER.debug("MongoDbSpringIntegrationTest: contextLoads()");
	}

	@Test
	public void testMongo() {
		// given
		DBObject objectToSave = BasicDBObjectBuilder.start().add("key", "value").get();

		// when
		mongoTemplate.save(objectToSave, "collection");

		// then
		List<DBObject> objectList = mongoTemplate.findAll(DBObject.class, "collection");
		assertThat(objectList).extracting("key").containsOnly("value");
	}

	@Test
	public void testCreateProject() {
		// given
		Project project = new Project();
		project.setName("Project name");
		project.setKey("project_key");
		project.setAccessToken("github_access_token");
		project.setUsername("github_username");
		
		// when
		projectService.createProject(project);
		
		// then
		List<Project> objectList = mongoTemplate.findAll(Project.class, "Project");
		assertThat(objectList).extracting("Key").containsOnly("project_key");
	}
	
	@Configuration
	@EnableMongoRepositories(basePackages = "com.example.app.repo")
	public static class MDBIntegrationTestConfiguration {

		@Bean
		public MongoTemplate mongoTemplate() throws UnknownHostException, IOException {
		    MongodExecutable mongodExecutable;
		    MongoTemplate mongoTemplate;
			
			String ip = "localhost";
			int port = 27018;

			IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
					.net(new Net(ip, port, Network.localhostIsIPv6())).build();

			MongodStarter starter = MongodStarter.getDefaultInstance();
			mongodExecutable = starter.prepare(mongodConfig);
			mongodExecutable.start();
			mongoTemplate = new MongoTemplate(new MongoClient(ip, port), "test");
			
			return mongoTemplate;
		}
		
		@Bean
		public ProjectService projectService() {
			return new ProjectServiceImpl();
		}
	}

}