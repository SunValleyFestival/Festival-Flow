package com.sunvalley.festivalFlowbe.service.utility;

import com.sunvalley.festivalFlowbe.entity.CollaboratorEntity;
import com.sunvalley.festivalFlowbe.entity.DayEntity;
import com.sunvalley.festivalFlowbe.entity.LocationEntity;
import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import com.sunvalley.festivalFlowbe.entity.utility.Attachment;
import com.sunvalley.festivalFlowbe.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ExportService {


    private final CollaboratorService collaboratorService;
    private final DayService dayService;
    private final LocationService locationService;
    private final ShiftService shiftService;
    private final AssociationService associationService;


    public Attachment export() throws IOException {
        Workbook workbook = new XSSFWorkbook();

        //collaboratori
        Sheet sheetCollaboratori = workbook.createSheet("Collaboratori");

        List<CollaboratorEntity> collaboratorEntities = collaboratorService.findCollaboratorEntitiesWhereIsPopulatedAndAssociationAccepted();

        for (int i = 0; i < collaboratorEntities.size(); i++) {
            Row row = sheetCollaboratori.createRow(i + 3);
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

        List<DayEntity> dayEntities = dayService.getAll();
        int dayCount = dayEntities.size();

        Map<Integer, List<ShiftEntity>> shiftsByDay = new HashMap<>();

        for (int i = 0; i < dayCount; i++) {
            List<LocationEntity> locationEntities = locationService.getLocationsByDayId(dayEntities.get(i).getId(), false);
            for (LocationEntity locationEntity : locationEntities) {
                List<ShiftEntity> shiftEntities = shiftService.getShiftsByLocationId(locationEntity.getId());
                for (ShiftEntity shiftEntity : shiftEntities) {
                    int day = shiftEntity.getLocation().getDay().getId();
                    shiftsByDay.computeIfAbsent(day, k -> new ArrayList<>()).add(shiftEntity);
                }
            }
        }
        CellStyle shiftNameStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        shiftNameStyle.setFont(font);


        Row rowDayCollaboratori = sheetCollaboratori.createRow(0);
        Row rowLocationCollaboratori = sheetCollaboratori.createRow(1);
        Row rowShiftCollaboratori = sheetCollaboratori.createRow(2);

        Cell indice = rowShiftCollaboratori.createCell(0);
        indice.setCellValue("Indice");
        indice.setCellStyle(shiftNameStyle);

        Cell id = rowShiftCollaboratori.createCell(1);
        id.setCellValue("Id");
        id.setCellStyle(shiftNameStyle);

        Cell name = rowShiftCollaboratori.createCell(2);
        name.setCellValue("Nome");
        name.setCellStyle(shiftNameStyle);

        Cell lastname = rowShiftCollaboratori.createCell(3);
        lastname.setCellValue("Cognome");
        lastname.setCellStyle(shiftNameStyle);

        Cell town = rowShiftCollaboratori.createCell(4);
        town.setCellValue("Città");
        town.setCellStyle(shiftNameStyle);

        Cell eta = rowShiftCollaboratori.createCell(5);
        eta.setCellValue("Età");
        eta.setCellStyle(shiftNameStyle);

        Cell phone = rowShiftCollaboratori.createCell(6);
        phone.setCellValue("Telefono");
        phone.setCellStyle(shiftNameStyle);

        Cell email = rowShiftCollaboratori.createCell(7);
        email.setCellValue("Email");
        email.setCellStyle(shiftNameStyle);

        Cell size = rowShiftCollaboratori.createCell(8);
        size.setCellValue("Taglia");
        size.setCellStyle(shiftNameStyle);

        Cell yearsExperience = rowShiftCollaboratori.createCell(9);
        yearsExperience.setCellValue("Anni di esperienza");
        yearsExperience.setCellStyle(shiftNameStyle);

        Cell nomeCognome = rowShiftCollaboratori.createCell(11);
        nomeCognome.setCellValue("Nome Cognome");
        nomeCognome.setCellStyle(shiftNameStyle);

        Cell ageUnder = rowShiftCollaboratori.createCell(13);
        ageUnder.setCellValue("Età < 18");
        ageUnder.setCellStyle(shiftNameStyle);

        Cell yearsExperienceUnder = rowShiftCollaboratori.createCell(12);
        yearsExperienceUnder.setCellValue("Nuovo");
        yearsExperienceUnder.setCellStyle(shiftNameStyle);

        int h = 15;
        for (DayEntity dayEntity : dayService.getAll()) {


            Cell cellDay = rowDayCollaboratori.createCell(h);
            cellDay.setCellValue(dayEntity.getName());
            cellDay.setCellStyle(shiftNameStyle);
            int firstCol = h;
            for (LocationEntity locationEntity : locationService.getLocationsByDayId(dayEntity.getId(), false)) {
                Cell cellLocation = rowLocationCollaboratori.createCell(h);
                cellLocation.setCellValue(locationEntity.getName());
                cellLocation.setCellStyle(shiftNameStyle);
                int firstColLocation = h;
                for (ShiftEntity shiftEntity : shiftService.getShiftsByLocationId(locationEntity.getId())) {
                    Cell cell = rowShiftCollaboratori.createCell(h);
                    cell.setCellValue(shiftEntity.getName());
                    cell.setCellStyle(shiftNameStyle);
                    int o;
                    for (o = 0; o < collaboratorEntities.size(); o++) {
                        if (associationService.existsByCollaboratorIdAndShiftIdAndAccepted(collaboratorEntities.get(o).getId(), shiftEntity.getId())) {
                            Row rowCollaborator = sheetCollaboratori.getRow(o + 3);
                            Cell cellCollaborator = rowCollaborator.createCell(h);
                            cellCollaborator.setCellValue("X");
                        }
                    }
                    Row rowFormula = sheetCollaboratori.createRow(o + 4);
                    Cell countfourmla = rowFormula.createCell(h);
                    countfourmla.getAddress();
                    String formula = "COUNTIF(" + countfourmla.getAddress().toString().charAt(0) + 4 + ":" + countfourmla.getAddress().toString().charAt(0) + (collaboratorEntities.size() + 4) + ",\"X\")";
                    countfourmla.setCellFormula(formula);
//                    countfourmla.setCellValue("forula");
                    h++;
                }
                if (firstColLocation != h) {
                    sheetCollaboratori.addMergedRegion(new CellRangeAddress(1, 1, firstColLocation, h - 1));
                }

            }
            if (firstCol != h) {
                sheetCollaboratori.addMergedRegion(new CellRangeAddress(0, 0, firstCol, h - 1));
            }
        }


        //collaboratori per turno
        Sheet bylocationSheet = workbook.createSheet("CollaboratoriPerTurno");


        for (int i = 3; i < 100; i++) {
            Row row1 = bylocationSheet.createRow(i);
            Cell cell = row1.createCell(0);
            cell.setCellValue(i - 2);
        }


        Row rowDay = bylocationSheet.createRow(0);
        Row rowLocation = bylocationSheet.createRow(1);
        Row rowShift = bylocationSheet.createRow(2);
        h = 1;
        for (DayEntity dayEntity : dayService.getAll()) {
            Cell cellDay = rowDay.createCell(h);
            cellDay.setCellValue(dayEntity.getName());
            cellDay.setCellStyle(shiftNameStyle);
            int firstCol = h;
            for (LocationEntity locationEntity : locationService.getLocationsByDayId(dayEntity.getId(), false)) {
                Cell cellLocation = rowLocation.createCell(h);
                cellLocation.setCellValue(locationEntity.getName());
                cellLocation.setCellStyle(shiftNameStyle);
                int firstColLocation = h;
                for (ShiftEntity shiftEntity : shiftService.getShiftsByLocationId(locationEntity.getId())) {
                    Cell cell = rowShift.createCell(h);
                    cell.setCellValue(shiftEntity.getName());
                    cell.setCellStyle(shiftNameStyle);
                    int k = 3;
                    for (CollaboratorEntity collaboratorEntity : collaboratorService.findCollaboratorEntitiesWhereIsPopulatedAndAssociationAcceptedByShiftId(shiftEntity.getId())) {
                        Row rowCollaborator = bylocationSheet.getRow(k);
                        Cell cellCollaborator = rowCollaborator.createCell(h);
                        cellCollaborator.setCellValue(collaboratorEntity.getFirstName() + " " + collaboratorEntity.getLastName());
                        k++;
                    }
                    h++;
                }
                if (firstColLocation != h) {
                    bylocationSheet.addMergedRegion(new CellRangeAddress(1, 1, firstColLocation, h - 1));
                }

            }
            if (firstCol != h) {
                bylocationSheet.addMergedRegion(new CellRangeAddress(0, 0, firstCol, h - 1));
            }
        }


//        File exportDir = new File("export");
//
//        if (!exportDir.exists()) {
//            exportDir.mkdirs();
//        }
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.HOUR_OF_DAY);

        String day = "Export_" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "_" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

        File outputFile = File.createTempFile(day, ".xlsx");
        Attachment attachment = new Attachment();
        attachment.setFilename(outputFile.getName());

        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            workbook.write(fos);
        }

        byte[] bytes = Files.readAllBytes(outputFile.toPath());

        attachment.setContent(bytes);
        return attachment;

//        FileOutputStream outputStream = new FileOutputStream(day);
//        workbook.write(outputStream);
//        workbook.close();
//        File


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
