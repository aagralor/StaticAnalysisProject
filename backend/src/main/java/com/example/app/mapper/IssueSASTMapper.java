package com.example.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.app.domain.IssueSAST;
import com.example.app.domain.sast.Vuln;

@Component
public final class IssueSASTMapper {

	private IssueSASTMapper() {
	}

	public IssueSAST toIssueSAST(Vuln vuln) {

		IssueSAST resp = new IssueSAST();

		resp.setReference(vuln.getTypeByXdocs());
		resp.setName(vuln.getNameByHtml());
		resp.setPriority(vuln.getPriorityByHtml());
		resp.setPriorityValue(vuln.getPriorityByXml());
		resp.setRank(vuln.getRankByXml());
		resp.setText(vuln.getTextByHtml());
		resp.setTextHtml(vuln.getTextHtmlByHtml());
		resp.setWarningHtml(vuln.getWarningTextHtmlByHtml());
		resp.setFileName(vuln.getFileNameByHtml());
		resp.setLineNumber(vuln.getLineNumberByHtml());
		resp.setClassName(vuln.getClassnameByXdocs());
		resp.setMessage(vuln.getMessageByXdocs());
		resp.setAbbrev(vuln.getAbbrevByXml());
		resp.setCategory(vuln.getCategoryByXml());
		resp.setMethodName(vuln.getMethodNameByXml());

		return resp;
	}

	public List<IssueSAST> toIssueSASTList(List<Vuln> vulnList) {

		List<IssueSAST> resp = new ArrayList<IssueSAST>();

		if (vulnList == null) {
			return resp;
		}

		for (Vuln v : vulnList) {
			resp.add(toIssueSAST(v));
		}

		return resp;
	}

}
