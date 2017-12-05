package com.invoice.util;

public class CodeUtil {
	
	/**
	 * 获取操作编号
	 * @return
	 */
	public static String getOpno(){
		int no=(int) (Math.random()*200+1);
		return no+"";
	} 

	/**
	 * 获取凭证号
	 * @return
	 */
	public static String getPzh(){
		int no=(int) (Math.random()*100000+1);
		String pzh= no+"";
		for (int i = 0; i < 6-pzh.length(); i++) {
			pzh="0"+pzh;
		}
		
		
		return pzh;
	} 
	
	
	public static String getRefNo(){
		
		String pzh="";
		for (int i = 0; i < 8; i++) {
			int no=(int) (Math.random()*10);
			pzh=pzh+no;
		}
		return pzh;
	} 
	public static String getSqm(){
		
		String pzh="";
		for (int i = 0; i < 6; i++) {
			int no=(int) (Math.random()*10);
			pzh=pzh+no;
		}
		return pzh;
	} 
	public static String getSdh(){
		
		String pzh="";
		for (int i = 0; i < 8; i++) {
			int no=(int) (Math.random()*10);
			pzh=pzh+no;
		}
		return pzh;
	} 
	public static String getMllNo(){
		
		String pzh="310440111";
		for (int i = 0; i < 6; i++) {
			int no=(int) (Math.random()*10);
			pzh=pzh+no;
		}
		return pzh;
	} 
	public static String getZdNo(){
		
		String pzh="8354";
		for (int i = 0; i <5; i++) {
			int no=(int) (Math.random()*10);
			pzh=pzh+no;
		}
		return pzh;
	} 
	
	
	public static void main(String[] args) {
//		System.out.println(CodeUtil.getOpno());
//		System.out.println(CodeUtil.getPzh());
		System.out.println(CodeUtil.getMllNo());
	}
}
