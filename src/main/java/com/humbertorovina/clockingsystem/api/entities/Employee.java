package com.humbertorovina.clockingsystem.api.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.humbertorovina.clockingsystem.api.enums.ProfileEnum;

@Entity
@Table(name = "employee")
public class Employee implements Serializable{
	
	private static final long serialVersionUID = -5754246207015712518L;
	
	private Long id;
	private String name;
	private String email;
	private String password;
	private String doc;
	private BigDecimal hourRate;
	private Float workHoursPerDay;
	private Float lunchHours;
	private ProfileEnum profile;
	private Date creationDate;
	private Date updateDate;
	private Company company;
	private List<Clocking> clocking;
	
	public Employee() {
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "doc", nullable = false)
	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	@Column(name = "hour_rate", nullable = true)
	public BigDecimal getHourRate() {
		return hourRate;
	}
	
	@Transient
	public Optional<BigDecimal> getHourRateOpt() {
		return Optional.ofNullable(hourRate);
	}

	public void setHourRate(BigDecimal hourRate) {
		this.hourRate = hourRate;
	}

	@Column(name = "work_hours_per_day", nullable = true)
	public Float getWorkHoursPerDay() {
		return workHoursPerDay;
	}
	
	@Transient
	public Optional<Float> getWorkHoursPerDayOpt() {
		return Optional.ofNullable(workHoursPerDay);
	}

	public void setWorkHoursPerDay(Float workHoursPerDay) {
		this.workHoursPerDay = workHoursPerDay;
	}

	@Column(name = "lunch_hours", nullable = true)
	public Float getLunchHours() {
		return lunchHours;
	}
	
	@Transient
	public Optional<Float> getLunchHoursOpt() {
		return Optional.ofNullable(lunchHours);
	}

	public void setLunchHours(Float lunchHours) {
		this.lunchHours = lunchHours;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "profile", nullable = false)
	public ProfileEnum getProfile() {
		return profile;
	}

	public void setProfile(ProfileEnum profile) {
		this.profile = profile;
	}

	@Column(name = "creation_date", nullable = false)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "update_date", nullable = false)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Clocking> getClocking() {
		return clocking;
	}

	public void setClocking(List<Clocking> clocking) {
		this.clocking = clocking;
	}
	
	@PreUpdate
    public void preUpdate() {
        updateDate = new Date();
    }
     
    @PrePersist
    public void prePersist() {
        creationDate = Calendar.getInstance().getTime();
        updateDate = Calendar.getInstance().getTime();
        
    }

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", doc=" + doc
				+ ", hourRate=" + hourRate + ", workHoursPerDay=" + workHoursPerDay + ", lunchHours="
				+ lunchHours + ", profile=" + profile + ", creationDate="
				+ creationDate + ", updateDate=" + updateDate + ", company=" + company.getId() + "]";
	}

    
}
