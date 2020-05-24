package edu.uoc.app.domain.sast;

import java.util.List;

import edu.uoc.app.domain.sast.xml.BugInstanceString;
import edu.uoc.app.domain.sast.xml.SourceLine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Vuln {

	// HTML
	private String nameByHtml;

	private String referenceByHtml;

	private String priorityByHtml;

	private String textByHtml;

	private String textHtmlByHtml;

	private String warningTextHtmlByHtml;

	private List<String> sourceLineListByHtml;

	private String fileNameByHtml;

	private String lineNumberByHtml;

	// XDOCS
    private String classnameByXdocs;

	private String typeByXdocs;

	private String priorityByXdocs;

	private String messageByXdocs;

	private String lineByXdocs;

	// XML

	private String typeByXml;

	private String priorityByXml;

	private String rankByXml;

	private String abbrevByXml;

	private String categoryByXml;

    private String classnameByXml;

    private String methodClassnameByXml;

    private String methodNameByXml;

    private String methodSignatureByXml;

    private List<SourceLine> traceByXml;

    private List<BugInstanceString> elementListByXml;



}
