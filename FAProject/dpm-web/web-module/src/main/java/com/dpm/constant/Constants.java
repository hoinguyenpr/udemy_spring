package com.dpm.constant;

public final class Constants {

	public static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";

	// modify by DinhDN 14/01/2021 10:47AM
	// modify by DinhDN 03/02/2021 05:20PM
	public static final String DEFAULT_PAGE_NUMBER = "0";
	public static final String DEFAULT_LIST_SIZE = "10";
	public static final String LIST_SORT_ASCENDING = "ASC";
	public static final String LIST_SORT_DESCENDING = "DESC";
	public static final String STATUS_PENDING = "Pending";
	public static final String STATUS_APPROVED = "Approved";
	public static final String STATUS_REJECTED = "Rejected";
	public static final String FORMAT_DATE = "dd/MM/yyyy";
	public static final String FORMAT_TIME = "HH:mm";
	public static final int STRING_SIZE_MAX = 20;
	public static final int STRING_SIZE_MIN = 2;
	public static final String DEFAULT_PREFIX_METAL_DETECTOR = "metalDetector";

	// modify by LamPQT 19/01/2021 12:12 PM
	public static final String PAGE_SIZE = "5";
	public static final String PAGE_NUMBER = "0";

	// Add by LongVT7
	public interface STATUS {
		Integer PENDING = 0;
		Integer VERIFIED = 1;
		//modified by VuDH11
		Integer REJECTED = 2;
		Integer DELETE = 3;

	}

	// ThuanLV6 21/1/2020
	// Update by LongVT7
	public interface MACHINE_TYPE {
		String SCALE = "SCALE";
		String WEIGHT = "WEIGHT";
		String PRESSING = "PRESSING";
	}

	public interface DEPARTMENT_TYPE {
		String SCALE = "SCALE";
		String SCALE_CALIBRATION = "SCALE_CALIBRATION";
	}

//	public static final String SCALE_MACHINE_TYPE = "Scale";
//	public static final String SCALE_CALIBRATION_DEPARTMENT = "Scale calibration";
//	public static final String WEIGHT_MACHINE_TYPE = "Weight";
//	public static final String SCALE_DEPARTMENT = "scale";

	// HoiNX1 25/1/2021
	public static final String SEMI_PRODUCT = "Semi";
	public static final String FINISHED_PRODUCT = "Finished";

	/**
	 * @author LongVT7
	 */
	public interface DEFAULT_SETTING_PREFIX {
		String PRESSING_MONITOR = "PRESSING_MONITOR";

		// ThienNTN1
		String FINISHED_PRODUCT_REPORT = "fn_pd_report";
		// VuDH11
		String TRACKING_PHBRIX_FORM = "trackingForm";
		// NguyenND6
		String MIXING_MATERIAL = "MIXING_MATERIAL";
	}

	public static final String FILE_NAME_DB = "qams_backup";

	/**
	 * @author TruongDD
	 */
	public interface PREFIX {
		String SIEVE_DRYING = "SIEVE_DRYING";
	}

}
