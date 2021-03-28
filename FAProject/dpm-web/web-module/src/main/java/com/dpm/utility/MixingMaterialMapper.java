package com.dpm.utility;

import com.dpm.dto.MixingMaterialDTO;
import com.dpm.entity.MixingMaterial;

public class MixingMaterialMapper {
	
	

	public static MixingMaterialDTO mapMixingToDTO(MixingMaterial mixingMaterial) {
		MixingMaterialDTO mixDto = new MixingMaterialDTO();
		mixDto.setId(mixingMaterial.getId());
		mixDto.setTypeProduct(mixingMaterial.getTypeProduct().getId().toString());
		mixDto.setDateMixing(mixingMaterial.getDateMixing().toString());
		mixDto.setMachine((mixingMaterial.getMachine().getId().toString()));
		mixDto.setBatch(mixingMaterial.getBatch().toString());
		mixDto.setAmountMaterial(String.valueOf(mixingMaterial.getAmountMaterial()));
		mixDto.setIngredientBatch(mixingMaterial.getIngredientBatch().getId().toString());
		mixDto.setMixingTime((int) mixingMaterial.getMixingTime());
		mixDto.setEmployee((mixingMaterial.getEmployee()).getId().toString());
		mixDto.setProductLot((mixingMaterial.getProductLot().getId().toString()));
		mixDto.setAmountAdditive(String.valueOf(mixingMaterial.getAmountAddtitive()));
		mixDto.setAdditive((mixingMaterial.getAdditive().getId().toString()));
		mixDto.setNo(String.valueOf(mixingMaterial.getNo()));
		mixDto.setStatus(mixingMaterial.getStatus());
		mixDto.setAdditiveCode((mixingMaterial.getAdditive().getAdditiveCode()));
		mixDto.setIngredientBatchCode(mixingMaterial.getIngredientBatch().getIngredientBatchCode());
		mixDto.setProductLotCode(mixingMaterial.getProductLot().getLotCode());
		mixDto.setEmployeeName(mixingMaterial.getEmployee().getEmployeeName());
		return mixDto;

	}
}