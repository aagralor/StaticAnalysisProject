package com.example.app.mapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.app.domain.sast.FindSecBugsAnalysis;
import com.example.app.domain.sast.Vuln;
import com.example.app.domain.sast.html.BugCollectionHtmlReport;
import com.example.app.domain.sast.html.VulnInstance;
import com.example.app.domain.sast.xdocs.BugCollectionXdocsReport;
import com.example.app.domain.sast.xdocs.BugInstance;
import com.example.app.domain.sast.xdocs.File;
import com.example.app.domain.sast.xml.BugCollectionXmlReport;
import com.example.app.utils.HtmlParser;
import com.example.app.utils.XmlParser;

@Component
public final class FindSecBugsAnalysisMapper {

	private FindSecBugsAnalysisMapper() {
	}

	public static FindSecBugsAnalysis toFindSecBugsAnalysis(BugCollectionXdocsReport xdocs, BugCollectionXmlReport xml, BugCollectionHtmlReport html) {

		ArrayList<Vuln> vulnList = new ArrayList<>();

		for (VulnInstance vi : html.getVulnList()) {
			Vuln vuln = new Vuln();

			vuln.setNameByHtml(vi.getName());
			vuln.setPriorityByHtml(vi.getPriority());
			vuln.setReferenceByHtml(vi.getReference());
			vuln.setSourceLineListByHtml(vi.getSourceLineList());
			vuln.setFileNameByHtml(getFilenameFromSourceLine(vi.getSourceLineList().get(0)));
			vuln.setLineNumberByHtml(getLineNumberFromSourceLine(vi.getSourceLineList().get(0)));
			vuln.setTextByHtml(vi.getText());
			vuln.setTextHtmlByHtml(vi.getTextHtml());
			vuln.setWarningTextHtmlByHtml(vi.getWarningTextHtml());

			Map<String, Object> bugInXdocsMap = getBugInstanceXdocs(xdocs.getFileList(), vuln.getFileNameByHtml(),
					vuln.getLineNumberByHtml(), vuln.getReferenceByHtml().substring(1));
			BugInstance bugInXdocs = (BugInstance) bugInXdocsMap.get("bugInstance");
			String bugInXdocsClassname = (String) bugInXdocsMap.get("classname");

			vuln.setClassnameByXdocs(bugInXdocsClassname);
			vuln.setTypeByXdocs(bugInXdocs.getType());
			vuln.setPriorityByXdocs(bugInXdocs.getPriority());
			vuln.setMessageByXdocs(bugInXdocs.getMessage());
			vuln.setLineByXdocs(bugInXdocs.getLine());

			com.example.app.domain.sast.xml.BugInstance bugInXml = getBugInstanceXml(xml.getBugInstanceList(),
					vuln.getFileNameByHtml(), vuln.getLineNumberByHtml(), vuln.getReferenceByHtml().substring(1));

			vuln.setTypeByXml(bugInXml.getType());
			vuln.setPriorityByXml(bugInXml.getPriority());
			vuln.setRankByXml(bugInXml.getRank());
			vuln.setAbbrevByXml(bugInXml.getAbbrev());
			vuln.setCategoryByXml(bugInXml.getCategory());
			vuln.setClassnameByXml(bugInXml.getClazz().getClassname());
			vuln.setMethodClassnameByXml(bugInXml.getMethod().getClassname());
			vuln.setMethodNameByXml(bugInXml.getMethod().getName());
			vuln.setMethodSignatureByXml(bugInXml.getMethod().getSignature());
			vuln.setTraceByXml(bugInXml.getSourceLineList());
			vuln.setElementListByXml(bugInXml.getStringList());

			vulnList.add(vuln);
		}

		FindSecBugsAnalysis analysis = new FindSecBugsAnalysis();

		analysis.setVersion(xml.getVersion());
		analysis.setSequence(xml.getSequence());
		analysis.setTimestamp(xml.getTimestamp());
		analysis.setAnalysisTimestamp(xml.getAnalysisTimestamp());
		analysis.setRelease(xml.getRelease());
		analysis.setProjectName(xml.getProject().getProjectName());
		analysis.setJarList(xml.getProject().getJarList());
		analysis.setPluginId(xml.getProject().getPlugin().getId());
		analysis.setPluginEnabled(xml.getProject().getPlugin().getEnabled());
		analysis.setVulnList(vulnList);

		return analysis;
	}

	private static Map<String, Object> getBugInstanceXdocs(List<File> files, String filename, String line,
			String reference) {

		File selectedFile = files.stream().filter(f -> f.getClassname().contains(filename.split("\\.")[0])).findFirst()
				.get();

		List<BugInstance> filteredReferenceList = selectedFile.getBugInstanceList().stream()
				.filter(b -> b.getType().equalsIgnoreCase(reference)).collect(Collectors.toList());

		BugInstance bugInstance = filteredReferenceList.stream().filter(b -> b.getLine().equalsIgnoreCase(line))
				.findAny().orElseGet(() -> filteredReferenceList.get(0));

		Map<String, Object> response = new HashMap<>();
		response.put("bugInstance", bugInstance);
		response.put("classname", selectedFile.getClassname());

		return response;
	}

	private static com.example.app.domain.sast.xml.BugInstance getBugInstanceXml(
			List<com.example.app.domain.sast.xml.BugInstance> bugList, String filename, String line, String reference) {

		List<com.example.app.domain.sast.xml.BugInstance> filteredReferenceList = bugList.stream()
				.filter(b -> b.getType().equalsIgnoreCase(reference)).collect(Collectors.toList());

		com.example.app.domain.sast.xml.BugInstance bugInstance = filteredReferenceList.stream()
				.filter(b -> b.getSourceLineList() != null)
				.filter(b -> b.getSourceLineList().get(0).getSourcefile().equalsIgnoreCase(filename))
				.filter(b -> b.getSourceLineList().get(0).getStart().equalsIgnoreCase(line)).findAny()
				.orElseGet(() -> filteredReferenceList.get(0));

		return bugInstance;
	}

	private static String getFilenameFromSourceLine(String sourceLine) {

		String[] splitSourceLine = sourceLine.split(":");

		return splitSourceLine[0];
	}

	private static String getLineNumberFromSourceLine(String sourceLine) {

		String[] splitSourceLine = sourceLine.split(":");
		int startIndex;
		int finalIndex;
		String response = splitSourceLine[1];

		if (response.contains("lines")) {
			startIndex = response.indexOf(" ");
			finalIndex = response.indexOf("-");

			return response.substring(startIndex, finalIndex).trim();
		}

		startIndex = response.indexOf(" ");
		finalIndex = response.indexOf("]");

		return response.substring(startIndex, finalIndex).trim();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.println("Hello world");

		BugCollectionHtmlReport html = HtmlParser
				.parseToBugCollectionFromHtml("project/application_report_non_nested_plain_HTML.htm");
		BugCollectionXmlReport xml = XmlParser
				.parseToBugCollectionFromXml("project/application_report_non_nested_XML.xml");
		BugCollectionXdocsReport xdocs = XmlParser
				.parseToBugCollectionFromXdocs("project/application_report_non_nested_XDOCS.xml");

		FindSecBugsAnalysis analysis = toFindSecBugsAnalysis(xdocs, xml, html);

//		ArrayList<Vuln> vulnList = new ArrayList<>();
//
//		for (VulnInstance vi : html.getVulnList()) {
//			Vuln vuln = new Vuln();
//
//			vuln.setNameByHtml(vi.getName());
//			vuln.setPriorityByHtml(vi.getPriority());
//			vuln.setReferenceByHtml(vi.getReference());
//			vuln.setSourceLineListByHtml(vi.getSourceLineList());
//			vuln.setFileNameByHtml(getFilenameFromSourceLine(vi.getSourceLineList().get(0)));
//			vuln.setLineNumberByHtml(getLineNumberFromSourceLine(vi.getSourceLineList().get(0)));
//			vuln.setTextByHtml(vi.getText());
//			vuln.setTextHtmlByHtml(vi.getTextHtml());
//			vuln.setWarningTextHtmlByHtml(vi.getWarningTextHtml());
//
//			Map<String, Object> bugInXdocsMap = getBugInstanceXdocs(xdocs.getFileList(), vuln.getFileNameByHtml(),
//					vuln.getLineNumberByHtml(), vuln.getReferenceByHtml().substring(1));
//			BugInstance bugInXdocs = (BugInstance) bugInXdocsMap.get("bugInstance");
//			String bugInXdocsClassname = (String) bugInXdocsMap.get("classname");
//
//			vuln.setClassnameByXdocs(bugInXdocsClassname);
//			vuln.setTypeByXdocs(bugInXdocs.getType());
//			vuln.setPriorityByXdocs(bugInXdocs.getPriority());
//			vuln.setMessageByXdocs(bugInXdocs.getMessage());
//			vuln.setLineByXdocs(bugInXdocs.getLine());
//
//			com.example.app.domain.sast.xml.BugInstance bugInXml = getBugInstanceXml(xml.getBugInstanceList(),
//					vuln.getFileNameByHtml(), vuln.getLineNumberByHtml(), vuln.getReferenceByHtml().substring(1));
//
//			vuln.setTypeByXml(bugInXml.getType());
//			vuln.setPriorityByXml(bugInXml.getPriority());
//			vuln.setRankByXml(bugInXml.getRank());
//			vuln.setAbbrevByXml(bugInXml.getAbbrev());
//			vuln.setCategoryByXml(bugInXml.getCategory());
//			vuln.setClassnameByXml(bugInXml.getClazz().getClassname());
//			vuln.setMethodClassnameByXml(bugInXml.getMethod().getClassname());
//			vuln.setMethodNameByXml(bugInXml.getMethod().getName());
//			vuln.setMethodSignatureByXml(bugInXml.getMethod().getSignature());
//			vuln.setTraceByXml(bugInXml.getSourceLineList());
//			vuln.setElementListByXml(bugInXml.getStringList());
//
//			vulnList.add(vuln);
//		}
//
//		FindSecBugsAnalysis analysis = new FindSecBugsAnalysis();
//
//		analysis.setVersion(xml.getVersion());
//		analysis.setSequence(xml.getSequence());
//		analysis.setTimestamp(xml.getTimestamp());
//		analysis.setAnalysisTimestamp(xml.getAnalysisTimestamp());
//		analysis.setRelease(xml.getRelease());
//		analysis.setProjectName(xml.getProject().getProjectName());
//		analysis.setJarList(xml.getProject().getJarList());
//		analysis.setPluginId(xml.getProject().getPlugin().getId());
//		analysis.setPluginEnabled(xml.getProject().getPlugin().getEnabled());
//		analysis.setVulnList(vulnList);

		System.out.println("Bye world");

	}

}
