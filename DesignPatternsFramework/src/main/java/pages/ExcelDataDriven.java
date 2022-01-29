package pages;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

// Identify testcases column by scanning entire row
// once column is identified then scan entire testcase column to identify purchase testcase row
// after you grab purchase testcase row = pull all data of that row and feed into test
public class ExcelDataDriven {
    public static WebDriver driver;

    public ExcelDataDriven(WebDriver driver) {
        this.driver = driver;
    }

    public ArrayList<String> getData(String testcase) throws IOException {
        ArrayList<String> a = new ArrayList<String>();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\excelData.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        int sheets = workbook.getNumberOfSheets();
        for (int i = 0; i < sheets; i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase("testdata")) {
                XSSFSheet sheet = workbook.getSheetAt(i);

                Iterator<Row> rows = sheet.iterator();
                Row firstRow = rows.next();
                int k = 0;
                int column = 0;
                Iterator<Cell> ce = firstRow.cellIterator();
                while (ce.hasNext()) {
                    Cell value = ce.next();
                    if (value.getStringCellValue().equalsIgnoreCase("Testcases")) {
                        column = k;
                    }
                    k++;
                }
                while (rows.hasNext()) {
                    Row r = rows.next();
                    if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testcase)) {
                        Iterator<Cell> cv = r.cellIterator();
                        while (cv.hasNext()) {
                            Cell c = cv.next();
                            if (c.getCellTypeEnum() == CellType.STRING)
                                a.add(c.getStringCellValue());
                            else
                                a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
                        }
                    }
                }
            }
        }
        return a;
    }
}
