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

import com.example.app.domain.github.WebhookInstallation;
import com.example.app.domain.github.WebhookRepository;
import com.example.app.service.GithubService;

@RestController
public class PingResource {

	@Autowired
	GithubService githubService;

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
			WebhookInstallation whio = new WebhookInstallation();
			Map<String, Object> inst = (Map<String, Object>) temp.get("installation");
			Map<String, String> instAccount = (Map<String, String>) inst.get("account");
			String repoSelection = ("Installation".equalsIgnoreCase(event) ? "repositories" : "repositories_added");
			whio.setUsername(instAccount.get("login"));
			whio.setInstallationId((Integer) inst.get("id"));
			List<Map<String, String>> repoList = ((List<Map<String, String>>) temp.get(repoSelection));
			List<WebhookRepository> whrList = new ArrayList<WebhookRepository>();
			for (Map<String, String> repo : repoList) {
				WebhookRepository whRepo = new WebhookRepository();
				whRepo.setId(repo.get("node_id"));
				whRepo.setName(repo.get("name"));
				whrList.add(whRepo);
			}
			whio.setRepoList(whrList);
			response = this.githubService.createWebhookInstallation(whio);
		}
		
		if ("Added".equalsIgnoreCase(action) && "Installation_repositories".equalsIgnoreCase(event))                                                                                                                               {
			WebhookInstallation whio = new WebhookInstallation();
			Map<String, Object> inst = (Map<String, Object>) temp.get("installation");
			Map<String, String> instAccount = (Map<String, String>) inst.get("account");
			String repoSelection = ("Installation".equalsIgnoreCase(event) ? "repositories" : "repositories_added");
			whio.setUsername(instAccount.get("login"));
			whio.setInstallationId((Integer) inst.get("id"));
			List<Map<String, String>> repoList = ((List<Map<String, String>>) temp.get(repoSelection));
			List<WebhookRepository> whrList = new ArrayList<WebhookRepository>();
			for (Map<String, String> repo : repoList) {
				WebhookRepository whRepo = new WebhookRepository();
				whRepo.setId(repo.get("node_id"));
				whRepo.setName(repo.get("name"));
				whrList.add(whRepo);
			}
			whio.setRepoList(whrList);
			response = this.githubService.createWebhookInstallation(whio);
		}

		return new ResponseEntity<>(ob, HttpStatus.OK);
	}
}
