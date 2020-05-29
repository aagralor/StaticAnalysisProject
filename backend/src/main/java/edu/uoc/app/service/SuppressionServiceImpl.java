package edu.uoc.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uoc.app.domain.Suppression;
import edu.uoc.app.domain.sca.xml.DependencyCheckSuppressions;
import edu.uoc.app.dto.sca.SuppressionDTO;
import edu.uoc.app.mapper.SuppressionMapper;
import edu.uoc.app.repo.SuppressionRepository;
import edu.uoc.app.utils.XmlParser;

@Service
public class SuppressionServiceImpl implements SuppressionService {

	@Autowired
	SuppressionRepository repo;

	@Autowired
	SuppressionMapper mapper;

	@Override
	public SuppressionDTO create(SuppressionDTO suppression) {
		return this.mapper.toSuppressionDTO(this.repo.save(this.mapper.toSuppressionFromDTO(suppression)));
	}

	@Override
	public List<SuppressionDTO> findAll() {
		List<Suppression> response =  this.repo.findAll();

		return this.mapper.toSuppressionDTOList(response);
	}

	@Override
	public List<SuppressionDTO> generateSuppressionsFile(String filepath) {

		List<Suppression> response =  this.repo.findAll();
		DependencyCheckSuppressions suppressions = this.mapper.toDependencyCheckSuppressions(response);

		try {
			XmlParser.createDependencyCheckSuppressions(filepath, suppressions);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this.mapper.toSuppressionDTOList(response);
	}

	public static <T> void main(String[] args) {
		System.out.println("Bye World");
	}

}
