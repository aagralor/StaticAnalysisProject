package edu.uoc.app.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import edu.uoc.app.domain.Suppression;
import edu.uoc.app.domain.sca.xml.DependencyCheckSuppressions;
import edu.uoc.app.domain.sca.xml.FilePath;
import edu.uoc.app.domain.sca.xml.Suppress;
import edu.uoc.app.dto.sca.SuppressionDTO;

@Component
public class SuppressionMapper {

	public SuppressionDTO toSuppressionDTO(Suppression in) {

		SuppressionDTO resp = new SuppressionDTO();

		resp.setNotes(in.getNotes());
		resp.setFilePath(in.getFilePath());
		resp.setFilePathIsRegex(in.getFilePathIsRegex());
		resp.setSha1(in.getSha1());
		resp.setCve(in.getCve());

		return resp;
	}

	public List<SuppressionDTO> toSuppressionDTOList(List<Suppression> in) {
		List<SuppressionDTO> resp = new ArrayList<SuppressionDTO>();

		if (in == null) {
			return resp;
		}

		for (Suppression e : in) {
			resp.add(toSuppressionDTO(e));
		}

		return resp;
	}

	public Suppression toSuppressionFromDTO(SuppressionDTO in) {

		Suppression resp = new Suppression();

		resp.setNotes(in.getNotes());
		if (in.getFilePath().isEmpty()) {
			resp.setFilePath(null);
			resp.setFilePathIsRegex(null);
		} else {
			resp.setFilePath(in.getFilePath());
			resp.setFilePathIsRegex(in.getFilePathIsRegex());
		}
		if (in.getSha1().isEmpty()) {
			resp.setSha1(null);
		} else {
			resp.setSha1(in.getSha1());
		}
		resp.setCve(in.getCve());

		return resp;
	}

	public List<Suppression> toSuppressionFromDTOList(List<SuppressionDTO> in) {
		List<Suppression> resp = new ArrayList<Suppression>();

		if (in == null) {
			return resp;
		}

		for (SuppressionDTO e : in) {
			resp.add(toSuppressionFromDTO(e));
		}

		return resp;
	}

	public Suppression toSuppression(Suppress in) {

		Suppression resp = new Suppression();

		resp.setNotes(in.getNotes());
		resp.setFilePath(in.getFilePath().getValue());
		resp.setFilePathIsRegex(in.getFilePath().getRegex());
		resp.setSha1(in.getSha1());
		resp.setCve(in.getCve());

		return resp;
	}

	public List<Suppression> toSuppressionList(List<Suppress> in) {
		List<Suppression> resp = new ArrayList<Suppression>();

		if (in == null) {
			return resp;
		}

		for (Suppress e : in) {
			resp.add(toSuppression(e));
		}

		return resp;
	}

	public Suppress toSuppress(Suppression in) {

		Suppress resp = new Suppress();

		resp.setNotes(in.getNotes());
		if (in.getFilePathIsRegex() != null && in.getFilePath() != null) {
			FilePath filePathResp = new FilePath();
			filePathResp.setRegex(in.getFilePathIsRegex());
			filePathResp.setValue(in.getFilePath());
			resp.setFilePath(filePathResp);
		} else {
			resp.setFilePath(null);
		}
		resp.setSha1(in.getSha1());
		resp.setCve(in.getCve());

		return resp;
	}

	public List<Suppress> toSuppressList(List<Suppression> in) {
		List<Suppress> resp = new ArrayList<Suppress>();

		if (in == null) {
			return resp;
		}

		for (Suppression e : in) {
			resp.add(toSuppress(e));
		}

		return resp;
	}

	public DependencyCheckSuppressions toDependencyCheckSuppressions(List<Suppression> in) {

		DependencyCheckSuppressions resp = new DependencyCheckSuppressions();

		resp.setXmlns("https://jeremylong.github.io/DependencyCheck/dependency-suppression.1.3.xsd");

		if (in == null) {
			return resp;
		}

		resp.setSuppressList(toSuppressList(in));

		return resp;

	}

}
