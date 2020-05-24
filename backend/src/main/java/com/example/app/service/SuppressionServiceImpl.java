package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.Suppression;
import com.example.app.repo.SuppressionRepository;

@Service
public class SuppressionServiceImpl implements SuppressionService {

	@Autowired
	SuppressionRepository repo;

	@Override
	public Suppression create(Suppression suppression) {
		return this.repo.save(suppression);
	}

	@Override
	public List<Suppression> findAll() {
		return this.repo.findAll();
	}

	public static <T> void main(String[] args) {
		System.out.println("Bye World");
	}

}
