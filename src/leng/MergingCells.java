package leng;

import java.io.FileOutputStream; 
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook; 
import org.apache.poi.ss.usermodel.Cell; 
import org.apache.poi.ss.usermodel.Row; 
import org.apache.poi.ss.usermodel.Sheet; 
import org.apache.poi.ss.usermodel.Workbook; 
import org.apache.poi.ss.util.CellRangeAddress;

public class MergingCells {

 /** 
  * \brief 方法名与方法功能概述  
  * @param args 
  * @attention 方法的使用注意事项 
  * @author Administrator 
  * @throws IOException 
  * @date 2014-5-24 
  * @note  begin modify by 修改人 修改时间   修改内容摘要说明 
  */ 
 public static void main(String[] args) throws IOException { 
  Workbook wb = new HSSFWorkbook(); 
  Sheet sheet = wb.createSheet("new sheet"); 
   
  Row row = sheet.createRow((short)1); 
  Cell cell = row.createCell((short)1); 
  cell.setCellValue("This is a test of merging"); 
   
  sheet.addMergedRegion(new CellRangeAddress( 
    1, //first row 
    1, //last row 
    1, //first column 
    2)); 
  FileOutputStream fileOutputStream = new FileOutputStream("D:\\workbook.xls"); 
  wb.write(fileOutputStream); 
  fileOutputStream.close(); 
 }
}