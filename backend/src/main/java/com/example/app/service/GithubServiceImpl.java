package com.example.app.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import com.example.app.domain.Project;
import com.example.app.dto.github.WebhookInstallation;
import com.example.app.repo.ProjectRepository;
import com.example.app.repo.WebhookInstallationRepository;
import com.example.app.utils.ZipHelper;
import org.apache.tomcat.util.codec.binary.Base64;
//import org.eclipse.egit.github.core.Repository;
//import org.eclipse.egit.github.core.RepositoryBranch;
//import org.eclipse.egit.github.core.client.GitHubClient;
//import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubServiceImpl<T> implements GithubService {

	@Autowired
	private WebhookInstallationRepository whRepo;

	@Autowired
	private ProjectRepository prRepo;

	@Override
	public WebhookInstallation createWebhookInstallation(WebhookInstallation wh) {

		return this.whRepo.save(wh);
	}

	@Override
	public WebhookInstallation getWebhookInstallationByInstId(Long instId) {

		return this.whRepo.findByInstallationId(instId);
	};

	@Override
	public Project linkAccessToken(WebhookInstallation wh) {

		System.out.println(wh.toString());
		Project project = this.prRepo.findByUserAndRepoName(wh.getUsername(), wh.getRepositoryName());
		System.out.println(project.toString());
		project.setBearerToken(wh.getBearerToken());

		Project response = this.prRepo.save(project);

		return response;
	}
	
	@Override
	public String downloadRepository(String repoName, String branchName, String username, String bearerToken) {

		String downloadFolder = "./target/analysis/";

		if (bearerToken != null) {
			executeCommand("git clone https://x-access-token:" + bearerToken + "@github.com/" +
					username + "/" + repoName + ".git", true);
			executeCommand("git --git-dir=" + repoName + "/.git --work-tree=" + repoName + " checkout develop", true);
			executeCommand("mkdir -p target/analysis", true);
			executeCommand("mv " + repoName + "/ target/analysis/" + repoName + "-" + branchName, true);
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
	
	private static void executeCommand(String command, boolean printOut) {
		Process p = null;
		String[] cmd = { "/bin/sh", "-c", command };

		try {
			p = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			System.err.println("Error on exec() method");
			e.printStackTrace();
		}

		try {
			if (printOut) {
				printInConsole(p.getInputStream(), System.out);
			}
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	private static void printInConsole(InputStream in, OutputStream out) throws IOException {
		while (true) {
			int c = in.read();
			if (c == -1) {
				break;
			}
			out.write((char) c);
		}
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
