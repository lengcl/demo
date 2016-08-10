package leng;

/**写入**/

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelWriter {

	public static String outputFile = "D:\\test.xls";

	public static void main(String argv[]) {

		try {

			// 创建新的Excel 工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();

			// 在Excel工作簿中建一工作表，其名为缺省值
			// 如要新建一名为"效益指标"的工作表，其语句为：
			// HSSFSheet sheet = workbook.createSheet("效益指标");
			HSSFSheet sheet = workbook.createSheet();
			// 在索引0的位置创建行（最顶端的行）
			HSSFRow row = sheet.createRow((short) 0);

			HSSFCell empCodeCell = row.createCell((short) 0);
			empCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			empCodeCell.setCellValue("员工代码");
			
			HSSFCell empNameCell = row.createCell((short) 1);
			empNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			empNameCell.setCellValue("姓名");

			HSSFCell sexCell = row.createCell((short) 2);
			sexCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			sexCell.setCellValue("性别");
			
			HSSFCell birthdayCell = row.createCell((short) 3);
			birthdayCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			birthdayCell.setCellValue("出生日期");

			HSSFCell orgCodeCell = row.createCell((short) 4);
			orgCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			orgCodeCell.setCellValue("机构代码");

			HSSFCell orgNameCell = row.createCell((short) 5);
			orgNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			orgNameCell.setCellValue("机构名称");
			
			HSSFCell contactTelCell = row.createCell((short) 6);
			contactTelCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			contactTelCell.setCellValue("联系电话");

			HSSFCell zjmCell = row.createCell((short) 7);
			zjmCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			zjmCell.setCellValue("助记码");
			for (int i=1; i<=10; i++) {
				row = sheet.createRow((short) i);
				empCodeCell = row.createCell((short) 0);
				empCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				empCodeCell.setCellValue("001_" + i);
				
				empNameCell = row.createCell((short) 1);
				empNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				empNameCell.setCellValue("张三_" + i);

				sexCell = row.createCell((short) 2);
				sexCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				sexCell.setCellValue("性别_" + i);
				
				birthdayCell = row.createCell((short) 3);
				birthdayCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				birthdayCell.setCellValue("出生日期_" + i);

				orgCodeCell = row.createCell((short) 4);
				orgCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				orgCodeCell.setCellValue("机构代码_" + i);

				orgNameCell = row.createCell((short) 5);
				orgNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				orgNameCell.setCellValue("机构名称_" + i);
				
				contactTelCell = row.createCell((short) 6);
				contactTelCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				contactTelCell.setCellValue("联系电话_" + i);

				zjmCell = row.createCell((short) 7);
				zjmCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				zjmCell.setCellValue("助记码_" + i);
				
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
			System.out.println("已运行 xlCreate() : " + e);
		}
	}
}

