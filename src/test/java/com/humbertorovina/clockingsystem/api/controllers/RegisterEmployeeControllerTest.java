package com.humbertorovina.clockingsystem.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.humbertorovina.clockingsystem.api.dtos.RegisterEmployeeDto;
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
public class RegisterEmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private EmployeeService employeeService;

    private static final String URL_BASE = "/api/register-employee/";
    private static final Long ID = 5L;
    private static final String EMPLOYEE_DOC = "3452645742";
    private static final String NAME = "Jack W.";
    private static final String EMAIL = "jackw@hr.com";
    private static final String PASSWORD = "123456";
    private static final String COMPANY_DOC = "123456";

    @Test
    @WithMockUser
    public void registerEmployeeTest() throws Exception{
        Employee employee = retrieveEmployeeData();
        BDDMockito.given(this.companyService.searchByDoc(Mockito.anyString())).willReturn(Optional.of(new Company()));
        BDDMockito.given(this.employeeService.persist(Mockito.any(Employee.class))).willReturn(employee);

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(this.retrievePostRequestJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.name", equalTo(NAME)))
                .andExpect(jsonPath("$.data.email", equalTo(EMAIL)))
                .andExpect(jsonPath("$.data.password", equalTo(null)))
                .andExpect(jsonPath("$.data.companyDoc", equalTo(COMPANY_DOC)))
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    private String retrievePostRequestJson() throws JsonProcessingException {
        RegisterEmployeeDto employeeDto = new RegisterEmployeeDto();
        employeeDto.setEmail(EMAIL);
        employeeDto.setDoc(EMPLOYEE_DOC);
        employeeDto.setName(NAME);
        employeeDto.setPassword(PASSWORD);
        employeeDto.setCompanyDoc(COMPANY_DOC);
        employeeDto.setLunchHours(null);
        employeeDto.setWorkHoursPerDay(null);
        employeeDto.setHourRate(null);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(employeeDto);
    }

    private Employee retrieveEmployeeData(){
        Employee employee = new Employee();
        employee.setId(ID);
        employee.setDoc(EMPLOYEE_DOC);
        employee.setName(NAME);
        employee.setEmail(EMAIL);
        employee.setPassword(PASSWORD);

        employee.setCompany(new Company());
        employee.getCompany().setDoc(COMPANY_DOC);
        return employee;
    }
}
