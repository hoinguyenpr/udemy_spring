package com.dpm.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dpm.entity.Additive;

@Repository
public interface AdditiveDAO extends JpaRepository<Additive, Integer> {

	@Query("SELECT a FROM Additive a WHERE " + "CONCAT(a.additiveName,' ',a.additiveCode)"
			+ "LIKE %?1%")
	public Page<Additive> findByAdditiveCodeOrAdditiveName(String keyword,
			Pageable pageable);

	@Query("SELECT COUNT(a.id) FROM Additive a WHERE "
			+ "CONCAT(a.additiveName,' ',a.additiveCode)" + "LIKE %?1%")
	public int countByAdditiveCodeOrAdditiveName(String keyword);

	//Modify By NguyenND6 14/01/2021 15:00
		public Optional<Additive> findByAdditiveCode(String code);
}
