package com.dpm.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dpm.dto.ProductReportDTO;
import com.dpm.entity.Product;

public class ProductExcelExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Product> listProducts;
	private ProductReportDTO productReporDdTO;

	public ProductExcelExporter(List<Product> listProducts,
			ProductReportDTO productReporDdTO) {
		this.listProducts = listProducts;
		this.productReporDdTO = productReporDdTO;
		workbook = new XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("Products");

		Row row = sheet.createRow(8);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);

		CellStyle styleHeader = createCellStyle(true, "Times New Roman", 12, false, true);
		
		createCell(sheet.createRow(0), 0, "CÔNG TY TNHH ABC", styleHeader);
		createCell(sheet.createRow(1), 0, "ABC CO., LTD", styleHeader);

		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 18));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 18));
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 8));
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 9, 18));

		createCell(sheet.createRow(3), 0, "BÁO CÁO THÀNH PHẨM SẢN XUẤT", styleHeader);
		createCell(sheet.createRow(4), 0, "FINISHED PRODUCT REPORT", styleHeader);

		int columnRowCount = 0;

		createCell(row, columnRowCount++, "Mã Sản Phẩm", style);
		createCell(row, columnRowCount++, "Tên Sản Phẩm", style);
		createCell(row, columnRowCount++, "Ngày Nhập", style);
		createCell(row, columnRowCount++, "Tổng NL", style);
		createCell(row, columnRowCount++, "Xác", style);
		createCell(row, columnRowCount++, "Cốt", style);
		createCell(row, columnRowCount++, "Mã Máy", style);
		createCell(row, columnRowCount++, "Loại SP", style);
		
		createCell(row, columnRowCount++, "Thành Phẩm", style);
		createCell(row, columnRowCount++, "Thành Phẩm Không Đạt", style);
		createCell(row, columnRowCount++, "Tách Nước", style);
		createCell(row, columnRowCount++, "Điểm Đen", style);
		createCell(row, columnRowCount++, "pH Thấp", style);
		createCell(row, columnRowCount++, "Vón cục", style);
		createCell(row, columnRowCount++, "Xylon", style);
		createCell(row, columnRowCount++, "Phế", style);
		createCell(row, columnRowCount++, "Cặn", style);
		createCell(row, columnRowCount++, "Bã", style);
		createCell(row, columnRowCount++, "Tổng", style);
		
		

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);
			
		} else if (value instanceof LocalDate) {
			cell.setCellValue(value.toString());
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}
	
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

	private void writeDataLines() {
		int rowCount = 9;

		CellStyle style = createCellStyle(true, "Times New Roman", 12, true, true);
		CellStyle styleHeader = createCellStyle(true, "Times New Roman", 12, false, true);
		
		Row row2 = sheet.createRow(6);
		
		String fromDate = productReporDdTO.getFromDate();
		String toDate = productReporDdTO.getFromDate();
		
		if (fromDate == null)
			fromDate = "";
		if (toDate == null)
			toDate = "";
		
		createCell(row2, 0, "Từ ngày: " + fromDate, styleHeader);
		createCell(row2, 9, "Đến ngày: " + toDate, styleHeader);
		
//		Row row1 = sheet.createRow(7);
//		
//		for (int i = 0; i < 19; i++) {
//			row1.createCell(i).setCellStyle(style);
//		}
		
		CellStyle styleFooter = createCellStyle(true, "Times New Roman", 12, false, true);
		
		int columnCountFooter = 0;
		
		for (Product product : listProducts) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(row, columnCount++, product.getProductId(), style);
			createCell(row, columnCount++, product.getProductName(), style);
			createCell(row, columnCount++, product.getProductEntryDate(), style);
			createCell(row, columnCount++, product.getTotalWeightInput(), style);
			createCell(row, columnCount++, product.getResidue(), style);
			createCell(row, columnCount++, product.getCoconutMilk(), style);
			createCell(row, columnCount++, product.getMachine().getMachineCode(), style);
			createCell(row, columnCount++, product.getTypeProduct().getTypeProductName(),
					style);
			
			createCell(row, columnCount++, product.getFinishedProductDetail(),
					style);
			createCell(row, columnCount++, product.getUnsatisfiedProduct(),
					style);

			createCell(row, columnCount++, product.getWaterExtract(),
					style);

			createCell(row, columnCount++, product.getCoconutSilk(),
					style);

			createCell(row, columnCount++, product.getLowPh(),
					style);

			createCell(row, columnCount++, product.getClotted(),
					style);

			createCell(row, columnCount++, product.getXylon(),
					style);

			createCell(row, columnCount++, product.getScrap(),
					style);
			
			createCell(row, columnCount++, product.getCrusted(),
					style);

			createCell(row, columnCount++, product.getResidueDetail(),
					style);

			createCell(row, columnCount++, product.getTotal(),
					style);
			columnCountFooter = --columnCount;
		}
		int countRowFooter = ++rowCount;
				
		Row rowFooter = sheet.createRow(countRowFooter);
		
		
		
		sheet.addMergedRegion(new CellRangeAddress(countRowFooter, countRowFooter, columnCountFooter-8, columnCountFooter));
		sheet.addMergedRegion(new CellRangeAddress(countRowFooter+1, countRowFooter+1, columnCountFooter-8, columnCountFooter));
				
		createCell(rowFooter, 10, "Người thẩm tra/ Verifier", styleFooter);
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}
}
