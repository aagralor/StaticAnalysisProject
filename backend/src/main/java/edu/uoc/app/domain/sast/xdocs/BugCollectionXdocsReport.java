package edu.uoc.app.domain.sast.xdocs;

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
public class BugCollectionXdocsReport {

    @JacksonXmlProperty(localName = "file")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<File> fileList;

    @JacksonXmlProperty(localName = "Errors")
    private Errors errorList;

}
