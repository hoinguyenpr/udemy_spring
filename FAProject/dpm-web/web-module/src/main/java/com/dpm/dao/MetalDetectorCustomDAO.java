/**
 * 
 */
package com.dpm.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.dpm.dto.MetalDetectorFilterDTO;
import com.dpm.entity.MetalDetector;

/**
 * @author DinhDN 23/01/2021
 *
 */

@Repository
public class MetalDetectorCustomDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetalDetectorCustomDAO.class);

	// autowiring entityManager helped to create and execute dynamic jpql
	@Autowired
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public Page<MetalDetector> filterAllByParameter(MetalDetectorFilterDTO metalDetectorFilterDTO, Pageable pageable) {

		LOGGER.info("Function filterAllByParameter of class MetalDetectorCustomDAO.");
		String jpql = "SELECT metalDetector FROM MetalDetector AS metalDetector "
				+ " JOIN metalDetector.productLot AS productLot" + " JOIN metalDetector.department AS department"
				+ " JOIN metalDetector.inspector AS inspector " + " WHERE  metalDetector.isDelete = 0 ";

		if (metalDetectorFilterDTO.getDepartmentCode() != null
				&& metalDetectorFilterDTO.getDepartmentCode().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getDepartmentCode().equalsIgnoreCase("all")) {
				jpql += " AND department.departmentCode LIKE :departmentCode ";
			}
		}

		// conditions based on screen filter parameters
		if (metalDetectorFilterDTO.getLotNo() != null && metalDetectorFilterDTO.getLotNo().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getLotNo().equalsIgnoreCase("all")) {
				jpql += " AND productLot.lotCode LIKE :lotCode ";
			}
		}

		if (metalDetectorFilterDTO.getTypeProductCode() != null
				&& metalDetectorFilterDTO.getTypeProductCode().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getTypeProductCode().equalsIgnoreCase("all")) {
				jpql += " AND productLot.typeProduct.typeProductCode LIKE :typeProductCode ";
			}
		}

		if (metalDetectorFilterDTO.getUserName() != null && metalDetectorFilterDTO.getUserName().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getUserName().equalsIgnoreCase("all")) {
				jpql += " AND inspector.username LIKE :username ";
			}
		}

		if (metalDetectorFilterDTO.getStartDate() != null && metalDetectorFilterDTO.getEndDate() != null) {
			jpql += " AND metalDetector.checkingDate >= :startDate AND metalDetector.checkingDate <= :endDate ";
		}

		if (metalDetectorFilterDTO.getCheckingSample() != null
				&& metalDetectorFilterDTO.getCheckingSample().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getCheckingSample().equalsIgnoreCase("all")) {
				if (metalDetectorFilterDTO.getCheckingSample().equalsIgnoreCase("1")) {
					jpql += " AND metalDetector.checkingSampleFe = TRUE ";
					jpql += " AND metalDetector.checkingSampleNonFe = TRUE ";
					jpql += " AND metalDetector.checkingSampleSus = TRUE ";
				} else {
					jpql += " AND metalDetector.id NOT IN ( "
							+ "SELECT subMetalDetector.id FROM MetalDetector AS subMetalDetector "
							+ "WHERE subMetalDetector.checkingSampleFe = TRUE "
							+ "AND subMetalDetector.checkingSampleNonFe = TRUE "
							+ "AND subMetalDetector.checkingSampleSus = TRUE ) ";
				}
			}
		}

		if (metalDetectorFilterDTO.getStatus() != null & metalDetectorFilterDTO.getStatus().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getStatus().equalsIgnoreCase("all")) {
				jpql += " AND metalDetector.status = :status ";
			}
		}

		Query query = entityManager.createQuery(jpql);

		if (metalDetectorFilterDTO.getDepartmentCode() != null
				&& metalDetectorFilterDTO.getDepartmentCode().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getDepartmentCode().equalsIgnoreCase("all")) {
				query.setParameter("departmentCode", metalDetectorFilterDTO.getDepartmentCode());
			}
		}

		if (metalDetectorFilterDTO.getLotNo() != null && metalDetectorFilterDTO.getLotNo().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getLotNo().equalsIgnoreCase("all")) {
				query.setParameter("lotCode", metalDetectorFilterDTO.getLotNo());
			}
		}

		if (metalDetectorFilterDTO.getTypeProductCode() != null
				&& metalDetectorFilterDTO.getTypeProductCode().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getTypeProductCode().equalsIgnoreCase("all")) {
				query.setParameter("typeProductCode", metalDetectorFilterDTO.getTypeProductCode());
			}
		}
		if (metalDetectorFilterDTO.getUserName() != null && metalDetectorFilterDTO.getUserName().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getUserName().equalsIgnoreCase("all")) {
				query.setParameter("username", metalDetectorFilterDTO.getUserName());
			}
		}

		if (metalDetectorFilterDTO.getStartDate() != null && metalDetectorFilterDTO.getEndDate() != null) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				Date date = dateFormat.parse(metalDetectorFilterDTO.getStartDate().replace("/", "-"));
				query.setParameter("startDate", date);
				date = dateFormat.parse(metalDetectorFilterDTO.getEndDate().replace("/", "-"));
				query.setParameter("endDate", date);

			} catch (ParseException e) {
				LOGGER.error("Error convert date: " + e.getMessage());
			}
		}

		if (metalDetectorFilterDTO.getStatus() != null) {
			if (!metalDetectorFilterDTO.getStatus().equalsIgnoreCase("all")) {
				query.setParameter("status", metalDetectorFilterDTO.getStatus());
			}
		}

		List<MetalDetector> queryTotal = new ArrayList<MetalDetector>();
		try {
			queryTotal = query.getResultList();
		} catch (Exception e) {
			LOGGER.error("Error getResultList all metalDetector: " + e.getMessage());
		}
		long totalElement = queryTotal.size();

		int pageNumber = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();
		query.setFirstResult((pageNumber) * pageSize);
		query.setMaxResults(pageSize);
		List<MetalDetector> metalDetectors = new ArrayList<MetalDetector>();
		try {
			metalDetectors = query.getResultList();
		} catch (Exception e1) {
			LOGGER.error("Error getResultList size of list metalDetector: " + e1.getMessage());
		}

		Page<MetalDetector> page = new PageImpl<MetalDetector>(metalDetectors, pageable, totalElement);

		return page;
	}

	@SuppressWarnings("unchecked")
	public List<MetalDetector> getListToExportByParameter(@Valid MetalDetectorFilterDTO metalDetectorFilterDTO) {

		LOGGER.info("Function getListToExportByParameter of class MetalDetectorCustomDAO.");
		String jpql = "SELECT metalDetector FROM MetalDetector AS metalDetector "
				+ " JOIN metalDetector.productLot AS productLot" + " JOIN metalDetector.department AS department"
				+ " JOIN metalDetector.inspector AS inspector " + " WHERE  metalDetector.isDelete = 0 ";

		if (metalDetectorFilterDTO.getDepartmentCode() != null
				&& metalDetectorFilterDTO.getDepartmentCode().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getDepartmentCode().equalsIgnoreCase("all")) {
				jpql += " AND department.departmentCode LIKE :departmentCode ";
			}
		}

		// conditions based on screen filter parameters
		if (metalDetectorFilterDTO.getLotNo() != null && metalDetectorFilterDTO.getLotNo().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getLotNo().equalsIgnoreCase("all")) {
				jpql += " AND productLot.lotCode LIKE :lotCode ";
			}
		}

		if (metalDetectorFilterDTO.getTypeProductCode() != null
				&& metalDetectorFilterDTO.getTypeProductCode().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getTypeProductCode().equalsIgnoreCase("all")) {
				jpql += " AND productLot.typeProduct.typeProductCode LIKE :typeProductCode ";
			}
		}

		if (metalDetectorFilterDTO.getUserName() != null && metalDetectorFilterDTO.getUserName().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getUserName().equalsIgnoreCase("all")) {
				jpql += " AND inspector.username LIKE :username ";
			}
		}

		if (metalDetectorFilterDTO.getStartDate() != null && metalDetectorFilterDTO.getEndDate() != null) {
			jpql += " AND metalDetector.checkingDate >= :startDate AND metalDetector.checkingDate <= :endDate ";
		}

		if (metalDetectorFilterDTO.getCheckingSample() != null
				&& metalDetectorFilterDTO.getCheckingSample().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getCheckingSample().equalsIgnoreCase("all")) {
				if (metalDetectorFilterDTO.getCheckingSample().equalsIgnoreCase("1")) {
					jpql += " AND metalDetector.checkingSampleFe = TRUE ";
					jpql += " AND metalDetector.checkingSampleNonFe = TRUE ";
					jpql += " AND metalDetector.checkingSampleSus = TRUE ";
				} else {
					jpql += " AND metalDetector.id NOT IN ( "
							+ "SELECT subMetalDetector.id FROM MetalDetector AS subMetalDetector "
							+ "WHERE subMetalDetector.checkingSampleFe = TRUE "
							+ "AND subMetalDetector.checkingSampleNonFe = TRUE "
							+ "AND subMetalDetector.checkingSampleSus = TRUE ) ";
				}
			}
		}

		if (metalDetectorFilterDTO.getStatus() != null & metalDetectorFilterDTO.getStatus().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getStatus().equalsIgnoreCase("all")) {
				jpql += " AND metalDetector.status = :status ";
			}
		}

		Query query = entityManager.createQuery(jpql);

		if (metalDetectorFilterDTO.getDepartmentCode() != null
				&& metalDetectorFilterDTO.getDepartmentCode().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getDepartmentCode().equalsIgnoreCase("all")) {
				query.setParameter("departmentCode", metalDetectorFilterDTO.getDepartmentCode());
			}
		}

		if (metalDetectorFilterDTO.getLotNo() != null && metalDetectorFilterDTO.getLotNo().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getLotNo().equalsIgnoreCase("all")) {
				query.setParameter("lotCode", metalDetectorFilterDTO.getLotNo());
			}
		}

		if (metalDetectorFilterDTO.getTypeProductCode() != null
				&& metalDetectorFilterDTO.getTypeProductCode().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getTypeProductCode().equalsIgnoreCase("all")) {
				query.setParameter("typeProductCode", metalDetectorFilterDTO.getTypeProductCode());
			}
		}
		if (metalDetectorFilterDTO.getUserName() != null && metalDetectorFilterDTO.getUserName().trim().length() > 0) {
			if (!metalDetectorFilterDTO.getUserName().equalsIgnoreCase("all")) {
				query.setParameter("username", metalDetectorFilterDTO.getUserName());
			}
		}

		if (metalDetectorFilterDTO.getStartDate() != null && metalDetectorFilterDTO.getEndDate() != null) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				Date date = dateFormat.parse(metalDetectorFilterDTO.getStartDate().replace("/", "-"));
				query.setParameter("startDate", date);
				date = dateFormat.parse(metalDetectorFilterDTO.getEndDate().replace("/", "-"));
				query.setParameter("endDate", date);

			} catch (ParseException e) {
				LOGGER.error("Error convert date: " + e.getMessage());
			}
		}

		if (metalDetectorFilterDTO.getStatus() != null) {
			if (!metalDetectorFilterDTO.getStatus().equalsIgnoreCase("all")) {
				query.setParameter("status", metalDetectorFilterDTO.getStatus());
			}
		}
		List<MetalDetector> metalDetectors = new ArrayList<MetalDetector>();
		try {
			metalDetectors = query.getResultList();
		} catch (Exception e) {
			LOGGER.error("Error getResultList all metalDetector: " + e.getMessage());
		}

		return metalDetectors;
	}

}