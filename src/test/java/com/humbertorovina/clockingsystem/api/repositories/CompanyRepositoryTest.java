package com.humbertorovina.clockingsystem.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.humbertorovina.clockingsystem.api.entities.Company;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CompanyRepositoryTest {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	private static final String DOC = "51463645000100";

	@Before
	public void setUp() throws Exception {
		Company company = new Company();
		company.setCompanyName("Sample Company");
		company.setDoc(DOC);
		this.companyRepository.save(company);
	}
	
	@After
    public final void tearDown() { 
		this.companyRepository.deleteAll();
	}

	@Test
	public void testSearchByDoc() {
		Company company = this.companyRepository.findByDoc(DOC);
		
		assertEquals(DOC, company.getDoc());
	}
}
