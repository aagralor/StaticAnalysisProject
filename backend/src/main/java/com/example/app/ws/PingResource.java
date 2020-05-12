package com.example.app.ws;

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

import com.example.app.dto.github.WebhookInstallation;
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

		System.out.println(ob);
		System.out.println(userAgent);
		System.out.println(delivery);
		System.out.println(event);

		Object response = ob;

		if ("Installation".equalsIgnoreCase(event) || "installation_repositories".equalsIgnoreCase(event)) {
			WebhookInstallation whio = new WebhookInstallation();
			Map<String, Object> temp = (Map<String, Object>) ob;
			Map<String, Object> inst = (Map<String, Object>) temp.get("installation");
			Map<String, String> instAccount = (Map<String, String>) inst.get("account");
			String repoSelection = ("Installation".equalsIgnoreCase(event) ? "repositories" : "repositories_added");
			Map<String, String> repo = ((List<Map<String, String>>) temp.get(repoSelection)).get(0);
			whio.setUsername(instAccount.get("login"));
			whio.setInstallationId((Integer) inst.get("id"));
			whio.setRepositoryId(repo.get("node_id"));
			whio.setRepositoryName(repo.get("name"));

			response = this.githubService.createWebhookInstallation(whio);
		}

		return new ResponseEntity<>(ob, HttpStatus.OK);
	}
}
