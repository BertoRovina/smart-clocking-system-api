package com.humbertorovina.clockingsystem.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.humbertorovina.clockingsystem.api.entities.Employee;
import com.humbertorovina.clockingsystem.api.repositories.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeServiceTest {
	
	@MockBean
	private EmployeeRepository employeeRepo;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Before
	public void setUp() throws Exception{
		BDDMockito.given(this.employeeRepo.save(Mockito.any(Employee.class))).willReturn(new Employee());
		BDDMockito.given(this.employeeRepo.findById(Mockito.anyLong())).willReturn(Optional.of(new Employee()));
		BDDMockito.given(this.employeeRepo.findByEmail(Mockito.anyString())).willReturn(new Employee());
		BDDMockito.given(this.employeeRepo.findByDoc(Mockito.anyString())).willReturn(new Employee());
	}
	
	@Test
	public void testEmployeePersist() {
		Employee employee = this.employeeService.persist(new Employee());
		
		assertNotNull(employee);
	}
	
	@Test
	public void testSearchEmployeeById() {
		Optional<Employee> employee = this.employeeService.searchById(1L);
		
		assertTrue(employee.isPresent());
	}
	
	@Test
	public void testSearchEmployeeByDoc() {
		Optional<Employee> employee = this.employeeService.searchByDoc("234234225");
		
		assertTrue(employee.isPresent());
	}
}
