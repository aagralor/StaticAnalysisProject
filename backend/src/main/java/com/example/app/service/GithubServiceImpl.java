package com.example.app.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.app.domain.Project;
import com.example.app.domain.github.BearerToken;
import com.example.app.domain.github.WebhookInstallation;
import com.example.app.repo.BearerTokenRepository;
import com.example.app.repo.ProjectRepository;
import com.example.app.repo.WebhookInstallationRepository;
import com.example.app.utils.CmdHelper;
import com.example.app.utils.ZipHelper;

@Service
public class GithubServiceImpl<T> implements GithubService {

	@Autowired
	private WebhookInstallationRepository whRepo;

	@Autowired
	private ProjectRepository prRepo;

	@Autowired
	private BearerTokenRepository btRepo;

	@Override
	public WebhookInstallation createWebhookInstallation(WebhookInstallation wh) {

		return this.whRepo.save(wh);
	}

	@Override
	public WebhookInstallation getWebhookInstallationByInstId(Long instId) {

		return this.whRepo.findByInstallationId(instId);
	};

	@Override
	public List<Project> updateAddAccessToken(WebhookInstallation wh) {

		BearerToken bt = this.btRepo.findByUsername(wh.getUsername());
		List<String> whrList = wh.getRepoList().stream().map(whr -> whr.getName()).collect(Collectors.toList());
		List<String> mergedList = bt.getRepoList();
		for (String s : whrList) {
			if (!mergedList.contains(s)) {
				mergedList.add(s);
			}
		}
		List<Project> projectList = getNewProjectList(mergedList, bt.getUsername(), bt.getBearerToken());
		bt.setRepoList(projectList.stream().map(p -> p.getRepositoryName()).collect(Collectors.toList()));
		this.btRepo.save(bt);
		List<Project> response = this.prRepo.save(projectList);

		System.out.println("WebhookInstallation:");
		System.out.println(wh);
		System.out.println("MergedList:");
		System.out.println(mergedList);
		System.out.println("ProjectList:");
		System.out.println(projectList);
		System.out.println("Response:");
		System.out.println(response);

		return response;
	}
	
	@Override
	public List<Project> updateRemoveAccessToken(WebhookInstallation wh) {

		BearerToken bt = this.btRepo.findByUsername(wh.getUsername());
		
		List<Project> btProjectList = getNewProjectList(bt.getRepoList(), bt.getUsername(), null);
		this.prRepo.save(btProjectList);

		List<String> whrListToQuit = wh.getRepoListToQuit().stream().map(whr -> whr.getName()).collect(Collectors.toList());
		List<String> mergedList = bt.getRepoList();
		for (String s : whrListToQuit) {
			if (mergedList.contains(s)) {
				mergedList.remove(s);
			}
		}
		List<Project> projectList = getNewProjectList(mergedList, bt.getUsername(), bt.getBearerToken());
		bt.setRepoList(projectList.stream().map(p -> p.getRepositoryName()).collect(Collectors.toList()));
		this.btRepo.save(bt);
		List<Project> response = this.prRepo.save(projectList);

		return response;
	}


	@Override
	public List<Project> removeAccessToken(WebhookInstallation wh) {

		BearerToken bt = this.btRepo.findByUsername(wh.getUsername());
		List<Project> projectList = getNewProjectList(bt.getRepoList(), bt.getUsername(), null);
		List<Project> response = this.prRepo.save(projectList);
		this.btRepo.delete(bt);

		return response;
	}

	@Override
	public List<Project> linkAccessToken(WebhookInstallation wh) {

		BearerToken bt = new BearerToken();
		bt.setBearerToken(wh.getBearerToken());
		bt.setUsername(wh.getUsername());
		List<Project> projectList = getNewProjectList(
				wh.getRepoList().stream().map(whr -> whr.getName()).collect(Collectors.toList()), wh.getUsername(),
				wh.getBearerToken());
		bt.setRepoList(projectList.stream().map(p -> p.getRepositoryName()).collect(Collectors.toList()));
		this.btRepo.save(bt);
		List<Project> response = this.prRepo.save(projectList);

		return response;
	}

	private List<Project> getNewProjectList(List<String> projectList, String username, String bearerToken) {
		List<Project> response = new ArrayList<Project>();
		for (String projectName : projectList) {
			try {
				Project project = this.prRepo.findByUserAndRepoName(username, projectName);
				project.setBearerToken(bearerToken);
				response.add(project);
			} catch (NullPointerException e) {
				System.out.println("Repo not found");
			}
		}
		return response;
	}

	@Override
	public String downloadRepository(String repoName, String branchName, String username, String bearerToken) {

		String downloadFolder = "./target/analysis/";

		if (bearerToken != null) {
			CmdHelper.executeCommand("git clone https://x-access-token:" + bearerToken + "@github.com/" + username + "/"
					+ repoName + ".git", true);
			CmdHelper.executeCommand(
					"git --git-dir=" + repoName + "/.git --work-tree=" + repoName + " checkout develop", true);
			CmdHelper.executeCommand("mkdir -p target/analysis", true);
			CmdHelper.executeCommand("mv " + repoName + "/ target/analysis/" + repoName + "-" + branchName, true);
		} else {
			RestTemplate templ = new RestTemplate();
			String url = generateDownloadUrl(username, repoName, branchName);
			byte[] downloadedBytes = null;
			downloadedBytes = templ.getForObject(url, byte[].class);
			try {
				ZipHelper.unzip(downloadedBytes, downloadFolder);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return generateDownloadFolder(downloadFolder, repoName, branchName);
	}

//	public static String downloadRepository2(String repoName, String branchName, String username, String bearerToken) {
//
//		String downloadFolder = "./target/analysis/";
//
//		if (bearerToken != null) {
//			executeCommand("git clone https://x-access-token:" + bearerToken + "@github.com/" +
//					username + "/" + repoName + ".git", true);
//			executeCommand("git --git-dir=" + repoName + "/.git --work-tree=" + repoName + " checkout develop", true);
//			executeCommand("mkdir -p target/analysis", true);
//			executeCommand("mv " + repoName + "/ target/analysis/" + repoName + "-" + branchName, true);
//		} else {
//			RestTemplate templ = new RestTemplate();
//			String url = generateDownloadUrl(username, repoName, branchName);
//			byte[] downloadedBytes = null;
//			downloadedBytes = templ.getForObject(url, byte[].class);
//			try {
//				ZipHelper.unzip(downloadedBytes, downloadFolder);
//			} catch (IOException e) {
//				throw new RuntimeException(e.getMessage());
//			}
//		}
//		return generateDownloadFolder(downloadFolder, repoName, branchName);
//	}

	private static String generateDownloadUrl(String username, String repoName, String branchName) {

		StringBuilder builder = new StringBuilder();

		builder.append("https://github.com/").append(username).append("/").append(repoName).append("/archive/")
				.append(branchName).append(".zip");

		return builder.toString();

	}

	private static String generateDownloadFolder(String downloadFolder, String repoName, String branchName) {

		StringBuilder builder = new StringBuilder();

		builder.append(downloadFolder).append(repoName).append("-").append(branchName);

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

	private static HttpHeaders createHeaders2(String token) {
		return new HttpHeaders() {
			{
				String authHeader = "Bearer " + new String(token);
				set("Authorization", authHeader);
			}
		};
	}

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

	public static <T> void main(String[] args) throws IOException {
//		GitHubClient client = new GitHubClient();
////		client.setCredentials("aagralor", "zYE9uueE");
//		client.setCredentials("aagralor", "0323c8384ccd0f52882385bcc84cbb69e7a6bf91");
////		client.setCredentials("aagralor@uoc.edu", "0323c8384ccd0f52882385bcc84cbb69e7a6bf91");
//
//		RepositoryService service = new RepositoryService(client);
//
//		List<Repository> myRepos = service.getRepositories("aagralor");
//		for (Repository repo : myRepos) {
//			System.out.println(repo.getName() + " Watchers: " + repo.getWatchers());
//		}
//		Repository repoTest = service.getRepository("aagralor", "test");
//		List<RepositoryBranch> branchesTest = service.getBranches(repoTest);
//		RepositoryBranch branchMasterTest = branchesTest.get(0);
//
//
//		Repository repoMain = service.getRepository("aagralor", "main");
//		Repository repoProject = service.getRepository("aagralor", "StaticAnalysisProject");

//		{
//			"access_token":"4448d2f5778af78ca4e7de53d67c7990cd54fa3b",
//			"token_type":"bearer",
//			"scope":""
//		}
//		RestTemplate templ = new RestTemplate();
//
//		byte[] downloadedBytesTest = templ.getForObject(generateDownloadUrl("aagralor", "test", "master"),
//				byte[].class);
//		ZipHelper.unzip(downloadedBytesTest, "./analysis/");
//
//		ResponseEntity<byte[]> bytesMain = templ.exchange(generateDownloadUrl("aagralor", "main", "master"),
//				HttpMethod.GET,
//				new HttpEntity<T>(createHeaders("aagralor", "0323c8384ccd0f52882385bcc84cbb69e7a6bf91")), byte[].class);
//		byte[] downloadedBytesMain = bytesMain.getBody();
//		ZipHelper.unzip(downloadedBytesMain, "./analysis/");

//		ResponseEntity<byte[]> bytesMain = templ.exchange(generateDownloadUrl("main", "master"), HttpMethod.GET,
//				new HttpEntity<T>(createHeaders2("4448d2f5778af78ca4e7de53d67c7990cd54fa3b")), byte[].class);
//		byte[] downloadedBytesMain = bytesMain.getBody();
//		ZipHelper.unzip(downloadedBytesMain, "./");
//
//		ResponseEntity<byte[]> bytesProject = templ.exchange(
//				generateDownloadUrl("aagralor", "StaticAnalysisProject", "develop"), HttpMethod.GET,
//				new HttpEntity<T>(createHeaders("aagralor", "0323c8384ccd0f52882385bcc84cbb69e7a6bf91")), byte[].class);
//		byte[] downloadedBytesProject = bytesProject.getBody();
//		ZipHelper.unzip(downloadedBytesProject, "./analysis/");

//		String downloadPath = downloadRepository2("StaticAnalysisProject", "develop", "aagralor", "a1dfdad31f4f5f9027d96408fa7cdfa57ceb6ddc");

		System.out.println("Bye World");

	}

}
