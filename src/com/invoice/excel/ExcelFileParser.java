package com.invoice.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.invoice.word.WordWriter;
import com.invoice.word.XpmxBean;

public class ExcelFileParser {

	public static Workbook getWb(String path) {
		try {
			return WorkbookFactory.create(new File(path));
		} catch (Exception e) {
			throw new RuntimeException("读取EXCEL文件出错", e);
		}
	}

	public static Sheet getSheet(Workbook wb, int sheetIndex) {
		if (wb == null) {
			throw new RuntimeException("工作簿对象为空");
		}
		int sheetSize = wb.getNumberOfSheets();
		if (sheetIndex < 0 || sheetIndex > sheetSize - 1) {
			throw new RuntimeException("工作表获取错误");
		}
		return wb.getSheetAt(sheetIndex);
	}

	public static List<List<String>> getExcelRows(Sheet sheet, int startLine, int endLine) {
		List<List<String>> list = new ArrayList<List<String>>();
		// 如果开始行号和结束行号都是-1的话，则全表读取
		if (startLine == -1)
			startLine = 0;
		if (endLine == -1) {
			endLine = sheet.getLastRowNum() + 1;
		} else {
			endLine += 1;
		}

		for (int i = startLine; i < endLine; i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				System.out.println(i+ " 该行为空，直接跳过");
				break;
			}

			int rowSize = row.getLastCellNum();

			List<String> rowList = new ArrayList<String>();

			int countnull = 0;

			for (int j = 0; j < rowSize; j++) {
				Cell cell = row.getCell(j);
				String temp = "";
				if (cell == null) {
//					System.out.println("该列为空，赋值双引号");
					temp = "####";
					continue;
				} else {
					int cellType = cell.getCellType();
					switch (cellType) {
					case Cell.CELL_TYPE_STRING:
						temp = cell.getStringCellValue().trim();
						temp = StringUtils.isEmpty(temp) ? "####" : temp;
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						temp = String.valueOf(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_FORMULA:
						temp = String.valueOf(cell.getCellFormula().trim());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
//							temp = DateUtil.parseToString(cell.getDateCellValue(),  
//                                    DateUtil.FORMAT_DATE); 
							temp=cell.getDateCellValue().toLocaleString();
						} else {
							temp = new DecimalFormat("#.######").format(cell.getNumericCellValue());
						}
						break;
					case Cell.CELL_TYPE_BLANK:
						temp = "####";
						break;
					case Cell.CELL_TYPE_ERROR:
						temp = "ERROR";
						break;
					default:
						temp = cell.toString().trim();
						break;
					}
				}

				if (!temp.equals("####")) {
					countnull++;
				}
				
				rowList.add(temp);
			}
			
			if (countnull == 0) {
				return list;
			}
			
			list.add(rowList);

		}
		return list;
	}
	
	public static List<List<String>> readExcel(String path){
		Workbook wb = getWb(path);
		List<List<String>> list = getExcelRows(getSheet(wb, 0), 1, -1);
		return list;
	}
	
	
	public static void writeExcel(String targetPath) throws IOException{
	        String fileName = "记录文件（小票）";  
	        String fileType = "xlsx";  
	        List<InsuraceExcelBean> list = new ArrayList<>();
	        
		 String title[] = {"签购单号","店名","系统英文综合","实际英文综合","地址","商户类别","销售日期","销售时间","收银员","消费金额","消费RMB","文本时间","单号公式","文本日期","排序"};  
	
		 writer(targetPath, fileName, fileType,list,title);  
	}
	
	
	public static void writer(String path, String fileName,String fileType,List<InsuraceExcelBean> list,String titleRow[]) throws IOException {  
        Workbook wb = null; 
        String excelPath = path+File.separator+fileName+"."+fileType;
        File file = new File(excelPath);
        Sheet sheet =null;
        //创建工作文档对象   
        if (!file.exists()) {
            if (fileType.equals("xls")) {
                wb = new HSSFWorkbook();
                
            } else if(fileType.equals("xlsx")) {
                
                    wb = new XSSFWorkbook();
            } else {
//            	System.out.println("文件格式不正确");
//					throw new SimpleException("文件格式不正确");
            	throw new RuntimeException("文件格式不正确");
            }
            //创建sheet对象   
            sheet = (Sheet) wb.createSheet("sheet1");  
            OutputStream outputStream = new FileOutputStream(excelPath);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            
        } else {
            if (fileType.equals("xls")) {  
                wb = new HSSFWorkbook();  
                
            } else if(fileType.equals("xlsx")) { 
                wb = new XSSFWorkbook();  
                
            } else {  
//            	System.out.println("文件格式不正确");
                throw new RuntimeException("文件格式不正确");
            }  
        }
         //创建sheet对象   
        if (sheet==null) {
            sheet = (Sheet) wb.createSheet("sheet1");  
        }
        
        //添加表头  
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        row.setHeight((short) 540); 
//        cell.setCellValue("被保险人员清单");    //创建第一行    
        
        CellStyle style = wb.createCellStyle(); // 样式对象      
        // 设置单元格的背景颜色为淡蓝色  
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index); 
        
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直      
        style.setAlignment(CellStyle.ALIGN_CENTER);// 水平   
        style.setWrapText(true);// 指定当单元格内容显示不下时自动换行
       
//        cell.setCellStyle(style); // 样式，居中
        
        Font font = wb.createFont();  
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);  
        font.setFontName("宋体");  
//        font.setFontHeight((short) 280);  
        style.setFont(font);  
//        // 单元格合并      
//        // 四个参数分别是：起始行，起始列，结束行，结束列      
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));  
//        sheet.autoSizeColumn(5200);
        
//        row = sheet.createRow(1);    //创建第二行    
        for(int i = 0;i < titleRow.length;i++){  
            cell = row.createCell(i);  
            cell.setCellValue(titleRow[i]);  
            cell.setCellStyle(style); // 样式，居中
//            sheet.setColumnWidth(i, 20 * 256); 
        }  
//        row.setHeight((short) 540); 

        //循环写入行数据   
//        for (int i = 0; i < list.size(); i++) {  
//            row = (Row) sheet.createRow(i+2);  
//            row.setHeight((short) 500); 
//            row.createCell(0).setCellValue(( list.get(i)).getInsuraceUser());
//            row.createCell(1).setCellValue(( list.get(i)).getIdCard());
//            row.createCell(2).setCellValue(( list.get(i)).getType());
//            row.createCell(3).setCellValue(( list.get(i)).getBankCardId());
//            row.createCell(4).setCellValue(( list.get(i)).getMoney());
//            row.createCell(5).setCellValue(( list.get(i)).getBuyTime());
//            row.createCell(6).setCellValue(( list.get(i)).getInsStartTime());
//            row.createCell(7).setCellValue(( list.get(i)).getInsEndTime());
//        }  
        
        //创建文件流   
        OutputStream stream = new FileOutputStream(excelPath);  
        //写入数据   
        wb.write(stream);  
        //关闭文件流   
        stream.close();  
    }  

	

	public static void main(String a[]) {
		// String path = "D:\\test.xlsx";
//		String path = "E:\\yl\\用户资料.xlsx";
//		String path = "E:\\yl\\入金文件.xlsx";
//		String path = "E:\\yl\\记录文件(小票).xlsx";
//		String path = "E:\\yl\\中国地区代码表(更详细).xls";
		
//		String pathType="E:\\yl\\new\\type\\综合购物.xlsx";
		String pathType="C:\\Users\\Administrator\\Desktop\\廖兰辉\\廖兰辉\\廖兰辉.xls";
		
//		Workbook wb = getWb(path);
//		List<List<String>> list = getExcelRows(getSheet(wb, 0), 1, -1);
//		for (int i = 0; i < list.size(); i++) {
//			List<String> row = list.get(i);
//			for (int j = 0; j < row.size(); j++) {
//				System.out.print(row.get(j) + "\t");
//			}
//			System.out.println();
//		}
		
		List<List<String>> list = readExcel(pathType);
		
		System.out.println(list.size());
		
//		for (int i = 0; i < list.size(); i++) {
//			
//			XpmxBean xbean=new XpmxBean();
//			
//			List<String> row = list.get(i);
//			
//			xbean.setMallName(row.get(1));
//			xbean.setXpNo(row.get(12));
//			xbean.setXpTime(row.get(6)+row.get(7));
//			xbean.setXpOpNo(row.get(8));
//			xbean.setMoneyAll(row.get(9));
//			xbean.setMoneyPay(row.get(10));
//			xbean.setMallAddress(row.get(4));
//			
//			for (int j = 0; j < row.size(); j++) {
//				System.out.print(row.get(j) + "\t");
//			}
//			System.out.println();
//			
//			try {
//				WordWriter.writeXpmx("E:\\yl\\xpmx.doc", "E:\\yl\\tt.doc", xbean);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
		
	}

}