package com.example.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.Suppression;
import com.example.app.domain.sca.xml.DependencyCheckSuppressions;
import com.example.app.mapper.SuppressionMapper;
import com.example.app.repo.SuppressionRepository;
import com.example.app.utils.XmlParser;

@Service
public class SuppressionServiceImpl implements SuppressionService {

	@Autowired
	SuppressionRepository repo;

	@Autowired
	SuppressionMapper mapper;

	@Override
	public Suppression create(Suppression suppression) {
		return this.repo.save(suppression);
	}

	@Override
	public List<Suppression> findAll() {
		List<Suppression> response =  this.repo.findAll();

		DependencyCheckSuppressions suppressions = this.mapper.toDependencyCheckSuppressions(response);

		try {
			XmlParser.createDependencyCheckSuppressions(".", suppressions);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public List<Suppression> generateSuppressionsFile(String filepath) {

		List<Suppression> response =  this.repo.findAll();
		DependencyCheckSuppressions suppressions = this.mapper.toDependencyCheckSuppressions(response);

		try {
			XmlParser.createDependencyCheckSuppressions(filepath, suppressions);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}

	public static <T> void main(String[] args) {
		System.out.println("Bye World");
	}

}
