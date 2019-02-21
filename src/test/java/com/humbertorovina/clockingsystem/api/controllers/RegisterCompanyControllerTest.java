package com.humbertorovina.clockingsystem.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.humbertorovina.clockingsystem.api.dtos.RegisterCompDto;
import com.humbertorovina.clockingsystem.api.entities.Company;
import com.humbertorovina.clockingsystem.api.entities.Employee;
import com.humbertorovina.clockingsystem.api.services.CompanyService;
import com.humbertorovina.clockingsystem.api.services.EmployeeService;
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

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RegisterCompanyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private EmployeeService employeeService;

    private final static String URL_BASE = "/api/register-company/";
    private final static Long ID = 1L;
    private final static String OWNER_NAME =  "William J.";
    private final static String OWNER_EMAIL = "williamJ@gmail.com";
    private final static String OWNER_DOC = "654321";
    private final static String PASSWORD = "123456";
    private final static String COMPANY_DOC = "123123";
    private final static String COMPANY_NAME = "William's Consulting";

    @Test
    @WithMockUser
    public void registerEmployeeTest() throws Exception{
        Company company = retrieveCompanyData();
        Employee employee = retrieveEmployeeData(company);

        BDDMockito.given(this.employeeService.persist(Mockito.any(Employee.class))).willReturn(employee);
        BDDMockito.given(this.companyService.persist(Mockito.any(Company.class))).willReturn(company);

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(this.retrievePostRequestJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.name", equalTo(OWNER_NAME)))
                .andExpect(jsonPath("$.data.email", equalTo(OWNER_EMAIL)))
                .andExpect(jsonPath("$.data.password", equalTo(null)))
                .andExpect(jsonPath("$.data.ownerDoc",equalTo(OWNER_DOC)))
                .andExpect(jsonPath("$.data.companyName",equalTo(COMPANY_NAME)))
                .andExpect(jsonPath("$.data.doc", equalTo(COMPANY_DOC)))
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    private String retrievePostRequestJson() throws JsonProcessingException {
        RegisterCompDto companyDto = new RegisterCompDto();
        companyDto.setEmail(OWNER_EMAIL);
        companyDto.setOwnerDoc(OWNER_DOC);
        companyDto.setName(OWNER_NAME);
        companyDto.setPassword(PASSWORD);
        companyDto.setDoc(COMPANY_DOC);
        companyDto.setCompanyName(COMPANY_NAME);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(companyDto);
    }

    private Company retrieveCompanyData(){
        Company company = new Company();
        company.setId(ID);
        company.setDoc(COMPANY_DOC);
        company.setCompanyName(COMPANY_NAME);

        return company;
    }

    private Employee retrieveEmployeeData(Company company){
        Employee employee = new Employee();
        employee.setId(ID);
        employee.setDoc(OWNER_DOC);
        employee.setName(OWNER_NAME);
        employee.setEmail(OWNER_EMAIL);
        employee.setPassword(PASSWORD);

        employee.setCompany(company);

        return employee;
    }
}
