package com.humbertorovina.clockingsystem.api.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.humbertorovina.clockingsystem.api.dtos.ClockingDto;
import com.humbertorovina.clockingsystem.api.entities.Employee;
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

import com.humbertorovina.clockingsystem.api.entities.Clocking;
import com.humbertorovina.clockingsystem.api.services.EmployeeService;
import com.humbertorovina.clockingsystem.api.services.ClockingService;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClockingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClockingService clockingService;

    @MockBean
    private EmployeeService employeeService;

    private static final String URL_BASE = "/api/clocking/";
    private static final Long EMPLOYEE_ID = 1L;
    private static final Long CLOCKING_ID = 1L;
    private static final String TYPE = TypeEnum.START_WORK.name();
    private static final Date DATE = new Date();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    @WithMockUser
    public void registerClockingTest() throws Exception{
        Clocking clocking = retrieveClockingData();
        BDDMockito.given(this.employeeService.searchById(Mockito.anyLong())).willReturn(Optional.of(new Employee()));
        BDDMockito.given(this.clockingService.persist(Mockito.any(Clocking.class))).willReturn(clocking);

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .with(csrf())
                .content(this.retrievePostRequestJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(CLOCKING_ID))
                .andExpect(jsonPath("$.data.type").value(TYPE))
                .andExpect(jsonPath("$.data.date").value(this.dateFormat.format(DATE)))
                .andExpect(jsonPath("$.data.employeeId").value(EMPLOYEE_ID))
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    @WithMockUser
    public void invalidEmployeeIdClockingRegistrationTest() throws Exception{
        BDDMockito.given(this.employeeService.searchById(Mockito.anyLong())).willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .with(csrf())
                .content(this.retrievePostRequestJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("Employee not found. Nonexistent ID."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
    public void removeClockingTest() throws Exception{
        BDDMockito.given(this.clockingService.searchById(Mockito.anyLong())).willReturn(Optional.of(new Clocking()));

        mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + CLOCKING_ID)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void removeClockingDeniedAccessTest() throws Exception{
        BDDMockito.given(this.clockingService.searchById(Mockito.anyLong())).willReturn(Optional.of(new Clocking()));

        mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + CLOCKING_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    private String retrievePostRequestJson() throws JsonProcessingException{
        ClockingDto clockingDto = new ClockingDto();
        clockingDto.setId(null);
        clockingDto.setType(TYPE);
        clockingDto.setDate(this.dateFormat.format(DATE));
        clockingDto.setEmployeeId(EMPLOYEE_ID);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(clockingDto);
    }

    private Clocking retrieveClockingData(){
        Clocking clocking = new Clocking();
        clocking.setId(CLOCKING_ID);
        clocking.setDate(DATE);
        clocking.setType(TypeEnum.valueOf(TYPE));
        clocking.setEmployee(new Employee());
        clocking.getEmployee().setId(EMPLOYEE_ID);
        return clocking;
    }

}