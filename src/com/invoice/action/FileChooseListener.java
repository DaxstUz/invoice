package com.invoice.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.invoice.util.AddressConsont;
import com.invoice.util.AddressEnum;

public class FileChooseListener implements ActionListener{

	private JTextField textField;
	
	private AddressEnum address;
	
	public FileChooseListener(JTextField textField,AddressEnum address) {
		this.textField=textField;
		this.address=address;
	}
	
	private boolean onlyFile=false;//判断是否只选择文件夹
	public FileChooseListener(JTextField textField,AddressEnum address,boolean onlyFile) {
		this.textField=textField;
		this.address=address;
		this.onlyFile=onlyFile;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JFileChooser fcDlg = new JFileChooser();
		if(onlyFile){
			fcDlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY );  
		}else{
			fcDlg.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
		}
		fcDlg.showDialog(new JLabel(), "选择");  
		 
		File file=fcDlg.getSelectedFile(); 
		if(file!=null){
//			 if(file.isDirectory()){  
//		            System.out.println("文件夹:"+file.getAbsolutePath());  
//		        }else if(file.isFile()){  
		            System.out.println("文件:"+file.getAbsolutePath());  
		            textField.setText(file.getAbsolutePath());
		            setAddress(file.getAbsolutePath());
//		        } 
		}
		
	}

	/**
	 * 设置文件地址
	 * @param path
	 */
	private void setAddress(String path){
		 if(address.equals(AddressEnum.USERADDRESS)){
			 AddressConsont.USERADDRESS=path;
         }
		 if(address.equals(AddressEnum.LISTADDRESS)){
			 AddressConsont.LISTADDRESS=path;
		 }
		 if(address.equals(AddressEnum.LISTSAVEADDRESS)){
			 AddressConsont.LISTSAVEADDRESS=path;
		 }
		 if(address.equals(AddressEnum.MALLADDRESS)){
			 AddressConsont.MALLADDRESS=path;
		 }
		 if(address.equals(AddressEnum.MONEYADDRESS)){
			 AddressConsont.MONEYADDRESS=path;
		 }
		 if(address.equals(AddressEnum.INVOICEADDRESS)){
			 AddressConsont.INVOICEADDRESS=path;
		 }
		 if(address.equals(AddressEnum.TEMPLATE)){
			 AddressConsont.TEMPLATE=path;
		 }
		 if(address.equals(AddressEnum.LISTTEMPLATEADDRESS)){
			 AddressConsont.LISTTEMPLATEADDRESS=path;
		 }
	}
}
