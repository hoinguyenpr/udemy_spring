package com.dpm.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dpm.entity.PressingMornitor;

@Repository
public interface PressingMornitorDAO extends JpaRepository<PressingMornitor, Integer>{

	Page<PressingMornitor> findAllByStatusNotAndInputTimeBetweenOrderByInputTimeDesc(Integer statusNot, LocalDateTime inputTimeStart, LocalDateTime imputTimeEnd, Pageable pageable);
	
	Page<PressingMornitor> findAllByStatusNotAndStatusAndInputTimeBetweenOrderByInputTimeDesc(Integer statusNot, Integer status,LocalDateTime inputTimeStart, LocalDateTime imputTimeEnd, Pageable pageable);
	
	List<PressingMornitor> findAllByInputTimeBetweenOrderByInputTimeDesc(LocalDateTime inputTimeStart, LocalDateTime imputTimeEnd);
	
	List<PressingMornitor> findAllByInputTimeGreaterThanEqualOrderByInputTimeDesc(LocalDateTime inputTime);
	
	Page<PressingMornitor> findAllByStatus(Integer status, Pageable pageable);
	
	/**
	 * @param status (0: PENDING; 1: ACCEPTED; 2: REJECT, 3: DELETE)
	 * @param id primary key in record
	 * @return number row effected
	 * @author LongVT7
	 */
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE PressingMornitor e SET e.status = :status WHERE e.id = :id")
	Integer updateStatusById(@Param(value = "status") Integer status, @Param(value = "id") Integer id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE PressingMornitor e SET e.status = :status WHERE e.id IN :id")
	Integer updateStatusByIds(@Param(value = "status") Integer status, @Param(value = "id") Integer[] id);



}
