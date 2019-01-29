package com.humbertorovina.clockingsystem.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.humbertorovina.clockingsystem.api.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{
	
	@Transactional(readOnly = true)
	Company findByDoc(String doc);
	
}
