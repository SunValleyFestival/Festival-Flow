package com.sunvalley.festivalFlowbe.service.utility;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.repository.CollaboratorRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportService {

    private final CollaboratorRepository collaboratorRepository;

    @Autowired
    public ExportService(CollaboratorRepository collaboratorRepository) {
        this.collaboratorRepository = collaboratorRepository;
    }


    public void export() throws IOException, InvalidFormatException {
        Workbook workbook = new XSSFWorkbook(readFile());
        Sheet sheet = workbook.getSheetAt(1);
        Row row = sheet.getRow(2);
        Cell cell = row.createCell(0);
        cell.setCellValue(1);

//
//        Row header = sheet.createRow(0);
//
//        CellStyle headerStyle = workbook.createCellStyle();
//        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
//        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
//        font.setFontName("Arial");
//        font.setFontHeightInPoints((short) 16);
//        font.setBold(true);
//        headerStyle.setFont(font);
//
//        Cell headerCell = header.createCell(0);
//        headerCell.setCellValue("Name");
//        headerCell.setCellStyle(headerStyle);
//
//        headerCell = header.createCell(1);
//        headerCell.setCellValue("Age");
//        headerCell.setCellStyle(headerStyle);
//
//        CellStyle style = workbook.createCellStyle();
//        style.setWrapText(true);
//
//        Row row = sheet.createRow(2);
//        Cell cell = row.createCell(0);
//        cell.setCellValue("John Smith");
//        cell.setCellStyle(style);
//
//        cell = row.createCell(1);
//        cell.setCellValue(20);
//        cell.setCellStyle(style);

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }


    private File readFile(){
        return new File("Pianif-turni-svf_vuoto.xlsx");
    }

}
