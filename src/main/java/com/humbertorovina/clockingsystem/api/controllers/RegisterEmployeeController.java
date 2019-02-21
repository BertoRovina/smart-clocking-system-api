package com.humbertorovina.clockingsystem.api.controllers;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.humbertorovina.clockingsystem.api.dtos.RegisterEmployeeDto;
import com.humbertorovina.clockingsystem.api.entities.Company;
import com.humbertorovina.clockingsystem.api.entities.Employee;
import com.humbertorovina.clockingsystem.api.enums.ProfileEnum;
import com.humbertorovina.clockingsystem.api.response.Response;
import com.humbertorovina.clockingsystem.api.services.CompanyService;
import com.humbertorovina.clockingsystem.api.services.EmployeeService;
import com.humbertorovina.clockingsystem.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/register-employee")
@CrossOrigin(origins = "*")
public class RegisterEmployeeController {
	
	private static final Logger log = LoggerFactory.getLogger(RegisterEmployeeController.class);
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private EmployeeService employeeService;

	public RegisterEmployeeController() {
	}

	/**
	 * Registers an employee.
	 * 
	 * @param registerEmployeeDto
	 * @param result
	 * @return ResponseEntity<Response<RegisterEmployeeDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	public ResponseEntity<Response<RegisterEmployeeDto>> register(@Valid @RequestBody RegisterEmployeeDto registerEmployeeDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Registering Employee: {}", registerEmployeeDto.toString());

		Response<RegisterEmployeeDto> response = new Response<>();

		validateExistingData(registerEmployeeDto, result);
		Employee employee = this.convertDtotoEmployee(registerEmployeeDto, result);

		if (result.hasErrors()) {
			log.error("Error during employee validation: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Company> company = this.companyService.searchByDoc(registerEmployeeDto.getCompanyDoc());
		company.ifPresent(emp -> employee.setCompany(emp));

		response.setData(this.converterRegisterEmployeeDto(this.employeeService.persist(employee)));
		return ResponseEntity.ok(response);
	}

	/**
	 * Checks if company is already registered and if employee doesn't exist in Database.
	 * 
	 * @param registerEmployeeDto
	 * @param result
	 */
	private void validateExistingData(RegisterEmployeeDto registerEmployeeDto, BindingResult result) {
		Optional<Company> company = this.companyService.searchByDoc(registerEmployeeDto.getCompanyDoc());
		if (!company.isPresent()) {
			result.addError(new ObjectError("company", "Company not registered."));
		}
		
		this.employeeService.searchByDoc(registerEmployeeDto.getDoc())
			.ifPresent(func -> result.addError(new ObjectError("employee", "Doc already registered.")));

		this.employeeService.searchByEmail(registerEmployeeDto.getEmail())
			.ifPresent(func -> result.addError(new ObjectError("employee", "Email already registered.")));
	}

	/**
	 * Converts DTO data into Employee.
	 * 
	 * @param registerEmployeeDto
	 * @param result
	 * @return Employee
	 * @throws NoSuchAlgorithmException
	 */
	private Employee convertDtotoEmployee(RegisterEmployeeDto registerEmployeeDto, BindingResult result)
			throws NoSuchAlgorithmException {
		Employee employee = new Employee();
		employee.setName(registerEmployeeDto.getName());
		employee.setEmail(registerEmployeeDto.getEmail());
		employee.setDoc(registerEmployeeDto.getDoc());
		employee.setProfile(ProfileEnum.ROLE_USER);
		employee.setPassword(PasswordUtils.generateBCrypt(registerEmployeeDto.getPassword()));
		registerEmployeeDto.getLunchHours()
				.ifPresent(lunchHours -> employee.setLunchHours(Float.valueOf(lunchHours)));
		registerEmployeeDto.getWorkHoursPerDay()
				.ifPresent(workHoursPerDay -> employee.setWorkHoursPerDay(Float.valueOf(workHoursPerDay)));
		registerEmployeeDto.getHourRate().ifPresent(hourRate -> employee.setHourRate(new BigDecimal(hourRate)));

		return employee;
	}

	/**
	 * Fills registration DTO with the Employee and Company's data.
	 * 
	 * @param employee
	 * @return RegisterEmployeeDto
	 */
	private RegisterEmployeeDto converterRegisterEmployeeDto(Employee employee) {
		RegisterEmployeeDto registerEmployeeDto = new RegisterEmployeeDto();
		registerEmployeeDto.setId(employee.getId());

		registerEmployeeDto.setName(employee.getName());
		registerEmployeeDto.setEmail(employee.getEmail());
		registerEmployeeDto.setDoc(employee.getDoc());
		registerEmployeeDto.setCompanyDoc(employee.getCompany().getDoc());
		employee.getLunchHoursOpt().ifPresent(lunchHours -> registerEmployeeDto
				.setLunchHours(Optional.of(Float.toString(lunchHours))));
		employee.getWorkHoursPerDayOpt().ifPresent(
				workHoursPerDay -> registerEmployeeDto.setWorkHoursPerDay(Optional.of(Float.toString(workHoursPerDay))));
		employee.getHourRateOpt()
				.ifPresent(hourRate -> registerEmployeeDto.setHourRate(Optional.of(hourRate.toString())));

		return registerEmployeeDto;
	}
}
