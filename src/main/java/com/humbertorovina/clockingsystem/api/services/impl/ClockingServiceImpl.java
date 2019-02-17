package com.humbertorovina.clockingsystem.api.services.impl;

import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.humbertorovina.clockingsystem.api.entities.Clocking;
import com.humbertorovina.clockingsystem.api.services.ClockingService;
import com.humbertorovina.clockingsystem.api.repositories.ClockingRepository;

@Service
public class ClockingServiceImpl implements ClockingService{
	
	private static final Logger log = LoggerFactory.getLogger(ClockingServiceImpl.class);
	
	@Autowired
	private ClockingRepository clockingRepo;

	public Page<Clocking> searchEmployeeById(Long employeeId, PageRequest pageRequest) {
		log.info("Searching clocking by employee ID {}", employeeId);
		return this.clockingRepo.findByEmployeeId(employeeId, pageRequest);
	}

	@Cacheable("clockingById")
	public Optional<Clocking> searchById(Long id) {
		log.info("Searching one clocking by ID {} ", id);
		return this.clockingRepo.findById(id);
	}

	@CachePut("clockingById")
	public Clocking persist(Clocking clocking) {
		log.info("Persisting clocking {} ", clocking);
		return this.clockingRepo.save(clocking);
	}

	public void remover(Long id) {
		log.info("Removing clocking with id {} ", id);
		this.clockingRepo.deleteById(id);
	}
}
