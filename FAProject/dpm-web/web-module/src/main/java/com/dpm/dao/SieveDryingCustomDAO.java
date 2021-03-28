package com.dpm.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.dpm.dto.SieveDryingReportDTO;
import com.dpm.entity.SieveDrying;

@Repository
public class SieveDryingCustomDAO {
	
	@Autowired
	private EntityManager em;
	
	public List<SieveDrying> getReport(SieveDryingReportDTO rdto){
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		
		List<Predicate> listCriteria = new ArrayList<Predicate>();
		
		CriteriaQuery<SieveDrying> criteriaQuery = builder.createQuery(SieveDrying.class);
		Root<SieveDrying> itemRoot = criteriaQuery.from(SieveDrying.class);
		
		listCriteria.add(builder.notEqual(itemRoot.get("status"), "Default"));
		
		if(rdto.getMachineId() != 0) {
			listCriteria.add(builder.equal(itemRoot.get("machine"), rdto.getMachineId()));
		}
		
		if(rdto.getLotCode() != 0) {
			listCriteria.add(builder.equal(itemRoot.get("lotCode"), rdto.getLotCode()));
		}
		
		if(rdto.getTypeProductId() != 0) {
			listCriteria.add(builder.equal(itemRoot.get("typeProduct"), rdto.getTypeProductId()));
		}
		
		if(rdto.getIngredientBatchCode() != 0) {
			listCriteria.add(builder.equal(itemRoot.get("ingredientBatch"), rdto.getIngredientBatchCode()));
		}
		
		switch (rdto.getStatus()) {
		case 1:
			listCriteria.add(builder.equal(itemRoot.get("status"), "Pending"));
			break;
		case 2:
			listCriteria.add(builder.equal(itemRoot.get("status"), "Approved"));
			break;
		case 3:
			listCriteria.add(builder.equal(itemRoot.get("status"), "Rejected"));
			break;
		default:
			break;
		}
		
		listCriteria.add(builder.between(itemRoot.get("inputDate"), rdto.getFromDate(), rdto.getToDate()));
		
		Predicate finalPredicate = builder.and(listCriteria.toArray(new Predicate[0]));
		criteriaQuery.where(finalPredicate);
		
		TypedQuery<SieveDrying> query = em.createQuery(criteriaQuery);
		
		List<SieveDrying> result = query.getResultList();
		
		return result;
	}
	
	public Page<SieveDrying> getReport(SieveDryingReportDTO rdto, Pageable pageable){
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		
		List<Predicate> listCriteria = new ArrayList<Predicate>();
		
		CriteriaQuery<SieveDrying> criteriaQuery = builder.createQuery(SieveDrying.class);
		Root<SieveDrying> itemRoot = criteriaQuery.from(SieveDrying.class);
		
		listCriteria.add(builder.notEqual(itemRoot.get("status"), "Default"));
		
		if(rdto.getMachineId() != 0) {
			listCriteria.add(builder.equal(itemRoot.get("machine"), rdto.getMachineId()));
		}
		
		if(rdto.getLotCode() != 0) {
			listCriteria.add(builder.equal(itemRoot.get("lotCode"), rdto.getLotCode()));
		}
		
		if(rdto.getTypeProductId() != 0) {
			listCriteria.add(builder.equal(itemRoot.get("typeProduct"), rdto.getTypeProductId()));
		}
		
		if(rdto.getIngredientBatchCode() != 0) {
			listCriteria.add(builder.equal(itemRoot.get("ingredientBatch"), rdto.getIngredientBatchCode()));
		}
		
		switch (rdto.getStatus()) {
		case 1:
			listCriteria.add(builder.equal(itemRoot.get("status"), "Pending"));
			break;
		case 2:
			listCriteria.add(builder.equal(itemRoot.get("status"), "Approved"));
			break;
		case 3:
			listCriteria.add(builder.equal(itemRoot.get("status"), "Rejected"));
			break;
		default:
			break;
		}
		
		listCriteria.add(builder.between(itemRoot.get("inputDate"), rdto.getFromDate(), rdto.getToDate()));
		
		Predicate finalPredicate = builder.and(listCriteria.toArray(new Predicate[0]));
		criteriaQuery.where(finalPredicate);
		int totalRows = em.createQuery(criteriaQuery).getResultList().size();
//		Page<SieveDrying> result = new PageImpl<SieveDrying>(em.createQuery(criteriaQuery).getResultList(), pageable,
//				totalRows);
		
		TypedQuery<SieveDrying> query = em.createQuery(criteriaQuery);
		
		query.setFirstResult( (int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		
	     Page<SieveDrying> result = new PageImpl<SieveDrying>(query.getResultList(), pageable, totalRows);
		
		return result;
	}

}
