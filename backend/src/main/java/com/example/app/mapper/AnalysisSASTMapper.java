package com.example.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.app.domain.AnalysisSAST;
import com.example.app.domain.sast.FindSecBugsAnalysis;

@Component
public final class AnalysisSASTMapper {
	
	@Autowired
	IssueSASTMapper issueSASTMapper;

	private AnalysisSASTMapper() {
	}

	public AnalysisSAST toAnalysisSAST(FindSecBugsAnalysis fsba) {

		AnalysisSAST resp = new AnalysisSAST();

		resp.setVersion(fsba.getVersion());
		resp.setSequence(fsba.getSequence());
		resp.setTimestamp(fsba.getTimestamp());
		resp.setAnalysisTimestamp(fsba.getAnalysisTimestamp());
		resp.setRelease(fsba.getRelease());
		resp.setProjectName(fsba.getProjectName());
		resp.setJarList(fsba.getJarList());
		resp.setIssueList(issueSASTMapper.toIssueSASTList(fsba.getVulnList()));
		
		return resp;
	}

	public List<AnalysisSAST> toAnalysisSASTList(List<FindSecBugsAnalysis> fsbaList) {

		List<AnalysisSAST> resp = new ArrayList<AnalysisSAST>();

		if (fsbaList == null) {
			return resp;
		}

		for (FindSecBugsAnalysis fsba : fsbaList) {
			resp.add(toAnalysisSAST(fsba));
		}

		return resp;
	}

}
