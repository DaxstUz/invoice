package com.invoice.test;

import com.invoice.thread.ExcelWriteInvoiceThread;

public class TestThread {

	public static void main(String[] args) {
		new  Thread(new ExcelWriteInvoiceThread()).start();
	}
}
