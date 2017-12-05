package com.invoice.thread;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.invoice.excel.ExcelFileParser;
import com.invoice.util.AddressConsont;
import com.invoice.util.DateTimeUtil;
import com.invoice.word.ShopsDetailBean;
import com.invoice.word.WordWriter;
import com.invoice.word.XpmxBean;

/**
 * 生成清单线程
 * 
 * @author Administrator
 *
 */
public class ExcelWriteInvoiceListThread implements Runnable {

	@Override
	public void run() {
		if (AddressConsont.LISTSAVEADDRESS == null || AddressConsont.LISTSAVEADDRESS.length() == 0) {
			return;
		}
		if (AddressConsont.LISTADDRESS == null || AddressConsont.LISTADDRESS.length() == 0) {
			return;
		}
		if (AddressConsont.LISTTEMPLATEADDRESS == null || AddressConsont.LISTTEMPLATEADDRESS.length() == 0) {
			return;
		}

		List<List<String>> list = ExcelFileParser.readExcel(AddressConsont.LISTADDRESS.toString());

		for (int i = 0; i < list.size(); i++) {
			XpmxBean xbean = new XpmxBean();
			List<String> row = list.get(i);

			xbean.setMallName(row.get(1));
			xbean.setXpNo(DateTimeUtil.formatYYYYMMDDNO(row.get(6)));
			xbean.setXpTime(DateTimeUtil.formatYYYYMMDD(row.get(6)) + " " + DateTimeUtil.formatHHMMSS(row.get(7)));
			xbean.setXpOpNo(row.get(8));
			// xbean.setMoneyAll(row.get(9));
			// xbean.setMoneyPay(row.get(9));
			xbean.setMallAddress(row.get(4));

			List<ShopsDetailBean> data = getDetail();
			xbean.setSl(data.size() + "");

			float allpay = 0;
			StringBuffer sBuffer = new StringBuffer();
			for (ShopsDetailBean shopsDetailBean : data) {
				sBuffer.append(shopsDetailBean.getShopName() + "\r" + shopsDetailBean.getPrice() + "\t\t\t"
						+ shopsDetailBean.getShopSl() + "\t\t "
						+ (float) (shopsDetailBean.getShopSl() * shopsDetailBean.getPrice()) + "\r");
				allpay += (float) (shopsDetailBean.getShopSl() * shopsDetailBean.getPrice());
			}

			BigDecimal b = new BigDecimal(allpay);
			float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
			
			xbean.setDetailContent(sBuffer.toString());
			xbean.setMoneyAll(f1 + "");
			xbean.setMoneyPay(f1 + "");

			String targetPath = AddressConsont.LISTSAVEADDRESS + "\\" + row.get(0) + ".doc";
			createFile(targetPath);

			try {
				WordWriter.writeXpmx(AddressConsont.LISTTEMPLATEADDRESS, targetPath, xbean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

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

	public List<ShopsDetailBean> getDetail() {
		ShopsDetailBean sDetailBean = new ShopsDetailBean("越南进口中原G7 卡布奇诺咖啡108g摩卡味（6条*18g）", 1, 9.90f);
		sDetailBean.setShopSl(getRom());
		ShopsDetailBean sDetailBean1 = new ShopsDetailBean("台湾进口国农草莓味牛乳饮品4连包215ml*4/组果味", 2, 21.90f);
		sDetailBean1.setShopSl(getRom());
		ShopsDetailBean sDetailBean2 = new ShopsDetailBean("金号枕巾 特价纯棉素色螺旋绣花温馨居家情侣枕头巾", 1, 29.00f);
		sDetailBean2.setShopSl(getRom());
		ShopsDetailBean sDetailBean3 = new ShopsDetailBean("清风原木纯品3层90克*40卷无芯卷纸卫生纸巾套装", 1, 4.90f);
		sDetailBean3.setShopSl(getRom());
		ShopsDetailBean sDetailBean4 = new ShopsDetailBean("花弄影卷纸 卫生纸 3层100克/卷x36卷长无芯 纸巾", 1, 48.00f);
		sDetailBean4.setShopSl(getRom());
		ShopsDetailBean sDetailBean5 = new ShopsDetailBean("英吉利婴幼儿牛肉番茄营养面条宝宝儿童辅食零食", 1, 29.50f);
		sDetailBean5.setShopSl(getRom());
		ShopsDetailBean sDetailBean7 = new ShopsDetailBean("康婴健婴儿奶瓶 宽口径奶瓶 防吐防滑PP奶瓶240ml", 1, 46.00f);
		sDetailBean7.setShopSl(getRom());
		ShopsDetailBean sDetailBean8 = new ShopsDetailBean("清风 原木纯品2层200抽*6包软包抽取式面纸巾", 1, 59.90f);
		sDetailBean8.setShopSl(getRom());
		ShopsDetailBean sDetailBean9 = new ShopsDetailBean("久量LED光控感应小夜灯429床头睡眠情景灯宝宝喂奶灯", 1, 6.00f);
		sDetailBean9.setShopSl(getRom());

		List<ShopsDetailBean> data = new ArrayList<>();
		data.add(sDetailBean);
		data.add(sDetailBean1);
		data.add(sDetailBean2);
		data.add(sDetailBean3);
		data.add(sDetailBean4);
		data.add(sDetailBean5);
		data.add(sDetailBean7);
		data.add(sDetailBean8);
		data.add(sDetailBean9);

		int total = (int) (Math.random() * 6) + 2;

		List<ShopsDetailBean> temp = new ArrayList<>();
		int[] tempindex = randomCommon(0, 8, total);
		for (int i = 0; i < tempindex.length; i++) {
			temp.add(data.get(tempindex[i]));
		}

		return temp;
	}

	private int getRom() {
		return (int) (Math.random() * 14) + 1;
	}

	public static void main(String[] args) {
		int a = 6;
		double b = 9.9;
		System.out.println((int) (Math.random() * 6) + 3);
		// System.out.println((float)(a*b));
		// System.out.println((int)(Math.random()*8)+1);

		// System.out.println(randomCommon(1,9,5).toString());
		// int[] temp=randomArray(0, 8, 2);
		// int[] temp=randomCommon(0,8,5);
		// for (int i = 0; i < temp.length; i++) {
		// System.out.print(temp[i]+" ");
		// }
		// System.out.println(temp);
	}

	/**
	 *  * 随机指定范围内N个不重复的数  * 在初始化的无重复待选数组中随机产生一个数放入结果中，  *
	 * 将待选数组被随机到的数，用待选数组(len-1)下标对应的数替换  * 然后从len-2里随机产生下一个随机数，如此类推  * @param
	 * max 指定范围最大值  * @param min 指定范围最小值  * @param n 随机数个数  * @return int[]
	 * 随机数结果集  
	 */
	public static int[] randomArray(int min, int max, int n) {
		int len = max - min + 1;
		if (max < min || n > len) {
			return null;
		}
		// 初始化给定范围的待选数组
		int[] source = new int[len];
		for (int i = min; i < min + len; i++) {
			source[i - min] = i;
		}
		int[] result = new int[n];
		Random rd = new Random();
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			// 待选数组0到(len-2)随机一个下标
			index = Math.abs(rd.nextInt() % len--);
			// 将随机到的数放入结果集
			result[i] = source[index];
			// 将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
			source[index] = source[len];
		}
		return result;
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

}
