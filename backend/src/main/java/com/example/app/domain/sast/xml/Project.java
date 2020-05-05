package com.example.app.domain.sast.xml;

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
public class Project {

    @JacksonXmlProperty(isAttribute = true)
    private String projectName;

    @JacksonXmlProperty(localName = "Jar")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<String> jarList;

    @JacksonXmlProperty(localName = "Plugin")
    @JacksonXmlElementWrapper(useWrapping = false)
    private Plugin plugin;
}
