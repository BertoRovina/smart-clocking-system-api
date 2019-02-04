package com.humbertorovina.clockingsystem.api.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.humbertorovina.clockingsystem.api.entities.Company;
import com.humbertorovina.clockingsystem.api.services.CompanyService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CompanyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyService companyService;

    private static final String SEARCH_COMPANY_DOC_URL = "/api/companies/doc/";
    private static final Long ID = Long.valueOf(1);
    private static final String DOC = "51463645000100";
    private static final String COMPANY_NAME = "COMPANY XYZ";

    @Test
    @WithMockUser
    public void testSearchCompanyInvalidDoc() throws Exception {
        BDDMockito.given(this.companyService.searchByDoc(Mockito.anyString())).willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get(SEARCH_COMPANY_DOC_URL + DOC).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("Company not found based on the Company Doc " + DOC));
    }

    @Test
    @WithMockUser
    public void testSearchCompanyValidDoc() throws Exception {
        BDDMockito.given(this.companyService.searchByDoc(Mockito.anyString()))
                .willReturn(Optional.of(this.retrieveCompanyData()));

        mvc.perform(MockMvcRequestBuilders.get(SEARCH_COMPANY_DOC_URL + DOC)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.companyName", equalTo(COMPANY_NAME)))
                .andExpect(jsonPath("$.data.companyDoc", equalTo(DOC)))
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    private Company retrieveCompanyData() {
        Company company = new Company();
        company.setId(ID);
        company.setCompanyName(COMPANY_NAME);
        company.setDoc(DOC);
        return company;
    }

}
