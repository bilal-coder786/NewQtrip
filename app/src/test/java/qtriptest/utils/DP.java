package qtriptest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DP {

    @DataProvider(name = "Data-Provider")
    public Object[][] dpMethodStatic() throws IOException {
        return dpMethod("TestCase03");  // 👈 Replace with actual sheet name if needed
    }

    public Object[][] dpMethod(String sheetName) throws IOException {
        int rowIndex = 0;
        int cellIndex = 0;
        List<List<String>> outputList = new ArrayList<>();

        FileInputStream excelFile = new FileInputStream(new File(
                "E:\\BILAL\\CRIO\\Practice_Java_Selenium_Code\\Qtrip\\app\\src\\test\\resources\\DatasetsforQTrip.xlsx"));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet selectedSheet = workbook.getSheet(sheetName);
        Iterator<Row> iterator = selectedSheet.iterator();

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            List<String> innerList = new ArrayList<>();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (rowIndex > 0 && cellIndex > 0) {
                    if (cell.getCellType() == CellType.STRING) {
                        innerList.add(cell.getStringCellValue());
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            innerList.add(sdf.format(cell.getDateCellValue()));
                        } else {
                            innerList.add(String.valueOf(cell.getNumericCellValue()));
                        }
                    }
                }
                cellIndex++;
            }

            if (!innerList.isEmpty()) {
                outputList.add(innerList);
            }
            rowIndex++;
            cellIndex = 0;
        }

        excelFile.close();
        workbook.close();

        System.out.println("Loaded sheet: " + sheetName);
        System.out.println("Data loaded: " + outputList.toString());

        return outputList.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
    }
}



// package qtriptest.utils;

// import java.io.File;
// import java.io.FileInputStream;
// import java.io.IOException;
// import java.lang.reflect.Method;
// import java.util.ArrayList;
// import java.util.Iterator;
// import java.util.List;

// import org.testng.annotations.DataProvider;

// import org.apache.poi.ss.usermodel.Workbook;
// import org.apache.poi.ss.usermodel.Sheet;
// import org.apache.poi.ss.usermodel.Row;
// import org.apache.poi.ss.usermodel.Cell;
// import org.apache.poi.ss.usermodel.CellType;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// public class DP {
//     // TODO: use correct annotation to connect the Data Provider with your Test Cases
//    // @DataProvider(name = "Data-Provider")
//     public Object[][] dpMethod(String sheetName) throws IOException {
//         int rowIndex = 0;
//         int cellIndex = 0;
//         List<List> outputList = new ArrayList<List>();

//         FileInputStream excelFile = new FileInputStream(new File(
//                 "E:\\BILAL\\CRIO\\Practice_Java_Selenium_Code\\Qtrip\\app\\src\\test\\resources\\DatasetsforQTrip.xlsx"));
//         Workbook workbook = new XSSFWorkbook(excelFile);
//         Sheet selectedSheet = workbook.getSheet(sheetName);
//         //Sheet selectedSheet =(Sheet) workbook.getSheet(sheetName);
//         Iterator<Row> iterator = selectedSheet.iterator();

//         while (iterator.hasNext()) {
//             Row nextRow = iterator.next();
//             Iterator<Cell> cellIterator = nextRow.cellIterator();
//             List<String> innerList = new ArrayList<String>();

//             while (cellIterator.hasNext()) {
//                 Cell cell = cellIterator.next();
//                 if (rowIndex > 0 && cellIndex > 0) {
//                     if (cell.getCellType() == CellType.STRING) {
//                         innerList.add(cell.getStringCellValue());
//                     } else if (cell.getCellType() == CellType.NUMERIC) {
//                         innerList.add(String.valueOf(cell.getNumericCellValue()));
//                     }
//                 }
//                 cellIndex = cellIndex + 1;
//             }
//             rowIndex = rowIndex + 1;
//             cellIndex = 0;
//             if (innerList.size() > 0)
//                 outputList.add(innerList);

//         }

//         excelFile.close();

//         System.out.println("Loaded sheet: " + sheetName);
//         System.out.println("Data loaded: " + outputList.toString());


//         String[][] stringArray =
//                 outputList.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
//         return stringArray;

//     }

    
// }