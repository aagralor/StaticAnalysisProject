package edu.uoc.app;

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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;

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
import edu.uoc.app.domain.Project;
import edu.uoc.app.repo.ProjectRepository;
import edu.uoc.app.service.ProjectService;

@DataMongoTest
@ContextConfiguration(classes = { UnitaryTest.MDBUnitaryTestConfiguration.class })
public class UnitaryTest extends AbstractBaseUnitaryTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UnitaryTest.class);

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	ProjectService projectService;
	
	@Autowired
	private ProjectRepository prRepo;

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

		// clean
		this.mongoTemplate.dropCollection("Project");
	}

	@Test
	public void testGetAllProjects() {
		// given
		Project project1 = new Project();
		project1.setName("Project name_1");
		project1.setKey("project_key_1");
		project1.setAccessToken("github_access_token_1");
		project1.setUsername("github_username_1");
		Project project2 = new Project();
		project2.setName("Project name_2");
		project2.setKey("project_key_2");
		project2.setAccessToken("github_access_token_2");
		project2.setUsername("github_username_2");
		this.mongoTemplate.save(project1, "Project");
		this.mongoTemplate.save(project2, "Project");

		// when
		List<Project> objectList = projectService.findAllProjects();

		// then
		assertThat(objectList).hasSize(2);

		// clean
		this.mongoTemplate.dropCollection("Project");
	}

	@Test
	public void testLinkAccessToken() {
		// given
		Project project = new Project();
		project.setName("Project name");
		project.setKey("project_key");
		project.setAccessToken("github_access_token");
		project.setUsername("github_username");
		project.setRepositoryName("repo_name");
		this.mongoTemplate.save(project, "Project");

		// when
		Project object = prRepo.findByUserAndRepoName(project.getUsername(), project.getRepositoryName());

		// then
		assertThat(object).isNotNull();

		// clean
		this.mongoTemplate.dropCollection("Project");
	}
	
	@Configuration
	@ComponentScan(basePackages = { "com.example.app.service", "com.example.app.mapper" })
	@EnableMongoRepositories(basePackages = "com.example.app.repo")
	public static class MDBUnitaryTestConfiguration {

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
	}

}
