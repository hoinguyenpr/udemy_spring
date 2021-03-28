package com.dpm.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dpm.constant.Constants;
import com.dpm.dao.CheckListCustomDAO;
import com.dpm.dao.CheckListDAO;
import com.dpm.dto.CheckListFilterDTO;
import com.dpm.dto.CheckListSettingDTO;
import com.dpm.entity.CheckList;
import com.dpm.service.CheckListSevice;
import com.dpm.service.DefaultSettingService;
import com.dpm.utility.Status;

@Service
public class CheckListSeviceImpl implements CheckListSevice {
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckListSeviceImpl.class);

	@Autowired
	private CheckListDAO checkListDAO;

	@Autowired
	private DefaultSettingService settingService;

	@Autowired
	private CheckListCustomDAO checkListCustomDAO;

	/**
	 * Create a new checklist or edit checklist by save() method of JPA
	 * 
	 * @author LamPQT
	 */
	public String createCheckList(CheckList checkList) {
		try {
			checkListDAO.save(checkList);
			LOGGER.info("Create=" + "SUCCESS");
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILED";
		}

	}

	/**
	 * Get List Checklist nearest in month that user created
	 * 
	 * @author LamPQT
	 */
	public Page<CheckList> getCheckListCurrentDay(Pageable pageable) {
		Page<CheckList> list = new PageImpl<CheckList>(new ArrayList<CheckList>());
		try {
			list = checkListDAO.findInMonth(pageable);
			return list;
		} catch (Exception e) {
			return list;
		}
	}

	/**
	 * Delete Checklist by Id
	 * 
	 * @author LamPQT
	 */
	@Transactional
	@Override
	public String delete(int id) {
		try {
			checkListDAO.updateStatus(id, Status.Removed);
			return Constants.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.FAILED;
		}
	}

	/**
	 * Get Checklist By Id
	 * 
	 * @author LamPQT
	 */
	@Override
	public Optional<CheckList> get(int id) {
		try {
			Optional<CheckList> check = checkListDAO.findById(id);
			return check;
		} catch (Exception e) {
			return Optional.empty();
		}

	}

	/**
	 * Get List filter and paging
	 * 
	 * @author LamPQT
	 */
//	@Override
//	public Page<CheckList> filter(String shift, String date, int lot, Pageable pageable) {
//		Page<CheckList> list = null;
//		String[] devive = shift.split("-");
//		LocalTime fromDate = LocalTime.parse(devive[0]);
//		LocalTime toDate = LocalTime.parse(devive[1]);
//		try {
//			LocalDate localdate = LocalDate.parse(date);
//			list = checkListDAO.filter(fromDate, toDate, localdate, lot, pageable);
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return list;
//		}
//
//	}

	/**
	 * Get Sort from Value Sort
	 * 
	 * @author LamPQT
	 */
	@Override
	public Sort setSort(String sortBy) {
		Sort sort = null;
		if (sortBy.equals("DESC")) {
			sort = Sort.by("modifyDate","date","time").descending();
		} else {
			sort = Sort.by("modifyDate","date","time").ascending();
		}

		return sort;
	}

	/**
	 * Get CheckList By Status
	 * 
	 * @author LamPQT
	 */
	@Override
	public CheckList getByStatus(Status default1) {
		CheckList defaults = null;
		try {
			defaults = checkListDAO.findByStatus(default1);
			return defaults;
		} catch (Exception e) {
			// log
			return null;

		}

	}

	/**
	 * Get List to export
	 * 
	 * @author LamPQT
	 */
//	@Override
//	public List<CheckList> getReportCheckList(String shift, String date, int lot) {
//		List<CheckList> list = null;
//		String[] devive = shift.split("-");
//		LocalTime fromTime = LocalTime.parse(devive[0]);
//		LocalTime toTime = LocalTime.parse(devive[1]);
//		try {
//			LocalDate localdate = LocalDate.parse(date);
//			list = checkListDAO.report(fromTime, toTime, localdate, lot);
//			return list;
//		} catch (Exception e) {
//			return list;
//
//		}
//	}

	/**
	 * Get List filter and paging by typeProduct and All LotCode
	 * 
	 * @author LamPQT
	 */
//	@Override
//	public Page<CheckList> filter(String shift, String date, String typeProduct, Pageable pageable) {
//		Page<CheckList> list = null;
//		String[] devive = shift.split("-");
//		LocalTime fromDate = LocalTime.parse(devive[0]);
//		LocalTime toDate = LocalTime.parse(devive[1]);
//		try {
//			LocalDate localdate = LocalDate.parse(date);
//			list = checkListDAO.filterByTypeLotAll(localdate, Integer.valueOf(typeProduct), fromDate, toDate, pageable);
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return list;
//		}
//	}

	/**
	 * Get List filter and paging by All TypeProduct and All LotCode
	 * 
	 * @author LamPQT
	 */
//	@Override
//	public Page<CheckList> filter(String shift, String date, Pageable pageable) {
//		Page<CheckList> list = null;
//		String[] devive = shift.split("-");
//		LocalTime fromDate = LocalTime.parse(devive[0]);
//		LocalTime toDate = LocalTime.parse(devive[1]);
//		try {
//			LocalDate localdate = LocalDate.parse(date);
//			list = checkListDAO.filterByAll(fromDate, toDate, localdate, pageable);
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return list;
//		}
//	}

	/**
	 * get Paging report by customDao
	 * 
	 * @author LamPQT
	 */
	@Override
	public List<CheckList> report(CheckListFilterDTO filter) {
		List<CheckList> checkLists = null;
		try {
			checkLists = checkListCustomDAO.report(filter);
			return checkLists;
		} catch (Exception e) {
			checkLists = new ArrayList<CheckList>();
			return checkLists;
		}

	}

	/**
	 * get Paging filter by customDao
	 * 
	 * @author LamPQT
	 */
	@Override
	public Page<CheckList> filter(CheckListFilterDTO filter, Pageable pageable) {
		Page<CheckList> checkLists = null;
		try {
			checkLists = checkListCustomDAO.filter(filter, pageable);
			return checkLists;
		} catch (Exception e) {
			checkLists = new PageImpl<CheckList>(new ArrayList<CheckList>());
			return checkLists;
		}
	}

	@Override
	public String setting(int settingId) {
		Optional<CheckList> checkList = checkListDAO.findById(settingId);

		if (checkList.isPresent()) {
			CheckListSettingDTO dto = checkList.get().toSettingDTO();
			try {
				settingService.updateCheckListSetting(dto);

			} catch (Exception e) {
				return Constants.FAILED;
			}
			return Constants.SUCCESS;
		} else {
			return Constants.FAILED;
		}

	}

	/**
	 * get Paging all record
	 * 
	 * @author LamPQT
	 */
	@Override
	public Page<CheckList> findAll(LocalDate date, String keyword, Pageable pageable) {
		LOGGER.info("findAll");
		
		Page<CheckList> checkLists = null;
		
		

		try {
			if ("".equals(keyword) || keyword.isEmpty()) {
				checkLists = checkListDAO.findAll(date,keyword, pageable);
			} else {
				if ("không đạt".equalsIgnoreCase(keyword) || "ng".equalsIgnoreCase(keyword)) {
					
					checkLists = checkListDAO.findAllWithOkOrNG(date, false, pageable);

				} else if ("đạt".contains(keyword.toLowerCase()) || "ok".equalsIgnoreCase(keyword)) {
					checkLists = checkListDAO.findAllWithOkOrNG(date, true, pageable);
					
				} else {
					checkLists = checkListDAO.findAll(date,keyword, pageable);
				}

			}
			LOGGER.debug(checkLists.getContent().toString());
			
			return checkLists;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			checkLists = new PageImpl<CheckList>(new ArrayList<CheckList>());			
			return checkLists;
		}
	}

	@Transactional
	@Override
	public String approval(int approveId) {
		try {
			checkListDAO.updateStatus(approveId, Status.Approved);
			return Constants.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.FAILED;
		}
	}

	@Transactional
	@Override
	public String reject(int rejectId) {
		try {
			checkListDAO.updateStatus(rejectId, Status.Rejected);
			return Constants.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.FAILED;
		}
	}
	@Transactional
	@Override
	public String remove(Integer removeId) {
		try {
			checkListDAO.updateStatus(removeId, Status.Removed);
			return Constants.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.FAILED;
		}
	}
}
