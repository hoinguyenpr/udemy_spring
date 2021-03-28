package com.dpm.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dpm.constant.Constants;
import com.dpm.dao.MachineDAO;
import com.dpm.dao.PackingDAO;
import com.dpm.dao.PackingFormDAO;
import com.dpm.dao.ProductLotDAO;
import com.dpm.dao.TypeOfPackingDAO;
import com.dpm.dao.TypeProductDAO;
import com.dpm.dto.PackingFormDTO;
import com.dpm.entity.Employee;
import com.dpm.entity.FinishedProduct;
import com.dpm.entity.Machine;
import com.dpm.entity.Packing;
import com.dpm.entity.PackingForm;
import com.dpm.entity.ProductLot;
import com.dpm.entity.TypeOfPacking;
import com.dpm.entity.TypeProduct;
import com.dpm.service.EmployeeService;
import com.dpm.service.FinishedProductService;
import com.dpm.service.PackingFormService;
import com.dpm.service.PackingService;
import com.dpm.service.ProductLotSevice;
import com.dpm.service.TypeOfPackingService;
import com.dpm.service.TypeProductService;
import com.dpm.utility.Status;

/**
 * Author: HoiNX1 Modified date: 14/01/2020 11:37 AM Modifier:
 */

@Service
public class PackingFormServiceImpl implements PackingFormService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PackingFormServiceImpl.class);

	@Autowired
	PackingFormDAO packingFormDAO;

	@Autowired
	PackingFormService packingFormService;

	@Autowired
	TypeOfPackingService typeOfPackingService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	PackingService packingService;

	@Autowired
	TypeProductService typeProductService;

	@Autowired
	FinishedProductService finishedProductService;

	@Autowired
	ProductLotSevice productLotSevice;

	@Autowired
	ProductLotDAO productLotDAO;

	@Autowired
	PackingDAO packingDAO;

	@Autowired
	TypeOfPackingDAO typeOfPackingDAO;

	@Autowired
	TypeProductDAO typeProductDAO;

	@Autowired
	MachineDAO machineDAO;

	/**
	 * Function get all MetalDetector from database.
	 * 
	 * @param pageable
	 * @return List object MetalDetector
	 */

	@Override
	public Page<PackingForm> getAllPackingForm(Pageable pageable) {

		List<PackingForm> listPackingForm = new ArrayList<PackingForm>();
		Page<PackingForm> pagePackingForm = new PageImpl<PackingForm>(listPackingForm);

		pagePackingForm = packingFormDAO.findAll(pageable);

		return pagePackingForm;
	}

	@Override
	public Page<PackingForm> searchPackingForm(Pageable pageable, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deletePackingFormByID(Integer packingFormId) {
		try {
			packingFormDAO.deleteById(packingFormId);
		} catch (Exception e) {
			LOGGER.error("ERROR when delete Metal Detector by id: " + e.getMessage());
		}
		return true;
	}

	@Override
	public PackingForm getPackingFormById(Integer id) {
		// TODO Auto-generated method stub
		return packingFormDAO.getOne(id);
	}

	@Override
	public PackingForm save(PackingForm packingForm) {

		return packingFormDAO.save(packingForm);
	}

	@Override
	public List<PackingForm> getAllPackingForm() {
		// TODO Auto-generated method stub
		return packingFormDAO.findAll();
	}

	private PackingForm updateAttributePackingForm(PackingForm packingFormUpdate, PackingFormDTO packingFormDTO) {

		PackingForm packingForm = new PackingForm();
		
		
		Employee createman = employeeService.getEmployeeById(packingFormDTO.getCreateManID());
		if (createman.getId() != null) {
			packingForm.setCreateman(createman);	
		}

		Employee foreman = employeeService.getEmployeeById(packingFormDTO.getForeManID());
		if (foreman.getId() != null) {
			packingForm.setForeman(foreman);
		}

		Employee inchargeman = employeeService.getEmployeeById(packingFormDTO.getInChargeManID());
		if (inchargeman.getId() != null) {
			packingForm.setPersonInCharge(inchargeman);
		}

		ProductLot finish_lot_code = productLotDAO.getProductLotByLotCode(packingFormDTO.getFinished_Product_Lot());
		ProductLot semi_lot_code = productLotDAO.getProductLotByLotCode(packingFormDTO.getSemi_Product_Lot());

		FinishedProduct semiProduct = finishedProductService.getProductByProductCodeAndType(semi_lot_code,
				Constants.SEMI_PRODUCT);
		if (semiProduct.getId() != null) {
			packingForm.setSemiProduct(semiProduct);
		}

		FinishedProduct finishedProduct = finishedProductService.getProductByProductCodeAndType(finish_lot_code,
				Constants.FINISHED_PRODUCT);
		if (finishedProduct.getId() != null) {
			packingForm.setFinishedProduct(finishedProduct);
		}

		Packing packing = packingDAO.getOne(packingFormDTO.getPackingID());
		if (packing.getId() != null) {
			packingForm.setPacking(packing);
		}

		TypeOfPacking typeOfPacking = typeOfPackingDAO.getOne(packingFormDTO.getTypeOfPackingID());
		if (typeOfPacking.getId() != null) {
			packingForm.setTypeofpacking(typeOfPacking);
		}

		TypeProduct typeProduct = typeProductDAO.getOne(packingFormDTO.getTypeProductID());
		if (typeProduct.getId() != null) {
			packingForm.setTypeProduct(typeProduct);
		}

		Machine machine = machineDAO.getOne(packingFormDTO.getMachineID());
		if (machine.getId() != null) {
			packingForm.setMachine(machine);
		}	
		
		DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
		String  datenew = packingFormDTO.getPackingDate().replace("/", "-");
		LocalDate date = LocalDate.parse(datenew, formatterDate);
		LocalTime time = LocalTime.parse(packingFormDTO.getTimePacking(), formatterTime);
		
		packingForm.setPackingDate(date);
		packingForm.setTime(time);

		packingForm.setQuantity(packingFormDTO.getQuanity());
		packingForm.setShift(packingFormDTO.getShift());
		packingForm.setPackingQuanity(packingFormDTO.getPackingQuanity());
		packingForm.setSttNo(packingFormDTO.getSttNo());
		packingForm.setStatus(Status.Pending);
		
		
		return packingForm;

	}

	@Override
	public Boolean saveOrUpdate(PackingFormDTO packingFormDTO) throws Exception {
		
		PackingForm packingForm = null;
		
		if(packingFormDTO.getId() == null) {
			System.out.println("test: null");
			packingForm = new PackingForm();
			packingForm = updateAttributePackingForm(packingForm, packingFormDTO);
			
		}else {
			try {
				packingForm = packingFormDAO.findById(packingFormDTO.getId()).get();
				if(packingForm != null) {
					packingForm = updateAttributePackingForm(packingForm, packingFormDTO);
				}
			} catch (Exception e) {
				LOGGER.error("ERROR find PackingForm by ID: " + e.getMessage());
				throw new Exception("ERROR find PackingForm by ID: " + e.getMessage());
			}
		}
		
		try {
			System.out.println(packingForm);
			packingFormDAO.save(packingForm);
			return true;
		}catch (Exception e) {
			LOGGER.error("ERROR while save new Packing Form: " + e.getMessage());
			throw new Exception("ERROR while save new Packing Form: " + e.getMessage());
		}
	}
	
}
