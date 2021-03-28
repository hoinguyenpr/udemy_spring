package com.dpm.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dpm.dto.MixingMaterialDTO;
import com.dpm.entity.MixingMaterial;

/**
 * Update: Nguyennd6 Created date: 01/12/2020 16:00 AM
 **/
public interface MixingMaterialService {

	
	public String createNewMixing(MixingMaterialDTO mxMaterialDTO);

	String updateMixing(MixingMaterialDTO mxMaterialDTO);

	String deleteMixingById(int id);

	Optional<MixingMaterial> getMixingById(int id);

	Optional<MixingMaterialDTO> getMixingDTOById(int id);

	Page<MixingMaterialDTO> findPaginated(Pageable pageable);

	String approve(int id);

	Page<MixingMaterialDTO> searchMixing(Pageable pageable, String keyword);

	Page<MixingMaterialDTO> filterMixing(Pageable pageable, String type, String date,
			String machine);

	List<MixingMaterialDTO> findByFilter(String type, String date, String machine);

	void exportMixing(HttpServletResponse response,String type, String date, String machine);

}
