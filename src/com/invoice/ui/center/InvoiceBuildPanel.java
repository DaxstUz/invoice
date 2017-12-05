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
public class InvoiceBuildPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton jButtonMoneyFileAddress;
	private JTextField textMoneyFileAddress;
	
	
	private JButton jButtonInvoiceFileAddress;
	private JButton jButtoncomplateFileAddress;
	private JTextField textInvoiceFileAddress;
	private JTextField complateFileAddress;
	
	private JButton jButtonMallFileAddress;
	private JTextField textMallFileAddress;
	
	private JButton jButtonUserFileAddress;
	private JTextField textUserFileAddress;
	
	public InvoiceBuildPanel() {
		this.setLayout(null);
		this.setBackground(Color.decode("#f0f0f0"));
        
        
        setTheme();
        init();
	}
	
	private void init() {

		JLabel labelBegain=new JLabel("入金表格起始行：");
		labelBegain.setHorizontalAlignment(SwingConstants.RIGHT);
		labelBegain.setBounds(20, 20, 140, 20);
		JTextField textBegain=new JTextField("2");
		textBegain.setBounds(170, 20, 40, 20);
		JLabel labelRecordBegain=new JLabel("表格记录起始行：");
		labelRecordBegain.setBounds(220, 20, 140, 20);
		JTextField textRecordBegain=new JTextField("2");
		textRecordBegain.setBounds(330, 20, 40, 20);
		JLabel labelRmb=new JLabel("美金RMB汇率：");
		labelRmb.setBounds(380, 20, 140, 20);
		JTextField textRmb=new JTextField("6.87");
		textRmb.setBounds(470, 20, 40, 20);
		
		
		
		JLabel labelMoneyFileAddress=new JLabel("入金文件地址：");
		labelMoneyFileAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		labelMoneyFileAddress.setBounds(20, 50, 140, 20);
		textMoneyFileAddress=new JTextField();
		textMoneyFileAddress.setBounds(170, 50, 340, 20);
		jButtonMoneyFileAddress=new JButton("选择");
		jButtonMoneyFileAddress.setMargin(new Insets(0,0,0,0));
		jButtonMoneyFileAddress.setBackground(Color.BLUE);
		jButtonMoneyFileAddress.setBounds(520, 50, 40, 20);
		
		JLabel labelMallFileAddress=new JLabel("商家资料存放地址：");
		labelMallFileAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		labelMallFileAddress.setBounds(20, 80, 140, 20);
		textMallFileAddress=new JTextField();
		textMallFileAddress.setBounds(170, 80, 340, 20);
		jButtonMallFileAddress=new JButton("选择");
		jButtonMallFileAddress.setMargin(new Insets(0,0,0,0));
		jButtonMallFileAddress.setBackground(Color.BLUE);
		jButtonMallFileAddress.setBounds(520, 80, 40, 20);
		
		
		JLabel labelUserFileAddress=new JLabel("用户文件地址：");
		labelUserFileAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		labelUserFileAddress.setBounds(20, 110, 140, 20);
		textUserFileAddress=new JTextField();
		textUserFileAddress.setBounds(170, 110, 340, 20);
		jButtonUserFileAddress=new JButton("选择");
		jButtonUserFileAddress.setMargin(new Insets(0,0,0,0));
		jButtonUserFileAddress.setBackground(Color.BLUE);
		jButtonUserFileAddress.setBounds(520, 110, 40, 20);
		
		JLabel labelInvoiceFileAddress=new JLabel("小票存放地址：");
		labelInvoiceFileAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		labelInvoiceFileAddress.setBounds(20, 140, 140, 20);
		textInvoiceFileAddress=new JTextField();
		textInvoiceFileAddress.setBounds(170, 140, 340, 20);
		jButtonInvoiceFileAddress=new JButton("选择");
		jButtonInvoiceFileAddress.setMargin(new Insets(0,0,0,0));
		jButtonInvoiceFileAddress.setBackground(Color.BLUE);
		jButtonInvoiceFileAddress.setBounds(520, 140, 40, 20);
		
		
		JLabel complate=new JLabel("模板地址：");
		complate.setHorizontalAlignment(SwingConstants.RIGHT);
		complate.setBounds(20, 170, 140, 20);
		complateFileAddress=new JTextField();
		complateFileAddress.setBounds(170, 170, 340, 20);
		jButtoncomplateFileAddress=new JButton("选择");
		jButtoncomplateFileAddress.setMargin(new Insets(0,0,0,0));
		jButtoncomplateFileAddress.setBackground(Color.BLUE);
		jButtoncomplateFileAddress.setBounds(520, 170, 40, 20);
		
		this.add(labelBegain);
		this.add(textBegain);
		this.add(labelRecordBegain);
		this.add(textRecordBegain);
		this.add(labelRmb);
		this.add(textRmb);
		
        this.add(labelMoneyFileAddress);
        this.add(labelMallFileAddress);
        this.add(labelUserFileAddress);
        this.add(labelInvoiceFileAddress);
        this.add(complate);
        this.add(complateFileAddress);
        this.add(jButtonMoneyFileAddress);
        this.add(jButtonMallFileAddress);
        this.add(jButtonUserFileAddress);
        this.add(jButtonInvoiceFileAddress);
        this.add(jButtoncomplateFileAddress);
        
        this.add(textMoneyFileAddress);
        this.add(textMallFileAddress);
        this.add(textUserFileAddress);
        this.add(textInvoiceFileAddress);
        
        addListener();
	}

	private void addListener() {
		jButtonInvoiceFileAddress.addActionListener(new FileChooseListener(textInvoiceFileAddress,AddressEnum.INVOICEADDRESS,true));
		jButtonMoneyFileAddress.addActionListener(new FileChooseListener(textMoneyFileAddress,AddressEnum.MONEYADDRESS));
		jButtonMallFileAddress.addActionListener(new FileChooseListener(textMallFileAddress,AddressEnum.MALLADDRESS));
		jButtonUserFileAddress.addActionListener(new FileChooseListener(textUserFileAddress,AddressEnum.USERADDRESS));
		jButtoncomplateFileAddress.addActionListener(new FileChooseListener(complateFileAddress,AddressEnum.TEMPLATE,true));
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
