/*package leng;


import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import com.jfinal.plugin.activerecord.Record;

public class ExcelExportUtil
 {

	*//**
	 * @param response
	 * @param request
	 * @param filename	�������ļ���
	 * @param titles �����к������Ķ�Ӧ.column:����,title������
	 * @param records ��¼
	 *//*
	@SuppressWarnings("unchecked")
	public static void exportByRecord(HttpServletResponse response,HttpServletRequest request,String filename,List<Pair> titles, List<Record> records){
		exportByRecord(response, request, filename, new SheetData(titles, records));
	}

	*//**
	 * @param response
	 * @param request
	 * @param filename	�������ļ���
	 * @param sheetDatas ����һ��sheet��Ҫ������
	 *//*
	public static void exportByRecord(HttpServletResponse response,HttpServletRequest request,String filename,SheetData... sheetDatas){

		HSSFWorkbook wb = new HSSFWorkbook();								

		//�����е�style
		CellStyle titleCellStyle = wb.createCellStyle();					
		titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);				//����
		titleCellStyle.setWrapText(true);									//�Զ�����	
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);						//�Ӵ�
		font.setFontName("΢���ź�");
		titleCellStyle.setFont(font);

		//�����е�style
		CellStyle cellStyle = wb.createCellStyle();					
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);	//��ֱ����
		cellStyle.setWrapText(true);	
		Font font2 = wb.createFont();
		font2.setFontName("΢���ź�");
		cellStyle.setFont(font2);	
		
		//����sheet
		for (SheetData sheetData : sheetDatas) {
			List<Pair> titles = sheetData.titles;
			
			List<Record> records = sheetData.records;
			
			HSSFSheet sheet = wb.createSheet();
			
			int rowIndex = 0,cellIndex=0;
			
			HSSFRow row = null;
			HSSFCell cell = null;

			//����������
			row = sheet.createRow(rowIndex);
			row.setHeight((short)450);
			rowIndex++;
			
			for (Pair pair : titles) {
				
				cell = row.createCell(cellIndex);
				cell.setCellStyle(titleCellStyle);				//������ʽ
				cellIndex++;
				
				cell.setCellValue(pair.title);
			}
			
			//����ÿһ��
			for (Record record : records) {

				row = sheet.createRow(rowIndex);
				row.setHeight((short)450);
				rowIndex++;
				cellIndex = 0;

				
				for (Pair pair : titles) {
					
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);				//������ʽ
					cellIndex++;
					
					Object value = record.get(pair.column);
					
					if(value!=null){
							
						cell.setCellValue(value.toString());
					}
				}
			}
		}
		
		//���
		writeStream(filename, wb, response,request);
	}

	*//**
	 * д�������
	 *//*
	private static void writeStream(String filename, HSSFWorkbook wb, HttpServletResponse response, HttpServletRequest request)
	{

		try
		{
			String agent = request.getHeader("USER-AGENT");

			filename += ".xls";

			filename.replaceAll("/", "-");
			// filename = new String(filename.getBytes("gbk"),"ISO8859_1");
			

			if (agent.toLowerCase().indexOf("firefox")>0)
			{
				filename = new String(filename.getBytes("utf-8"), "iso-8859-1");
			}else{
				filename = URLEncoder.encode(filename, "UTF-8");
			}

			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			response.setContentType("application/octet-stream;charset=UTF-8");
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
	
	*//**
	 * �����к������Ķ�Ӧ
	 *//*
	public static class Pair {
		public String column;
		
		public String title;
		
		public Pair(String column,String title){
			this.column = column;
			
			this.title = title;
			
		}
	}
	
	*//**
	 * ����һ��sheet��Ҫ������
	 *//*
	public static class SheetData{
		public List<Pair> titles;
		public List<Record> records;
		
		public SheetData(List<Pair> titles, List<Record> records) {
			this.titles = titles;
			
			this.records = records;
		}
	}
}
*/