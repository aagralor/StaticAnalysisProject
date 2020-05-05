package com.example.app.ws;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.app.dto.github.GithubIdentity;

@RestController
public class GithubController {

	@PostMapping(path = "/github/accesstoken")
	public ResponseEntity<Object> getGithubAccessToken(@RequestBody GithubIdentity identity) {

		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl = "https://github.com/login/oauth/access_token?client_id=Iv1.9ad3617300b9f691&client_secret=d59cbb6f3c4f09e858f9a9a7ad9b309dfd8da700&code=";
		ResponseEntity<Object> response = restTemplate.getForEntity(fooResourceUrl + identity.getCode(), Object.class);

		return new ResponseEntity<>(response, response.getStatusCode());
	}
}
