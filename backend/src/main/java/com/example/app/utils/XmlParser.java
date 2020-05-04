package com.example.app.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipInputStream;

import com.example.app.domain.xdocs.BugCollectionXdocsReport;
import com.example.app.domain.xml.BugCollectionXmlReport;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public final class XmlParser {

    private static final int BUFFER_SIZE = 4096;

    private XmlParser() {
    }

    public static void unzip(byte[] data, String dirName) throws IOException {
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
    }

    public static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.println("Hello world");

	    XmlMapper xmlMapper = new XmlMapper();

		File fileXml = new File("project/application_report_XML.xml");
	    String xml = inputStreamToString(new FileInputStream(fileXml));
	    BugCollectionXmlReport valueXml = xmlMapper.readValue(xml, BugCollectionXmlReport.class);
//	    assertTrue(value.getX() == 1 && value.getY() == 2);

		File fileXdocs = new File("project/application_report_XDOCS.xml");
	    String xdocs = inputStreamToString(new FileInputStream(fileXdocs));
	    BugCollectionXdocsReport valueXdocs = xmlMapper.readValue(xdocs, BugCollectionXdocsReport.class);
//	    assertTrue(value.getX() == 1 && value.getY() == 2);

	    System.out.println("Bye world");
	}

}
