package com.example.app.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.domain.Analysis;
import com.example.app.domain.Project;
import com.example.app.domain.github.WebhookInstallation;
import com.example.app.domain.github.WebhookPush;
import com.example.app.domain.github.WebhookRepository;
import com.example.app.service.AnalysisService;
import com.example.app.service.GithubService;
import com.example.app.service.ProjectService;

@RestController
public class PingResource {

	@Autowired
	GithubService ghService;

	@Autowired
	AnalysisService anService;

	@Autowired
	ProjectService prService;

	@GetMapping(path = "/ping")
	public ResponseEntity<Map<String, String>> ping() {
		Map<String, String> map = new HashMap<>();
		map.put("ping", "pong");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@PostMapping(path = "/webhook")
	public ResponseEntity<Object> webhook(@RequestBody Object ob, @RequestHeader("User-Agent") String userAgent,
			@RequestHeader("X-GitHub-Delivery") String delivery, @RequestHeader("X-GitHub-Event") String event) {

		System.out.println("Event:");
		System.out.println(event);
		System.out.println("Body:");
		System.out.println(ob);

		Object response = ob;
		Map<String, Object> temp = (Map<String, Object>) ob;
		String action = (String) temp.get("action");

		if ("Created".equalsIgnoreCase(action) && "Installation".equalsIgnoreCase(event)) {
			WebhookInstallation whio = getWebhookInstallation(temp, action);
			response = this.ghService.createWebhookInstallation(whio);
		}

		if ("Added".equalsIgnoreCase(action) && "Installation_repositories".equalsIgnoreCase(event)) {
			WebhookInstallation whio = getWebhookInstallation(temp, action);
			response = this.ghService.updateAddAccessToken(whio);
		}

		if ("Removed".equalsIgnoreCase(action) && "Installation_repositories".equalsIgnoreCase(event)) {
			WebhookInstallation whio = getWebhookInstallation(temp, action);
			response = this.ghService.updateRemoveAccessToken(whio);
		}

		if ("Deleted".equalsIgnoreCase(action) && "Installation".equalsIgnoreCase(event)) {
			WebhookInstallation whio = getWebhookInstallation(temp, action);
			response = this.ghService.removeAccessToken(whio);
		}

		if ("Push".equalsIgnoreCase(event)) {
			WebhookPush whp = getWebhookPush(temp);
			response = this.ghService.createWebhookPush(whp);
			Project project = this.prService.findByUserAndRepo(whp.getUsername(), whp.getRepo().getName());
			if (whp.getBranchRef().endsWith(project.getBranchName())) {
				System.out.println("Match Project");
				this.initAnalysysByPush(project);
			}

			System.out.println(whp);
			System.out.println(project);

		}

		return new ResponseEntity<>(ob, HttpStatus.OK);
	}

	private void initAnalysysByPush(Project project) {

		Analysis  curAnl = new Analysis();
		curAnl.setCompletion("10");
		curAnl.setStatus("RUNNING");
		Analysis response = this.anService.createOrUpdateAnalysis(curAnl);
		Project updatedProject = this.prService.addAnalysis(project, response.getId());

		new Thread(() -> {
    		String downloadPath = this.ghService.downloadRepository(updatedProject.getRepositoryName(), updatedProject.getBranchName(),
    				updatedProject.getUsername(), (updatedProject.getBearerToken() != null ? updatedProject.getBearerToken() : null));

    		try {
    			Analysis analysis = this.anService.execute(downloadPath, curAnl);

    			analysis.setCompletion("100");
    			analysis.setStatus("COMPLETE");
    			this.anService.createOrUpdateAnalysis(analysis);
    		} catch (RuntimeException e) {
    			e.printStackTrace();
    			curAnl.setCompletion("100");
    			curAnl.setStatus("CANCELLED");
    			this.anService.createOrUpdateAnalysis(curAnl);
    		}
		}).start();
	}

	private WebhookInstallation getWebhookInstallation(Map<String, Object> in, String repoAction) {

		WebhookInstallation whio = new WebhookInstallation();
		Map<String, Object> inst = (Map<String, Object>) in.get("installation");
		Map<String, String> instAccount = (Map<String, String>) inst.get("account");
		whio.setUsername(instAccount.get("login"));
		whio.setInstallationId((Integer) inst.get("id"));
		if (!"Deleted".equalsIgnoreCase(repoAction)) {
			List<Map<String, String>> repoList = ((List<Map<String, String>>) in
					.get(("Created".equalsIgnoreCase(repoAction) ? "repositories" : "repositories_added")));
			List<WebhookRepository> whrList = new ArrayList<WebhookRepository>();
			for (Map<String, String> repo : repoList) {
				WebhookRepository whRepo = new WebhookRepository();
				whRepo.setId(repo.get("node_id"));
				whRepo.setName(repo.get("name"));
				whrList.add(whRepo);
			}
			whio.setRepoList(whrList);
			if ("Removed".equalsIgnoreCase(repoAction) || "Added".equalsIgnoreCase(repoAction)) {
				List<Map<String, String>> repoListToQuit = ((List<Map<String, String>>) in.get("repositories_removed"));
				List<WebhookRepository> whrListToQuit = new ArrayList<WebhookRepository>();
				for (Map<String, String> repo : repoListToQuit) {
					WebhookRepository whRepo = new WebhookRepository();
					whRepo.setId(repo.get("node_id"));
					whRepo.setName(repo.get("name"));
					whrListToQuit.add(whRepo);
				}
				whio.setRepoListToQuit(whrListToQuit);
			}
		}

		return whio;

	}

	private WebhookPush getWebhookPush(Map<String, Object> in) {

		WebhookPush whp = new WebhookPush();
		Map<String, String> pusher = (Map<String, String>) in.get("pusher");
		whp.setUsername(pusher.get("name"));
		whp.setBranchRef((String) in.get("ref"));
		whp.setBefore((String) in.get("before"));
		whp.setAfter((String) in.get("after"));
		Map<String, String> repo = (Map<String, String>) in.get("repository");
		WebhookRepository whRepo = new WebhookRepository();
		whRepo.setId(repo.get("node_id"));
		whRepo.setName(repo.get("name"));
		whp.setRepo(whRepo);

		return whp;
	}

}
