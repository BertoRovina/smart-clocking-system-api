package com.humbertorovina.clockingsystem.api.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.Length;

//Substitute deprecated values on hibernate
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;


public class RegisterEmployeeDto {
	
	private Long id;
	private String name;
	private String email;
	private String password;
	private String doc;
	private Optional<String> hourRate = Optional.empty();
	private Optional<String> workHoursPerDay = Optional.empty();
	private Optional<String> lunchHours = Optional.empty();
	private String companyDoc;

	public RegisterEmployeeDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Name must not be empty.")
	@Length(min = 3, max = 200, message = "Name should have between 3 and 20 characters.")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotEmpty(message = "Email must not be empty.")
	@Length(min = 5, max = 200, message = "Email should have between 5 and 200 characters.")
	@Email(message="Email inválido.")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty(message = "Password must not be empty.")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotEmpty(message = "CPF must not be empty.")
	@Size(min=3,max=20, message = "Doc should have between 3 and 20 characters.")
	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public Optional<String> getHourRate() {
		return hourRate;
	}

	public void setHourRate(Optional<String> hourRate) {
		this.hourRate = hourRate;
	}

	public Optional<String> getWorkHoursPerDay() {
		return workHoursPerDay;
	}

	public void setWorkHoursPerDay(Optional<String> workHoursPerDay) {
		this.workHoursPerDay = workHoursPerDay;
	}

	public Optional<String> getLunchHours() {
		return lunchHours;
	}

	public void setLunchHours(Optional<String> lunchHours) {
		this.lunchHours = lunchHours;
	}

	@NotEmpty(message = "CNPJ must not be empty.")
	@Size(min=3,max=20, message = "Company Doc should have between 3 and 20 characters.")
	public String getCompanyDoc() {
		return companyDoc;
	}

	public void setCompanyDoc(String companyDoc) {
		this.companyDoc = companyDoc;
	}

	@Override
	public String toString() {
		return "RegisterEmployeeDto [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", doc=" + doc + ", hourRate=" + hourRate + ", workHoursPerDay=" + workHoursPerDay + ", lunchHours="
				+ lunchHours + ", companyDoc=" + companyDoc + "]";
	}
}
