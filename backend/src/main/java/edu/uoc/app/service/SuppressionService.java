package edu.uoc.app.service;

import java.util.List;

import edu.uoc.app.domain.Suppression;

public interface SuppressionService {

	Suppression create(Suppression suppression);

	List<Suppression> findAll();

	List<Suppression> generateSuppressionsFile(String filepath);

}
