package com.dpm.service;

import java.util.List;

import com.dpm.dto.BrixpHTrackingDTO;
import com.dpm.dto.CheckListSettingDTO;
import com.dpm.dto.MetalDetectorDTO;
import com.dpm.dto.MixingMaterialDTO;
import com.dpm.dto.ProductDTO1;
import com.dpm.entity.DefaultSetting;
import com.dpm.entity.MetalDetector;
import com.dpm.entity.PressingMornitor;
import com.dpm.entity.SieveDrying;
import com.dpm.entity.TrackingPHBx;

/**
 * 
 * @author LamPQT
 * 
 */

public interface DefaultSettingService {

	// Create by ThienNTN1
	DefaultSetting getDefaultByPrefixAndKey(String prefix, String key);

	// Create by LamPQT 03:45 PM
	/**
	 * Function get List DefaultSetting by prefix (name feature)
	 * 
	 * @param prefix
	 * 
	 */
	List<DefaultSetting> getDefaultByFeature(String prefix);

	// Create by LamPQT 03:45 PM
	/**
	 * Function convert from List<DefaultSetting> to CheckList
	 * 
	 * @param List<DefaultSetting>
	 * 
	 */
	CheckListSettingDTO convert(List<DefaultSetting> settings);

	// Create by LamPQT 03:45 PM
	/**
	 * Function get update DefaultSettings
	 * 
	 * @param CheckListSettingDTO
	 * 
	 */
	void updateCheckListSetting(CheckListSettingDTO dto);

	ProductDTO1 convertToProductDTO1(List<DefaultSetting> settings);

	// Create by ThienNTN1
	void updateFinishedProductReportSetting(ProductDTO1 dto1);

	// create by DinhDN 27/01/2021 04:18 AM
	/**
	 * Function get MetalDetectorDTO from DefaultSetting table
	 * 
	 * @param prefix
	 * @return List object DefaultSetting
	 */
	public MetalDetectorDTO getMetalDetectorDTOByPrefix(String prefix);

	// create by DinhDN 27/01/2021 02:43 AM
	/**
	 * Function create or update setting of metal detector
	 * 
	 * @param metalDetectorDTO
	 * @return Boolean. True if success, otherwise return false
	 */
	public Boolean saveOrUpdateMetalDetectorSetting(MetalDetectorDTO metalDetectorDTO);

	/**
	 * Function create or update setting of metal detector
	 * 
	 * @return Boolean. True if success, otherwise return false
	 */
	public Boolean saveOrUpdateMetalDetectorSetting(MetalDetector metalDetector);
	
	/**
	 * @author LongVT7
	 * @return PressingMonitor (Entity)
	 */
	public PressingMornitor getDefaultValuePressingMonitor();

	/**
	 * @author LongVT7
	 * @return true if update success
	 * @return false if update has any error
	 */
	public Boolean updateDefaultValuePresingMonitor(PressingMornitor pm);

	/**
	 * @author TruongDD
	 * @return true if set success
	 * @return false if set failed
	 */

	public String setDefaultSieveDrying(SieveDrying dto);

	/**
	 * @author TruongDD
	 * @return true if get success
	 * @return false if get failed
	 */

	public SieveDrying getDefaultSieveDrying();

	/**
	 * @author VuDH11
	 */
	public TrackingPHBx getDefaultTrackingForm();
	void addDefaultTrackingForm(BrixpHTrackingDTO dto);

	// create by NguyenND6 02/02/2021
	/**
	 * Function get Mixing from DefaultSetting table
	 * 
	 * @param prefix
	 * @return List object DefaultSetting
	 */
	public MixingMaterialDTO getMixingMaterialDefault();
	public void setMixingMaterialDefualt(MixingMaterialDTO mixingMaterialDTO);

	

	
	

}
