package edu.uoc.app.domain.github;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "WebhookRepository")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class WebhookRepository {

	private String id;

	private String name;

}
