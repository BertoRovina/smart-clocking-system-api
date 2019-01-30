package com.humbertorovina.clockingsystem.api.services;

import java.util.Optional;

import com.humbertorovina.clockingsystem.api.entities.Employee;

public interface EmployeeService {
	
	/**
	 * Persists a employee in the Database
	 * 
	 * @param employee
	 * @return Employee
	 */
	Employee persist(Employee employee);
	
	/**
	 * Search employee by doc
	 * 
	 * @param Doc
	 * @return Optional<Employee>
	 */
	Optional<Employee> searchByDoc(String doc);
	
	/**
	 * Search employee by email
	 * 
	 * @param email
	 * @return Optional<Employee>
	 */
	Optional<Employee> searchByEmail(String email);
	
	/**
	 * Search employee by id
	 * 
	 * @param id
	 * @return Optional<Employee>
	 */
	Optional<Employee> searchById(Long id);
	
	
}
