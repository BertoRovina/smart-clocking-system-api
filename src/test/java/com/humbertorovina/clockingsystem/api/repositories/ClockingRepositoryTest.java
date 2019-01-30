package com.humbertorovina.clockingsystem.api.repositories;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.humbertorovina.clockingsystem.api.entities.Company;
import com.humbertorovina.clockingsystem.api.entities.Employee;
import com.humbertorovina.clockingsystem.api.entities.Clocking;
import com.humbertorovina.clockingsystem.api.enums.ProfileEnum;
import com.humbertorovina.clockingsystem.api.enums.TypeEnum;
import com.humbertorovina.clockingsystem.api.utils.PasswordUtils;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClockingRepositoryTest {
	
	@Autowired
	private ClockingRepository clockingRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	private Long employeeId;

	@Before
	public void setUp() throws Exception {
		Company company = this.companyRepository.save(retrieveDataCompany());
		
		Employee employee = this.employeeRepository.save(retrieveDataEmployee(company));
		this.employeeId = employee.getId();
		
		this.clockingRepository.save(retrieveDataClockings(employee));
		this.clockingRepository.save(retrieveDataClockings(employee));
	}

	@After
	public void tearDown() throws Exception {
		this.companyRepository.deleteAll();
	}

	@Test
	public void testSearchClockingsByEmployeeId() {
		List<Clocking> clockings = this.clockingRepository.findByEmployeeId(employeeId);
		
		assertEquals(2, clockings.size());
	}
	
	@Test
	public void testSearchClockingsByEmployeeIdPageable() {
		@Deprecated
		PageRequest page = new PageRequest(0, 10);
		Page<Clocking> clockings = this.clockingRepository.findByEmployeeId(employeeId, page);
		
		assertEquals(2, clockings.getTotalElements());
	}
	
	private Clocking retrieveDataClockings(Employee employee) {
		Clocking lancameto = new Clocking();
		lancameto.setData(new Date());
		lancameto.setType(TypeEnum.START_LUNCH);
		lancameto.setEmployee(employee);
		return lancameto;
	}

	private Employee retrieveDataEmployee(Company company) throws NoSuchAlgorithmException {
		Employee employee = new Employee();
		employee.setName("Fulano de Tal");
		employee.setProfile(ProfileEnum.ROLE_USER);
		employee.setPassword(PasswordUtils.generateBCrypt("123456"));
		employee.setDoc("24291173474");
		employee.setEmail("email@email.com");
		employee.setCompany(company);
		return employee;
	}

	private Company retrieveDataCompany() {
		Company company = new Company();
		company.setCompanyName("Example Company");
		company.setDoc("51463645000100");
		return company;
	}
}
