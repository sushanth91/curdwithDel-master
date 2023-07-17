package com.crudoperationRestApi;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query(value = "SELECT * FROM patient WHERE externalref = ?1 AND tenantid = ?2 AND patientid = ?3", nativeQuery = true)
    Patient findByExternalrefAndTenantIdAndPatientId(String externalref, String tenantId, String patientId);
//    
    
    
//	@Transactional
	//void deleteByPatientIdAndTenantidAndExternalref(String patientid,String tenantid,String externalref);
	
	@Query(value="SELECT * FROM patients WHERE externalref ->> 'emrid' = :emrid AND tenantid = :tenantid", nativeQuery=true)
	Patient findPatientByTenantidAndExternalref(@Param("tenantid") String tenantid, @Param("emrid") String emrid);
	
	
	
//	@Query(value="SELECT * FROM patients WHERE externalref = :externalref AND tenantid = :tenantid", nativeQuery=true)
//	Patient findPatientByTenantidAndExternalref1(@Param("tenantid") String tenantid, @Param("externalref") String externalref);
}




