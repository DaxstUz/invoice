package com.invoice.ui.head;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class HeadPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel tips;
	
	public HeadPanel() {
		this.setLayout(null);
		this.setBackground(Color.decode("#25ab5e"));
		tips=new JLabel("  您好 欢迎使用本产品!");
		tips.setForeground(Color.WHITE);
		tips.setBounds(0, 10, 650, 50);
		this.add(tips);
	}
}
