package com.invoice.word;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

public class WordWriter {
	
	public static void main(String[] args) {
		try {
			new WordWriter().testWrite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testWrite() throws Exception {  
//	      String templatePath = "E:\\test.doc";  
	      String templatePath = "test.doc";  
	      InputStream is = new FileInputStream(templatePath);  
	      HWPFDocument doc = new HWPFDocument(is);  
	      Range range = doc.getRange();  
	      //把range范围内的${reportDate}替换为当前的日期  
	      range.replaceText("${reportDate}", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));  
	      range.replaceText("${appleAmt}", "100.00");  
	      range.replaceText("${bananaAmt}", "200.00");  
	      range.replaceText("${totalAmt}", "300.00");  
	      OutputStream os = new FileOutputStream("E:\\write.doc");  
	      //把doc输出到输出流中  
	      doc.write(os);  
	      this.closeStream(os);  
	      this.closeStream(is);  
	   }  
	
	/**
	 * 写入模板
	 * @param templatePath
	 * @param targtPath
	 * @param bean
	 * @throws Exception
	 */
	public static void writeFile(String templatePath,String targtPath,WriteBean bean) throws Exception{
	      InputStream is = new FileInputStream(templatePath);  
	      HWPFDocument doc = new HWPFDocument(is);  
	      Range range = doc.getRange();  
	      //把range范围内的${reportDate}替换为当前的日期  
	      range.replaceText("${fpDate}", bean.getFpDate());  
	      range.replaceText("${fpName}", bean.getFpName());  
	      range.replaceText("${fpBankName}", bean.getFpBankName());  
	      range.replaceText("${fpMoney}", bean.getFpMoney()+"");  
	      range.replaceText("${fpNo}", bean.getFpNo());  
	      range.replaceText("${mallNo}", bean.getMallNo());  
	      range.replaceText("${zdNo}", bean.getZdNo());  
	      range.replaceText("${opNo}", bean.getOpNo());  
	      range.replaceText("${sdh}", bean.getSdh());  
	      range.replaceText("${expDate}", bean.getExpDate());  
	      range.replaceText("${pch}", bean.getPch());  
	      range.replaceText("${pzm}", bean.getPzm());  
	      range.replaceText("${sqm}", bean.getSqm());  
	      range.replaceText("${refNo}", bean.getRefNo());  
	      OutputStream os = new FileOutputStream(targtPath);  
	      //把doc输出到输出流中  
	      doc.write(os);  
	      closeStream(os);  
	      closeStream(is);  
	}
	
	
	public static void writeXpmx(String templatePath,String targtPath,XpmxBean bean) throws Exception{
		 InputStream is = new FileInputStream(templatePath);  
	      HWPFDocument doc = new HWPFDocument(is);  
	      Range range = doc.getRange();  
	      //把range范围内的${reportDate}替换为当前的日期  
	      range.replaceText("${mallName}", bean.getMallName());  
	      range.replaceText("${xpNo}", bean.getXpNo());  
	      range.replaceText("${xpTime}", bean.getXpTime());  
	      range.replaceText("${xpOpNo}", bean.getXpOpNo());  
	      range.replaceText("${detailContent}", bean.getDetailContent());  
	      range.replaceText("${sl}", bean.getSl());  
	      range.replaceText("${moneyAll}", bean.getMoneyAll());  
	      range.replaceText("${moneyPay}", bean.getMoneyPay());  
	      range.replaceText("${mallAddress}", bean.getMallAddress());  
	      OutputStream os = new FileOutputStream(targtPath);  
	      //把doc输出到输出流中  
	      doc.write(os);  
	      closeStream(os);  
	      closeStream(is);  
	}

	/** 
	    * 关闭输入流 
	    * @param is 
	    */  
	   private static void closeStream(InputStream is) {  
	      if (is != null) {  
	         try {  
	            is.close();  
	         } catch (IOException e) {  
	            e.printStackTrace();  
	         }  
	      }  
	   }  
	   
	   /** 
	    * 关闭输出流 
	    * @param os 
	    */  
	   private static void closeStream(OutputStream os) {  
	      if (os != null) {  
	         try {  
	            os.close();  
	         } catch (IOException e) {  
	            e.printStackTrace();  
	         }  
	      }  
	   } 
}
