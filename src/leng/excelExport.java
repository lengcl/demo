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
	public static String fileToBeRead = "D:\\���ܱ��豸��������.xls";
	public static String outputFile = "D:\\test1.xls";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// ������Excel�������ļ�������
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
			// �½�һ����ļ���
			FileOutputStream fOut = new FileOutputStream(outputFile);
			// ����Ӧ��Excel ����������
			workbook.write(fOut);
			fOut.flush();
			// �����������ر��ļ�
			fOut.close();
			System.out.println("�ļ�����...");			
			
		} catch (Exception e) {
			System.out.println("������xlRead() : " + e);
		}
	}

}
