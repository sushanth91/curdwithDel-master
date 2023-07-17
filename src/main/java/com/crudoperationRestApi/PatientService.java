package com.crudoperationRestApi;

import java.util.List;

public interface PatientService {
	
	
	//save patient
	Patient savePatient(Patient patient);
	
	//get patient
	List<Patient> getPatient();
	
	
//	delete patient by tenantid and patientid and extenalref
	
	void deleteByTenantidAndPatientidAndexternalref(String tenantid,String patientid,String externalref);
	
	
	Patient getPatientByTenantidAndExternalref(String tenantid,String emrid);

	Object getPatientByTenantidAndExternalrefs(String tenantid, Integer emrid);

}
