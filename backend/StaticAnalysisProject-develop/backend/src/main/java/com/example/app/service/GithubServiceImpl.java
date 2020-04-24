package com.example.app.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.eclipse.egit.github.core.Download;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.GitHubRequest;
import org.eclipse.egit.github.core.client.GitHubResponse;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.app.utils.ZipHelper;

public class GithubServiceImpl implements GithubService {

	public List<String> findRepositoryNamesWithAdminPermission(String accessToken) {
//		GitHubClient client = createClient(accessToken);

//		WithPermissionsRepositoryService service = new WithPermissionsRepositoryService(client);
//		List<WithPermissionsRepository> repositories = service.getPermissionRepositories();
//		List<String> repoSlugs = new ArrayList<>();
//		for (WithPermissionsRepository r : repositories) {
//			if(!r.getPermissions().isAdmin()) {
//				continue;
//			}
//			org.eclipse.egit.github.core.User owner = r.getOwner();
//			repoSlugs.add(owner.getLogin() + "/" + r.getName());
//		}
//		return repoSlugs;

		return null;
	}

//	private GitHubClient createClient(String accessToken) {
//		GitHubClient client = new GitHubClient(oauthConfig.getGitHubApiHost(), oauthConfig.getPort(), oauthConfig.getScheme());
//		client.setOAuth2Token(accessToken);
//		return client;
//	}

	private static String generateDownloadUrl(String repoName, String branchName) {

		StringBuilder builder = new StringBuilder();

		builder.append("https://github.com/aagralor/").append(repoName).append("/archive/").append(branchName)
				.append(".zip");

		return builder.toString();

	}

	private static HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}

	public static <T> void main(String[] args) throws IOException {
		GitHubClient client = new GitHubClient();
//		client.setCredentials("aagralor", "zYE9uueE");
		client.setCredentials("aagralor", "0323c8384ccd0f52882385bcc84cbb69e7a6bf91");
//		client.setCredentials("aagralor@uoc.edu", "0323c8384ccd0f52882385bcc84cbb69e7a6bf91");

		RepositoryService service = new RepositoryService(client);

		List<Repository> myRepos = service.getRepositories("aagralor");
		for (Repository repo : myRepos)
			System.out.println(repo.getName() + " Watchers: " + repo.getWatchers());

		Repository repoTest = service.getRepository("aagralor", "test");
		List<RepositoryBranch> branchesTest = service.getBranches(repoTest);
		RepositoryBranch branchMasterTest = branchesTest.get(0);

//		GitHubRequest requestTest = new GitHubRequest();
//		requestTest.setUri(generateDownloadUrl("test", "master"));
//		requestTest.setType(Download.class);
//		GitHubResponse responseTest = client.get(requestTest);

		Repository repoMain = service.getRepository("aagralor", "main");
		List<RepositoryBranch> branchesMain = service.getBranches(repoMain);
		RepositoryBranch branchMasterMain = branchesMain.get(0);

		RestTemplate templ = new RestTemplate();

		byte[] downloadedBytesTest = templ.getForObject(generateDownloadUrl("test", "master"), byte[].class);
		ZipHelper.unzip(downloadedBytesTest, "./");

		ResponseEntity<byte[]> bytesMain = templ.exchange(generateDownloadUrl("main", "master"), HttpMethod.GET,
				new HttpEntity<T>(createHeaders("aagralor", "0323c8384ccd0f52882385bcc84cbb69e7a6bf91")), byte[].class);
		
		byte[] downloadedBytesMain = bytesMain.getBody();
		ZipHelper.unzip(downloadedBytesMain, "./");

		System.out.println("Bye World");

	};

}