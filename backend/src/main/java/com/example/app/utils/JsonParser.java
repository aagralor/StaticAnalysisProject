package com.example.app.utils;

import java.io.File;
import java.io.IOException;

import com.example.app.domain.sca.DependencyCheckAnalysis;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonParser {

	private JsonParser() {
	}

	public static DependencyCheckAnalysis parseToAnalysisFromJson(String filePath)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		DependencyCheckAnalysis dcr = objectMapper.readValue(new File(filePath),
				DependencyCheckAnalysis.class);

		return dcr;
	}

	public static void main(String[] args) {

		System.out.println("Bye world");
	}

}
