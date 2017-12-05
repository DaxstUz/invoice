package com.invoice.ui.operate;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.invoice.thread.ExcelWriteInvoiceListThread;
import com.invoice.thread.ExcelWriteInvoiceThread;
import com.invoice.util.TabConsont;
import com.invoice.word.MSWordPoi4;

public class MyOperatePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton jstart;
	private JButton jstop;
	private JButton jbreak;
	private JButton jset;
	
	private JButton jsave;
	private JButton jreset;

	public MyOperatePanel() {
		this.setLayout(null);
		this.setBackground(Color.pink);
		init();
		
		addListener();
	}

	private void addListener() {
		jstart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (TabConsont.selectIndex) {
				case 0:
					new  Thread(new ExcelWriteInvoiceThread()).start();
					break;
				case 1:
					new  Thread(new ExcelWriteInvoiceListThread()).start();
					break;

				default:
					break;
				}
				
//				MSWordPoi4.www();
				}
		});
	}

	private void init() {
		jstart=new JButton("启动");
		jstop=new JButton("暂停");
		jbreak=new JButton("终止");
		jset=new JButton("热键设置");
		jsave=new JButton("保存");
		jreset=new JButton("还原");
		
		jstart.setBounds(10, 10, 100, 40);
		jstop.setBounds(120, 10, 100, 40);
		jbreak.setBounds(230, 10, 100, 40);
		jset.setBounds(340, 10, 80, 40);
		jset.setMargin(new Insets(0,0,0,0));
		jsave.setBounds(460, 10, 80, 40);
		jreset.setBounds(550, 10, 80, 40);
		this.add(jstart);
		this.add(jstop);
		this.add(jbreak);
		this.add(jset);
		this.add(jsave);
		this.add(jreset);
	}
}
