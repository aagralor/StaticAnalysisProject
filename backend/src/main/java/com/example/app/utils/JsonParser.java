package com.example.app.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.app.domain.sast.xml.BugCollectionXmlReport;
import com.example.app.domain.sca.DependencyCheckAnalysis;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonParser {

	private JsonParser() {
	}

	public static BugCollectionXmlReport parseToBugCollectionFromXml(String filePath)
			throws FileNotFoundException, IOException {

//		XmlMapper xmlMapper = new XmlMapper();
//
//		File fileXml = new File(filePath);
//		String xml = FileTools.inputStreamToString(new FileInputStream(fileXml));
//		BugCollectionXmlReport valueXml = xmlMapper.readValue(xml, BugCollectionXmlReport.class);
//
//		return valueXml;

		return null;
	}

	public static String parseToBugCollectionFromXdocs(String filePath)
			throws FileNotFoundException, IOException {

//		XmlMapper xmlMapper = new XmlMapper();
//
//		File fileXdocs = new File(filePath);
//		String xdocs = FileTools.inputStreamToString(new FileInputStream(fileXdocs));
//		BugCollectionXdocsReport valueXdocs = xmlMapper.readValue(xdocs, BugCollectionXdocsReport.class);
//
//		return valueXdocs;

		return null;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.println("Hello world");

//		XmlMapper xmlMapper = new XmlMapper();
//
//		File fileXml = new File("project/application_report_XML.xml");
//		String xml = FileTools.inputStreamToString(new FileInputStream(fileXml));
//		BugCollectionXmlReport valueXml = xmlMapper.readValue(xml, BugCollectionXmlReport.class);
//	    assertTrue(value.getX() == 1 && value.getY() == 2);
//		BugCollectionXmlReport valueXml = parseToBugCollectionFromXml("project/application_report_XML.xml");

//		File fileXdocs = new File("project/application_report_XDOCS.xml");
//		String xdocs = FileTools.inputStreamToString(new FileInputStream(fileXdocs));
//		BugCollectionXdocsReport valueXdocs = xmlMapper.readValue(xdocs, BugCollectionXdocsReport.class);
//	    assertTrue(value.getX() == 1 && value.getY() == 2);
//		BugCollectionXdocsReport valueXdocs = parseToBugCollectionFromXdocs("project/application_report_XDOCS.xml");

		ObjectMapper objectMapper = new ObjectMapper();
//		AnalysisSCAMapper analysisMapper = new AnalysisSCAMapper();
		//		Car car = new Car("yellow", "renault");
//		objectMapper.writeValue(new File("project/car.json"), car);

		DependencyCheckAnalysis dcr = objectMapper.readValue(new File("project/report_JSON.json"), DependencyCheckAnalysis.class);

//		AnalysisSCA sca = analysisMapper.toAnalysisSCA(dcr);
		System.out.println("Bye world");
	}


}
