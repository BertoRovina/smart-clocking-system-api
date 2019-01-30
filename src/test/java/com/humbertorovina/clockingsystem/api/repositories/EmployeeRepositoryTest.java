package com.humbertorovina.clockingsystem.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.humbertorovina.clockingsystem.api.entities.Company;
import com.humbertorovina.clockingsystem.api.entities.Employee;
import com.humbertorovina.clockingsystem.api.enums.ProfileEnum;
import com.humbertorovina.clockingsystem.api.utils.PasswordUtils;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeRepositoryTest {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private CompanyRepository companyRepository;

	private static final String EMAIL = "email@email.com";
	private static final String DOC = "24291173474";

	@Before
	public void setUp() throws Exception {
		Company company = this.companyRepository.save(retrieveCompanyData());
		this.employeeRepository.save(retrieveEmployeeData(company));
	}

	@After
	public final void tearDown() {
		this.companyRepository.deleteAll();
	}

	@Test
	public void testSearchEmployeeByEmail() {
		Employee employee = this.employeeRepository.findByEmail(EMAIL);

		assertEquals(EMAIL, employee.getEmail());
	}

	@Test
	public void testSearchEmployeeByDoc() {
		Employee employee = this.employeeRepository.findByDoc(DOC);
		assertEquals(DOC, employee.getDoc());
	}

	@Test
	public void testSearchEmployeeByEmailAndDoc() {
		Employee employee = this.employeeRepository.findByDocOrEmail(DOC, EMAIL);

		assertNotNull(employee);
	}

	@Test
	public void testSearchEmployeeByEmailOrDocForInvalidEmail() {
		Employee employee = this.employeeRepository.findByDocOrEmail(DOC, "email@invali.com");

		assertNotNull(employee);
	}

	@Test
	public void testSearchEmployeeByEmailAndDocForInvalidDoc() {
		Employee employee = this.employeeRepository.findByDocOrEmail("12345678901", EMAIL);

		assertNotNull(employee);
	}

	private Employee retrieveEmployeeData(Company company) throws NoSuchAlgorithmException {
		Employee employee = new Employee();
		employee.setName("Fool Foolman");
		employee.setProfile(ProfileEnum.ROLE_USER);
		employee.setPassword(PasswordUtils.generateBCrypt("123456"));
		employee.setDoc(DOC);
		employee.setEmail(EMAIL);
		employee.setCompany(company);
		return employee;
	}

	private Company retrieveCompanyData() {
		Company company = new Company();
		company.setCompanyName("Example Company");
		company.setDoc("51463645000100");
		return company;
	}
}
