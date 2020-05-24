package edu.uoc.app.domain.sast.html;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BugCollectionHtmlReport {

    private List<VulnInstance> vulnList;

}
