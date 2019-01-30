package com.humbertorovina.clockingsystem.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.humbertorovina.clockingsystem.api.entities.Clocking;

public interface ClockingService {
	
	/**
	 * Return a paginated list of clockings from a specific employee
	 * 
	 * @param employeeId
	 * @param pageRequest
	 * @return Page<Clocking>
	 */
	Page<Clocking> searchEmployeeById(Long employeeId, PageRequest pageRequest);
	
	/**
	 * Return one clocking by Id
	 * 
	 * @param id
	 * @return Optional<Clocking>
	 */
	Optional<Clocking> searchById(Long id);
	
	/**
	 * Persist one clocking in the Database
	 * 
	 * @param clocking
	 * @return Clocking
	 */
	Clocking persist(Clocking clocking);
	
	/**
	 * Removes a clocking from the database
	 * 
	 * @param id
	 */
	void remover(Long id);
}
