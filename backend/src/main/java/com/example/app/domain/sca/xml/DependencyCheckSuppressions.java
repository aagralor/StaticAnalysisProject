package com.example.app.domain.sca.xml;

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
@JacksonXmlRootElement(localName = "suppressions")
public class DependencyCheckSuppressions {

	@JacksonXmlProperty(isAttribute = true)
	private String xmlns;

    @JacksonXmlProperty(localName = "suppress")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Suppress> suppressList;


}
