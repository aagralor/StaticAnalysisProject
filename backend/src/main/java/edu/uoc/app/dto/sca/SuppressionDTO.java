package edu.uoc.app.dto.sca;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SuppressionDTO {

	private String notes;

	private String filePath;

	private Boolean filePathIsRegex;

	private String sha1;

	private String cve;

}
