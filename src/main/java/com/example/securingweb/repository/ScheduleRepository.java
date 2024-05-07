
package com.example.securingweb.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.securingweb.entities.Schedule;



/**
 *
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Integer>, JpaSpecificationExecutor <Schedule> , 
	CrudRepository <Schedule, Integer>
{

/*	@Query("SELECT s FROM EmployeeSchedule s WHERE SUBSTRING(s.fromDate, 1, 4) = ?1 and s.approval='Approved'")
	public List<EmployeeSchedule> findByYear(String year);
	
	
	@Query("SELECT s FROM EmployeeSchedule s WHERE s.user.id=?1 and s.fromDate>= ?2 and s.toDate <=?3")
	public List<EmployeeSchedule> findByEmpStatusByDateRange(Integer empId, Date start, Date end);
	
	@Query("SELECT s FROM EmployeeSchedule s WHERE s.user=?1")
	public List<EmployeeSchedule> findbyempSchlByempId(User empId);

	@Query("SELECT s FROM EmployeeSchedule s WHERE s.approval='Pending Approval'")
	public List<EmployeeSchedule> findbyApproval();*/
	Schedule findByTitle(String title);
	
	
	@Query("SELECT s FROM Schedule s WHERE s.startDate = current_date()")
	public List<Schedule> findTdyEvents();
	
	@Query("SELECT s FROM Schedule s WHERE s.startDate =?1")
	public List<Schedule> findTomEvents(Date tomdate);
	
	@Transactional
	@Modifying
    @Query("UPDATE Schedule s SET s.status = :status WHERE s.id = :id")
    public int updateEventStatus(@Param("id") int id, @Param("status") int status);

	@Transactional
	@Modifying
    @Query("UPDATE Schedule s SET s.isDeleted = :status , s.isManualEntry = 1 WHERE s.id = :id")
    public int manualDelete(@Param("id") int id, @Param("status") int status);
	
	@Query("SELECT s FROM Schedule s WHERE s.transmission_type_id =?1")
	public List<Schedule> findEventsByTransId(String transmission_id);
	
	@Query("SELECT s FROM Schedule s WHERE s.region_id =?1")
	public List<Schedule> findEventsByRegionId(String region_id);
	
	@Query("SELECT s FROM Schedule s WHERE s.pms_id =?1")
	public List<Schedule> findEventsByPmsId(String pms_id);

	@Query("SELECT s FROM Schedule s WHERE s.ship_id =?1")
	public List<Schedule> findEventsByShipId(String ship_id);
	
	/*@Query("SELECT s.id,s.startDate,s.startTime,s.description,s.title,s.status FROM Schedule s"
			+ " LEFT JOIN  AS400BusinessRuleMap a on s.businessRule = a.businessRule"
			+ " LEFT JOIN  Ship b on a.ship = b.code"
			+ " LEFT JOIN  Brand c on b.brand = c.id"
			+ " LEFT JOIN  BusinessRule d on s.businessRule = d.id"
			+ " LEFT JOIN  TransmissionType e on d.transmissionTypeBR = e.id")
	public List<Schedule> findSearchedEvents();*/

	/*@Query("SELECT s.id,s.startDate,s.startTime,s.description,s.title,s.status FROM Schedule s"
			+ " LEFT JOIN  AS400BusinessRuleMap a on s.businessRule = a.businessRule"
			+ " LEFT JOIN  Ship b on a.ship = b.code"
			+ " LEFT JOIN  Brand c on b.brand = c.id"
			+ " LEFT JOIN  BusinessRule d on s.businessRule = d.id"
			+ " LEFT JOIN  TransmissionType e on d.transmissionTypeBR = e.id")
	List<Schedule> findSearchedEvents(Specification<Schedule> specification);*/
	
//	@Procedure(name = "test_proc")
//	public List<Schedule> findSearchedEvents(@Param("inParam1") String inParam1);
	
	@Procedure
	void insertScheduleData(Integer paramID, String paramEvent);

	@Procedure
	void updateScheduleData(Integer paramID, String paramEvent);
}
