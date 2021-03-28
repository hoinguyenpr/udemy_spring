package com.dpm.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dpm.constant.Constants;
import com.dpm.dao.MixingMaterialDAO;
import com.dpm.dto.MixingMaterialDTO;
import com.dpm.entity.Additive;
import com.dpm.entity.Employee;
import com.dpm.entity.IngredientBatch;
import com.dpm.entity.Machine;
import com.dpm.entity.MixingMaterial;
import com.dpm.entity.ProductLot;
import com.dpm.entity.TypeProduct;
import com.dpm.service.AdditiveService;
import com.dpm.service.EmployeeService;
import com.dpm.service.IngredientBatchService;
import com.dpm.service.MachineService;
import com.dpm.service.MixingMaterialService;
import com.dpm.service.ProductLotSevice;
import com.dpm.service.TypeProductService;
import com.dpm.utility.LocalDateUtil;
import com.dpm.utility.MixingMaterialMapper;

/**
 * Update: Nguyennd6 Created date: 01/12/2020 16:00 AM
 **/
@Service
public class MixingMaterialServiceImpl implements MixingMaterialService {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	@Autowired
	private MixingMaterialDAO mixingMaterialDAO;
	
	private MachineService machineService;
	private TypeProductService typeProductService;
	private IngredientBatchService ingreeBatchService;
	private ProductLotSevice productLotService;
	private AdditiveService additiveService;
	private EmployeeService employeeService;
	
	
	@Autowired
	public MixingMaterialServiceImpl(MachineService machineService, TypeProductService typeProductService,
			IngredientBatchService ingreeBatchService, ProductLotSevice productLotService,
			AdditiveService additiveService, EmployeeService employeeService) {
		this.machineService = machineService;
		this.typeProductService = typeProductService;
		this.ingreeBatchService = ingreeBatchService;
		this.productLotService = productLotService;
		this.additiveService = additiveService;
		this.employeeService = employeeService;
	}

	@Override
	public String createNewMixing(MixingMaterialDTO mxMaterialDTO) {

		MixingMaterial mixingMaterial = new MixingMaterial();
		mixingMaterial =this.mapDTOToMixing(mxMaterialDTO);
		mixingMaterialDAO.save(mixingMaterial);
		return Constants.SUCCESS;

	}

	@Override
	public String deleteMixingById(int id) {
		Optional<MixingMaterial> mixOptional = mixingMaterialDAO.findById(id);

		if (mixOptional.isPresent()) {
			mixOptional.get().setDeleted(true);
			mixingMaterialDAO.save(mixOptional.get());
			System.out.println(mixOptional.get());
			return Constants.SUCCESS;
		} else {
			return Constants.FAILED;
		}
	}

	@Override
	public Optional<MixingMaterial> getMixingById(int id) {

		return mixingMaterialDAO.findById(id);
	}

	@Override
	public Optional<MixingMaterialDTO> getMixingDTOById(int id) {
		// TODO Auto-generated method stub

		Optional<MixingMaterial> mixOptional = mixingMaterialDAO.findById(id);

		return mixOptional.map(MixingMaterialMapper::mapMixingToDTO);

	}

	@Override
	public String updateMixing(MixingMaterialDTO mxMaterialDTO) {
		// TODO Auto-generated method stub
		Optional<MixingMaterial> mixOptional = mixingMaterialDAO
				.findById(mxMaterialDTO.getId());

		if (mixOptional.isPresent()) {
			this.mapDTOToMixing(mxMaterialDTO);
			mixingMaterialDAO.save(mixOptional.get());
			return Constants.SUCCESS;
		}
		return null;
	}

	@Override
	public String approve(int id) {
		Optional<MixingMaterial> mixOptional = mixingMaterialDAO.findById(id);

		if (mixOptional.isPresent()) {
			mixOptional.get().setStatus(true);
			mixingMaterialDAO.save(mixOptional.get());
			System.out.println(mixOptional.get());
			return Constants.SUCCESS;
		} else {
			return Constants.FAILED;
		}
	}

	@Override
	public Page<MixingMaterialDTO> findPaginated(Pageable pageable) {

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<MixingMaterialDTO> list;

		List<MixingMaterialDTO> mixDTOList = mixingMaterialDAO.findAll().stream()
				.map(MixingMaterialMapper::mapMixingToDTO).collect(Collectors.toList());
		if (mixDTOList.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, mixDTOList.size());
			list = mixDTOList.subList(startItem, toIndex);
		}

		Page<MixingMaterialDTO> mixingPage = new PageImpl<MixingMaterialDTO>(list,
				PageRequest.of(currentPage, pageSize), mixDTOList.size());
		return mixingPage;
	}

	@Override
	public Page<MixingMaterialDTO> searchMixing(Pageable pageable, String keyword) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<MixingMaterialDTO> list;
		List<MixingMaterialDTO> mixDTOList = mixingMaterialDAO
				.findByIngredientBatchNameOrEmployeeName(keyword).stream()
				.map(MixingMaterialMapper::mapMixingToDTO).collect(Collectors.toList());
		if (mixDTOList.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, mixDTOList.size());
			list = mixDTOList.subList(startItem, toIndex);
		}
		Page<MixingMaterialDTO> mixingPage = new PageImpl<MixingMaterialDTO>(list,
				PageRequest.of(currentPage, pageSize), mixDTOList.size());
		return mixingPage;

	}

	@Override
	public Page<MixingMaterialDTO> filterMixing(Pageable pageable, String type,
			String date, String machine) {

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<MixingMaterialDTO> list;
		List<MixingMaterialDTO> mixDTOList = mixingMaterialDAO
				.findByTypeProductAndDateAndMachine(type, date, machine).stream()
				.map(MixingMaterialMapper::mapMixingToDTO).collect(Collectors.toList());
		if (mixDTOList.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, mixDTOList.size());
			list = mixDTOList.subList(startItem, toIndex);
		}
		Page<MixingMaterialDTO> mixingPage = new PageImpl<MixingMaterialDTO>(list,
				PageRequest.of(currentPage, pageSize), mixDTOList.size());
		return mixingPage;

	}

	@Override
	public List<MixingMaterialDTO> findByFilter(String type, String date,
			String machine) {
		return mixingMaterialDAO.findByTypeProductAndDateAndMachine(type, date, machine)
				.stream().map(MixingMaterialMapper::mapMixingToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public void exportMixing(HttpServletResponse response, String type, String date,
			String machine) {
		List<MixingMaterialDTO> listDTO = this.findByFilter(type, date, machine);
		workbook = new XSSFWorkbook();
		try {

			CellStyle style1 = createStyle("Times New Roman", 16, true,
					HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.CENTER, false,
					true);

			CellStyle style2 = createStyle("Times New Roman", 11, false,
					HorizontalAlignment.LEFT, VerticalAlignment.CENTER, false, true);
			sheet = workbook.createSheet("Mixing Report");

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 2));
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 5));
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 8));

			createCell(sheet.createRow(0), 0, "BÁO CÁO TRỘN / MIXING REPORT", style1);

			Row row1 = sheet.createRow(1);
			createCell(row1, 0, "Loại/ Type: " + type, style2);
			createCell(row1, 3, "Ngày thực hiện/ Date: " + date, style2);
			createCell(row1, 6, "Máy SP/ Machine: " + machine, style2);
			writeHeaderLine();
			writeDataLines(listDTO);
			this.setColumnSize();
			ServletOutputStream stream = response.getOutputStream();

			workbook.write(stream);
			workbook.close();

			stream.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void writeHeaderLine() {
		Row row2 = sheet.createRow(2);
		Row row3 = sheet.createRow(3);

		CellStyle style = createStyle("Times New Roman", 10, true,
				HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, true);
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 5));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 6, 6));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 7, 7));
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 8, 8));
		row3.setHeightInPoints((5 * sheet.getDefaultRowHeightInPoints()));
		createCell(row2, 0, "Số mẻ Batch Number ", style);
		createCell(row2, 1, "Nguyên liệu/ Material", style);
		createCell(row3, 1, "Số lượng (L) Quantity", style);
		createCell(row3, 2, "Mã lô Batch Code", style);
		createCell(row2, 3, "Đường phụ gia(Kg) Suggar Additive", style);
		createCell(row3, 3, "Số lượng/ Amount", style);
		createCell(row3, 4, "Mã phụ gia/ Additive", style);
		createCell(row3, 5, "No", style);
		createCell(row2, 6, "Thời gian trộn/ Time Mixing", style);
		createCell(row2, 7, "Mã Lô TP/ Finished product lot code", style);
		createCell(row2, 8, "Người thực hiện/ Implementer", style);

	}

	private void setColumnSize() {
		int widthExcel = 1;
		int width256 = (int) Math.round((widthExcel * Units.DEFAULT_CHARACTER_WIDTH + 5f)
				/ Units.DEFAULT_CHARACTER_WIDTH * 256f);

		sheet.setColumnWidth(0, 5 * width256);
		sheet.setColumnWidth(1, 10 * width256);
		sheet.setColumnWidth(2, 6 * width256);
		sheet.setColumnWidth(3, 6 * width256);
		sheet.setColumnWidth(4, 6 * width256);
		sheet.setColumnWidth(5, 6 * width256);
		sheet.setColumnWidth(6, 6 * width256);
		sheet.setColumnWidth(7, 6 * width256);
		sheet.setColumnWidth(8, 10 * width256);

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private void writeDataLines(List<MixingMaterialDTO> list) {

		int rowCount = 4;

		CellStyle style = createStyle("Times New Roman", 10, false,
				HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, true);
		for (MixingMaterialDTO p : list) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(row, columnCount++, p.getBatch(), style);
			createCell(row, columnCount++, String.valueOf(p.getAmountMaterial()), style);
			createCell(row, columnCount++,
					p.getIngredientBatchCode(), style);
			createCell(row, columnCount++, String.valueOf(p.getAmountAdditive()), style);
			createCell(row, columnCount++, p.getAdditiveCode(), style);
			createCell(row, columnCount++, p.getNo(), style);
			createCell(row, columnCount++, p.getMixingTime(), style);
			createCell(row, columnCount++, p.getProductLotCode(), style);
			createCell(row, columnCount++, p.getEmployeeName(), style);
		}

	}

	private CellStyle createStyle(String fontName, int fontHeight, boolean bold,
			HorizontalAlignment hAlightment, VerticalAlignment vAlightment,
			boolean wrapText, boolean border) {
		CellStyle style = new XSSFCellStyle(workbook.getStylesSource());

		XSSFFont font = workbook.createFont();
		font.setFontHeight(fontHeight);
		font.setFontName(fontName);
		font.setBold(bold);

		style.setFont(font);
		style.setWrapText(wrapText);
		if (hAlightment != null) {
			style.setAlignment(hAlightment);
		}
		if (vAlightment != null) {
			style.setVerticalAlignment(vAlightment);
		}
		if (wrapText) {
			style.setWrapText(wrapText);
		}
		if (border) {
			style.setBorderTop(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
		}

		return style;
	}

	private MixingMaterial mapDTOToMixing(MixingMaterialDTO mixingMaterialDTO) {
		MixingMaterial mixingMaterial = new MixingMaterial();
		Optional<TypeProduct> opTypeProduct = typeProductService.get(Integer.parseInt(mixingMaterialDTO.getTypeProduct()));
		Optional<Machine> opMachine = machineService.get(Integer.parseInt(mixingMaterialDTO.getMachine()));
		Optional<IngredientBatch> opIngredientBatch = ingreeBatchService.get(Integer.parseInt(mixingMaterialDTO.getIngredientBatch()));
		Optional<ProductLot> opProductLot = productLotService.get(Integer.parseInt(mixingMaterialDTO.getProductLot()));
		Optional<Additive> opAdditive = additiveService.getById(Integer.parseInt(mixingMaterialDTO.getAdditive()));
		Optional<Employee> opEmployee = employeeService.get(Integer.parseInt(mixingMaterialDTO.getEmployee()));
		mixingMaterial.setTypeProduct(opTypeProduct.get());
		mixingMaterial.setDateMixing(LocalDateUtil.parse(mixingMaterialDTO.getDateMixing()));
		mixingMaterial.setBatch(Integer.parseInt(mixingMaterialDTO.getBatch()));
		mixingMaterial.setIngredientBatch(opIngredientBatch.get());
		mixingMaterial.setAmountMaterial(Float.parseFloat(mixingMaterialDTO.getAmountMaterial()));
		mixingMaterial.setMixingTime((mixingMaterialDTO.getMixingTime()));
		mixingMaterial.setAdditive(opAdditive.get());
		mixingMaterial.setAmountAddtitive(Float.parseFloat(mixingMaterialDTO.getAmountAdditive()));
		mixingMaterial.setProductLot(opProductLot.get());
		mixingMaterial.setMachine(opMachine.get());
		mixingMaterial.setEmployee(opEmployee.get());

		
		
		return mixingMaterial;
	};
}
