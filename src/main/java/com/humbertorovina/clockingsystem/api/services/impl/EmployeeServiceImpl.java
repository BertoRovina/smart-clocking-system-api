package com.humbertorovina.clockingsystem.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humbertorovina.clockingsystem.api.entities.Employee;
import com.humbertorovina.clockingsystem.api.services.EmployeeService;
import com.humbertorovina.clockingsystem.api.repositories.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private EmployeeRepository employeeRepo;

	@Override
	public Employee persist(Employee employee) {
		log.info("Persisting employee: {}", employee);
		return this.employeeRepo.save(employee);
	}

	@Override
	public Optional<Employee> searchByDoc(String doc) {
		log.info("Searching employee by doc: {}", doc);
		return Optional.ofNullable(this.employeeRepo.findByDoc(doc));
	}

	@Override
	public Optional<Employee> searchByEmail(String email) {
		log.info("Searching employee by email: {}", email);
		return Optional.ofNullable(this.employeeRepo.findByEmail(email));
	}

	@Override
	public Optional<Employee> searchById(Long id) {
		log.info("Searching employee by id: {}", id);
		return  this.employeeRepo.findById(id);
	}

}
