package com.sunvalley.festivalFlowbe.service.utility;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.entity.DayEntity;
import com.sunvalley.festivalFlowbe.entity.LocationEntity;
import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import com.sunvalley.festivalFlowbe.repository.CollaboratorRepository;
import com.sunvalley.festivalFlowbe.repository.DayRepository;
import com.sunvalley.festivalFlowbe.repository.LocationRepository;
import com.sunvalley.festivalFlowbe.repository.ShiftRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


@Service
public class ExportService {

    private final CollaboratorRepository collaboratorRepository;
    private final ShiftRepository shiftRepository;
    private final LocationRepository locationRepository;
    private final DayRepository dayRepository;

    @Autowired
    public ExportService(CollaboratorRepository collaboratorRepository, ShiftRepository shiftRepository, LocationRepository locationRepository, DayRepository dayRepository) {
        this.collaboratorRepository = collaboratorRepository;
        this.shiftRepository = shiftRepository;
        this.locationRepository = locationRepository;
        this.dayRepository = dayRepository;
    }


    public void export() throws IOException, InvalidFormatException {
        Workbook workbook = new XSSFWorkbook(readFile());
        Sheet sheet = workbook.getSheetAt(1);
        List<CollaboratorEntity> collaboratorEntities = collaboratorRepository.findAll();

        for (int i = 0; i < collaboratorEntities.size(); i++) {
            Row row = sheet.getRow(i + 1);
            Cell indice = row.createCell(0);
            indice.setCellValue(i + 1);
            Cell id = row.createCell(1);
            id.setCellValue(collaboratorEntities.get(i).getId());
            Cell name = row.createCell(2);
            name.setCellValue(collaboratorEntities.get(i).getFirstName());
            Cell lastname = row.createCell(3);
            lastname.setCellValue(collaboratorEntities.get(i).getLastName());
            Cell town = row.createCell(4);
            town.setCellValue(collaboratorEntities.get(i).getTown());
            Cell age = row.createCell(5);
            int ageNum = getAge(collaboratorEntities.get(i).getAge());

            age.setCellValue(ageNum);
            Cell phone = row.createCell(6);
            phone.setCellValue(collaboratorEntities.get(i).getPhone());
            Cell email = row.createCell(7);
            email.setCellValue(collaboratorEntities.get(i).getEmail());
            Cell size = row.createCell(8);
            size.setCellValue(collaboratorEntities.get(i).getSize());
            Cell yearsExperience = row.createCell(9);
            int yearsExperienceNum = collaboratorEntities.get(i).getYearsExperience();
            yearsExperience.setCellValue(yearsExperienceNum);

            if (yearsExperienceNum < 1) {
                Cell yearsExperienceUnder = row.createCell(12);
                yearsExperienceUnder.setCellValue("X");
            }

            if (ageNum < 18) {
                Cell ageUnder = row.createCell(13);
                ageUnder.setCellValue("X");
            }


            String firstNameLastName = collaboratorEntities.get(i).getFirstName() + " " + collaboratorEntities.get(i).getLastName();
            Cell nameSurname = row.createCell(11);
            nameSurname.setCellValue(firstNameLastName);

        }

        int dayCount = dayRepository.findAll().size();
        int locationCount = locationRepository.findAll().size();
        int shiftCount = shiftRepository.findAll().size();

        Row row = sheet.getRow(0);

        List<DayEntity> dayEntities = dayRepository.findAll();


        Map<Integer, List<ShiftEntity>> shiftsByDay = new HashMap<>();

        for (int i = 0; i < dayCount; i++) {
            List<LocationEntity> locationEntities = locationRepository.findByDay(dayEntities.get(i));
            for (LocationEntity locationEntity : locationEntities) {
                List<ShiftEntity> shiftEntities = shiftRepository.findByLocationId(locationEntity.getId());
                for (ShiftEntity shiftEntity : shiftEntities) {
                    int day = shiftEntity.getLocation().getDay().getId();
                    shiftsByDay.computeIfAbsent(day, k -> new ArrayList<>()).add(shiftEntity);
                }
            }
        }
        int index = 0;
        for (DayEntity dayEntity : dayEntities) {
            for (int j = 0; j < shiftsByDay.get(dayEntity.getId()).size(); j++) {
                index++;
                row.createCell(14+index).setCellValue(shiftsByDay.get(dayEntity.getId()).get(j).getName()+"day: " + dayEntity.getId()+"location: "+shiftsByDay.get(dayEntity.getId()).get(j).getLocation().getName());

            }
        }


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


        File exportDir = new File("export"); // Create a File object for the "export" directory

// Check if the directory exists (optional, but recommended for robustness)
        if (!exportDir.exists()) {
            exportDir.mkdirs(); // Create the directory if it doesn't exist
        }
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.HOUR_OF_DAY);

        String day = exportDir.getAbsolutePath() + "/Export_" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "_" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ".xlsx";

        FileOutputStream outputStream = new FileOutputStream(day);
        workbook.write(outputStream);
        workbook.close();

    }


    private File readFile() {
        return new File("Pianif-turni-svf_vuoto.xlsx");
    }

    private int getAge(Date date) {
        long differenzaMillisecondi = new Date().getTime() - date.getTime();
        long anni = differenzaMillisecondi / (1000L * 60 * 60 * 24 * 365);
        return (int) anni;
    }

}
