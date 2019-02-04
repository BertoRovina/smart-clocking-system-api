package com.humbertorovina.clockingsystem.api.dtos;

import org.hibernate.validator.constraints.Length;

// Substitute deprecated values on hibernate
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

public class RegisterCompDto {
	
	private Long id;
	private String name;
	private String email;
	private String password;
	private String doc;
	private String companyName;
	private String ownerDoc;

	public RegisterCompDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Name must not be empty.")
	@Length(min = 3, max = 200, message = "Name should have between 3 and 200 characters.")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotEmpty(message = "Email must not be empty.")
	@Length(min = 5, max = 200, message = "Email should have between  5 and 200 characters.")
	@Email(message="Invalid Email.")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty(message = "Password must not be empty")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotEmpty(message = "Owner DOC must not be empty")
	@Size(min=3,max=20, message = "Doc should have between 3 and 20 characters.")
	public String getOwnerDoc() {
		return doc;
	}

	public void setOwnerDoc(String doc) {
		this.doc = doc;
	}

	@NotEmpty(message = "Company name must not be empty.")
	@Length(min = 5, max = 200, message = "Company name should have between 5 and 200 characters.")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@NotEmpty(message = "DOC must not be empty.")
	@Size(min=3,max=20, message = "Owner Doc should have between 3 and 20 characters.")
	public String getDoc() {
		return ownerDoc;
	}

	public void setDoc(String cnpj) {
		this.ownerDoc = cnpj;
	}

	@Override
	public String toString() {
		return "RegisterCompDto [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", doc="
				+ doc + ", companyName=" + companyName + ", ownerDoc=" + ownerDoc + "]";
	}
}
