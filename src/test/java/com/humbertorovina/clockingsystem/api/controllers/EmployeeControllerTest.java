package com.humbertorovina.clockingsystem.api.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;

import com.humbertorovina.clockingsystem.api.enums.ProfileEnum;
import com.humbertorovina.clockingsystem.api.enums.TypeEnum;
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

import com.humbertorovina.clockingsystem.api.entities.Employee;
import com.humbertorovina.clockingsystem.api.services.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    private static final String SEARCH_EMPLOYEE_DOC_URL = "/api/employees/";
    private static final Long ID = Long.valueOf(1);
    private static final String DOC = "3452645742";
    private static final String NAME = "Jack The First";
    private static final String EMAIL = "jackthefirst@hr.com";
    private static final BigDecimal HOUR_RATE = BigDecimal.valueOf(29.5);
    private static final ProfileEnum PROFILE = ProfileEnum.ROLE_USER;

    @Test
    @WithMockUser
    public void testSearchEmployeeId() throws Exception{
        BDDMockito.given(this.employeeService.searchById(Mockito.any()))
                .willReturn(Optional.of(this.retrieveEmployeeData()));

        mvc.perform(MockMvcRequestBuilders.get(SEARCH_EMPLOYEE_DOC_URL + ID)
            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.id").value(ID))
                    .andExpect(jsonPath("$.data.name", equalTo(NAME)))
                    .andExpect(jsonPath("$.data.email", equalTo(EMAIL)))
                    .andExpect(jsonPath("$.errors").isEmpty());
    }

    private Employee retrieveEmployeeData(){
        Employee employee = new Employee();
        employee.setId(ID);
        employee.setDoc(DOC);
        employee.setName(NAME);
        employee.setEmail(EMAIL);
        employee.setHourRate(HOUR_RATE);
        employee.setProfile(PROFILE);
        return employee;
    }
}
