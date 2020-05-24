package com.example.app.service;

import java.util.List;

import com.example.app.domain.Suppression;

public interface SuppressionService {

	Suppression create(Suppression suppression);

	List<Suppression> findAll();

	List<Suppression> generateSuppressionsFile(String filepath);

}
