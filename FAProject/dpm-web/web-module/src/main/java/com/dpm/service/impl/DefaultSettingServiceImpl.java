package com.dpm.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dpm.configs.DataGeneration;
import com.dpm.constant.Constants;
import com.dpm.controller.MetalDetectorController;
import com.dpm.dao.DefaultSettingDAO;
import com.dpm.dto.BrixpHTrackingDTO;
import com.dpm.dto.CheckListSettingDTO;
import com.dpm.dto.MetalDetectorDTO;
import com.dpm.dto.MixingMaterialDTO;
import com.dpm.dto.ProductDTO1;
import com.dpm.entity.Additive;
import com.dpm.entity.DefaultSetting;
import com.dpm.entity.Employee;
import com.dpm.entity.IngredientBatch;
import com.dpm.entity.Machine;
import com.dpm.entity.MetalDetector;
import com.dpm.entity.Packing;
import com.dpm.entity.PressingMornitor;
import com.dpm.entity.ProductLot;
import com.dpm.entity.SieveDrying;
import com.dpm.entity.TrackingPHBx;
import com.dpm.entity.TypeProduct;
import com.dpm.service.DefaultSettingService;
import com.dpm.service.ProductLotSevice;

@Service
public class DefaultSettingServiceImpl implements DefaultSettingService {

	// create by DinhDN 27/01/2021 02:37 AM
	private static final Logger LOGGER = LoggerFactory.getLogger(MetalDetectorController.class);

	@Autowired
	private DefaultSettingDAO defaultSettingDAO;
	
	@Autowired
	private DataGeneration dataGen;

	/**
	 * Get Default values by prefix or by feature
	 * 
	 * @param feature name or prefix of setting default
	 * @author LamPQT
	 */
	@Override
	public List<DefaultSetting> getDefaultByFeature(String feature) {

		return defaultSettingDAO.findByPrefix(feature);

	}

	/**
	 * Function convert from List<DefaultSetting> to CheckListSettingDTO
	 * 
	 * @param feature name or prefix of setting default
	 * @author LamPQT
	 */
	@Override
	public CheckListSettingDTO convert(List<DefaultSetting> settings) {
		CheckListSettingDTO def = new CheckListSettingDTO();
		for (DefaultSetting defaultSetting : settings) {
			
				switch (defaultSetting.getKey()) {
				case "qc":
					def.setQc(defaultSetting.getValue());

					break;
				case "lotCode":

					def.setLotCode(defaultSetting.getValue());
					break;
				case "moisture":

					def.setMoisture(defaultSetting.getValue());
					break;
				case "ph":

					def.setPh(defaultSetting.getValue());
					break;
				case "brix":

					def.setBrix(defaultSetting.getValue());
					break;
				case "color":
					if ("0".equals(defaultSetting.getValue())) {
						def.setColor(true);
					} else {
						def.setColor(false);
					}

					break;
				case "taste":
					if ("0".equals(defaultSetting.getValue())) {
						def.setTaste(true);
					} else {
						def.setTaste(false);
					}
					break;
				case "impurity":

					def.setImpurity(defaultSetting.getValue());
					break;
				case "quantity_satisfied":

					def.setQuantitySatisfied(defaultSetting.getValue());
					break;
				case "quantity_unsatisfied":

					def.setQuantityUnsatisfied(defaultSetting.getValue());
					break;

				case "quantity_mix":

					def.setQuantityMix(defaultSetting.getValue());
					break;
				case "remark":

					def.setRemark(defaultSetting.getValue());
					break;
				}
			
		}

		return def;
	}

	@Override
	public ProductDTO1 convertToProductDTO1(List<DefaultSetting> settings) {
		ProductDTO1 productDTO1 = new ProductDTO1();
		for (DefaultSetting defaultSetting : settings) {
				switch (defaultSetting.getKey()) {
				case "fn_pd_product_code":
					productDTO1.setProductCode(defaultSetting.getValue());
					break;
				case "fn_pd_product_name":
					productDTO1.setProductName(defaultSetting.getValue());
					break;
				case "fn_pd_type_product":
					TypeProduct typeProduct = new TypeProduct();
					typeProduct.setId(Integer.valueOf(defaultSetting.getValue()));
					productDTO1.setTypeProduct(typeProduct);
					break;

				case "fn_pd_input_date":
					productDTO1.setProductEntryDate(defaultSetting.getValue());
					break;
				case "fn_pd_machine_no":
					Machine machine = new Machine();
					machine.setId(Integer.valueOf(defaultSetting.getValue()));
					productDTO1.setMachine(machine);
					break;
				case "fn_pd_total_weight_input":
					productDTO1.setTotalWeightInput(Double.valueOf(defaultSetting.getValue()));
					break;

				case "fn_pd_residue_input":
					productDTO1.setResidue(Integer.valueOf(defaultSetting.getValue()));
					break;
				case "fn_pd_coconut_milk_input":
					productDTO1.setCoconutMilk(Integer.valueOf(defaultSetting.getValue()));
					break;

				// Finished Product Report
				case "fn_pd_finished_product":

					productDTO1.setFinishedProductDetail(Integer.valueOf(defaultSetting.getValue()));
					break;

				case "fn_pd_unsatisfied_product":

					productDTO1.setUnsatisfiedProduct(Integer.valueOf(defaultSetting.getValue()));
					break;
				case "fn_pd_water_extract":

					productDTO1.setWaterExtract(Integer.valueOf(defaultSetting.getValue()));
					break;
				case "fn_pd_black_point":
					productDTO1.setCoconutSilk(Integer.valueOf(defaultSetting.getValue()));
					break;

				case "fn_pd_low_ph":
					productDTO1.setLowPh(Integer.valueOf(defaultSetting.getValue()));
					break;
				case "fn_pd_clotted":
					productDTO1.setClotted(Integer.valueOf(defaultSetting.getValue()));
					break;
				case "fn_pd_xylon":
					productDTO1.setXylon(Integer.valueOf(defaultSetting.getValue()));
					break;
				case "fn_pd_scrap":
					productDTO1.setScrap(Integer.valueOf(defaultSetting.getValue()));
					break;
				case "fn_pd_crusted":
					productDTO1.setCrusted(Integer.valueOf(defaultSetting.getValue()));
					break;
				case "fn_pd_residue":
					productDTO1.setResidueDetail(Integer.valueOf(defaultSetting.getValue()));
					break;
				}
		}
		return productDTO1;
	}

	@Override
	public void updateFinishedProductReportSetting(ProductDTO1 dto) {

		List<DefaultSetting> listDefaultSettings = defaultSettingDAO
				.findByPrefix(Constants.DEFAULT_SETTING_PREFIX.FINISHED_PRODUCT_REPORT);

		if (!listDefaultSettings.isEmpty()) {
			listDefaultSettings.forEach(action -> {
				switch (action.getKey()) {
				case "fn_pd_product_code":
					action.setValue(dto.getProductCode());
					break;
				case "fn_pd_product_name":
					action.setValue(dto.getProductName());
					break;
				case "fn_pd_type_product":
					action.setValue(Integer.toString(dto.getTypeProduct().getId()));
					break;
				case "fn_pd_input_date":
					action.setValue(dto.getProductEntryDate());
					break;
				case "fn_pd_machine_no":
					action.setValue(Integer.toString(dto.getMachine().getId()));
					break;
				case "fn_pd_total_weight_input":
					action.setValue(Double.toString(dto.getTotalWeightInput()));
					break;

				case "fn_pd_residue_input":
					action.setValue(Integer.toString(dto.getResidue()));
					break;
				case "fn_pd_coconut_milk_input":
					action.setValue(Integer.toString(dto.getCoconutMilk()));
					break;
				// Finished Product Report
				case "fn_pd_finished_product":
					action.setValue(Integer.toString(dto.getFinishedProductDetail()));
					break;
				case "fn_pd_unsatisfied_product":

					action.setValue(Integer.toString(dto.getUnsatisfiedProduct()));
					break;
				case "fn_pd_water_extract":

					action.setValue(Integer.toString(dto.getWaterExtract()));
					break;
				case "fn_pd_black_point":
					action.setValue(Integer.toString(dto.getCoconutSilk()));
					break;

				case "fn_pd_low_ph":
					action.setValue(Integer.toString(dto.getLowPh()));
					break;
				case "fn_pd_clotted":
					action.setValue(Integer.toString(dto.getClotted()));
					break;
				case "fn_pd_xylon":
					action.setValue(Integer.toString(dto.getXylon()));
					break;
				case "fn_pd_scrap":
					action.setValue(Integer.toString(dto.getScrap()));
					break;
				case "fn_pd_crusted":
					action.setValue(Integer.toString(dto.getCrusted()));
					break;
				case "fn_pd_residue":
					action.setValue(Integer.toString(dto.getResidueDetail()));
					break;
				default: {
					break;
				}
				}

			});
			defaultSettingDAO.saveAll(listDefaultSettings);
		}

	}

	/**
	 * Update default setting of checklist module
	 * 
	 * @param feature name or prefix of setting default
	 * @author LamPQT
	 */
	@Override
	public void updateCheckListSetting(CheckListSettingDTO dto) {
		DefaultSetting check_qc = defaultSettingDAO.findByPrefixAndKey("qa_check", "qc");
		DefaultSetting check_date = defaultSettingDAO.findByPrefixAndKey("qa_check", "date");
		DefaultSetting check_lotCode = defaultSettingDAO.findByPrefixAndKey("qa_check", "lotCode");
		DefaultSetting check_moisture = defaultSettingDAO.findByPrefixAndKey("qa_check", "moisture");
		DefaultSetting check_ph = defaultSettingDAO.findByPrefixAndKey("qa_check", "ph");
		DefaultSetting check_brix = defaultSettingDAO.findByPrefixAndKey("qa_check", "brix");
		DefaultSetting check_color = defaultSettingDAO.findByPrefixAndKey("qa_check", "color");
		DefaultSetting check_taste = defaultSettingDAO.findByPrefixAndKey("qa_check", "taste");
		DefaultSetting check_impurity = defaultSettingDAO.findByPrefixAndKey("qa_check", "impurity");
		DefaultSetting check_quantity_satisfied = defaultSettingDAO.findByPrefixAndKey("qa_check",
				"quantity_satisfied");
		DefaultSetting check_quantity_unsatisfied = defaultSettingDAO.findByPrefixAndKey("qa_check",
				"quantity_unsatisfied");
		DefaultSetting check_quantity_mix = defaultSettingDAO.findByPrefixAndKey("qa_check", "quantity_mix");
		DefaultSetting check_remark = defaultSettingDAO.findByPrefixAndKey("qa_check", "remark");

		check_qc.setValue(dto.getQc());
		check_lotCode.setValue(dto.getLotCode());
		check_moisture.setValue(dto.getMoisture());
		check_ph.setValue(dto.getPh());
		check_brix.setValue(dto.getBrix());
		if (dto.isColor()) {
			check_color.setValue("0");
		} else {
			check_color.setValue("1");
		}
		if (dto.isTaste()) {
			check_taste.setValue("0");
		} else {
			check_taste.setValue("1");
		}
		check_impurity.setValue(dto.getImpurity());
		check_quantity_satisfied.setValue(dto.getQuantitySatisfied());
		check_quantity_unsatisfied.setValue(dto.getQuantityUnsatisfied());
		check_quantity_mix.setValue(dto.getQuantityMix());
		check_remark.setValue(dto.getRemark());

		defaultSettingDAO.saveAll(Arrays.asList(check_qc, check_date, check_lotCode, check_moisture, check_ph,
				check_brix, check_color, check_taste, check_impurity, check_quantity_satisfied,
				check_quantity_unsatisfied, check_quantity_mix, check_remark));
	}

	// create by DinhDN 27/01/2021 04:18 AM
	// modify by DinhDN 03/0202021 05:56 PM
	/**
	 * Function get MetalDetectorDTO from DefaultSetting table
	 * 
	 * @param prefix
	 * @return List object DefaultSetting
	 */
	@Override
	public MetalDetectorDTO getMetalDetectorDTOByPrefix(String prefix) {
		LOGGER.info("Function getMetalDetectorDTOByPrefix.");
		MetalDetectorDTO metalDetectorDTO = new MetalDetectorDTO();
		List<DefaultSetting> defaultSettings = new ArrayList<DefaultSetting>();
		try {
			defaultSettings = defaultSettingDAO.findByPrefix(prefix);
			LOGGER.error("Function getMetalDetectorDTOByPrefix \"" + Constants.SUCCESS + "\" ");
		} catch (Exception e) {
			LOGGER.error("Function getMetalDetectorDTOByPrefix \"" + Constants.FAILED + "\": " + e.getMessage());
		}
		// defaultSetting for meatalDetector has exist in table Default_setting
		if (!defaultSettings.isEmpty()) {
			Iterator<DefaultSetting> iterator = defaultSettings.iterator();
			while (iterator.hasNext()) {
				DefaultSetting recordSetting = iterator.next();
				// Update all variable of MetalDetectorDTO
				String prefixKey = recordSetting.getKey();
				switch (prefixKey) {
				case "departmentCode":
					metalDetectorDTO.setDepartmentCode(recordSetting.getValue());
					break;
				case "typeProductCode":
					metalDetectorDTO.setTypeProductCode(recordSetting.getValue());
					break;
				case "lotNo":
					metalDetectorDTO.setLotNo(recordSetting.getValue());
					break;
				case "frequency":
					metalDetectorDTO.setFrequency(recordSetting.getValue());
					break;
				case "optionsRadiosFe":
					metalDetectorDTO.setOptionsRadiosFe(Boolean.valueOf(recordSetting.getValue()));
					break;
				case "optionsRadiosNonfe":
					metalDetectorDTO.setOptionsRadiosNonfe(Boolean.valueOf(recordSetting.getValue()));
					break;
				case "optionsRadiosSus":
					metalDetectorDTO.setOptionsRadiosSus(Boolean.valueOf(recordSetting.getValue()));
					break;
				case "userName":
					metalDetectorDTO.setUserName(recordSetting.getValue());
					break;
				case "status":
					metalDetectorDTO.setStatus(recordSetting.getValue());
					break;

				}
			}
		}
		return metalDetectorDTO;
	}



/**
	 * Function create or update setting of metal detector
	 * 
	 * @return Boolean. True if success, otherwise return false
	 * @throws Exception
	 */
	@Override
	public Boolean saveOrUpdateMetalDetectorSetting(MetalDetector metalDetector) {
		LOGGER.info("Function saveOrUpdateMetalDetectorSetting. ");
		List<DefaultSetting> defaultSettings = new ArrayList<DefaultSetting>();
		try {
			defaultSettings = defaultSettingDAO.findByPrefix(Constants.DEFAULT_PREFIX_METAL_DETECTOR);
			LOGGER.info("Function saveOrUpdateMetalDetectorSetting findByPrefix \"" + Constants.SUCCESS + "\" ");
		} catch (Exception e) {
			LOGGER.error("Function saveOrUpdateMetalDetectorSetting \"" + Constants.FAILED + "\": " + e.getMessage());
		}

		if (defaultSettings.isEmpty()) {
			if (!saveMetalDetectorSetting(metalDetector)) {
				return false;
			}
		} else {
			updateMetalDetectorSetting(metalDetector);
		}
		return true;

	}

	private boolean saveMetalDetectorSetting(MetalDetector metalDetector) {
		LOGGER.info("Function saveMetalDetectorSetting(MetalDetector metalDetector).");

		DefaultSetting departmentCodeSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR,
				"departmentCode", metalDetector.getDepartment().getDepartmentCode());
		DefaultSetting typeProductCodeSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR,
				"typeProductCode", metalDetector.getProductLot().getTypeProduct().getTypeProductCode());
		DefaultSetting lotNoSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "lotNo",
				metalDetector.getProductLot().getLotCode());
		DefaultSetting frequencySetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "frequency",
				metalDetector.getFrequency());
		DefaultSetting optionsRadiosFeSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR,
				"optionsRadiosFe", metalDetector.getCheckingSampleFe().toString());
		DefaultSetting optionsRadiosNonfeSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR,
				"optionsRadiosNonfe", metalDetector.getCheckingSampleNonFe().toString());
		DefaultSetting optionsRadiosSusSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR,
				"optionsRadiosSus", metalDetector.getCheckingSampleSus().toString());
		// Update by DinhDN 30/01/2021 19:20:00
		DefaultSetting employeeIdSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "userName",
				metalDetector.getInspector().getUsername());
		// Update by DinhDN 03/02/2021 05:48 PM
		DefaultSetting statusSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "status",
				metalDetector.getStatus());
		// Try to save all record
		try {
			defaultSettingDAO.saveAll(Arrays.asList(departmentCodeSetting, typeProductCodeSetting, lotNoSetting,
					frequencySetting, optionsRadiosFeSetting, optionsRadiosNonfeSetting, optionsRadiosSusSetting,
					employeeIdSetting, statusSetting));
			LOGGER.info("Function saveMetalDetectorSetting(MetalDetector metalDetector) \"" + Constants.SUCCESS + "\"");
		} catch (Exception e) {
			LOGGER.error("Function saveMetalDetectorSetting(MetalDetector metalDetector) \"" + Constants.FAILED + "\": "
					+ e.getMessage());
			return false;
		}
		return true;
	}

	private boolean updateMetalDetectorSetting(MetalDetector metalDetector) {
		LOGGER.info("Function updateMetalDetectorSetting(MetalDetector metalDetector).");

		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "departmentCode",
				metalDetector.getDepartment().getDepartmentCode())) {
			return false;
		}
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "typeProductCode",
				metalDetector.getProductLot().getTypeProduct().getTypeProductCode())) {
			return false;
		}
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "lotNo",
				metalDetector.getProductLot().getLotCode())) {
			return false;
		}
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "frequency",
				metalDetector.getFrequency())) {
			return false;
		}
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "optionsRadiosFe",
				metalDetector.getCheckingSampleFe().toString())) {
			return false;
		}
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "optionsRadiosNonfe",
				metalDetector.getCheckingSampleNonFe().toString())) {
			return false;
		}
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "optionsRadiosSus",
				metalDetector.getCheckingSampleSus().toString())) {
			return false;
		}
		// Update by DinhDN 30/01/2021 19:20:00
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "userName",
				metalDetector.getInspector().getUsername())) {
			return false;
		}
		// Update by DinhDN 03/02/2021 06:38 PM
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "status", metalDetector.getStatus())) {
			return false;
		}
		return true;
	}
	
	// create by DinhDN 27/01/2021 02:43 AM
	// modify by DinhDN 03/0202021 05:56 PM
	/**
	 * Function create or update setting of metal detector
	 * 
	 * @param metalDetectorDTO
	 * @return Boolean. True if success, otherwise return false
	 * @throws Exception
	 */
	@Override
	public Boolean saveOrUpdateMetalDetectorSetting(MetalDetectorDTO metalDetectorDTO) {
		LOGGER.info("Function saveOrUpdateMetalDetectorSetting. ");
		List<DefaultSetting> defaultSettings = new ArrayList<DefaultSetting>();
		try {
			defaultSettings = defaultSettingDAO.findByPrefix(Constants.DEFAULT_PREFIX_METAL_DETECTOR);
			LOGGER.info("Function saveOrUpdateMetalDetectorSetting findByPrefix \"" + Constants.SUCCESS + "\" ");
		} catch (Exception e) {
			LOGGER.error("Function saveOrUpdateMetalDetectorSetting \"" + Constants.FAILED + "\": " + e.getMessage());
		}

		if (defaultSettings.isEmpty()) {
			if (!saveMetalDetectorSetting(metalDetectorDTO)) {
				return false;
			}
		} else {
			updateMetalDetectorSetting(metalDetectorDTO);
		}
		return true;

	}

	// create by DinhDN 27/01/2021 03:43 AM
	// modify by DinhDN 03/0202021 05:56 PM
	/**
	 * Function check DefaultSetting. If key is exist then update DefaultSetting,
	 * othersiwe create new DefaultSetting.
	 * 
	 * @param metalDetectorDTO
	 * @return Boolean. true if success, otherwise false.
	 */
	private Boolean updateMetalDetectorSetting(MetalDetectorDTO metalDetectorDTO) {
		LOGGER.info("Function updateMetalDetectorSetting.");

		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "departmentCode",
				metalDetectorDTO.getDepartmentCode())) {
			return false;
		}
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "typeProductCode",
				metalDetectorDTO.getTypeProductCode())) {
			return false;
		}
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "lotNo", metalDetectorDTO.getLotNo())) {
			return false;
		}
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "frequency",
				metalDetectorDTO.getFrequency())) {
			return false;
		}
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "optionsRadiosFe",
				metalDetectorDTO.getOptionsRadiosFe().toString())) {
			return false;
		}
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "optionsRadiosNonfe",
				metalDetectorDTO.getOptionsRadiosNonfe().toString())) {
			return false;
		}
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "optionsRadiosSus",
				metalDetectorDTO.getOptionsRadiosSus().toString())) {
			return false;
		}
		// Update by DinhDN 30/01/2021 19:20:00
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "userName",
				metalDetectorDTO.getUserName())) {
			return false;
		}
		// Update by DinhDN 03/02/2021 06:38 PM
		if (!getRecordAndSaveOrUpdate(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "status",
				metalDetectorDTO.getStatus())) {
			return false;
		}
		return true;
	}

	// create by DinhDN 27/01/2021 03:43 AM
	// modify by DinhDN 03/0202021 05:56 PM
	/**
	 * Function get record exist with Prefix And Key and update or create new
	 * record.
	 * 
	 * @param prefix
	 * @param key
	 * @param value
	 * @return Boolean
	 */
	private Boolean getRecordAndSaveOrUpdate(String prefix, String key, String value) {
		LOGGER.info("Function getRecordAndSaveOrUpdate.");

		DefaultSetting objectRecord = null;
		// Try get record with Prefix And Key
		try {
			objectRecord = defaultSettingDAO.findByPrefixAndKey(prefix, key);
		} catch (Exception e) {
			LOGGER.error("ERROR get record By Prefix And Key: " + e.getMessage());
		}
		// Record is exist then update value
		if (objectRecord != null) {
			objectRecord.setValue(value);
			if (!saveDefaultSetting(objectRecord)) {
				return false;
			}
		} else { // Record is not exist then create new record
			objectRecord = new DefaultSetting(prefix, key, value);
			if (!saveDefaultSetting(objectRecord)) {
				return false;
			}
		}
		return true;

	}

	// create by DinhDN 27/01/2021 02:43 AM
	// modify by DinhDN 03/0202021 05:56 PM
	/**
	 * Function save new DefaultSetting for matel detector
	 * 
	 * @param metalDetectorDTO
	 * @return Boolean. true if success, otherwise false.
	 */
	private Boolean saveMetalDetectorSetting(MetalDetectorDTO metalDetectorDTO) {
		LOGGER.info("Function saveMetalDetectorSetting.");

		DefaultSetting departmentCodeSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR,
				"departmentCode", metalDetectorDTO.getDepartmentCode());
		DefaultSetting typeProductCodeSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR,
				"typeProductCode", metalDetectorDTO.getTypeProductCode());
		DefaultSetting lotNoSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "lotNo",
				metalDetectorDTO.getLotNo());
		DefaultSetting frequencySetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "frequency",
				metalDetectorDTO.getFrequency());
		DefaultSetting optionsRadiosFeSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR,
				"optionsRadiosFe", metalDetectorDTO.getOptionsRadiosFe().toString());
		DefaultSetting optionsRadiosNonfeSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR,
				"optionsRadiosNonfe", metalDetectorDTO.getOptionsRadiosNonfe().toString());
		DefaultSetting optionsRadiosSusSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR,
				"optionsRadiosSus", metalDetectorDTO.getOptionsRadiosSus().toString());
		// Update by DinhDN 30/01/2021 19:20:00
		DefaultSetting employeeIdSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "userName",
				metalDetectorDTO.getUserName());
		// Update by DinhDN 03/02/2021 05:48 PM
		DefaultSetting statusSetting = new DefaultSetting(Constants.DEFAULT_PREFIX_METAL_DETECTOR, "status",
				metalDetectorDTO.getStatus());
		// Try to save all record
		try {
			defaultSettingDAO.saveAll(Arrays.asList(departmentCodeSetting, typeProductCodeSetting, lotNoSetting,
					frequencySetting, optionsRadiosFeSetting, optionsRadiosNonfeSetting, optionsRadiosSusSetting,
					employeeIdSetting, statusSetting));
			LOGGER.info("Function saveMetalDetectorSetting \"" + Constants.SUCCESS + "\"");
		} catch (Exception e) {
			LOGGER.error("Function saveMetalDetectorSetting \"" + Constants.FAILED + "\": " + e.getMessage());
			return false;
		}
		return true;
	}

	// create by DinhDN 27/01/2021 02:43 AM
	// modify by DinhDN 03/0202021 05:56 PM
	/**
	 * Function save record of table DefaultSetting
	 * 
	 * @param defaultSetting
	 * @return Boolean
	 */
	private Boolean saveDefaultSetting(DefaultSetting defaultSetting) {
		LOGGER.info("Function saveDefaultSetting.");
		// Try to save or update one record
		try {
			defaultSettingDAO.save(defaultSetting);
			LOGGER.info("Function saveDefaultSetting \"" + Constants.SUCCESS + "\"");
		} catch (Exception e) {
			LOGGER.error("Function saveDefaultSetting \"" + Constants.FAILED + "\": " + e.getMessage());
			return false;
		}
		return true;
	}

	// Create by ThienNTN1
	@Override
	public DefaultSetting getDefaultByPrefixAndKey(String prefix, String key) {

		return defaultSettingDAO.findByPrefixAndKey(prefix, key);
	}

	@Override
	public PressingMornitor getDefaultValuePressingMonitor() {
		List<DefaultSetting> listDefaultSettings = defaultSettingDAO
				.findByPrefix(Constants.DEFAULT_SETTING_PREFIX.PRESSING_MONITOR);

		if (!listDefaultSettings.isEmpty()) {

			PressingMornitor mornitor = new PressingMornitor();
			mornitor.setId(0);
			mornitor.setInputTime(LocalDateTime.now());

			listDefaultSettings.forEach(action -> {
				switch (action.getKey()) {

				case "TYPE_PRODUCT": {
					TypeProduct tmp = null;

					if (action.getValue() == null) {

					} else {
						tmp = new TypeProduct();
						tmp.setId(Integer.valueOf(action.getValue()));
					}
					mornitor.setTypeProduct(tmp);
					break;
				}

				case "MACHINE": {
					Machine tmp = new Machine();
					tmp.setId(Integer.valueOf(action.getValue()));
					mornitor.setMachine(tmp);
					break;
				}

				case "INGREDIENT_BATCH": {
					IngredientBatch tmp = new IngredientBatch();
					tmp.setId(Integer.valueOf(action.getValue()));
					mornitor.setIngredientBatch(tmp);
					break;
				}

				case "RESIDUE": {
					mornitor.setWeightResidue(Integer.valueOf(action.getValue()));
					break;
				}

				case "WEIGHT": {
					mornitor.setWeight(Integer.valueOf(action.getValue()));
					break;
				}

				case "NET_STATUS": {
					mornitor.setNetStatusBeforePress(Boolean.valueOf(action.getValue()));
					break;
				}

				case "PRESS_TIME": {
					mornitor.setPressingTime(Integer.valueOf(action.getValue()));
					break;
				}

				case "PRESSURE_STATUS": {
					mornitor.setPressureCondition(Boolean.valueOf(action.getValue()));
					break;
				}

				case "WEIGHT_MILK": {
					mornitor.setWeightCoconutMilk(Integer.valueOf(action.getValue()));
					break;
				}

				default:
					break;
				}

			});
			return mornitor;
		}

		return null;
	}

	@Override
	public Boolean updateDefaultValuePresingMonitor(PressingMornitor pm) {

		List<DefaultSetting> listDefaultSettings = defaultSettingDAO
				.findByPrefix(Constants.DEFAULT_SETTING_PREFIX.PRESSING_MONITOR);

		if (!listDefaultSettings.isEmpty()) {

			listDefaultSettings.forEach(action -> {
				switch (action.getKey()) {

				case "TYPE_PRODUCT": {
					action.setValue(pm.getTypeProduct().getId().toString());
					break;
				}

				case "MACHINE": {
					action.setValue(pm.getMachine().getId().toString());
					break;
				}

				case "INGREDIENT_BATCH": {
					action.setValue(pm.getIngredientBatch().getId().toString());
					break;
				}

				case "RESIDUE": {
					action.setValue(pm.getWeightResidue() == null ? "0" : pm.getWeightResidue().toString());
					break;
				}

				case "WEIGHT": {
					action.setValue(pm.getWeight() == null ? "0" : pm.getWeight().toString());
					break;
				}

				case "NET_STATUS": {
					action.setValue(pm.getNetStatusBeforePress().toString());
					break;
				}

				case "PRESS_TIME": {
					action.setValue(pm.getPressingTime() == null ? "0" : pm.getPressingTime().toString());
					break;
				}

				case "PRESSURE_STATUS": {
					action.setValue(String.valueOf(pm.isPressureCondition()));
					break;
				}

				case "WEIGHT_MILK": {
					action.setValue(pm.getWeightCoconutMilk() == null ? "0" : pm.getWeightCoconutMilk().toString());
					break;
				}

				default:
					break;
				}

			});

			defaultSettingDAO.saveAll(listDefaultSettings);
			return true;

		}
		return false;
	}

	// Added by TruongDD 01/27/2021
	@Override
	public String setDefaultSieveDrying(SieveDrying sd) {

		if (defaultSettingDAO.findByPrefix(Constants.PREFIX.SIEVE_DRYING).isEmpty()) {
			dataGen.createDefaultSettingValueForPressingMonitor();
		}

		List<DefaultSetting> sdDefault = defaultSettingDAO.findByPrefix(Constants.PREFIX.SIEVE_DRYING);

		if (!sdDefault.isEmpty()) {

			sdDefault.forEach(action -> {
				switch (action.getKey()) {

				case "WORKER": {
					action.setValue(sd.getWorker().getId().toString());
					break;
				}

				case "QC": {
					action.setValue(sd.getQc().getId().toString());
					break;
				}

				case "VERIFIER": {
					action.setValue(sd.getVerifier().getId().toString());
					break;
				}

				case "TYPE_PRODUCT": {
					action.setValue(sd.getTypeProduct().getId().toString());
					break;
				}

				case "INPUT_DATE": {
					action.setValue(sd.getInputDate().toString());
					break;
				}

				case "PRODUCT_LOT": {
					action.setValue(sd.getLotId().getId().toString());
					break;
				}

				case "MACHINE": {
					action.setValue(sd.getMachine().getId().toString());
					break;
				}

				case "INGREDIENT_BATCH": {
					action.setValue(sd.getIngredientBatch().getId().toString());
					break;
				}

				case "MOISTURE": {
					try {
						action.setValue(String.valueOf(sd.getMoisture()));
					} catch (Exception e) {
						action.setValue("-1");
						e.printStackTrace();
					}
					break;
				}

				case "PRESSURE": {
					action.setValue(String.valueOf(sd.getPressure()));
					break;
				}

				case "INPUT_TEMP": {
					action.setValue(String.valueOf(sd.getInputTemp()));
					break;
				}

				case "pH": {
					action.setValue(String.valueOf(sd.getpH()));
					break;
				}

				case "OUTPUT_TEMP": {
					action.setValue(String.valueOf(sd.getOutputTemp()));
					break;
				}

				case "IMPURITIES": {
					action.setValue(String.valueOf(sd.isImpurities()));
					break;
				}

				case "SIZE": {
					action.setValue(String.valueOf(sd.isSize()));
					break;
				}

				case "COLOR": {
					action.setValue(String.valueOf(sd.isColor()));
					break;
				}

				case "ODOR": {
					action.setValue(String.valueOf(sd.isOdor()));
					break;
				}

				case "TASTE": {
					action.setValue(String.valueOf(sd.isTaste()));
					break;
				}

				case "NET_STAT": {
					action.setValue(String.valueOf(sd.isNetStat()));
					break;
				}

				default:
					break;
				}

			});

			defaultSettingDAO.saveAll(sdDefault);
			return "UPDATE_DEFAULT_VALUE_SUCCESS";

		}
		return "UPDATE_DEFAULT_VALUE_FAILED";
	}

	// Added by TruongDD 01/27/2021
	@Override
	public SieveDrying getDefaultSieveDrying() {
		List<DefaultSetting> sdDefault = defaultSettingDAO.findByPrefix(Constants.PREFIX.SIEVE_DRYING);

		if (!sdDefault.isEmpty()) {

			SieveDrying sd = new SieveDrying();
			sd.setId(0);

			sdDefault.forEach(action -> {
				switch (action.getKey()) {

				case "WORKER": {
					Employee tmp = new Employee();
					tmp.setId(Integer.valueOf(action.getValue()));
					sd.setWorker(tmp);
					break;
				}

				case "QC": {
					Employee tmp = new Employee();
					tmp.setId(Integer.valueOf(action.getValue()));
					sd.setQc(tmp);
					break;
				}

				case "VERIFIER": {
					Employee tmp = new Employee();
					tmp.setId(Integer.valueOf(action.getValue()));
					sd.setVerifier(tmp);
					break;
				}

				case "TYPE_PRODUCT": {
					TypeProduct tmp = new TypeProduct();
					tmp.setId(Integer.valueOf(action.getValue()));
					sd.setTypeProduct(tmp);
					break;
				}

				case "INPUT_DATE": {
					// TODO check for Date format
					LocalDate tmp;
					DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					tmp = LocalDate.parse(action.getValue(), dateFormatter);
					sd.setInputDate(tmp);
					break;
				}

				case "INPUT_TIME": {
					// TODO check for Time format
					LocalTime tmp;
					DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
					tmp = LocalTime.parse(action.getValue(), timeFormatter);
					sd.setInputTime(tmp);
					break;
				}

				case "PRODUCT_LOT": {
					ProductLot tmp = new ProductLot();
					tmp.setId(Integer.valueOf(action.getValue()));
					sd.setLotCode(tmp);
					break;
				}

				case "MACHINE": {
					Machine tmp = new Machine();
					tmp.setId(Integer.valueOf(action.getValue()));
					sd.setMachine(tmp);
					break;
				}

				case "INGREDIENT_BATCH": {
					IngredientBatch tmp = new IngredientBatch();
					tmp.setId(Integer.valueOf(action.getValue()));
					sd.setIngredientBatch(tmp);
					break;
				}

				case "MOISTURE": {
					sd.setMoisture(Float.valueOf(action.getValue()));
					break;
				}

				case "PRESSURE": {
					sd.setPressure(Float.valueOf(action.getValue()));
					break;
				}

				case "INPUT_TEMP": {
					sd.setInputTemp(Float.valueOf(action.getValue()));
					break;
				}

				case "pH": {
					sd.setpH(Float.valueOf(action.getValue()));
					break;
				}

				case "OUTPUT_TEMP": {
					sd.setOutputTemp(Float.valueOf(action.getValue()));
					break;
				}

				case "IMPURITIES": {
					sd.setImpurities(Boolean.valueOf(action.getValue()));
					break;
				}

				case "SIZE": {
					sd.setSize(Boolean.valueOf(action.getValue()));
					break;
				}

				case "COLOR": {
					sd.setColor(Boolean.valueOf(action.getValue()));
					break;
				}

				case "ODOR": {
					sd.setOdor(Boolean.valueOf(action.getValue()));
					break;
				}

				case "TASTE": {
					sd.setTaste(Boolean.valueOf(action.getValue()));
					break;
				}

				case "NET_STAT": {
					sd.setNetStat(Boolean.valueOf(action.getValue()));
					break;
				}

				default:
					break;
				}
			});
			return sd;
		}
		return null;
	}

	/**
	 * @author VuDH11
	 * return trackingPHBx
	 */

	@Autowired
	ProductLotSevice product;

	@Override
	public TrackingPHBx getDefaultTrackingForm() {
		// TODO Auto-generated method stub
		List<DefaultSetting> sdDefault = defaultSettingDAO
				.findByPrefix(Constants.DEFAULT_SETTING_PREFIX.TRACKING_PHBRIX_FORM);

		if (!sdDefault.isEmpty()) {

			TrackingPHBx form = new TrackingPHBx();
			form.setId(0);

			sdDefault.forEach(action -> {
				switch (action.getKey()) {

				case "INGREDIENT_BATCH": {
					IngredientBatch tmp = new IngredientBatch();
					tmp.setId(Integer.valueOf(action.getValue()));
					form.setIngredient(tmp);
					break;
				}

				case "PRODUCT_LOT": {
					ProductLot tmp = new ProductLot();
					tmp = product.findById(Integer.valueOf(action.getValue()));
//					tmp.setId(Integer.valueOf(action.getValue()));

					form.setProductLot(tmp);
					break;
				}

				case "MACHINE": {
					Machine tmp = new Machine();
					tmp.setId(Integer.valueOf(action.getValue()));
					form.setMachine(tmp);
					break;
				}

				case "PERSON_IN_CHARGE": {
					Employee tmp = new Employee();
					tmp.setId(Integer.valueOf(action.getValue()));
					form.setPersonInCharge(tmp);
					break;
				}

				case "VERIFIER": {
					// TODO check for Date format
					Employee tmp = new Employee();
					tmp.setId(Integer.valueOf(action.getValue()));
					form.setVerifier(tmp);
					break;
				}

				case "PACKING": {
					Packing tmp = new Packing();
					tmp.setId(Integer.valueOf(action.getValue()));
					form.setPacking(tmp);
					break;
				}

				case "PRESSURE": {
					form.setPressure(Integer.valueOf(action.getValue()));
					break;
				}

				case "PH_PREMIX": {
					form.setpHPreMix(Integer.valueOf(action.getValue()));
					break;
				}

				case "PH_MIX": {
					form.setpHMix(Integer.valueOf(action.getValue()));
					break;
				}

				case "PH_HOMO": {
					form.setpHHomo(Integer.valueOf(action.getValue()));
					break;
				}

				case "BRIX_PREMIX": {
					form.setBrixPreMix(Float.valueOf(action.getValue()));
					break;
				}

				case "BRIX_MIX": {
					form.setBrixMix(Float.valueOf(action.getValue()));
					break;
				}

				case "BRIX_HOMO": {
					form.setBrixHomo(Float.valueOf(action.getValue()));
					break;
				}

				default:
					break;
				}
			});
			return form;
		}
		return null;
	}

	/**
	 * @author VuDH11
	 * @param BrixTrackingDTO
	 * save default setting using dto
	 */

	@Override
	public void addDefaultTrackingForm(BrixpHTrackingDTO dto) {
		// TODO Auto-generated method stub
		List<DefaultSetting> sdDefault = defaultSettingDAO
				.findByPrefix(Constants.DEFAULT_SETTING_PREFIX.TRACKING_PHBRIX_FORM);

		System.out.println("\n\n\n\n\n\n  " + dto.toString());

		if (!sdDefault.isEmpty()) {

			sdDefault.forEach(action -> {
				switch (action.getKey()) {

				case "INGREDIENT_BATCH": {
					action.setValue(dto.getIngredientId());
					break;
				}

				case "PRODUCT_LOT": {
					action.setValue(dto.getProductLotId());
					break;
				}

				case "MACHINE": {
					action.setValue(dto.getMachineId());
					break;
				}

				case "PERSON_IN_CHARGE": {
					action.setValue(dto.getPersonInChargeId());
					break;
				}

				case "VERIFIER": {
					// TODO check for Date format
					action.setValue(dto.getVerifierId());
					break;
				}

				case "PACKING": {
					action.setValue(dto.getPackingId());
					break;
				}

				case "PRESSURE": {
					action.setValue(String.valueOf(dto.getPressure()));
					break;
				}

				case "PH_PREMIX": {
					action.setValue(String.valueOf(dto.getpHPreMix()));
					break;
				}

				case "PH_MIX": {
					action.setValue(String.valueOf(dto.getpHMix()));
					break;
				}

				case "PH_HOMO": {
					action.setValue(String.valueOf(dto.getpHHomo()));
					break;
				}

				case "BRIX_PREMIX": {
					action.setValue(String.valueOf(dto.getBrixPreMix()));
					break;
				}

				case "BRIX_MIX": {
					action.setValue(String.valueOf(dto.getBrixMix()));
					break;
				}

				case "BRIX_HOMO": {
					action.setValue(String.valueOf(dto.getBrixHomo()));
					break;
				}

				default:
					break;
				}
			});
			defaultSettingDAO.saveAll(sdDefault);
		}
	}



	/**
	 * @author NguyenND6
	 */

	@Override
	public MixingMaterialDTO getMixingMaterialDefault() {
		List<DefaultSetting> sdDefault = defaultSettingDAO
				.findByPrefix(Constants.DEFAULT_SETTING_PREFIX.MIXING_MATERIAL);

		if (!sdDefault.isEmpty()) {

			MixingMaterialDTO sd = new MixingMaterialDTO();
			sd.setId(0);

			sdDefault.forEach(action -> {
				switch (action.getKey()) {

				case "TYPE_PRODUCT": {
					TypeProduct tmp = new TypeProduct();
					tmp.setId(Integer.valueOf(action.getValue()));
					sd.setTypeProduct(tmp.getId().toString());
					break;
				}

				case "DATE": {
					// TODO check for Date format
					LocalDate tmp;
					DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					tmp = LocalDate.parse(action.getValue(), dateFormatter);
					sd.setDateMixing(tmp.toString());
					break;
				}

				case "TIME": {
					// TODO check for Time format
					sd.setMixingTime(Integer.valueOf(action.getValue()));
					break;
				}

				case "PRODUCT_LOT": {
					ProductLot tmp = new ProductLot();
					tmp.setId(Integer.valueOf(action.getValue()));
					sd.setProductLot(tmp.getId().toString());
					break;
				}

				case "MACHINE": {
					Machine tmp = new Machine();
					tmp.setId(Integer.valueOf(action.getValue()));
					sd.setMachine(tmp.getId().toString());
					break;
				}

				case "ADDITIVE": {
					Additive tmp = new Additive();
					tmp.setId(Integer.valueOf(action.getValue()));
					sd.setAdditive(tmp.getId().toString());
					break;
				}

				case "INGREDIENT_BATCH": {
					IngredientBatch tmp = new IngredientBatch();
					tmp.setId(Integer.valueOf(action.getValue()));
					sd.setIngredientBatch(tmp.getId().toString());
					break;
				}

				case "EMPLOYEE": {
					Employee tmp = new Employee();
					tmp.setId(Integer.valueOf(action.getValue()));
					sd.setEmployee(tmp.getId().toString());
					break;
				}

				case "BATCH": {
					sd.setBatch(Integer.valueOf(action.getValue()).toString());
					System.out.println("-------------------------------------------------");
					System.out.println(Integer.valueOf(action.getValue()).toString());
					break;
				}

				case "AMOUNT_MATERIAL": {
					sd.setAmountMaterial(Float.valueOf(action.getValue()).toString());
					System.out.println("-------------------------------------------------");
					System.out.println(Float.valueOf(action.getValue()).toString());
					break;
				}

				case "AMOUNT_ADDITIVE": {
					sd.setAmountAdditive(Float.valueOf(action.getValue()).toString());
					break;
				}

				case "NO": {
					sd.setNo(Float.valueOf(action.getValue()).toString());
					break;
				}

				default:
					break;
				}
			});
			return sd;
		}
		return null;
	}

	@Override
	public void setMixingMaterialDefualt(MixingMaterialDTO mixingMaterial) {
		// TODO Auto-generated method stub
		List<DefaultSetting> sdDefault = defaultSettingDAO
				.findByPrefix(Constants.DEFAULT_SETTING_PREFIX.MIXING_MATERIAL);

		if (!sdDefault.isEmpty()) {

			

			sdDefault.forEach(action -> {
				switch (action.getKey()) {

				case "TYPE_PRODUCT": {
					action.setValue(mixingMaterial.getTypeProduct());
					
					break;
				}

				case "DATE": {
					// TODO check for Date format
					action.setValue(mixingMaterial.getDateMixing());
					break;
				}

				case "TIME": {
					// TODO check for Time format
					action.setValue(String.valueOf(mixingMaterial.getMixingTime()));
					System.out.println(mixingMaterial.getMixingTime());
					break;
				}

				case "PRODUCT_LOT": {
					action.setValue(mixingMaterial.getProductLot());
					break;
				}

				case "MACHINE": {
				
					action.setValue(mixingMaterial.getMachine());
					break;
				}

				case "ADDITIVE": {
					
					action.setValue(mixingMaterial.getAdditive());
					break;
				}

				case "INGREDIENT_BATCH": {
					action.setValue(mixingMaterial.getIngredientBatch());
					break;
				}

				case "EMPLOYEE": {
					action.setValue(mixingMaterial.getEmployee());
					break;
				}

				case "BATCH": {
					action.setValue(mixingMaterial.getBatch());
					break;
				}

				case "AMOUNT_MATERIAL": {
					action.setValue(mixingMaterial.getAmountMaterial());
					break;
				}

				case "AMOUNT_ADDITIVE": {
					action.setValue(mixingMaterial.getAmountAdditive());
					break;
				}

				case "NO": {
					action.setValue(mixingMaterial.getNo());
					break;
				}

				default:
					break;
				}
			});
			defaultSettingDAO.saveAll(sdDefault);
		}

	}
}