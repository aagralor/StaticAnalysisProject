package com.example.app.domain.mongo;

import java.util.List;

import com.example.app.domain.xml.Project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindSecBugsAnalysis {
	
	// XML
	private String version;
	
    private String sequence;

    private String timestamp;

    private String analysisTimestamp;
    
    private String release;
    
    private Project project;
    
    private String projectName;
    
    private List<String> jarList;
    
	private String pluginId;

	private String pluginEnabled;
	
	private List<Vuln> vulnList;

}
