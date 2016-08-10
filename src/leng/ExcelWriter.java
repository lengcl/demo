package leng;

/**д��**/

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelWriter {

	public static String outputFile = "D:\\test.xls";

	public static void main(String argv[]) {

		try {

			// �����µ�Excel ������
			HSSFWorkbook workbook = new HSSFWorkbook();

			// ��Excel�������н�һ����������Ϊȱʡֵ
			// ��Ҫ�½�һ��Ϊ"Ч��ָ��"�Ĺ����������Ϊ��
			// HSSFSheet sheet = workbook.createSheet("Ч��ָ��");
			HSSFSheet sheet = workbook.createSheet();
			// ������0��λ�ô����У���˵��У�
			HSSFRow row = sheet.createRow((short) 0);

			HSSFCell empCodeCell = row.createCell((short) 0);
			empCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			empCodeCell.setCellValue("Ա������");
			
			HSSFCell empNameCell = row.createCell((short) 1);
			empNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			empNameCell.setCellValue("����");

			HSSFCell sexCell = row.createCell((short) 2);
			sexCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			sexCell.setCellValue("�Ա�");
			
			HSSFCell birthdayCell = row.createCell((short) 3);
			birthdayCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			birthdayCell.setCellValue("��������");

			HSSFCell orgCodeCell = row.createCell((short) 4);
			orgCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			orgCodeCell.setCellValue("��������");

			HSSFCell orgNameCell = row.createCell((short) 5);
			orgNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			orgNameCell.setCellValue("��������");
			
			HSSFCell contactTelCell = row.createCell((short) 6);
			contactTelCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			contactTelCell.setCellValue("��ϵ�绰");

			HSSFCell zjmCell = row.createCell((short) 7);
			zjmCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			zjmCell.setCellValue("������");
			for (int i=1; i<=10; i++) {
				row = sheet.createRow((short) i);
				empCodeCell = row.createCell((short) 0);
				empCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				empCodeCell.setCellValue("001_" + i);
				
				empNameCell = row.createCell((short) 1);
				empNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				empNameCell.setCellValue("����_" + i);

				sexCell = row.createCell((short) 2);
				sexCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				sexCell.setCellValue("�Ա�_" + i);
				
				birthdayCell = row.createCell((short) 3);
				birthdayCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				birthdayCell.setCellValue("��������_" + i);

				orgCodeCell = row.createCell((short) 4);
				orgCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				orgCodeCell.setCellValue("��������_" + i);

				orgNameCell = row.createCell((short) 5);
				orgNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				orgNameCell.setCellValue("��������_" + i);
				
				contactTelCell = row.createCell((short) 6);
				contactTelCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				contactTelCell.setCellValue("��ϵ�绰_" + i);

				zjmCell = row.createCell((short) 7);
				zjmCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				zjmCell.setCellValue("������_" + i);
				
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
			System.out.println("������ xlCreate() : " + e);
		}
	}
}

