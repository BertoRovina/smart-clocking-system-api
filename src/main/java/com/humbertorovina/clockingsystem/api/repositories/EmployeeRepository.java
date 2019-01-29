package com.humbertorovina.clockingsystem.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.humbertorovina.clockingsystem.api.entities.Employee;

@Transactional(readOnly = true)
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	Employee findByDoc(String doc);
	
	Employee findByEmail(String email);
	
	Employee findByDocOrEmail(String doc, String email);
}
