package com.humbertorovina.clockingsystem.api.services;

import java.util.Optional;

import com.humbertorovina.clockingsystem.api.entities.Company;

public interface CompanyService {
	
	/**
	 * Returns a company based on a Doc
	 * 
	 * @param doc
	 * @return Optional<Company>
	 */
	Optional<Company> searchByDoc(String doc);
	
	/**
	 * Register a new company to the Database
	 * 
	 * @param company
	 * @return Company
	 */
	Company persistent(Company company);
}
