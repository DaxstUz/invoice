package com.invoice.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.OverlayLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.invoice.ui.bottom.MyBottomPanel;
import com.invoice.ui.center.InvoiceBuildPanel;
import com.invoice.ui.center.InvoiceListBuildPanel;
import com.invoice.ui.head.HeadPanel;
import com.invoice.ui.operate.MyOperatePanel;
import com.invoice.util.TabConsont;

public class InvoiceStart extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JPanel jPanel;
	
	private MyBottomPanel bottomPanel;
	
	private MyOperatePanel operatePanel;
	
	private InvoiceBuildPanel invoiceBuildPanel;
//	private InvoiceListBuildPanel invoiceListBuildPanel;
	
	private HeadPanel headPanel;
	
	private JTabbedPane tabs;
	
	private JPanel p = new JPanel();
	
	public InvoiceStart() {
		addConpone();
		initLocation();
	}

	private void addConpone() {
		
		tabs = new ClippedTitleTabbedPane();
		
//		tabs.addTab("asdfasd", new JLabel("456746"));
//        tabs.addTab("1234123", new JScrollPane(new JTree()));
		
		jPanel=new JPanel(null);
		
		headPanel=new HeadPanel();
		headPanel.setBounds(0, 0, 650, 60);
		jPanel.add(headPanel);
		
		invoiceBuildPanel=new InvoiceBuildPanel();
//		invoiceListBuildPanel=new InvoiceListBuildPanel();
//		invoiceBuildPanel.setBounds(0, 60, 650, 310);
//		invoiceBuildPanel.setBounds(0, 0, 650, 310);
//		jPanel.add(invoiceBuildPanel);
		
		p.setBounds(0, 60, 650, 310);
		tabs.addTab("小票生成", invoiceBuildPanel);
//		tabs.addTab("清单生成", invoiceListBuildPanel);
		
		tabs.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println(tabs.getSelectedIndex());
				System.out.println(tabs.getTitleAt(tabs.getSelectedIndex()));
				
				TabConsont.selectIndex=tabs.getSelectedIndex();
			}
		});
		 
		p.setLayout(new OverlayLayout(p));
		 
		tabs.setAlignmentX(0.0f);
	    tabs.setAlignmentY(0.0f);
	    
	    p.add(tabs);
	    
		jPanel.add(p);
		
		operatePanel=new MyOperatePanel();
		operatePanel.setBounds(0, 370, 650, 60);
		jPanel.add(operatePanel);
		
		bottomPanel=new MyBottomPanel();
		bottomPanel.setBounds(0, 430, 650, 80);
		jPanel.add(bottomPanel);
		this.add(jPanel);
		
		
	}

	private void initLocation() {
		this.setBounds(200, 100, 650, 510);
		this.setTitle("发票生成器1.0.1");
		this.setVisible(true);
//		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	}
	public static void main(String[] args) {
		new InvoiceStart();
	}

}
