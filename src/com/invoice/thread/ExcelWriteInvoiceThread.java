package com.invoice.thread;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.invoice.excel.ExcelFileParser;
import com.invoice.util.AddressConsont;
import com.invoice.util.CodeUtil;
import com.invoice.util.DateTimeUtil;
import com.invoice.word.ShopsDetailBean;
import com.invoice.word.WordWriter;
import com.invoice.word.WriteBean;
import com.invoice.word.XpmxBean;

/**
 * 生成发票线程
 * 
 * @author Administrator
 *
 */
public class ExcelWriteInvoiceThread implements Runnable {

	private String tempTime;
	
	@Override
	public void run() {
		if (AddressConsont.MONEYADDRESS == null || AddressConsont.MONEYADDRESS.length() == 0) {
			return;
		}
		if (AddressConsont.MALLADDRESS == null || AddressConsont.MALLADDRESS.length() == 0) {
			return;
		}
		if (AddressConsont.USERADDRESS == null || AddressConsont.USERADDRESS.length() == 0) {
			return;
		}
		if (AddressConsont.INVOICEADDRESS == null || AddressConsont.INVOICEADDRESS.length() == 0) {
			return;
		}
		if (AddressConsont.TEMPLATE == null || AddressConsont.TEMPLATE.length() == 0) {
			return;
		}

		List<List<String>> listMoney = ExcelFileParser.readExcel(AddressConsont.MONEYADDRESS.toString());
		// for (int i = 0; i < listMoney.size(); i++) {
		// List<String> row = listMoney.get(i);
		// for (int j = 0; j < row.size(); j++) {
		// System.out.print(row.get(j) + "\t");
		// }
		// System.out.println();
		// }

		// System.out.println("商户信息==================================");
		List<List<String>> listMall = ExcelFileParser.readExcel(AddressConsont.MALLADDRESS.toString());
		Map<String, Object> mallInfo = new HashMap<>();
		for (int i = 0; i < listMall.size(); i++) {
			List<String> row = listMall.get(i);
			mallInfo.put(row.get(2).trim(), row);
		}

		// for (int i = 0; i < listMall.size(); i++) {
		// List<String> row = listMall.get(i);
		// for (int j = 0; j < row.size(); j++) {
		// System.out.print(row.get(j) + "\t");
		// }
		// System.out.println();
		// }

		List<List<String>> users = ExcelFileParser.readExcel(AddressConsont.USERADDRESS.toString());
		Map<String, List<String>> userinfo = new HashMap<>();
		for (int i = 0; i < users.size(); i++) {
			List<String> row = users.get(i);
			userinfo.put(row.get(0).trim(), row);
		}

		
		
		for (int i = 0; i < listMoney.size(); i++) {
			List<String> row = listMoney.get(i);
			if (i % 2 == 0) {
				List<String> mall = getMall(row.get(2).trim(), listMall);
				if(mall==null){
					System.out.println(row.get(2).trim()+" 这个商场找不到");
					continue;
				}
				WriteBean wBean = new WriteBean();
				wBean.setFpName(mall.get(0));
				wBean.setAddress(mall.get(1));
				
				wBean.setPerson(row.get(0));

				tempTime=DateTimeUtil.formatAsPattern(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
				
				if (!"####".equals(row.get(1))) {
					wBean.setFpDate(DateTimeUtil.formatHHMMSS2(row.get(1)) + " " + DateTimeUtil.getTimes());
				}

				// if(mall.size()>10){
				// wBean.setMallNo(mall.get(10));
				// }
				wBean.setMallNo(CodeUtil.getMllNo());
				// if(mall.size()>11){
				// wBean.setZdNo(mall.get(11));
				// }
				wBean.setZdNo(CodeUtil.getZdNo());
				// if(mall.size()>13){
				// wBean.setSdh(mall.get(13));
				// }
				wBean.setSdh(CodeUtil.getSdh());
				// if(mall.size()>16){
				// wBean.setPch(mall.get(16));
				// }
				wBean.setPch(CodeUtil.getPzh());
				// if(mall.size()>14){
				// wBean.setSqm(mall.get(14));
				// }
				wBean.setSqm(CodeUtil.getSqm());
				// if(mall.size()>15){
				// wBean.setRefNo(mall.get(15));
				// }
				wBean.setRefNo(CodeUtil.getRefNo());
				// if(mall.size()>17){
				// wBean.setPzm(mall.get(17));
				// }
				wBean.setPzm(CodeUtil.getPzh());
				// if(mall.size()>12){
				// wBean.setOpNo(mall.get(12));
				// }
				wBean.setOpNo(CodeUtil.getOpno());

				List<String> u = userinfo.get(row.get(0).trim());
				if(u==null){
					System.out.println(row.get(0).trim() +"  找不到这个人");
					continue;
				}
				String bankno = u.get(1) + "******" + u.get(2);
				wBean.setFpNo(bankno);
				wBean.setFpBankName(u.get(4));
				if (!"####".equals(u.get(3))) {
					wBean.setExpDate(DateTimeUtil.formatYYYYMM(u.get(3)));
				}

				wBean.setFpMoney(row.get(7));

				setMoney(wBean, mall);

				// String
				// targetPath=AddressConsont.INVOICEADDRESS+"\\"+row.get(0)+System.currentTimeMillis()+".doc";
				String targetPath = AddressConsont.INVOICEADDRESS + "\\" + wBean.getPerson()+wBean.getFpName()+DateTimeUtil.formatYYYYMMDDNO4NAME(wBean.getFpDate()) + ".doc";
				createFile(targetPath);
				try {
					// 先生成word文档
					WordWriter.writeFile(getTemPath(), targetPath, wBean);
					// 再写入记录小票文件
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void setMoney(WriteBean wBean, List<String> mall) {
		// String pathType="E:\\yl\\new\\type\\综合购物.xlsx";
		String pathType = AddressConsont.TEMPLATE + "\\type\\" + mall.get(5).trim() + ".xlsx";

		List<List<String>> listGoods = ExcelFileParser.readExcel(pathType);

		int total = (int) (Math.random() * 3) + 20;

		float allpay = 0;
		StringBuffer sBuffer = new StringBuffer();

		List<ShopsDetailBean> targetGoods = new ArrayList<>();

		float fpnmoney = Float.valueOf(wBean.getFpMoney()) * 6.87f;
		BigDecimal fpm = new BigDecimal(fpnmoney);
		fpnmoney = fpm.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

		boolean quailified = true;
		while (quailified) {
			allpay = 0;
			sBuffer = new StringBuffer();
			targetGoods.clear();

			int[] tempindex = randomCommon(0, listGoods.size(), total);
			for (int i = 0; i < tempindex.length; i++) {
				List<String> items = listGoods.get(tempindex[i]);

				int sl;
				if ("综合购物".equals(mall.get(5).trim()) || "化妆品".equals(mall.get(5).trim())) {
					sl = getRom();
				} else {
					sl = 1;
				}
				if ("综合购物".equals(mall.get(5).trim()) && i % 2 == 0 && tempindex[i] < 10000) {
					items = listGoods.get(tempindex[i] + 20000);
				} else {
					items = listGoods.get(tempindex[i]);
				}

				BigDecimal b = new BigDecimal(items.get(1));
				float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
				ShopsDetailBean sDetailBean = new ShopsDetailBean(items.get(0), sl, f1);
				targetGoods.add(sDetailBean);

				BigDecimal b2 = new BigDecimal(sDetailBean.getShopSl() * sDetailBean.getPrice());
				float f2 = b2.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();// 合计

				BigDecimal b3 = new BigDecimal(sDetailBean.getShopSl() * sDetailBean.getPrice());
				float f3 = b3.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();// 合计

				BigDecimal b4 = new BigDecimal(sDetailBean.getPrice());
				float f4 = b4.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();// 合计

				sBuffer.append(sDetailBean.getShopName() + "\r" + f4 + "\t\t\t" + sDetailBean.getShopSl() + "\t\t " + f3
						+ "\r");
				allpay += f2;

				if (fpnmoney > allpay && fpnmoney - allpay < 30) {
					BigDecimal end = new BigDecimal((fpnmoney - allpay));
					float fend = end.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

					sBuffer.append(listGoods.get(0).get(0) + "\r" + fend + "\t\t\t" + 1 + "\t\t " + fend + "\r");
					quailified = false;
					break;
				}
			}

		}

		System.out.println(wBean.getFpName() + "   " + fpnmoney + " 美元");
		

		DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p=decimalFormat.format(fpnmoney);//format 返回的是字符串
		
		wBean.setFpMoney(p);

		XpmxBean xbean = new XpmxBean();
		xbean.setDetailContent(sBuffer.toString());
		xbean.setMoneyAll(p);
		xbean.setMoneyPay(p);
		xbean.setSl((targetGoods.size() + 1) + "");

		xbean.setMallName(wBean.getFpName());
		xbean.setXpNo(DateTimeUtil.formatYYYYMMDDNO2(wBean.getFpDate()));
		xbean.setXpTime(DateTimeUtil.getTimesAfter2(wBean.getFpDate()));
		xbean.setXpOpNo(wBean.getOpNo());
		xbean.setMallAddress(wBean.getAddress());
		xbean.setPerson(wBean.getPerson());
		writeList(xbean);
	}

	private void writeList(XpmxBean xbean) {

		File file = new File(AddressConsont.INVOICEADDRESS + "\\list");

		if (!file.exists()) {
			file.mkdirs();
		}

		String targetPath = AddressConsont.INVOICEADDRESS + "\\list\\" + xbean.getPerson()+xbean.getMallName()+DateTimeUtil.formatYYYYMMDDNO4NAME(xbean.getXpTime()) + ".doc";
		createFile(targetPath);

		try {
			WordWriter.writeXpmx(AddressConsont.TEMPLATE + "\\xpmx.doc", targetPath, xbean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int getRom() {
		return (int) (Math.random() * 3) + 2;
	}

	/**
	 * 随机指定范围内N个不重复的数 最简单最基本的方法
	 * 
	 * @param min
	 *            指定范围最小值
	 * @param max
	 *            指定范围最大值
	 * @param n
	 *            随机数个数
	 */
	public static int[] randomCommon(int min, int max, int n) {
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		int[] result = new int[n];
		int count = 0;
		while (count < n) {
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true;
			for (int j = 0; j < n; j++) {
				if (num == result[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				result[count] = num;
				count++;
			}
		}
		return result;
	}

	private List<String> getMall(String key, List<List<String>> listMall) {
		for (List<String> list : listMall) {
			if (key.equals(list.get(2).trim())) {
				return list;
			}
		}
		return null;
	}

	private String getTemPath() {
		int index = (int) (Math.random() * 10) + 1;
		return AddressConsont.TEMPLATE + "\\fp" + index + ".doc";
	}

	/**
	 * 创建文件
	 * 
	 * @param filePath
	 */
	private void createFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
