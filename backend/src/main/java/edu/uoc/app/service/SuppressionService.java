package edu.uoc.app.service;

import java.util.List;

import edu.uoc.app.dto.sca.SuppressionDTO;

public interface SuppressionService {

	SuppressionDTO create(SuppressionDTO suppression);

	List<SuppressionDTO> findAll();

	List<SuppressionDTO> generateSuppressionsFile(String filepath);

}
