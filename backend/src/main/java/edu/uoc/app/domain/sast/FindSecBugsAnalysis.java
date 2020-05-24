package edu.uoc.app.domain.sast;

import java.util.List;

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

    private String projectName;

    private List<String> jarList;

	private String pluginId;

	private String pluginEnabled;

	// XML, XDOCS, HTML
	private List<Vuln> vulnList;

}
