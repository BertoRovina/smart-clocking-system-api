package com.humbertorovina.clockingsystem.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.humbertorovina.clockingsystem.api.enums.TypeEnum;


@Entity
@Table(name = "clockling")
public class Clocking implements Serializable {
	
	private static final long serialVersionUID = 6524560251526772839L;

	private Long id;
	private Date date;
	private String description;
	private String localization;
	private Date creationDate;
	private Date updateDate;
	private TypeEnum type;
	private Employee employee;

	public Clocking() {
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", nullable = false)
	public Date getData() {
		return date;
	}

	public void setData(Date date) {
		this.date = date;
	}

	@Column(name = "description", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "localization", nullable = true)
	public String getLocalization() {
		return localization;
	}

	public void setLocalization(String localization) {
		this.localization = localization;
	}

	@Column(name = "creation_date", nullable = false)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date dataCriacao) {
		this.creationDate = dataCriacao;
	}

	@Column(name = "updateDate", nullable = false)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	public TypeEnum getType() {
		return type;
	}

	public void setType(TypeEnum type) {
		this.type = type;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee Employee) {
		this.employee = Employee;
	}
	
	@PreUpdate
    public void preUpdate() {
        updateDate = new Date();
    }
     
    @PrePersist
    public void prePersist() {
        final Date current = new Date();
        creationDate = current;
        updateDate = current;
    }

	@Override
	public String toString() {
		return "Clocking [id=" + id + ", date=" + date + ", description=" + description + ", localization="
				+ localization + ", creationDate=" + creationDate + ", updateDate=" + updateDate + ", type=" + type
				+ ", employee=" + employee + "]";
	}

    

}