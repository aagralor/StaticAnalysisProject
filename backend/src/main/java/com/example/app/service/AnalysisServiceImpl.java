package com.example.app.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

@Service
public final class AnalysisServiceImpl implements AnalysisService {

	private AnalysisServiceImpl() {
	}

	static void copy(InputStream in, OutputStream out) throws IOException {
	    while (true) {
	        int c = in.read();
	        if (c == -1) {
	            break;
	        }
	        out.write((char) c);
	    }
	}

	public static void main(String[] args) throws IOException, InterruptedException {

	    Process p = null;

	    try {
	        p = Runtime.getRuntime().exec("/opt/apache-maven-3.6.3/bin/mvn -f ./target/analysis/StaticAnalysisProject-develop clean install -f ./analysis/StaticAnalysisProject-develop clean install");
	    } catch (IOException e) {
	        System.err.println("Error on exec() method");
	        e.printStackTrace();
	    }

	    copy(p.getInputStream(), System.out);
	    p.waitFor();

	    System.out.println("Bye World");

	}

}
