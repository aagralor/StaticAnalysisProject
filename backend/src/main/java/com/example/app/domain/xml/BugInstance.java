package com.example.app.domain.xml;

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
public class BugInstance {

	@JacksonXmlProperty(isAttribute = true)
	private String type;

	@JacksonXmlProperty(isAttribute = true)
	private String priority;

	@JacksonXmlProperty(isAttribute = true)
	private String rank;

	@JacksonXmlProperty(isAttribute = true)
	private String abbrev;
	
	@JacksonXmlProperty(isAttribute = true)
	private String category;
	
    @JacksonXmlProperty(localName = "Class")
    @JacksonXmlElementWrapper(useWrapping = false)
    private Class clazz;

    @JacksonXmlProperty(localName = "Method")
    @JacksonXmlElementWrapper(useWrapping = false)
    private Method method;
    
    @JacksonXmlProperty(localName = "SourceLine")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<BugInstanceSourceLine> sourceLineList;
    
    @JacksonXmlProperty(localName = "String")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<BugInstanceString> stringList;
}
