package com.humbertorovina.clockingsystem.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.humbertorovina.clockingsystem.api.entities.Clocking;
import com.humbertorovina.clockingsystem.api.repositories.ClockingRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClockingServiceTest {
	
	@MockBean
	private ClockingRepository clockingRepo;
	
	@Autowired
	private ClockingService clockingService;
	
	@Before
	public void setUp() throws Exception{
		BDDMockito.given(this.clockingRepo.findByEmployeeId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
		.willReturn(new PageImpl<Clocking>(new ArrayList<Clocking>()));
		BDDMockito.given(this.clockingRepo.findById(Mockito.anyLong())).willReturn(Optional.of(new Clocking()));
		BDDMockito.given(this.clockingRepo.save(Mockito.any(Clocking.class))).willReturn(new Clocking());
	}
	
	@Test
	public void testSearchClockingByEmployeeId() {
		Page<Clocking> lancamento = this.clockingService.searchEmployeeById(1L, new PageRequest(0, 10));

		assertNotNull(lancamento);
	}
	
	@Test
	public void testSearchClockingById() {
		Optional<Clocking> lancamento = this.clockingService.searchById(1L);

		assertTrue(lancamento.isPresent());
	}
	
	@Test
	public void testPersistClocking() {
		Clocking lancamento = this.clockingService.persist(new Clocking());

		assertNotNull(lancamento);
	}
}
