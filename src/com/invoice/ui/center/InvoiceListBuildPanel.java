package com.invoice.ui.center;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.invoice.action.FileChooseListener;
import com.invoice.util.AddressEnum;

/**
 * 小票生成界面
 * @author Administrator
 *
 */
public class InvoiceListBuildPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton jButtonListFileAddress;
	private JTextField textListFileAddress;
	
	private JButton jButtonListSaveFileAddress;
	private JButton jButtonListTempSaveFileAddress;
	private JTextField textListSaveFileAddress;
	private JTextField textListTempFileAddress;
	
	public InvoiceListBuildPanel() {
		this.setLayout(null);
		this.setBackground(Color.decode("#f0f0f0"));
        
        
        setTheme();
        init();
	}
	
	private void init() {

		JLabel labelBegain=new JLabel("小票资料起始行：");
		labelBegain.setHorizontalAlignment(SwingConstants.RIGHT);
		labelBegain.setBounds(20, 20, 140, 20);
		JTextField textBegain=new JTextField("2");
		textBegain.setBounds(170, 20, 40, 20);
		
		JLabel labelMoneyFileAddress=new JLabel("小票资料存放地址：");
		labelMoneyFileAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		labelMoneyFileAddress.setBounds(20, 50, 140, 20);
		textListFileAddress=new JTextField();
		textListFileAddress.setBounds(170, 50, 340, 20);
		jButtonListFileAddress=new JButton("选择");
		jButtonListFileAddress.setMargin(new Insets(0,0,0,0));
		jButtonListFileAddress.setBackground(Color.BLUE);
		jButtonListFileAddress.setBounds(520, 50, 40, 20);
		
		JLabel labelMallFileAddress=new JLabel("清单文件存放地址：");
		labelMallFileAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		labelMallFileAddress.setBounds(20, 80, 140, 20);
		textListSaveFileAddress=new JTextField();
		textListSaveFileAddress.setBounds(170, 80, 340, 20);
		jButtonListSaveFileAddress=new JButton("选择");
		jButtonListSaveFileAddress.setMargin(new Insets(0,0,0,0));
		jButtonListSaveFileAddress.setBackground(Color.BLUE);
		jButtonListSaveFileAddress.setBounds(520, 80, 40, 20);
		
		JLabel labelTempAddress=new JLabel("清单文件模板地址：");
		labelTempAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTempAddress.setBounds(20, 110, 140, 20);
		
		textListTempFileAddress=new JTextField();
		textListTempFileAddress.setBounds(170, 110, 340, 20);
		jButtonListTempSaveFileAddress=new JButton("选择");
		jButtonListTempSaveFileAddress.setMargin(new Insets(0,0,0,0));
		jButtonListTempSaveFileAddress.setBackground(Color.BLUE);
		jButtonListTempSaveFileAddress.setBounds(520, 110, 40, 20);
		
		
		this.add(labelBegain);
		this.add(textBegain);
		
        this.add(labelMoneyFileAddress);
        this.add(labelMallFileAddress);
        
        this.add(labelTempAddress);
        this.add(textListTempFileAddress);
        
        this.add(jButtonListFileAddress);
        this.add(jButtonListSaveFileAddress);
        
        this.add(textListFileAddress);
        this.add(textListSaveFileAddress);
        this.add(jButtonListTempSaveFileAddress);
        
        addListener();
	}

	private void addListener() {
		jButtonListFileAddress.addActionListener(new FileChooseListener(textListFileAddress,AddressEnum.LISTADDRESS));
		jButtonListSaveFileAddress.addActionListener(new FileChooseListener(textListSaveFileAddress,AddressEnum.LISTSAVEADDRESS,true));
		jButtonListTempSaveFileAddress.addActionListener(new FileChooseListener(textListTempFileAddress,AddressEnum.LISTTEMPLATEADDRESS));
	}

	void setTheme(){
		if(UIManager.getLookAndFeel().isSupportedLookAndFeel()){
			final String platform = UIManager.getSystemLookAndFeelClassName();
			// If the current Look & Feel does not match the platform Look & Feel,
			// change it so it does.
			if (!UIManager.getLookAndFeel().getName().equals(platform)) {
				try {
					UIManager.setLookAndFeel(platform);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		}
	}
}
