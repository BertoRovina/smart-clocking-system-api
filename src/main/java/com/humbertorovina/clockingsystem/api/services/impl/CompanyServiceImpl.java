package com.humbertorovina.clockingsystem.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humbertorovina.clockingsystem.api.entities.Company;
import com.humbertorovina.clockingsystem.api.repositories.CompanyRepository;
import com.humbertorovina.clockingsystem.api.services.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService{
	
	private static final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public Optional<Company> searchByDoc(String doc) {
		log.info("Searching company based on Doc {}", doc);
		return Optional.ofNullable(companyRepository.findByDoc(doc));
	}

	@Override
	public Company persist(Company company) {
		log.info("Persisting company: {}", company);
		return this.companyRepository.save(company);
	}

}
