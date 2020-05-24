package com.example.app.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.Analysis;
import com.example.app.domain.DependencySCA;
import com.example.app.domain.IssueSAST;
import com.example.app.domain.Project;
import com.example.app.repo.ProjectRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	ProjectRepository repo;

	@Override
	public byte[] generatePDF(Project project, Analysis analysis) {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		Document document = new Document(PageSize.A4);
		try {
			@SuppressWarnings("unused")
//			PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("analysis_report.pdf"));
			PdfWriter pdfWriter = PdfWriter.getInstance(document, buffer);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		document.open();

		// Metadata
		document.addAuthor("Alberto Agra");
		document.addCreationDate();
		document.addCreator("IssueSecurityCenter");
		document.addTitle("Java Vulnerability Report");
		document.addSubject("Contains all the vulnerabilities repeorted in a SAST scan.");

		// Cover
		Image uocImage;
		Image javaImage;

		Font fontTitle = new Font(FontFamily.HELVETICA, 24, Font.BOLD);
		Paragraph title = new Paragraph("IssueSecurityCenter", fontTitle);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingBefore(25);

		Font fontProjectName = new Font(FontFamily.HELVETICA, 30, Font.BOLD);
		Paragraph projectName = new Paragraph(project.getName(), fontProjectName);
		projectName.setAlignment(Element.ALIGN_CENTER);
		projectName.setSpacingBefore(35);

		Font fontSubtitleName = new Font(FontFamily.HELVETICA, 20, Font.BOLD);
		Paragraph repoName = new Paragraph("Github Repository: " + project.getRepositoryName(), fontSubtitleName);
		repoName.setAlignment(Element.ALIGN_CENTER);
		repoName.setSpacingBefore(35);

		Paragraph branch = new Paragraph("Branch: " + project.getBranchName(), fontSubtitleName);
		branch.setAlignment(Element.ALIGN_CENTER);
		branch.setSpacingBefore(15);

		branch.setSpacingAfter(145);

		try {
			uocImage = Image.getInstance("img/uoc_logo.jpg");
			uocImage.scaleAbsolute(500, 200);

			javaImage = Image.getInstance("img/java_logo.jpg");
			javaImage.scaleAbsolute(200, 120);
			javaImage.setAlignment(Element.ALIGN_CENTER);

			document.add(uocImage);
			document.add(title);
			document.add(projectName);
			document.add(repoName);
			document.add(branch);
			document.add(javaImage);
		} catch (IOException | DocumentException e1) {
			e1.printStackTrace();
		}

		// SAST
		document.newPage();

		Paragraph sastTitle = new Paragraph("SAST RESULTS", fontTitle);
		sastTitle.setAlignment(Element.ALIGN_CENTER);
		sastTitle.setSpacingAfter(55);

		try {
			document.add(sastTitle);
			addSASTIssues(analysis.getSast().getIssueList(), document);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		// SAST
		document.newPage();

		Paragraph scaTitle = new Paragraph("SCA RESULTS", fontTitle);
		sastTitle.setAlignment(Element.ALIGN_CENTER);
		sastTitle.setSpacingAfter(55);
		try {
			document.add(scaTitle);
			List<DependencySCA> dependencyList = analysis.getSca().getDependencyList().stream().filter(Objects::nonNull)
					.collect(Collectors.toList());
			addSCAVulns(dependencyList, document);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		// CLOSE
		document.close();

		return buffer.toByteArray();
	}

	private void addSASTIssues(List<IssueSAST> issueList, Document document) throws DocumentException {

		Font fontTitle = new Font(FontFamily.HELVETICA, 24, Font.BOLD);

		issueList.forEach(is -> {
			Paragraph title = new Paragraph(is.getName(), fontTitle);
			title.setSpacingAfter(25);

			Paragraph abbrev = new Paragraph("Abbrev: " + is.getAbbrev());
			Paragraph priority = new Paragraph("Priority: " + is.getPriority());
			Paragraph reference = new Paragraph("Reference: " + is.getReference());
			reference.setSpacingAfter(15);
			Paragraph className = new Paragraph("Classname: " + is.getClassName());
			Paragraph method = new Paragraph("Method: " + is.getMethodName());
			Paragraph line = new Paragraph("Line: " + is.getLineNumber());
			line.setSpacingAfter(15);
			Paragraph message = new Paragraph("Message: " + is.getMessage());
			message.setSpacingAfter(15);
			Paragraph reason = new Paragraph("Reason: " + is.getText());
			reason.setSpacingAfter(190);

			try {
				document.add(title);
				document.add(abbrev);
				document.add(priority);
				document.add(reference);
				document.add(className);
				document.add(method);
				document.add(line);
				document.add(message);
				document.add(reason);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		});

	}

	private void addSCAVulns(List<DependencySCA> depList, Document document) {

		Font fontTitle = new Font(FontFamily.HELVETICA, 24, Font.BOLD);

//		List<VulnerabilitySCA> vulnList = depList.stream()
//				.map(d -> d.getVulnerabilityList())
//				.filter(Objects::nonNull)
//				.flatMap(Collection::stream)
//				.collect(Collectors.toList());

		depList.stream().forEach(dep -> {
			String fileName = dep.getFileName();
			String filePath = dep.getFilePath();
			String sha256 = dep.getSha256();

			dep.getVulnerabilityList().stream().filter(Objects::nonNull).forEach(is -> {
				Paragraph title = new Paragraph(is.getName(), fontTitle);
				title.setSpacingAfter(25);
				Paragraph name = new Paragraph("Filename: " + fileName);
				name.setSpacingAfter(15);
				Paragraph path = new Paragraph("Filepath: " + filePath);
				path.setSpacingAfter(15);
				Paragraph sha = new Paragraph("SHA256: " + sha256);
				sha.setSpacingAfter(15);
				Paragraph source = new Paragraph("Source: " + is.getSource());
				source.setSpacingAfter(15);
				Paragraph severity = new Paragraph("Severity: " + is.getSeverity());
				severity.setSpacingAfter(15);
				Paragraph cvssv2 = new Paragraph("CVSSv2: " + is.getCvssv2());
				cvssv2.setSpacingAfter(15);
				Paragraph cvssv3 = new Paragraph("CVSSv3: " + is.getCvssv3());
				cvssv3.setSpacingAfter(15);
				Paragraph cwe = new Paragraph("CWE: " + is.getCweList());
				cwe.setSpacingAfter(15);
				Paragraph desc = new Paragraph("Description: " + is.getDescription());
				desc.setSpacingAfter(190);

				try {
					document.add(title);
					document.add(name);
					document.add(path);
					document.add(sha);
					document.add(source);
					document.add(severity);
					document.add(cvssv2);
					document.add(cvssv3);
					document.add(cwe);
					document.add(desc);
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			});
		});

	}

	private String toXmlString(Analysis analysis) {

		XmlMapper mapper = new XmlMapper();
		String prettyXmlString = null;

		List<String> warnings = analysis.getSast().getIssueList().stream().map(i -> i.getWarningHtml())
				.collect(Collectors.toList());

		try {
			prettyXmlString = mapper.writeValueAsString(warnings);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		try {
			mapper.writeValue(new File("simple_bean.xml"), warnings);
		} catch (IOException e) {
			e.printStackTrace();
		}
		File file = new File("simple_bean.xml");

		return prettyXmlString;
	}

	private String toJsonString(Analysis analysis) {

		ObjectMapper mapper = new ObjectMapper();
		String prettyJsonString = null;

		try {
			prettyJsonString = mapper.writeValueAsString(
					analysis.getSast().getIssueList().stream().map(i -> i.getMessage()).collect(Collectors.toList()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return prettyJsonString;
	}

	private void writeStringInPDF(String text) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("myJSON.pdf"));
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}

		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Chunk chunk = new Chunk(text, font);

		try {
			document.add(chunk);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		document.close();
	}

	public static <T> void main(String[] args) {
		System.out.println("Bye World");
	}

}
