package com.humbertorovina.clockingsystem.api.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;


@Entity
@Table(name = "empresa")
public class Company implements Serializable{
	
	private static final long serialVersionUID = 395453462;
	
	private Long id;
	private String socialReason;
	private String doc;
	private Date creationDate;
	private Date updateDate;
	private List<Employee> employees;
	
	public Company() {
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "razao_social", nullable = false)
	public String getSocialReason() {
		return socialReason;
	}

	public void setSocialReason(String socialReason) {
		this.socialReason = socialReason;
	}

	@Column(name = "doc", nullable = false)
	public String getDoc() {
		return doc;
	}

	public void setDoc(String cnpj) {
		this.doc = cnpj;
	}

	@Column(name = "creation_date", nullable = false)
	public Date getcreationDate() {
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

	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Employee> getEmployees() {
		return employees;
	}

	public void setFuncionarios(List<Employee> employees) {
		this.employees = employees;
	}

	@PreUpdate
    public void preUpdate() {
        updateDate = new Date();
    }
     
    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        creationDate = atual;
        updateDate = atual;
    }

	@Override
	public String toString() {
		return "Company [id=" + id + ", socialReason=" + socialReason + ", doc=" + doc + ", creationDate="
				+ creationDate + ", updateDate=" + updateDate + ", employees=" + employees + "]";
	}
}
