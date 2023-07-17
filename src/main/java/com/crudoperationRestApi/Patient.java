package com.crudoperationRestApi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonStringType;


@Entity
@Table(name="patient1")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String patientid;
	
	private String tenantid;
	
	@Type(type="json")
	@Column(name="externalref")
	Externalref_Long externalref;

	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Patient(Long id, String name, String patientid, String tenantid, Externalref_Long externalref) {
		super();
		this.id = id;
		this.name = name;
		this.patientid = patientid;
		this.tenantid = tenantid;
		this.externalref = externalref;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatientid() {
		return patientid;
	}

	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	public Externalref_Long getExternalref() {
		return externalref;
	}

	public void setExternalref(Externalref_Long externalref) {
		this.externalref = externalref;
	}
}
