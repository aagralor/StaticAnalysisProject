package com.example.app.domain.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SourceLine {

	private String classname;

	private String start;

	private String end;

	private String startBytecode;

	private String endBytecode;

	private String sourcefile;

	private String sourcepath;

}
