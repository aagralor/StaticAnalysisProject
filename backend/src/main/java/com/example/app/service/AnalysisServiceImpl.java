package com.example.app.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.Analysis;
import com.example.app.domain.Project;
import com.example.app.domain.sast.FindSecBugsAnalysis;
import com.example.app.domain.sast.html.BugCollectionHtmlReport;
import com.example.app.domain.sast.xdocs.BugCollectionXdocsReport;
import com.example.app.domain.sast.xml.BugCollectionXmlReport;
import com.example.app.domain.sca.DependencyCheckAnalysis;
import com.example.app.dto.sast.AnalysisStatusDTO;
import com.example.app.mapper.AnalysisSASTMapper;
import com.example.app.mapper.FindSecBugsAnalysisMapper;
import com.example.app.repo.AnalysisRepository;
import com.example.app.repo.ProjectRepository;
import com.example.app.utils.HtmlParser;
import com.example.app.utils.XmlParser;

@Service
public final class AnalysisServiceImpl implements AnalysisService {

	@Autowired
	FindSecBugsAnalysisMapper fsbAnalysisMapper;

	@Autowired
	AnalysisRepository repo;

	@Autowired
	ProjectRepository projectRepo;

	@Autowired
	AnalysisSASTMapper analysisSASTMapper;

	@Override
	public Analysis findLastAnalysis(String projectKey) {
		Project project = this.projectRepo.findByKey(projectKey);

		List<Analysis> analysisList = (List<Analysis>) this.repo.findAll(project.getAnalysisList());
		Analysis response = analysisList.get(0);

		for (Analysis a : analysisList) {
			Date dateAts = new Date(Long.parseLong(a.getSast().getAnalysisTimestamp()));
			Date dateResponse = new Date(Long.parseLong(response.getSast().getAnalysisTimestamp()));

			if (dateAts.after(dateResponse)) {
				response = a;
			}
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sf.format(new Date(Long.parseLong(response.getSast().getAnalysisTimestamp()))));

		return response;
	}

	@Override
	public Analysis createOrUpdateAnalysis(Analysis analysis) {

		Analysis response = this.repo.save(analysis);

		return response;
	}

	@Override
	public AnalysisStatusDTO checkStatus(String id) {

		Analysis a = this.repo.findOne(id);
		AnalysisStatusDTO response = new AnalysisStatusDTO();
		response.setValue(a.getCompletion());
		response.setStatus(a.getStatus());

		return response;
	}

	@Override
	public Analysis execute(String pathToFolder, Analysis currentAnalysis) {
		executeCommand(generateCommandForMvnBuild(pathToFolder), true);
		currentAnalysis.setCompletion("35");
		this.repo.save(currentAnalysis);
		executeCommand(generateCommandForJarList(pathToFolder), true);

		executeCommand(generateCommandForHtmlReport(pathToFolder), true);
		currentAnalysis.setCompletion("50");
		this.repo.save(currentAnalysis);
		executeCommand(generateCommandForXmlReport(pathToFolder), true);
		currentAnalysis.setCompletion("65");
		this.repo.save(currentAnalysis);
		executeCommand(generateCommandForXdocsReport(pathToFolder), true);
		currentAnalysis.setCompletion("80");
		this.repo.save(currentAnalysis);
		executeCommand(generateCommandForJsonReport(pathToFolder), true);
		currentAnalysis.setCompletion("95");
		Analysis result = this.repo.save(currentAnalysis);
		FindSecBugsAnalysis resultSAST = null;
		DependencyCheckAnalysis resultSCA = null;

		try {
			BugCollectionHtmlReport html = HtmlParser
					.parseToBugCollectionFromHtml(pathToFolder.concat("/report_HTML.htm"));
			BugCollectionXmlReport xml = XmlParser.parseToBugCollectionFromXml(pathToFolder.concat("/report_XML.xml"));
			BugCollectionXdocsReport xdocs = XmlParser
					.parseToBugCollectionFromXdocs(pathToFolder.concat("/report_XDOCS.xml"));
			resultSAST = this.fsbAnalysisMapper.toFindSecBugsAnalysis(xdocs, xml, html);
			result.setSast(this.analysisSASTMapper.toAnalysisSAST(resultSAST));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}

		return result;
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

	private static String generateCommandForXdocsReport(String path) {
		StringBuilder sb = new StringBuilder();
		sb.append("cat ").append(path);
		sb.append("/jarList.txt | ");
		sb.append("/home/alberto/workspace_TFM/find-sec-bugs-APP/findsecbugs.sh");
		sb.append(" -xargs -progress -xdocs -nested:false -output ");
//		"cat jarList.txt | /home/alberto/workspace_TFM/find-sec-bugs-APP/findsecbugs.sh -xargs -progress -xdocs -nested:false -output report_XDOCS.xml" };
		sb.append(path).append("/report_XDOCS.xml");
		return sb.toString();
	}

	private static String generateCommandForXmlReport(String path) {
		StringBuilder sb = new StringBuilder();
		sb.append("cat ").append(path);
		sb.append("/jarList.txt | ");
		sb.append("/home/alberto/workspace_TFM/find-sec-bugs-APP/findsecbugs.sh");
		sb.append(" -xargs -progress -xml -nested:false -output ");
//		"cat jarList.txt | /home/alberto/workspace_TFM/find-sec-bugs-APP/findsecbugs.sh -xargs -progress -xml -nested:false -output report_XML.xml" };
		sb.append(path).append("/report_XML.xml");
		return sb.toString();
	}

	private static String generateCommandForHtmlReport(String path) {
		StringBuilder sb = new StringBuilder();
		sb.append("cat ").append(path);
		sb.append("/jarList.txt | ");
		sb.append("/home/alberto/workspace_TFM/find-sec-bugs-APP/findsecbugs.sh");
		sb.append(" -xargs -progress -html:plain.xsl -nested:false -output ");
		sb.append(path).append("/report_HTML.htm");
//		"cat jarList.txt | /home/alberto/workspace_TFM/find-sec-bugs-APP/findsecbugs.sh -xargs -progress -html:plain.xsl -nested:false -output report_HTML.htm"
		return sb.toString();
	}

	private static String generateCommandForJsonReport(String path) {
		StringBuilder sb = new StringBuilder();
		sb.append("/home/alberto/workspace_TFM/dependency-check-APP/bin/dependency-check.sh");
		sb.append(" --project application --scan ");
		sb.append(path).append("/**/*.jar --format JSON --out ");
		sb.append(path).append("/report_JSON.json");
//		"/home/alberto/workspace_TFM/dependency-check-APP/bin/dependency-check.sh --project test --scan ./target/analysis/StaticAnalysisProject-develop/**/*.jar --format JSON --out ./project/application_report_JSON.json"
		return sb.toString();
	}

	private static String generateCommandForMvnBuild(String path) {
		StringBuilder sb = new StringBuilder();
		sb.append("/opt/apache-maven-3.6.3/bin/mvn -f ");
		sb.append(path).append(" clean install");
//		/opt/apache-maven-3.6.3/bin/mvn -f ./target/analysis/StaticAnalysisProject-develop clean install
		return sb.toString();
	}

	private static String generateCommandForJarList(String path) {
		StringBuilder sb = new StringBuilder();
		sb.append("find ").append(path).append(" -name *.jar > ");
		sb.append(path).append("/jarList.txt");
//		find ./target/analysis/StaticAnalysisProject-develop -name *.jar > jarList.txt
		return sb.toString();
	}

	private static void writeInFile(String text, String pathFile) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(pathFile));
			writer.write(text);

		} catch (IOException e) {
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
			}
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

	private static String copyToString(InputStream in) throws IOException {
		StringBuilder textBuilder = new StringBuilder();

		try (Reader reader = new BufferedReader(
				new InputStreamReader(in, Charset.forName(StandardCharsets.UTF_8.name())))) {
			int c = 0;
			while ((c = reader.read()) != -1) {
				textBuilder.append((char) c);
			}
		}

		return textBuilder.toString();
	}

	private static String getPwd() throws IOException {
		Process p = null;

		try {
			p = Runtime.getRuntime().exec("pwd");
		} catch (IOException e) {
			System.err.println("Error on exec() method");
		}

		return copyToString(p.getInputStream());
	}

	public static void main(String[] args) throws IOException, InterruptedException {
//		Process p = null;

//	    try {
//	        p = Runtime.getRuntime().exec("/opt/apache-maven-3.6.3/bin/mvn -f ./target/analysis/StaticAnalysisProject-develop clean install");
//	    } catch (IOException e) {
//	        System.err.println("Error on exec() method");
//	        e.printStackTrace();
//	    }
//	    printInConsole(p.getInputStream(), System.out);
//	    p.waitFor();
//
//		String pwd = getPwd().trim() + "/";
//
//		String[] cmd = { "/bin/sh", "-c",
//				"find ./target/analysis/StaticAnalysisProject-develop -name *.jar > jarList.txt" };
//		try {
//			p = Runtime.getRuntime().exec(cmd);
//		} catch (IOException e) {
//			System.err.println("Error on exec() method");
//			e.printStackTrace();
//		}
//		p.waitFor();
//
//		String[] cmd2 = { "/bin/sh", "-c",
//				"cat jarList.txt | /home/alberto/workspace_TFM/find-sec-bugs-APP/findsecbugs.sh -xargs -progress -html:plain.xsl -nested:false -output report_HTML.htm" };
//		try {
//			p = Runtime.getRuntime().exec(cmd2);
//		} catch (IOException e) {
//			System.err.println("Error on exec() method");
//			e.printStackTrace();
//		}
//		printInConsole(p.getInputStream(), System.out);
//		p.waitFor();
//
//		String[] cmd3 = { "/bin/sh", "-c",
//				"cat jarList.txt | /home/alberto/workspace_TFM/find-sec-bugs-APP/findsecbugs.sh -xargs -progress -xml -nested:false -output report_XML.xml" };
//		try {
//			p = Runtime.getRuntime().exec(cmd3);
//		} catch (IOException e) {
//			System.err.println("Error on exec() method");
//			e.printStackTrace();
//		}
//		printInConsole(p.getInputStream(), System.out);
//		p.waitFor();
//
//		String[] cmd4 = { "/bin/sh", "-c",
//				"cat jarList.txt | /home/alberto/workspace_TFM/find-sec-bugs-APP/findsecbugs.sh -xargs -progress -xdocs -nested:false -output report_XDOCS.xml" };
//		try {
//			p = Runtime.getRuntime().exec(cmd4);
//		} catch (IOException e) {
//			System.err.println("Error on exec() method");
//			e.printStackTrace();
//		}
//		printInConsole(p.getInputStream(), System.out);
//		p.waitFor();
//
//		String[] cmd5 = { "/bin/sh", "-c",
//				"/home/alberto/workspace_TFM/dependency-check-APP/bin/dependency-check.sh --project test --scan ./target/analysis/StaticAnalysisProject-develop/**/*.jar --format JSON --out ./project/application_report_JSON.json" };
//		try {
//			p = Runtime.getRuntime().exec(cmd5);
//		} catch (IOException e) {
//			System.err.println("Error on exec() method");
//			e.printStackTrace();
//		}
//		printInConsole(p.getInputStream(), System.out);
//		p.waitFor();

		executeCommand(generateCommandForJsonReport("./target/analysis/StaticAnalysisProject-develop"), true);

		System.out.println("Bye World");

	}

}
