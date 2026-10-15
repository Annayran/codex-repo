import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.InputStream;

public class ReadXls {
    public static void main(String[] args) throws Exception {
        DataFormatter formatter = new DataFormatter();
        InputStream in = new FileInputStream(args[0]);
        try {
            Workbook wb = new HSSFWorkbook(in);
            System.out.println("SHEETS=" + wb.getNumberOfSheets());
            for (int s = 0; s < wb.getNumberOfSheets(); s++) {
                Sheet sheet = wb.getSheetAt(s);
                System.out.println("SHEET\t" + s + "\t" + sheet.getSheetName() + "\tlastRow=" + sheet.getLastRowNum());
                int maxRows = Math.min(sheet.getLastRowNum(), 200);
                for (int r = 0; r <= maxRows; r++) {
                    Row row = sheet.getRow(r);
                    if (row == null) {
                        continue;
                    }
                    short lastCell = row.getLastCellNum();
                    if (lastCell < 0) {
                        continue;
                    }
                    StringBuilder line = new StringBuilder();
                    line.append(r + 1);
                    for (int c = 0; c < lastCell; c++) {
                        Cell cell = row.getCell(c);
                        String value = cell == null ? "" : formatter.formatCellValue(cell);
                        line.append('\t').append(value.replace('\n', ' ').replace('\r', ' '));
                    }
                    System.out.println(line);
                }
            }
        } finally {
            in.close();
        }
    }
}
