package com.example.app.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.app.domain.AnalysisSCA;
import com.example.app.domain.sca.DependencyCheckAnalysis;

@Component
public final class AnalysisSCAMapper {

	@Autowired
	DependencySCAMapper dependencySCAMapper;

	public AnalysisSCAMapper() {
	}

	public AnalysisSCA toAnalysisSCA(DependencyCheckAnalysis dca) {

		AnalysisSCA resp = new AnalysisSCA();

		resp.setName(dca.getProjectInfo().getName());
		resp.setReportDate(dca.getProjectInfo().getReportDate());
		resp.setDataSourceList(
				dca.getScanInfo().getDataSourceList().stream().map(ds -> ds.getName()).collect(Collectors.toList()));
		resp.setDependencyList(this.dependencySCAMapper.toDependencySCAList(dca.getDependencyList()));

		return resp;
	}

	public List<AnalysisSCA> toAnalysisSCAList(List<DependencyCheckAnalysis> dcaList) {

		List<AnalysisSCA> resp = new ArrayList<AnalysisSCA>();

		if (dcaList == null) {
			return resp;
		}

		for (DependencyCheckAnalysis dca : dcaList) {
			resp.add(toAnalysisSCA(dca));
		}

		return resp;
	}

}
