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

import com.humbertorovina.clockingsystem.api.entities.Company;
import com.humbertorovina.clockingsystem.api.repositories.CompanyRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CompanyServiceTest {
	
	@MockBean
	private CompanyRepository companyRepo;
	
	@Autowired
	private CompanyService companyService;
	
	private static final String DOC = "2342645242";
	
	@Before
	public void setUp() throws Exception{
		BDDMockito.given(this.companyRepo.findByDoc(Mockito.anyString())).willReturn(new Company());
		BDDMockito.given(this.companyRepo.save(Mockito.any(Company.class))).willReturn(new Company());
	}
	
	@Test
	public void testSearchCompanyByDoc() {
		Optional<Company> company = this.companyService.searchByDoc(DOC);

		assertTrue(company.isPresent());
	}
	
	@Test
	public void testPersistirEmpresa() {
		Company company = this.companyService.persistent(new Company());

		assertNotNull(company);
	}
	
}
