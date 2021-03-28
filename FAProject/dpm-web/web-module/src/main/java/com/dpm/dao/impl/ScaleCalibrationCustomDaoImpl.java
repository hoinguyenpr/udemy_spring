package com.dpm.dao.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.dpm.dao.ScaleCalibrationCustomDao;
import com.dpm.dto.ScaleCalibrationFilterDto;
import com.dpm.entity.Employee;
import com.dpm.entity.ScaleCalibration;
import com.dpm.service.EmployeeService;
/**
 * 
 * @author ThuanLV6- 2/2/2021
 *
 */
@Repository
public class ScaleCalibrationCustomDaoImpl implements ScaleCalibrationCustomDao {

	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private EmployeeService employeeService;
	
	
	
	@Override
	public Page<ScaleCalibration> filter(ScaleCalibrationFilterDto filter, Pageable pageable) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		List<Predicate> predicates = new ArrayList<Predicate>();

		CriteriaQuery<ScaleCalibration> criteriaQuery = cb.createQuery(ScaleCalibration.class);
		Root<ScaleCalibration> root = criteriaQuery.from(ScaleCalibration.class);
		
		// filter delete status
		predicates.add(cb.equal(root.get("isDeleted"), false));

		// filter between startDate and endDate
		if(filter.getStartDate()!=null && filter.getEndDate()!=null) {
			predicates.add(cb.between(root.get("createdDate"), filter.getStartDate(), filter.getEndDate()));
		} else if(filter.getStartDate()==null && filter.getEndDate()!=null) {
			predicates.add(cb.lessThanOrEqualTo(root.<LocalDateTime>get("createdDate"), filter.getEndDate()));
		} else if(filter.getStartDate()!=null && filter.getEndDate()==null) {
			predicates.add(cb.greaterThanOrEqualTo(root.<LocalDateTime>get("createdDate"),filter.getStartDate()));
		} 
		
	
		// filter by calibrator
		if(filter.getCalibratorId()!=null 
				&& filter.getCalibratorId()!=0) {
			if(employeeService.getEmployeeById(filter.getCalibratorId())!=null) {
				Join<ScaleCalibration, Employee> calibratorJoin = root.join("calibrator");
				predicates.add(cb.equal(calibratorJoin.get("id"), filter.getCalibratorId()));
			}
		}
		
		// filter by inspector
		if(filter.getInspectorId()!=null
				&& filter.getInspectorId()!=0) {
			if(employeeService.getEmployeeById(filter.getInspectorId())!=null) {
				Join<ScaleCalibration, Employee> inspectorJoin = root.join("inspector");
				predicates.add(cb.equal(inspectorJoin.get("id"), filter.getInspectorId()));
			}
		}
	
//		predicates.add();
		
		// filter status
//		if(filter.isPending())
		
		Predicate finalPredicate = cb.and(predicates.toArray(new Predicate[] {}));
		
		criteriaQuery.where(finalPredicate);
		criteriaQuery.orderBy(cb.desc(root.get("modifiedDate")));
		
//		System.out.println(finalPredicate);
		
		int totalRows = em.createQuery(criteriaQuery).getResultList().size();
		TypedQuery<ScaleCalibration> query = em.createQuery(criteriaQuery);
		query.setFirstResult( (int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
	
		
	    Page<ScaleCalibration> result = new PageImpl<ScaleCalibration>(query.getResultList(), pageable, totalRows);

		
		return result;
		
		
	}



	@Override
	public List<ScaleCalibration> export(ScaleCalibrationFilterDto filter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		List<Predicate> predicates = new ArrayList<Predicate>();

		CriteriaQuery<ScaleCalibration> criteriaQuery = cb.createQuery(ScaleCalibration.class);
		Root<ScaleCalibration> root = criteriaQuery.from(ScaleCalibration.class);
		
		// filter delete status
		predicates.add(cb.equal(root.get("isDeleted"), false));

		// filter between startDate and endDate
		if(filter.getStartDate()!=null && filter.getEndDate()!=null) {
			predicates.add(cb.between(root.get("createdDate"), filter.getStartDate(), filter.getEndDate()));
		} else if(filter.getStartDate()==null && filter.getEndDate()!=null) {
			predicates.add(cb.lessThanOrEqualTo(root.<LocalDateTime>get("createdDate"), filter.getEndDate()));
		} else if(filter.getStartDate()!=null && filter.getEndDate()==null) {
			predicates.add(cb.greaterThanOrEqualTo(root.<LocalDateTime>get("createdDate"),filter.getStartDate()));
		} 
		
	
		// filter by calibrator
		if(filter.getCalibratorId()!=null 
				&& filter.getCalibratorId()!=0) {
			if(employeeService.getEmployeeById(filter.getCalibratorId())!=null) {
				Join<ScaleCalibration, Employee> calibratorJoin = root.join("calibrator");
				predicates.add(cb.equal(calibratorJoin.get("id"), filter.getCalibratorId()));
			}
		}
		
		// filter by inspector
		if(filter.getInspectorId()!=null
				&& filter.getInspectorId()!=0) {
			if(employeeService.getEmployeeById(filter.getInspectorId())!=null) {
				Join<ScaleCalibration, Employee> inspectorJoin = root.join("inspector");
				predicates.add(cb.equal(inspectorJoin.get("id"), filter.getInspectorId()));
			}
		}
	
//		predicates.add();
		
		// filter status
//		if(filter.isPending())
		
		Predicate finalPredicate = cb.and(predicates.toArray(new Predicate[] {}));
		
		criteriaQuery.where(finalPredicate);
		criteriaQuery.orderBy(cb.desc(root.get("modifiedDate")));
		
//		System.out.println(finalPredicate);
		
	
		TypedQuery<ScaleCalibration> query = em.createQuery(criteriaQuery);
		
	
		
	    List<ScaleCalibration> result = query.getResultList();
		return result;
	}

}
