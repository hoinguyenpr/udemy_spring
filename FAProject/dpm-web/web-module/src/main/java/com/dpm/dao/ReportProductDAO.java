package com.dpm.dao;

import java.time.LocalDate;
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
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import com.dpm.dto.ProductReportDTO;
import com.dpm.entity.Product;
import com.dpm.entity.SieveDrying;

@Repository
public class ReportProductDAO {

	@Autowired
	private EntityManager em;

	public Page<Product> getReport(ProductReportDTO rdto, Pageable pageable) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		
		List<Predicate> listCriteria = new ArrayList<Predicate>();
		
		CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
		Root<Product> itemRoot = criteriaQuery.from(Product.class);
		
		if (rdto.getMachineId() != 0) {
			listCriteria.add(builder.equal(itemRoot.get("machine"), rdto.getMachineId()));
		}

		if (rdto.getTypeProductId() != 0) {
			listCriteria.add(builder.equal(itemRoot.get("typeProduct"), rdto.getTypeProductId()));
		}
		
		listCriteria.add(builder.between(itemRoot.get("productEntryDate"), LocalDate.parse(rdto.getFromDate()), LocalDate.parse(rdto.getToDate())));
		
		Predicate finalPredicate = builder.and(listCriteria.toArray(new Predicate[0]));

		criteriaQuery.where(finalPredicate);
		
		int totalRows = em.createQuery(criteriaQuery).getResultList().size();

		TypedQuery<Product> query = em.createQuery(criteriaQuery);
		
		query.setFirstResult( (int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Page<Product> result = new PageImpl<Product>(query.getResultList(), pageable, totalRows);

		return result;

	}
}
