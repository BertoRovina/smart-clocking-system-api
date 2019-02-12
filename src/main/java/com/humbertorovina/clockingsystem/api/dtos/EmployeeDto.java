package com.humbertorovina.clockingsystem.api.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmployeeDto {

    private Long id;
    private String name;
    private String email;
    private Optional<String> password = Optional.empty();
    private Optional<String> hourRate = Optional.empty();
    private Optional<String> workHoursPerDay = Optional.empty();
    private Optional<String> lunchHours = Optional.empty();

    public EmployeeDto(){ }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    @NotEmpty(message = "Name must no be empty.")
    @Length(min = 5, max = 200, message = "Name should have between 5 and 200 characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = "Email must not be empty.")
    @Length(min = 5, max = 200, message = "Email should have between 5 and 200 characters")
    @Email(message = "Invalid Email")
    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public Optional<String> getPassword(){
        return password;
    }

    public void setPassword(Optional<String> password){
        this.password = password;
    }

    public Optional<String> getHourRate(){
        return hourRate;
    }

    public void setHourRate(Optional<String> hourRate){
        this.hourRate = hourRate;
    }

    public Optional<String> getWorkHoursPerDay(){
        return this.workHoursPerDay = workHoursPerDay;
    }

    public void setWorkHoursPerDay(Optional<String> workHoursPerDay){
        this.workHoursPerDay = workHoursPerDay;
    }

    public Optional<String> getLunchHours() {
        return lunchHours;
    }

    public void setLunchHours(Optional<String> lunchHours) {
        this.lunchHours = lunchHours;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", hourRate=" + hourRate +
                ", workHoursPerDay=" + workHoursPerDay +
                ", lunchHours=" + lunchHours +
                '}';
    }
}
