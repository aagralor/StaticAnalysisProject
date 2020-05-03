package com.example.app.domain.xdocs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class File {
	
    @JacksonXmlProperty(isAttribute = true)
    private String classname;
    
    @JacksonXmlProperty(localName = "BugInstance")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<BugInstance> bugInstanceList;

}
