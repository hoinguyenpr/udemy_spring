package com.dpm.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.dpm.dto.CheckListFilterDTO;
import com.dpm.entity.CheckList;
import com.dpm.entity.TypeProduct;
import com.dpm.service.CheckListExportService;
import com.dpm.service.TypeProductService;

@Service
public class CheckListExportServiceImpl implements CheckListExportService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckListExportServiceImpl.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private TypeProductService typeProductService;
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<CheckList> checkLists;
	private CheckListFilterDTO filter;
	private Locale loc;


	
	private void writeHeaderLine() {
		sheet = workbook.createSheet("CheckList");
		CellStyle style = createStyle("Times New Roman", 12, true, null, null, false,
				false);
		sheet.getPrintSetup().setLandscape(true);
		sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		PrintSetup ps = sheet.getPrintSetup();

		sheet.setAutobreaks(true);

		ps.setFitHeight((short) 1);
		ps.setFitWidth((short) 1);

		// Create Header
		Header header = sheet.getHeader();
		header.setLeft(messageSource.getMessage("checklist.excel.companyname", null, loc));

		// Create Title of Sheet
		Row row0 = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
		CellStyle style2 = createStyle("Times New Roman", 15, true,
				HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.CENTER, false,
				false);
		String type = "";
		Optional<TypeProduct> typeProduct = null;
		
		
		
		try {
			System.out.println(filter);
			if (filter.getProductType() == 0) {
				type = messageSource.getMessage("checklist.excel.title.all", null, loc);
			}else{
				
				typeProduct = typeProductService.get(filter.getProductType());
				if (typeProduct.isPresent()) {
					type = typeProduct.get().getTypeProductName();
				}
				
				
			}
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		}
		
		createCell(row0, 0, messageSource.getMessage("checklist.excel.title", null, loc) +" "+type.toUpperCase(), style2);

		Row row1 = sheet.createRow(1);
		CellStyle style3 = createStyle("Times New Roman", 12, false, null,
				VerticalAlignment.BOTTOM, false, false);
		
		
		createCell(row1, 0, messageSource.getMessage("checklist.excel.date", null, loc)+": " + this.filter.getDate(), style3);

		// Create Table Header
		Row row = sheet.createRow(2);
		row.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
		CellStyle style4 = createStyle("Times New Roman", 12, true,
				HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, true);

		createCell(row, 0, messageSource.getMessage("checklist.excel.hour", null, loc), style4);
		createCell(row, 1, messageSource.getMessage("checklist.excel.type", null, loc), style4);
		createCell(row, 2, messageSource.getMessage("checklist.excel.mosture", null, loc), style4);
		createCell(row, 3, messageSource.getMessage("checklist.excel.ph", null, loc), style4);
		createCell(row, 4, messageSource.getMessage("checklist.excel.brix", null, loc), style4);
		createCell(row, 5, messageSource.getMessage("checklist.excel.taste", null, loc), style4);
		createCell(row, 6, messageSource.getMessage("checklist.excel.color", null, loc), style4);
		createCell(row, 7, messageSource.getMessage("checklist.excel.inpurity", null, loc), style4);
		createCell(row, 8, messageSource.getMessage("checklist.excel.quantitySatisfied", null, loc), style4);
		createCell(row, 9, messageSource.getMessage("checklist.excel.quantityUnsatisfied", null, loc), style4);
		createCell(row, 10,messageSource.getMessage("checklist.excel.quantityMix", null, loc), style4);
		createCell(row, 11,messageSource.getMessage("checklist.excel.remarkLabel", null, loc), style4);
		createCell(row, 12,messageSource.getMessage("checklist.excel.qcLabel", null, loc), style4);

		setColumnSize();
	}

	public void setColumnSize() {
		int widthExcel = 1;
		int width256 = (int) Math.round((widthExcel * Units.DEFAULT_CHARACTER_WIDTH + 5f)
				/ Units.DEFAULT_CHARACTER_WIDTH * 256f);

		sheet.setColumnWidth(0, 3 * width256);
		sheet.setColumnWidth(1, 10 * width256);
		sheet.setColumnWidth(2, 4 * width256);
		sheet.setColumnWidth(3, 4 * width256);
		sheet.setColumnWidth(4, 4 * width256);
		sheet.setColumnWidth(5, 5 * width256);
		sheet.setColumnWidth(6, 5 * width256);
		sheet.setColumnWidth(7, 4 * width256);
		sheet.setColumnWidth(8, 6 * width256);
		sheet.setColumnWidth(9, 6 * width256);
		sheet.setColumnWidth(10, 6 * width256);
		sheet.setColumnWidth(11, 10 * width256);
		sheet.setColumnWidth(12, 6 * width256);

	}

	private void writeDataLines() {
		int cellIndex = 3;

		CellStyle style = createStyle("Times New Roman", 12, false,
				HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, true);

		int startCol = cellIndex;
		int startHour = checkLists.get(0).getTime().getHour();
		int endHour = 0;
		int indexCol = 0;

		List<Integer> merges = new ArrayList<Integer>();
		merges.add(cellIndex);// add first cell

		for (int i = 0; i < this.checkLists.size(); i++) {
			CheckList checklist = checkLists.get(i);
			Row row = sheet.createRow(cellIndex++);
			createRow(row, checklist, style); // create a cell
			endHour = checklist.getTime().getHour();
			indexCol = cellIndex - startCol + 1;
			if (startHour != endHour) { // hour in column : [10h 10h 11h] (11h is endHour,
										// 10h is startHour)
				merges.add(indexCol);
				merges.add(indexCol + 1);// update index into merge list
				startHour = endHour;
			}
			if (i == checkLists.size() - 1) {
				merges.add(cellIndex - 1); // add the last index of merge
			}		
			
		}
		
		int indexOne, indexTwo;
		for (indexOne = 0; indexOne < merges.size(); indexOne += 2) {
			for (indexTwo = indexOne + 1; indexTwo < merges.size(); indexTwo++) {
				try {
					if (merges.get(indexOne) == merges.get(indexTwo)) {
						indexTwo -= 2;
						continue;
					} else {
						sheet.addMergedRegionUnsafe(new CellRangeAddress(
								merges.get(indexOne), merges.get(indexTwo), 0, 0)); // merge column  by indexBefor  and indexAfter
					}
				} catch (Exception e) {
					// log
				}
				break;
			}
		}
		// verifier
		Row row2 = sheet.createRow(merges.get(merges.size()-1)+1);
		CellStyle style5 = createStyle("Times New Roman", 12, true,
				HorizontalAlignment.LEFT, VerticalAlignment.TOP, false, false);
		createCell(row2, 9, messageSource.getMessage("checklist.excel.verifier", null, loc), style5);
		
		// signature
		Row row3 = sheet.createRow(merges.get(merges.size()-1)+2);
		createCell(row3, 9, "", style5);
		
	}

	public void createRow(Row row, CheckList checklist, CellStyle style) {
		int columnCount = 0;
		String ok = messageSource.getMessage("checklist.excel.ok", null, loc);
		String ng = messageSource.getMessage("checklist.excel.ng", null, loc);
		createCell(row, columnCount++, checklist.getTime().getHour() + "h", style);
		createCell(row, columnCount++,
				checklist.getLot().getTypeProduct().getTypeProductName()+"\n"+checklist.getLot().getLotCode(), style);
		createCell(row, columnCount++, checklist.getMoisture(), style);
		createCell(row, columnCount++, checklist.getPh(), style);
		createCell(row, columnCount++, checklist.getBrix(), style);
		createCell(row, columnCount++, checklist.isColor() ? ok : ng, style);
		createCell(row, columnCount++, checklist.isTaste() ? ok : ng, style);
		createCell(row, columnCount++, checklist.getImpurity(), style);
		createCell(row, columnCount++, checklist.getQuantitySatisfied(), style);
		createCell(row, columnCount++, checklist.getQuantityUnsatisfied(), style);
		createCell(row, columnCount++, checklist.getQuantityMix(), style);
		createCell(row, columnCount++, checklist.getRemark(), style);
		createCell(row, columnCount++, checklist.getQc().getEmployeeName(), style);

	}

	public void export(HttpServletResponse response,List<CheckList> checkLists, CheckListFilterDTO filter, Locale loc ) throws IOException {
		this.checkLists = checkLists;
		this.filter =  filter;
		this.loc = loc;
		workbook = new XSSFWorkbook();
		writeHeaderLine();
		writeDataLines();
		// Date and Lot Code

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		
		outputStream.flush();
		outputStream.close();

	}

	public void createCell(Row row, int columnCount, Object value, CellStyle style) {
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if (value instanceof LocalDate) {
			cell.setCellValue(value.toString());
		} else if (value instanceof LocalTime) {
			cell.setCellValue(value.toString());
		} else if (value instanceof Short) {
			cell.setCellValue((Short) value);
		} else if (value instanceof Float) {
			cell.setCellValue(String.valueOf(value));
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);

	}

	public CellStyle createStyle(String fontName, int fontHeight, boolean bold,
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
}
