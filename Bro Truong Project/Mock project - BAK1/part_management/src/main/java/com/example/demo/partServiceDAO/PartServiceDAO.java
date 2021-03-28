package com.example.demo.partServiceDAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PartDetail;

@Repository
public interface PartServiceDAO extends JpaRepository<PartDetail, Integer>{
	
	public PartDetail findByPartNumber(String partNumber);
	
	@Query(value = "SELECT * FROM demo.part_detail pd WHERE pd.status = 'ACTIVE'", nativeQuery = true)
	public List<PartDetail> getAllActive();
	
}
