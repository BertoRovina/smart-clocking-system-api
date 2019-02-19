package com.humbertorovina.clockingsystem.api.controllers;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.swing.text.html.Option;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.humbertorovina.clockingsystem.api.dtos.EmployeeDto;
import com.humbertorovina.clockingsystem.api.entities.Employee;
import com.humbertorovina.clockingsystem.api.response.Response;
import com.humbertorovina.clockingsystem.api.services.EmployeeService;
import com.humbertorovina.clockingsystem.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    public EmployeeController(){ }


    /**
     * Returns a employee based on the ID
     *
     * @param employeeId
     * @return ResponseEntity<Response<EmployeeDto>>
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<EmployeeDto>> searchByDoc(@PathVariable("id") Long employeeId){
        log.info("Searching Employee by id: {}", employeeId);
        Response<EmployeeDto> response = new Response<>();
        Optional<Employee> employee = employeeService.searchById(employeeId);

        if(!employee.isPresent()){
            log.info("Employee not found based on the Employee Id: {}", employeeId);
            response.getErrors().add("Employee not found based on the Employee Id:" + employeeId);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertEmployeeDto(employee.get()));
        return ResponseEntity.ok(response);
    }

    /**
     * Updates an Employee data
     *
     * @param id
     * @param employeeDto
     * @param result
     * @return
     * @throws NoSuchAlgorithmException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<EmployeeDto>> update(@PathVariable("id") Long id,
                                                        @Valid @RequestBody EmployeeDto employeeDto,
                                                        BindingResult result) throws NoSuchAlgorithmException{

        log.info("Updating Employee: {}", employeeDto.toString());

        Response<EmployeeDto> response = new Response<>();

        Optional<Employee> employee = this.employeeService.searchById(id);
        if(!employee.isPresent()){
            result.addError(new ObjectError("Employee", "Employee not found."));
        }

        updateEmployeeData(employee.get(), employeeDto, result);

        if (result.hasErrors()){
            log.error("Error validating Employee: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.employeeService.persist(employee.get());
        response.setData(this.convertEmployeeDto(employee.get()));
        return ResponseEntity.ok(response);
    }

    /**
     * Updates Employee data based on data found in the DTO
     *
     * @param employee
     * @param employeeDto
     * @param result
     * @throws NoSuchAlgorithmException
     */
    private void updateEmployeeData(Employee employee, EmployeeDto employeeDto, BindingResult result)
                throws NoSuchAlgorithmException{
        employee.setName(employeeDto.getName());

        if (!employee.getEmail().equals(employeeDto.getEmail())){

            this.employeeService.searchByEmail(employeeDto.getEmail())
                    .ifPresent(emp -> result.addError(new ObjectError("email", "Email already exists.")));

            employee.setEmail(employeeDto.getEmail());
        }

        employee.setLunchHours(null);
        employeeDto.getLunchHours()
                .ifPresent(lunchHours -> employee.setLunchHours(Float.valueOf(lunchHours)));

        employee.setWorkHoursPerDay(null);
        employeeDto.getWorkHoursPerDay()
                .ifPresent(workingHoursPerDay -> employee.setWorkHoursPerDay(Float.valueOf(workingHoursPerDay)));

        employee.setHourRate(null);
        employeeDto.getHourRate()
                .ifPresent(hourRate -> employee.setHourRate(new BigDecimal(hourRate)));

        if (employeeDto.getPassword().isPresent()){
            employee.setPassword(PasswordUtils.generateBCrypt(employeeDto.getPassword().get()));
        }
    }

    /**
     * Returns a DTO with Employee data
     *
     * @param employee
     * @return
     */
    private EmployeeDto convertEmployeeDto(Employee employee){
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(employee.getId());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setName(employee.getName());
        employee.getLunchHoursOpt().ifPresent(
                lunchHours -> employeeDto.setLunchHours(Optional.of(Float.toString(lunchHours))));
        employee.getWorkHoursPerDayOpt().ifPresent(
                workHoursPerDay -> employeeDto.setWorkHoursPerDay(Optional.of(Float.toString(workHoursPerDay))));
        employee.getHourRateOpt().ifPresent(
                hourRate -> employeeDto.setHourRate(Optional.of(hourRate.toString())));

        return employeeDto;
    }

}
