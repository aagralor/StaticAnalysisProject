package com.example.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.app.domain.xdocs.BugCollectionXdocsReport;
import com.example.app.domain.xml.BugCollectionXmlReport;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public final class XmlParser {

	private XmlParser() {
	}

	public static BugCollectionXmlReport parseToBugCollectionFromXml(String filePath)
			throws FileNotFoundException, IOException {

		XmlMapper xmlMapper = new XmlMapper();

		File fileXml = new File(filePath);
		String xml = FileTools.inputStreamToString(new FileInputStream(fileXml));
		BugCollectionXmlReport valueXml = xmlMapper.readValue(xml, BugCollectionXmlReport.class);

		return valueXml;
	}

	public static BugCollectionXdocsReport parseToBugCollectionFromXdocs(String filePath)
			throws FileNotFoundException, IOException {

		XmlMapper xmlMapper = new XmlMapper();

		File fileXdocs = new File(filePath);
		String xdocs = FileTools.inputStreamToString(new FileInputStream(fileXdocs));
		BugCollectionXdocsReport valueXdocs = xmlMapper.readValue(xdocs, BugCollectionXdocsReport.class);

		return valueXdocs;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.println("Hello world");

//		XmlMapper xmlMapper = new XmlMapper();
//
//		File fileXml = new File("project/application_report_XML.xml");
//		String xml = FileTools.inputStreamToString(new FileInputStream(fileXml));
//		BugCollectionXmlReport valueXml = xmlMapper.readValue(xml, BugCollectionXmlReport.class);
//	    assertTrue(value.getX() == 1 && value.getY() == 2);
		BugCollectionXmlReport valueXml = parseToBugCollectionFromXml("project/application_report_XML.xml");
		
//		File fileXdocs = new File("project/application_report_XDOCS.xml");
//		String xdocs = FileTools.inputStreamToString(new FileInputStream(fileXdocs));
//		BugCollectionXdocsReport valueXdocs = xmlMapper.readValue(xdocs, BugCollectionXdocsReport.class);
//	    assertTrue(value.getX() == 1 && value.getY() == 2);
		BugCollectionXdocsReport valueXdocs = parseToBugCollectionFromXdocs("project/application_report_XDOCS.xml");

		System.out.println("Bye world");
	}

}
