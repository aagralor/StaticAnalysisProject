package com.example.app.domain.sast.html;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VulnInstance {

	private String name;

	private String reference;

	private String priority;

	private String text;

	private String textHtml;

	private String warningTextHtml;

	private List<String> sourceLineList;

}
