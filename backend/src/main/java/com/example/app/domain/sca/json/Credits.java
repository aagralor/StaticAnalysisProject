package com.example.app.domain.sca.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Credits {

    @JsonProperty("NVD")
    private String nvd;

    @JsonProperty("NPM")
    private String npm;

    @JsonProperty("RETIREJS")
    private String retirejs;

    @JsonProperty("OSSINDEX")
    private String ossindex;

}
