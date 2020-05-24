package edu.uoc.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.uoc.app.domain.DependencySCA;
import edu.uoc.app.domain.sca.json.Dependency;

@Component
public class DependencySCAMapper {

	@Autowired
	VulnerabilitySCAMapper vulnerabilitySCAMapper;

	public DependencySCA toDependencySCA(Dependency in) {

		DependencySCA resp = new DependencySCA();

		resp.setFileName(in.getFileName());
		resp.setFilePath(in.getFilePath());
		resp.setSha256(in.getSha256());
		resp.setSha1(in.getSha1());
		resp.setDescription(in.getDescription());
		resp.setPackageList(in.getPackageList());
		resp.setVulnerabilityIdList(in.getVulnerabilityIdList());
		resp.setVulnerabilityList(this.vulnerabilitySCAMapper.toVulnerabilitySCAList(in.getVulnerabilityList()));

		return resp;
	}

	public List<DependencySCA> toDependencySCAList(List<Dependency> in) {
		List<DependencySCA> resp = new ArrayList<DependencySCA>();

		if (in == null) {
			return resp;
		}

		for (Dependency e : in) {
			resp.add(toDependencySCA(e));
		}

		return resp;
	}

}
