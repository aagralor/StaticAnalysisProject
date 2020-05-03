package com.example.app.domain.xml;

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
public class Class {
	
    @JacksonXmlProperty(isAttribute = true)
    private String classname;

    @JacksonXmlProperty(localName = "SourceLine")
    private ClassSourceLine sourceLine;
    
}
