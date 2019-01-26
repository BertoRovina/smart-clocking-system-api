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

import com.humbertorovina.clocklingsystem.api.enums.TypeEnum;


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
	@Column(name = "data", nullable = false)
	public Date getData() {
		return date;
	}

	public void setData(Date data) {
		this.date = data;
	}

	@Column(name = "descricao", nullable = true)
	public String getDescricao() {
		return description;
	}

	public void setDescricao(String descricao) {
		this.description = descricao;
	}
	
	@Column(name = "localizacao", nullable = true)
	public String getLocalizacao() {
		return localization;
	}

	public void setLocalizacao(String localizacao) {
		this.localization = localizacao;
	}

	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriacao() {
		return creationDate;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.creationDate = dataCriacao;
	}

	@Column(name = "data_atualizacao", nullable = false)
	public Date getDataAtualizacao() {
		return updateDate;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.updateDate = dataAtualizacao;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false)
	public TypeEnum getTipo() {
		return type;
	}

	public void setTipo(TypeEnum tipo) {
		this.type = tipo;
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
        final Date atual = new Date();
        creationDate = atual;
        updateDate = atual;
    }

	@Override
	public String toString() {
		return "Clocking [id=" + id + ", date=" + date + ", description=" + description + ", localization="
				+ localization + ", creationDate=" + creationDate + ", updateDate=" + updateDate + ", type=" + type
				+ ", employee=" + employee + "]";
	}

    

}