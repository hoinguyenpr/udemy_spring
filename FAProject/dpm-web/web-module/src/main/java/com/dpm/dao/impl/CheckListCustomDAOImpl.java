package com.dpm.dao.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.dpm.dao.CheckListCustomDAO;
import com.dpm.dto.CheckListFilterDTO;
import com.dpm.entity.CheckList;
import com.dpm.entity.ProductLot;
import com.dpm.entity.TypeProduct;

@Repository
public class CheckListCustomDAOImpl implements CheckListCustomDAO {

	@Autowired
	private EntityManager em;

	@Override
	public List<CheckList> report(CheckListFilterDTO filter) {
		
		System.out.println("-----------------------------------------------------------------------");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		String[] devive = filter.getShift().split("-");
		LocalTime fromTime = LocalTime.parse(devive[0],DateTimeFormatter.ofPattern("HH:mm:ss"));
		LocalTime toTime = LocalTime.parse(devive[1],DateTimeFormatter.ofPattern("HH:mm:ss"));
		System.out.println(fromTime.toString());
		System.out.println(toTime.toString());
		List<Predicate> predicates = new ArrayList<Predicate>();

		CriteriaQuery<CheckList> criteriaQuery = builder.createQuery(CheckList.class);
		Root<CheckList> checklist = criteriaQuery.from(CheckList.class);
//		criteriaQuery.select(checklist);
		
		predicates.add(builder.between(checklist.get("time"), fromTime, toTime));
		predicates.add(builder.equal(checklist.get("status"), filter.getStatus()));

		
		predicates.add(builder.equal(checklist.get("date"), LocalDate.parse(filter.getDate())));
		if (filter.getProductType() != 0) {
			if (filter.getLot()!=0) {
				Join<CheckList, ProductLot> lotJoin = checklist.join("lot");
				predicates.add(builder.equal(lotJoin.get("id"), filter.getLot()));
			}else {
				Join<CheckList, ProductLot> lotJoin = checklist.join("lot");
				Join<ProductLot, TypeProduct> typeJoin = lotJoin.join("typeProduct");
				predicates.add(builder.equal(typeJoin.get("id"), Integer.valueOf(filter.getProductType())));
			}
		}else {
			if (filter.getLot()!=0) {
				Join<CheckList, ProductLot> lotJoin = checklist.join("lot");
				predicates.add(builder.equal(lotJoin.get("id"), filter.getLot()));
			}
		}
		
		
		
		
		Predicate finalPredicate = builder.and(predicates.toArray(new Predicate[0]));
		criteriaQuery.where(finalPredicate);
		criteriaQuery.orderBy(builder.asc(checklist.get("time")));
		
		TypedQuery<CheckList> query = em.createQuery(criteriaQuery);
		
		List<CheckList> result = query.getResultList();
		
		return result;
	}

	@Override
	public Page<CheckList> filter(CheckListFilterDTO filter, Pageable pageable) {
		System.out.println("-----------------------------------------------------------------------");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		String[] devive = filter.getShift().split("-");
		LocalTime fromTime = LocalTime.parse(devive[0],DateTimeFormatter.ofPattern("HH:mm:ss"));
		LocalTime toTime = LocalTime.parse(devive[1],DateTimeFormatter.ofPattern("HH:mm:ss"));
		System.out.println(fromTime.toString());
		System.out.println(toTime.toString());
		List<Predicate> predicates = new ArrayList<Predicate>();

		CriteriaQuery<CheckList> criteriaQuery = builder.createQuery(CheckList.class);
		Root<CheckList> checklist = criteriaQuery.from(CheckList.class);
//		criteriaQuery.select(checklist);
		
		predicates.add(builder.equal(checklist.get("status"), filter.getStatus()));

		predicates.add(builder.between(checklist.get("time"), fromTime, toTime));
	
		
		predicates.add(builder.equal(checklist.get("date"), LocalDate.parse(filter.getDate())));
		if (filter.getProductType() != 0) {
			if (filter.getLot()!=0) {
				Join<CheckList, ProductLot> lotJoin = checklist.join("lot");
				predicates.add(builder.equal(lotJoin.get("id"), filter.getLot()));
			}else {
				Join<CheckList, ProductLot> lotJoin = checklist.join("lot");
				Join<ProductLot, TypeProduct> typeJoin = lotJoin.join("typeProduct");
				predicates.add(builder.equal(typeJoin.get("id"), Integer.valueOf(filter.getProductType())));
			}
		}else {
			if (filter.getLot()!=0) {
				Join<CheckList, ProductLot> lotJoin = checklist.join("lot");
				predicates.add(builder.equal(lotJoin.get("id"), filter.getLot()));
			}
		}
		
		
		
		
		Predicate finalPredicate = builder.and(predicates.toArray(new Predicate[0]));
		criteriaQuery.where(finalPredicate);
		
		System.out.println(finalPredicate);
		
		int totalRows = em.createQuery(criteriaQuery).getResultList().size();
		TypedQuery<CheckList> query = em.createQuery(criteriaQuery);
		query.setFirstResult( (int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		
	     Page<CheckList> result = new PageImpl<CheckList>(query.getResultList(), pageable, totalRows);

		
		return result;
	}

}
