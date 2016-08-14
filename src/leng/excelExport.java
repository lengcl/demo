package leng;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class excelExport {

	/**
	 * @param args
	 */
	public static String fileToBeRead = "D:\\电能表设备配置评价.xls";
	public static String outputFile = "D:\\test1.xls";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					fileToBeRead));
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow row = sheet.getRow((short) 0);
			//HSSFCellStyle cellStyle = workbook.createCellStyle();
			
			for(int i = 6;i<=40;i++)
			{
				row = sheet.getRow((short) i);
				HSSFCell empCell = row.getCell((short) 4);
				HSSFCell empName = row.getCell((short) 0);
				System.out.println(empName);
				empCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				empCell.setCellValue("222");
				
				
			}
			// 新建一输出文件流
			FileOutputStream fOut = new FileOutputStream(outputFile);
			// 把相应的Excel 工作簿存盘
			workbook.write(fOut);
			fOut.flush();
			// 操作结束，关闭文件
			fOut.close();
			System.out.println("文件生成...");			
			
		} catch (Exception e) {
			System.out.println("已运行xlRead() : " + e);
		}
	}

}
