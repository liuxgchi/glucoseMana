package com.common.library.util;

import java.math.BigDecimal;

/**
 * 修改格式 工具类
 * @author renheng
 *
 */
public class FormatUtil {

	/**
	 * 将double类型的数字改为保留两位小数
	 * @param number要格式的数字
	 * @return
	 */
	public static double getTwoNumber(double number){
		BigDecimal b=new BigDecimal(number);
		double d=b.setScale(2, 4).doubleValue();
		return d;
	}
}
