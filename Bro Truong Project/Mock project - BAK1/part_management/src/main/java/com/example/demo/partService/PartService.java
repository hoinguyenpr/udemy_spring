package com.example.demo.partService;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.entity.PartDetail;

@Component
public interface PartService {
	
	public String savePart(PartDetail part);
	
	public List<PartDetail> getAllPart();
	
	public PartDetail getPartByNumber(String partNumber);

	public String updatePart(PartDetail part);
	
	public String obsoletePart(String partNumber);

	public List<PartDetail> getAllActive();
	
}
