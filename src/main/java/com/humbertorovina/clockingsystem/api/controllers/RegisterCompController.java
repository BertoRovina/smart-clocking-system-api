package com.humbertorovina.clockingsystem.api.controllers;

import java.security.NoSuchAlgorithmException;

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

import com.humbertorovina.clockingsystem.api.dtos.RegisterCompDto;
import com.humbertorovina.clockingsystem.api.entities.Company;
import com.humbertorovina.clockingsystem.api.entities.Employee;
import com.humbertorovina.clockingsystem.api.enums.ProfileEnum;
import com.humbertorovina.clockingsystem.api.response.Response;
import com.humbertorovina.clockingsystem.api.services.CompanyService;
import com.humbertorovina.clockingsystem.api.services.EmployeeService;
import com.humbertorovina.clockingsystem.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/register-company")
@CrossOrigin(origins = "*") // Allows requisitions from any domain (for demonstration only)
public class RegisterCompController {
	
	private static final Logger log = LoggerFactory.getLogger(RegisterCompController.class);
	
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private CompanyService companyService;
	
	public RegisterCompController() { }
	
	
	/**
	 * Registers a company 
	 * 
	 * @param registerCompDto
	 * @param result
	 * @return ResponseEntity<Response<RegisterCompDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	public ResponseEntity<Response<RegisterCompDto>> register(@Valid @RequestBody RegisterCompDto registerCompDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Registering Company: {}", registerCompDto.toString());
		Response<RegisterCompDto> response = new Response<>();

		validateExistingData(registerCompDto, result);
		Company company = this.convertDtoToCompany(registerCompDto);
		Employee employee = this.convertDtoToEmployee(registerCompDto, result);

		if (result.hasErrors()) {
			log.error("Error while validating the registration data: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.companyService.persist(company);
		employee.setCompany(company);

		response.setData(this.convertCompanyRegistrationToDto(this.employeeService.persist(employee)));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Checks if the company or employee already exists in the Database
	 * 
	 * @param registerCompDto
	 * @param result
	 */
	private void validateExistingData(RegisterCompDto registerCompDto, BindingResult result) {
		this.companyService.searchByDoc(registerCompDto.getDoc())
				.ifPresent(comp -> result.addError(new ObjectError("company", "Company already registered.")));

		this.employeeService.searchByDoc(registerCompDto.getOwnerDoc())
				.ifPresent(emp -> result.addError(new ObjectError("employee", "DOC already registered.")));

		this.employeeService.searchByEmail(registerCompDto.getEmail())
				.ifPresent(emp -> result.addError(new ObjectError("employee", "Email already registered.")));
	}
	
	/**
	 * Convert data from DTO to company.
	 * 
	 * @param registerCompDto
	 * @return Company
	 */
	private Company convertDtoToCompany(RegisterCompDto registerCompDto) {
		Company company = new Company();
		company.setDoc(registerCompDto.getDoc());
		company.setCompanyName(registerCompDto.getCompanyName());

		return company;
	}

	/**
	 * Convert data from DTO to employee.
	 * 
	 * @param registerCompDto
	 * @param result
	 * @return Employee
	 * @throws NoSuchAlgorithmException
	 */
	private Employee convertDtoToEmployee(RegisterCompDto registerCompDto, BindingResult result)
			throws NoSuchAlgorithmException {
		Employee employee = new Employee();
		employee.setName(registerCompDto.getName());
		employee.setEmail(registerCompDto.getEmail());
		employee.setDoc(registerCompDto.getOwnerDoc());
		employee.setProfile(ProfileEnum.ROLE_ADMIN);
		employee.setPassword(PasswordUtils.generateBCrypt(registerCompDto.getPassword()));

		return employee;
	}

	/**
	 * Fills the register DTO with the data from the employee and the company.
	 * 
	 * @param employee
	 * @return RegisterCompDto
	 */
	private RegisterCompDto convertCompanyRegistrationToDto(Employee employee) {
		RegisterCompDto registerCompDto = new RegisterCompDto();
		registerCompDto.setId(employee.getId());
		registerCompDto.setName(employee.getName());
		registerCompDto.setEmail(employee.getEmail());
		registerCompDto.setOwnerDoc(employee.getDoc());
		registerCompDto.setCompanyName(employee.getCompany().getCompanyName());
		registerCompDto.setDoc(employee.getCompany().getDoc());

		return registerCompDto;
	}
	
}
