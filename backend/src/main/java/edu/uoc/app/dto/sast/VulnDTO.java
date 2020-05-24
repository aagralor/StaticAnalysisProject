package edu.uoc.app.dto.sast;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VulnDTO {

	private String value;

	private String type;

	private String scope;


}
