/**
 * Copyright 2016-2018 boxiao.cn
 */
package cn.boxiao.bxn.meeting.util;

import java.text.DecimalFormat;

/**
 * @author liumeng
 * @since bxc 1.0 2017年4月25日
 */
public class TextUtils {

	public static String computeNormalRates(int normalNum, int needJoinNum) {

		// 计算全勤率
		double normalNumDouble = normalNum * 1.0;
		double normalRates = 0d;
		if (needJoinNum != 0) {
			normalRates = normalNumDouble / needJoinNum;
		}

		DecimalFormat df1 = new DecimalFormat("0.00%");
		return df1.format(normalRates);
	}

	public static int compare(String str1, String str2) {
		return compare(str1, str2, true);
	}

	private static int compare(final String str1, final String str2, final boolean nullIsLess) {
		if (str1 == str2) {
			return 0;
		}
		if (str1 == null) {
			return nullIsLess ? -1 : 1;
		}
		if (str2 == null) {
			return nullIsLess ? 1 : -1;
		}
		return str1.compareTo(str2);
	}
}
