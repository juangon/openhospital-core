package org.isf.accounting.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.isf.accounting.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountingBillIoOperationRepository
		extends JpaRepository<Bill, Integer>, AccountingBillIoOperationRepositoryCustom {

	List<Bill> findByStatusOrderByDateDesc(String status);

	List<Bill> findByStatusAndPatient_codeOrderByDateDesc(String status, int patientId);

	List<Bill> findAllByOrderByDateDesc();

	@Modifying
	@Query(value = "UPDATE BILLS SET BLL_STATUS = 'D' WHERE BLL_ID = :billId", nativeQuery = true)
	void updateDeleteWhereId(@Param("billId") Integer billId);

	@Query(value = "SELECT * FROM BILLS WHERE DATE(BLL_DATE) BETWEEN :dateFrom AND :dateTo", nativeQuery = true)
	List<Bill> findAllWhereDates(@Param("dateFrom") Timestamp dateFrom, @Param("dateTo") Timestamp dateTo);
	
	@Query(value = "SELECT * FROM BILLS  WHERE  (DATE(BLL_DATE) BETWEEN :dateFrom AND :dateTo ) "
			+" AND ( BLL_ID_PAT= :patientCode)", nativeQuery = true)
	ArrayList<Bill> findByDateAndPatient(@Param("dateFrom")GregorianCalendar dateFrom, @Param("dateTo") GregorianCalendar dateTo, @Param("patientCode")Integer patientCode);
	
	@Query(value = "SELECT * FROM BILLS "
			+"WHERE BLL_STATUS='O' "
			+"  AND BLL_ID_PAT=:patID ", nativeQuery = true)
	ArrayList<Bill> findAllPendindBillsByPatient(@Param("patID")int patID);
}