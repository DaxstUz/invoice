package com.invoice.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelManage {  


    public static void main(String[] args) throws IOException {  
        String path = "E:/";  
        String fileName = "被保险人员清单(新增)04";  
        String fileType = "xlsx";  
        List<InsuraceExcelBean> list = new ArrayList<>();
        for(int i=0; i<6; i++){
            InsuraceExcelBean bean = new InsuraceExcelBean();  
            bean.setInsuraceUser("test"+i);
            bean.setBankCardId("4444444444"+i+","+"55544444444444"+i+","+"999999999999999"+i);
            bean.setIdCard("666666"+i);
            bean.setBuyTime("2016-05-06");
            bean.setInsEndTime("2016-05-07");
            bean.setInsStartTime("2017-05-06");
            bean.setMoney("20,000");
            bean.setType("储蓄卡");
            
            list.add(bean);
        }
        String title[] = {"被保险人姓名","身份证号","账户类型","银行卡号","保险金额(元)","购买时间","保单生效时间","保单失效时间"};  
//        createExcel("E:/被保险人员清单(新增).xlsx","sheet1",fileType,title);  
        
        writer(path, fileName, fileType,list,title);  
    }  
    
    @SuppressWarnings("resource")
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
            	System.out.println("文件格式不正确");
//					throw new SimpleException("文件格式不正确");
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
            	System.out.println("文件格式不正确");
//                throw new SimpleException("文件格式不正确");
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
        cell.setCellValue("被保险人员清单");    //创建第一行    
        
        CellStyle style = wb.createCellStyle(); // 样式对象      
        // 设置单元格的背景颜色为淡蓝色  
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index); 
        
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直      
        style.setAlignment(CellStyle.ALIGN_CENTER);// 水平   
        style.setWrapText(true);// 指定当单元格内容显示不下时自动换行
       
        cell.setCellStyle(style); // 样式，居中
        
        Font font = wb.createFont();  
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);  
        font.setFontName("宋体");  
        font.setFontHeight((short) 280);  
        style.setFont(font);  
        // 单元格合并      
        // 四个参数分别是：起始行，起始列，结束行，结束列      
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));  
        sheet.autoSizeColumn(5200);
        
        row = sheet.createRow(1);    //创建第二行    
        for(int i = 0;i < titleRow.length;i++){  
            cell = row.createCell(i);  
            cell.setCellValue(titleRow[i]);  
            cell.setCellStyle(style); // 样式，居中
            sheet.setColumnWidth(i, 20 * 256); 
        }  
        row.setHeight((short) 540); 

        //循环写入行数据   
        for (int i = 0; i < list.size(); i++) {  
            row = (Row) sheet.createRow(i+2);  
            row.setHeight((short) 500); 
            row.createCell(0).setCellValue(( list.get(i)).getInsuraceUser());
            row.createCell(1).setCellValue(( list.get(i)).getIdCard());
            row.createCell(2).setCellValue(( list.get(i)).getType());
            row.createCell(3).setCellValue(( list.get(i)).getBankCardId());
            row.createCell(4).setCellValue(( list.get(i)).getMoney());
            row.createCell(5).setCellValue(( list.get(i)).getBuyTime());
            row.createCell(6).setCellValue(( list.get(i)).getInsStartTime());
            row.createCell(7).setCellValue(( list.get(i)).getInsEndTime());
        }  
        
        //创建文件流   
        OutputStream stream = new FileOutputStream(excelPath);  
        //写入数据   
        wb.write(stream);  
        //关闭文件流   
        stream.close();  
    }  
    
}  
