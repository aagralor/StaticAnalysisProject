package com.example.app.domain.xml;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "BugCollection")
public class BugCollectionXmlReport {

	@JacksonXmlProperty(isAttribute = true)
	private String version;

	@JacksonXmlProperty(isAttribute = true)
    private String sequence;
 
	@JacksonXmlProperty(isAttribute = true)
    private String timestamp;
    
	@JacksonXmlProperty(isAttribute = true)
    private String analysisTimestamp;
    
	@JacksonXmlProperty(isAttribute = true)
    private String release;
	
    @JacksonXmlProperty(localName = "Project")
    @JacksonXmlElementWrapper(useWrapping = false)
    private Project project;
    
    @JacksonXmlProperty(localName = "BugInstance")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<BugInstance> bugInstanceList;


}
