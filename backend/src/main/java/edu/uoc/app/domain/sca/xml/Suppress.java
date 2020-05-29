package edu.uoc.app.domain.sca.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "suppress")
@JsonInclude(Include.NON_EMPTY)
public class Suppress {

	@JacksonXmlProperty(localName = "notes")
	private String notes;

	@JacksonXmlProperty(localName = "filePath")
	private FilePath filePath;

	@JacksonXmlProperty(localName = "sha1")
	private String sha1;

	@JacksonXmlProperty(localName = "cve")
	private String cve;

}
