package com.humbertorovina.clockingsystem.api.repositories;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.humbertorovina.clockingsystem.api.entities.Clocking;

@Transactional(readOnly = true)
@NamedQueries({
		@NamedQuery(name = "ClockingRepository.findByEmployeeId", 
				query = "SELECT clock FROM Clocking clock WHERE clock.employee.id = :employeeId") })
public interface ClockingRepository extends JpaRepository<Clocking, Long> {

	List<Clocking> findByEmployeeId(@Param("employeeId") Long employeeId);

	Page<Clocking> findByEmployeeId(@Param("employeeId") Long employeeId, Pageable pageable);
}