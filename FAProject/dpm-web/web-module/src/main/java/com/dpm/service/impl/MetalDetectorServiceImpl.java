/**
 * 
 */
package com.dpm.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.dpm.dao.MetalDetectorCustomDAO;
import com.dpm.dao.MetalDetectorDAO;
import com.dpm.dto.MetalDetectorDTO;
import com.dpm.dto.MetalDetectorFilterDTO;
import com.dpm.entity.Department;
import com.dpm.entity.Employee;
import com.dpm.entity.MetalDetector;
import com.dpm.entity.ProductLot;
import com.dpm.entity.TypeProduct;
import com.dpm.service.DepartmentService;
import com.dpm.service.EmployeeService;
import com.dpm.service.MetalDetectorService;
import com.dpm.service.ProductLotSevice;
import com.dpm.service.TypeProductService;

/**
 * @author DinhDN
 *
 */

@Service
public class MetalDetectorServiceImpl implements MetalDetectorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetalDetectorServiceImpl.class);

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	@Autowired
	MetalDetectorDAO metalDetectorDAO;

	@Autowired
	MetalDetectorCustomDAO metalDetectorCustomDAO;

	@Autowired
	TypeProductService typeProductService;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	ProductLotSevice productLotSevice;

	@Autowired
	EmployeeService employeeService;

	/**
	 * Function create or update new MetalDetector in database by MetalDetectorDTO.
	 * 
	 * @param MetalDetectorDTO
	 * @return True if create or update success, otherwise false.
	 */
	@Override
	public Boolean saveOrUpdate(@Valid MetalDetectorDTO metalDetectorDTO) throws Exception {
		LOGGER.info("Function saveOrUpdate of class MetalDetectorService.");

		MetalDetector metalDetector = null;

		if (metalDetectorDTO.getId() == null) {
			metalDetector = new MetalDetector();
			metalDetector = updateAttributeMetalDetector(metalDetector, metalDetectorDTO);
		} else {

			try {
				// Try get MetalDetector in database with parameter is MetalDetectorId
				metalDetector = metalDetectorDAO.findById(metalDetectorDTO.getId()).get();

				// Update MetalDetector If MetalDetectorId is exists in database.
				if (metalDetector != null) {
					metalDetector = updateAttributeMetalDetector(metalDetector, metalDetectorDTO);
				}
			} catch (Exception e) {
				LOGGER.error("ERROR find MetalDetector by ID: " + e.getMessage());
				throw new Exception("ERROR find MetalDetector by ID: " + e.getMessage());
			}
		}

		// try save or update to database
		try {
			metalDetectorDAO.save(metalDetector);
			return true;
		} catch (Exception e) {
			LOGGER.error("ERROR while save new MetalDetector: " + e.getMessage());
			throw new Exception("ERROR while save new MetalDetector: " + e.getMessage());
		}
	}

	/**
	 * Function create or update new MetalDetector in database by MetalDetector.
	 * 
	 * @param metalDetector
	 * @return True if create or update success, otherwise false.
	 */
	@Override
	public Boolean saveOrUpdate(MetalDetector metalDetector) {
		try {
			metalDetectorDAO.save(metalDetector);
		} catch (Exception e) {
			LOGGER.error("ERROR saveOrUpdate MetalDetector: " + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Function update metal detector if each attribute is exits in database.
	 * 
	 * @param metalDetectorUpdate
	 * @param metalDetectorDTO
	 * @return MetalDetector was update.
	 */
	private MetalDetector updateAttributeMetalDetector(MetalDetector metalDetectorUpdate,
			@Valid MetalDetectorDTO metalDetectorDTO) {

		// Try get Department by DepartmentCode and update MetalDetector
		ProductLot productLot = productLotSevice.findByLotCode(metalDetectorDTO.getLotNo()).get();
		if (productLot != null) {
			// Try get typeProduct by typeProductId and update productLot
			TypeProduct typeProduct = typeProductService
					.getTypeProductByTypeProductCode(metalDetectorDTO.getTypeProductCode());
			productLot.setTypeProduct(typeProduct);

			metalDetectorUpdate.setProductLot(productLot);
		}

		// Try get Department by DepartmentCode and update MetalDetector
		Department department = departmentService.getDepartmentById(metalDetectorDTO.getDepartmentCode());
		if (department.getDepartmentCode() != null) {
			metalDetectorUpdate.setDepartment(department);
		}

		// Try get Employee by username and update MetalDetector
		Employee inspector = employeeService.getEmployeeByUserName(metalDetectorDTO.getUserName());
		if (inspector.getId() != null) {
			metalDetectorUpdate.setInspector(inspector);
		}
		metalDetectorUpdate.setCheckingDate(metalDetectorDTO.getDateCheck());
		metalDetectorUpdate.setCheckingTime(metalDetectorDTO.getTimeCheck());
		metalDetectorUpdate.setFrequency(metalDetectorDTO.getFrequency());
		metalDetectorUpdate.setCheckingSampleFe(metalDetectorDTO.getOptionsRadiosFe());
		metalDetectorUpdate.setCheckingSampleNonFe(metalDetectorDTO.getOptionsRadiosNonfe());
		metalDetectorUpdate.setCheckingSampleSus(metalDetectorDTO.getOptionsRadiosSus());
		metalDetectorUpdate.setCorrectiveAction(metalDetectorDTO.getCorrectiveAction());
		metalDetectorUpdate.setStatus(metalDetectorDTO.getStatus());
		return metalDetectorUpdate;

	}

	/**
	 * Function get all MetalDetector from database.
	 * 
	 * @param pageable
	 * @return List object MetalDetector
	 */
	@Override
	public Page<MetalDetector> getAllMetalDetector(Pageable pageable) {

		LOGGER.info("Function getAllMetalDetector of class MetalDetectorService.");

		// Create new list metalDetectors
		List<MetalDetector> metalDetectors = new ArrayList<MetalDetector>();
		Page<MetalDetector> pageMetalDetector = new PageImpl<>(metalDetectors);

		// Try get list metalDetector from database
		try {
			pageMetalDetector = metalDetectorDAO.findAllMetalDetectorAvailable(pageable);
		} catch (Exception e) {
			LOGGER.error("ERROR when get all Metal Detector: " + e.getMessage());
		}

		return pageMetalDetector;
	}

	@Override
	public Page<MetalDetector> getAllMetalDetectorSortByStatus(Pageable pageable) {

		LOGGER.info("Function getAllMetalDetectorSortByStatus of class MetalDetectorService.");

		// Create new list metalDetectors
		Page<MetalDetector> pageMetalDetector = new PageImpl<MetalDetector>(new ArrayList<MetalDetector>());

		// Try get list metalDetector from database
		try {
			pageMetalDetector = metalDetectorDAO.getAllSortByStatus(pageable);
		} catch (Exception e) {
			LOGGER.error("ERROR when get all Metal Detector sort by status: " + e.getMessage());
		}

		return pageMetalDetector;
	}

	/**
	 * Function delete MetalDetector by metalDetectorId
	 * 
	 * @param metalDetectorId
	 * @return Boolean. True if delete success, otherwise fail
	 */
	@Override
	public Boolean deleteMetalDetectorById(Integer metalDetectorId) {
		try {
			metalDetectorDAO.deleteById(metalDetectorId);
		} catch (Exception e) {
			LOGGER.error("ERROR when delete Metal Detector by id: " + e.getMessage());
		}
		return true;
	}

	/**
	 * Function get MetalDetector by metalDetectorId
	 * 
	 * @param metalDetectorId
	 * @return Object MetalDetector
	 */
	@Override
	public MetalDetector getMetalDetectorById(Integer metalDetectorId) {
		LOGGER.info("Function getMetalDetectorById of class MetalDetectorService.");
		MetalDetector metalDetector = new MetalDetector();
		try {
			metalDetector = metalDetectorDAO.findById(metalDetectorId).get();
		} catch (Exception e) {
			LOGGER.error("ERROR get MetalDetector by metalDetectorId: " + e.getMessage());
		}
		return metalDetector;
	}

	/**
	 * Function find data by user filter
	 * 
	 * @param metalDetectorFilterDTO
	 * @param pageable
	 * @return List object MetalDetector
	 */
	@Override
	public Page<MetalDetector> filterAllByParameter(MetalDetectorFilterDTO metalDetectorFilterDTO, Pageable pageable) {
		Page<MetalDetector> metalDetectors = new PageImpl<MetalDetector>(new ArrayList<MetalDetector>());

		try {
			metalDetectors = metalDetectorCustomDAO.filterAllByParameter(metalDetectorFilterDTO, pageable);
		} catch (Exception e) {
			LOGGER.error("ERROR filter All By Parameter metalDetectorFilterDTO: " + e.getMessage());
		}
		return metalDetectors;
	}

	/**
	 * Function get list metal detector with filter and export to excell file.
	 * 
	 * @param response
	 * @param metalDetectorFilterDTO
	 * @return Boolean.
	 */
	@Override
	public Boolean exportExcel(HttpServletResponse response, @Valid MetalDetectorFilterDTO metalDetectorFilterDTO) {
		List<MetalDetector> metalDetectors = metalDetectorCustomDAO.getListToExportByParameter(metalDetectorFilterDTO);

		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Metal detector report");

		int rowNumber = 1;

		rowNumber = writeLogo(rowNumber);
		rowNumber = writeTitle(rowNumber);
		rowNumber = writeHeaderLine(rowNumber);
		rowNumber = writeDataLines(metalDetectors, rowNumber);
		writeFooter(rowNumber);

		// Write report to file
		try (ServletOutputStream servletOutputStream = response.getOutputStream()) {
			workbook.write(servletOutputStream);
			return true;
		} catch (IOException e) {
			LOGGER.error("Error write file: " + e.getMessage());
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					LOGGER.error("Error close workbook: " + e.getMessage());
				}
			}
		}
		return false;
	}

	/**
	 * Function create data for title form report to excell
	 * 
	 * @param rowNumber
	 * @return rowNumber
	 */
	private int writeTitle(int rowNumber) {
		int columnNumber = 0;

		CellStyle cellStyle = createCellStyle(true, "Times New Roman", 16, false, true);

		Row row = sheet.createRow(rowNumber);
		row.setHeight((short) 500);
		mergCell(rowNumber, rowNumber++, columnNumber, (columnNumber + 11));
		createCellValue(row, columnNumber, "BIỂU MẪU KIỂM TRA MÁY RÀ KIM LOẠI", cellStyle);

		row = sheet.createRow(rowNumber);
		row.setHeight((short) 500);
		mergCell(rowNumber, rowNumber++, columnNumber, (columnNumber + 11));
		createCellValue(row, columnNumber, "METAL DETECTOR CHECKING FORM", cellStyle);

		rowNumber++;
		return rowNumber;
	}

	/**
	 * Function create data for header line form report to excell
	 * 
	 * @param workbook
	 * @param sheet
	 * @param rowNumber
	 * @return rowNumber
	 */
	private int writeHeaderLine(int rowNumber) {
		int columnNumber = 0;

		CellStyle cellStyle = createCellStyle(true, "Times New Roman", 12, true, true);

		Row row = sheet.createRow(rowNumber);
		for (int i = 0; i < 12; i++) {
			row.createCell(i).setCellStyle(cellStyle);
			;
			if (i < 5) {
				mergCell(rowNumber, rowNumber + 1, i, i);
			} else if (i == 5) {
				mergCell(rowNumber, rowNumber, i, i + 2);
			} else if (i >= 8) {
				mergCell(rowNumber, rowNumber + 1, i, i);
			}
		}
		createCellValue(row, columnNumber++, "#", cellStyle);
		createCellValue(row, columnNumber++, "Ngày kiểm tra \n Checking date", cellStyle);
		createCellValue(row, columnNumber++, "Mã lô \n Lot No.", cellStyle);
		createCellValue(row, columnNumber++, "Loại sản phẩm \n Type of product.", cellStyle);
		createCellValue(row, columnNumber++, "Giờ kiểm tra \n Time", cellStyle);

		int startColumnHeaderRowNext = columnNumber;
		createCellValue(row, columnNumber++, "Mẫu kiểm tra \n Sensory checking sample", cellStyle);

		columnNumber += 2;
		createCellValue(row, columnNumber++, "Tần xuất \n Frequency", cellStyle);
		createCellValue(row, columnNumber++, "HĐKP \n Corrective action", cellStyle);
		createCellValue(row, columnNumber++, "Người kiểm tra \n Inspector", cellStyle);
		createCellValue(row, columnNumber++, "Mã phòng ban \n Dept Code", cellStyle);

		rowNumber++;
		row = sheet.createRow(rowNumber++);
		for (int i = 0; i < 12; i++) {
			row.createCell(i).setCellStyle(cellStyle);
			;
		}
		createCellValue(row, startColumnHeaderRowNext++, "Fe \n (3.0mm)" + "", cellStyle);
		createCellValue(row, startColumnHeaderRowNext++, "Non-Fe \n ( 3.5mm)", cellStyle);
		createCellValue(row, startColumnHeaderRowNext++, "Sus \n (3.5 mm)", cellStyle);
		return rowNumber;
	}

	/**
	 * Function create data for logo form report to excell
	 * 
	 * @param rowNumber
	 * @return rowNumber
	 */
	private int writeLogo(int rowNumber) {
		int columnNumber = 0;

		CellStyle cellStyle = createCellStyle(true, "Times New Roman", 14, false, true);

		Row row = sheet.createRow(rowNumber);
		row.setHeight((short) 400);
		mergCell(rowNumber, rowNumber++, columnNumber, (columnNumber + 3));
		createCellValue(row, columnNumber, "CÔNG TY TNHH ABC", cellStyle);

		row = sheet.createRow(rowNumber);
		row.setHeight((short) 400);
		mergCell(rowNumber, rowNumber++, columnNumber, (columnNumber + 3));
		createCellValue(row, columnNumber, "ABC CO., LTD", cellStyle);

		rowNumber++;
		return rowNumber;
	}

	/**
	 * Function merg cell
	 * 
	 * @param rowNumber
	 * @param rowNumber2
	 * @param columnNumber
	 * @param columnNumber2
	 */
	private void mergCell(int rowNumber, int rowNumber2, int columnNumber, int columnNumber2) {
		sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber2, columnNumber, columnNumber2));
	}

	/**
	 * Function set fill color style Grey for cell
	 * 
	 * @param cellStyle
	 * @return CellStyle
	 */
	private CellStyle setCellFillColorGrey(CellStyle cellStyle) {
		cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return cellStyle;
	}

	/**
	 * Function create data for body form report to excell
	 * 
	 * @param metalDetectors
	 * @param rowNumber
	 * @return rowNumber
	 */
	private int writeDataLines(List<MetalDetector> metalDetectors, int rowNumber) {

		// set style for cell
		CellStyle cellStyleWhite = createCellStyle(false, "Times New Roman", 12, true, true);
		CellStyle cellStyleGrey = createCellStyle(false, "Times New Roman", 12, true, true);
		cellStyleGrey = setCellFillColorGrey(cellStyleGrey);

		rowNumber--;
		for (MetalDetector metalDetector : metalDetectors) {
			rowNumber++;
			int columnNumber = 0;

			CellStyle cellStyle = (rowNumber % 2) == 0 ? cellStyleWhite : cellStyleGrey;
			Row row = sheet.createRow(rowNumber);
			createCellValue(row, columnNumber++, rowNumber - 8, cellStyle);
			createCellValue(row, columnNumber++, metalDetector.getCheckingDate().toString(), cellStyle);
			createCellValue(row, columnNumber++, metalDetector.getProductLot().getLotCode(), cellStyle);
			createCellValue(row, columnNumber++, metalDetector.getProductLot().getTypeProduct().getTypeProductCode(),
					cellStyle);
			createCellValue(row, columnNumber++, metalDetector.getCheckingTime().toString(), cellStyle);
			createCellValue(row, columnNumber++, metalDetector.getCheckingSampleFe() ? "Đạt" : "Không đạt", cellStyle);
			createCellValue(row, columnNumber++, metalDetector.getCheckingSampleNonFe() ? "Đạt" : "Không đạt",
					cellStyle);
			createCellValue(row, columnNumber++, metalDetector.getCheckingSampleSus() ? "Đạt" : "Không đạt", cellStyle);
			createCellValue(row, columnNumber++, metalDetector.getFrequency(), cellStyle);
			createCellValue(row, columnNumber++, metalDetector.getCorrectiveAction(), cellStyle);
			createCellValue(row, columnNumber++, metalDetector.getInspector().getUsername(), cellStyle);
			createCellValue(row, columnNumber++, metalDetector.getDepartment().getDepartmentCode(), cellStyle);

		}
		return rowNumber;
	}

	/**
	 * Function create data for footer form report to excell
	 * 
	 * @param rowNumber
	 */
	private void writeFooter(int rowNumber) {

		// set style for cell
		CellStyle cellStyle = createCellStyle(true, "Times New Roman", 14, false, true);

		rowNumber++;
		Row row = sheet.createRow(++rowNumber);
		row.setHeight((short) 400);

		int columnNumber = 0;
		mergCell(rowNumber, rowNumber, columnNumber, columnNumber + 2);
		createCellValue(row, columnNumber, " GMP-BM07A, PB:04", cellStyle);

		columnNumber = 8;
		mergCell(rowNumber, rowNumber, columnNumber, columnNumber + 2);
		createCellValue(row, columnNumber, "Người thẩm tra / Verifier", cellStyle);

		for (int i = 0; i < 5; i++) {
			row = sheet.createRow(++rowNumber);
		}
	}

	/**
	 * Fuction create cell style.
	 * 
	 * @param bold
	 * @param fontName
	 * @param fontHeight
	 * @param border
	 * @param alignCenter
	 * @return CellStyle
	 */
	private CellStyle createCellStyle(boolean bold, String fontName, Integer fontHeight, boolean border,
			Boolean alignCenter) {
		CellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(bold);
		font.setFontName(fontName);
		font.setFontHeight(fontHeight);
		if (border) {
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setBorderLeft(BorderStyle.THIN);
		}
		if (alignCenter) {
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		}
		cellStyle.setWrapText(true);
		cellStyle.setFont(font);

		return cellStyle;

	}
//	sheet.autoSizeColumn(columnNumber);

	/**
	 * Function set cell value.
	 * 
	 * @param row
	 * @param columnNumber
	 * @param value
	 * @return Cell
	 */
	private Cell createCellValue(Row row, int columnNumber, Object value, CellStyle cellStyle) {
		sheet.autoSizeColumn(columnNumber, true);
		Cell cell = row.createCell(columnNumber);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(cellStyle);
		return cell;
	}

	/**
	 * Function validate dateTime in form report
	 * 
	 * @param objectErrors
	 * @param date
	 * @param fieldName
	 * @return List ObjectError
	 */
	@Override
	public List<ObjectError> validateDateTime(List<ObjectError> objectErrors, String date, String fieldName) {

		if (!date.matches("^\\d{2}[/]\\d{2}[/]\\d{4}")) {
			FieldError fieldError = new FieldError("MetalDetectorFilterDTO", fieldName, fieldName + " format incorect");
			objectErrors.add(fieldError);
			LOGGER.error("ERROR while validate " + fieldName + ": " + date);
		}
		return objectErrors;
	}

	/**
	 * Function validate feild in form report
	 * 
	 * @param objectErrors
	 * @param typeProducts
	 * @param param
	 * @param fieldName
	 * @return List ObjectError
	 */
	@Override
	public List<ObjectError> validateOject(List<ObjectError> objectErrors, List<Object> Objects, String param,
			String fieldName) {

		if (!param.equalsIgnoreCase("all")) {
			if (!Objects.isEmpty()) {
				List<Object> listObjectFound = findObjectByParam(Objects, param);
				if (listObjectFound.isEmpty()) {
					FieldError fieldError = new FieldError("MetalDetectorFilterDTO", fieldName,
							fieldName + " not found");
					objectErrors.add(fieldError);
					LOGGER.info("ERROR while validate " + fieldName + ": " + param);
				}
			}
		}
		return objectErrors;
	}

	/**
	 * Function find object by param in list Objects
	 * 
	 * @param Objects
	 * @param param
	 * @return List Object
	 */
	private List<Object> findObjectByParam(List<Object> Objects, String param) {
		List<Object> listObjectFound = new ArrayList<Object>();
		if (Objects.get(0) instanceof Department) {
			// Find departmentCode in list departments
			listObjectFound = Objects.stream().filter(p -> ((Department) p).getDepartmentCode().equalsIgnoreCase(param))
					.collect(Collectors.toList());
		}
		if (Objects.get(0) instanceof TypeProduct) {
			// Find typeProductCode in list Objects
			listObjectFound = Objects.stream()
					.filter(p -> ((TypeProduct) p).getTypeProductCode().equalsIgnoreCase(param))
					.collect(Collectors.toList());
		}
		if (Objects.get(0) instanceof ProductLot) {
			// Find typeProductCode in list Objects
			listObjectFound = Objects.stream().filter(p -> ((ProductLot) p).getLotCode().equalsIgnoreCase(param))
					.collect(Collectors.toList());
		}
		if (Objects.get(0) instanceof Employee) {
			// Find typeProductCode in list Objects
			listObjectFound = Objects.stream().filter(p -> ((Employee) p).getUsername().equalsIgnoreCase(param))
					.collect(Collectors.toList());
		}
		return listObjectFound;
	}

	/**
	 * Function set object MetalDetectorDTO attribute from object metalDetector
	 * 
	 * @param metalDetector
	 * @param metalDetectorDTO
	 * @return object MetalDetectorDTO
	 */
	@Override
	public MetalDetectorDTO setAttributeMetalDetectorDTO(MetalDetector metalDetector,
			MetalDetectorDTO metalDetectorDTO) {
		metalDetectorDTO.setId(metalDetector.getId());
		metalDetectorDTO.setDepartmentCode(metalDetector.getDepartment().getDepartmentCode());
		metalDetectorDTO.setTypeProductCode(metalDetector.getProductLot().getTypeProduct().getTypeProductCode());
		metalDetectorDTO.setLotNo(metalDetector.getProductLot().getLotCode());
		metalDetectorDTO.setDateCheck(metalDetector.getCheckingDate());
		metalDetectorDTO.setTimeCheck(metalDetector.getCheckingTime());
		metalDetectorDTO.setFrequency(metalDetector.getFrequency());
		metalDetectorDTO.setOptionsRadiosFe(metalDetector.getCheckingSampleFe());
		metalDetectorDTO.setOptionsRadiosNonfe(metalDetector.getCheckingSampleNonFe());
		metalDetectorDTO.setOptionsRadiosSus(metalDetector.getCheckingSampleSus());
		metalDetectorDTO.setCorrectiveAction(metalDetector.getCorrectiveAction());
		metalDetectorDTO.setUserName(metalDetector.getInspector().getUsername());

		return metalDetectorDTO;
	}

	/**
	 * Function search MetalDetector from database with parameter.
	 * 
	 * @param startDate
	 * @param endDateO
	 * @param pageable
	 * @return object MetalDetect
	 */
	@Override
	public Page<MetalDetector> searchMetalDetector(Date startDate, Date endDate, Pageable pageable) {
		Page<MetalDetector> metalDetectors = new PageImpl<MetalDetector>(new ArrayList<MetalDetector>());
		try {
			metalDetectors = metalDetectorDAO.findMetalDetectorByKeyword(startDate, endDate, pageable);
		} catch (Exception e) {
			LOGGER.error("ERROR get MetalDetector by date range: " + e.getMessage());
		}
		return metalDetectors;
	}

	/**
	 * Function search MetalDetector from database with parameter.
	 * 
	 * @param startDate
	 * @param endDate
	 * @param search
	 * @param pageable
	 * @return object MetalDetect
	 */
	@Override
	public Page<MetalDetector> searchMetalDetector(Date startDate, Date endDate, String keyword, Pageable pageable) {
		Page<MetalDetector> metalDetectors = new PageImpl<MetalDetector>(new ArrayList<MetalDetector>());
		try {
			metalDetectors = metalDetectorDAO.findMetalDetectorByKeyword(startDate, endDate, keyword, pageable);
		} catch (Exception e) {
			LOGGER.error("ERROR get MetalDetector by date range and keyword: " + e.getMessage());
		}
		return metalDetectors;
	}

}