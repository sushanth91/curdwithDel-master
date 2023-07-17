package com.crudoperationRestApi;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;




@Service
public class PatientService_Impl implements PatientService{

	@Autowired
	private PatientRepository patientRepo;
	
	@Autowired
	Gson gson;
	
	@Autowired
	EntityManager em;
	@Override
	public Patient savePatient(Patient patient) {
		  // Create a JSON object
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("key1", "value1");
//        jsonObject.put("key2", "value2");
//
//        // Create a new entity and set its JSON column to the JSON object
//        MyEntity entity = new MyEntity();
//        entity.setExternalRef(jsonObject.toString());
		
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("emrid", patient.getExternalref().getEmrid());
////		patient.setExternalref(jsonObject);
		Patient p = new Patient();
		Externalref_Long externalref = new Externalref_Long();
		externalref.setEmrid(patient.getExternalref().getEmrid());
		p.setExternalref(externalref);
		p.setName(patient.getName());
		p.setPatientid(patient.getPatientid());
		p.setTenantid(patient.getTenantid());
		
 		Patient newPatient = patientRepo.save(p);
		
		
		
		
//		Patient newPatient = patientRepo.save(patient);
		return newPatient;
	}

	@Override
	public List<Patient> getPatient() {
		List<Patient> findAll = patientRepo.findAll();
		return findAll;
	}

	@Override
	public Patient getPatientByTenantidAndExternalref(String tenantid, String externalref) {
		Patient findPatientByTenantidAndExternalref = patientRepo.findPatientByTenantidAndExternalref(tenantid, externalref);
		return findPatientByTenantidAndExternalref;
	}

	@Override
	public Object getPatientByTenantidAndExternalrefs(String tenantid, Integer emrid) {
		Object res=null;
		String jsons="[]";
		ObjectModel obj=null;
		System.out.println("from service : "+ emrid);
		try {
			
			StoredProcedureQuery spq = em.createStoredProcedureQuery("get_patient1",ObjectModel.class);
			spq.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
			spq.setParameter(1,tenantid);
			
			spq.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
			spq.setParameter(2,emrid);
			
			
			
			obj = (ObjectModel)spq.getSingleResult();
			System.out.println(obj);
			if(obj!=null) {
				jsons = gson.toJson(obj.getResult());
				res = new ObjectMapper().readValue(jsons, Object.class);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return res;
	}

	@Override
	public void deleteByTenantidAndPatientidAndexternalref(String tenantId, String patientid, String externalref) {
		 Patient patient = patientRepo.findByExternalrefAndTenantIdAndPatientId(externalref, tenantId, patientid);
	        if(patient != null) {
	            patientRepo.delete(patient);
	        } else {
	            throw new EntityNotFoundException("Patient not found with externalref " + externalref + ", tenantId " + tenantId + ", and patientId " + patientid);
	        }
	 }
//	

}
