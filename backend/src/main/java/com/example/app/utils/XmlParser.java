package com.example.app.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipInputStream;

import com.example.app.domain.BugCollection;
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
	    
		File file = new File("project/application_report_XDOCS.xml");
	    XmlMapper xmlMapper = new XmlMapper();
	    String xml = inputStreamToString(new FileInputStream(file));
	    
	    BugCollection value = xmlMapper.readValue(xml, BugCollection.class);
//	    assertTrue(value.getX() == 1 && value.getY() == 2);
	
	    System.out.println("Bye world");
	}

}
