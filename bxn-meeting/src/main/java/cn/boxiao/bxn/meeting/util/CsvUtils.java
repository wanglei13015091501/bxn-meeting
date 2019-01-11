/**
 * Copyright 2016-2018 boxiao.cn
 */
package cn.boxiao.bxn.meeting.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author liumeng
 * @since bxc-space 1.0 2016年4月19日
 */
public class CsvUtils {

	/**
	 * 将List转换为逗号分隔的字符串
	 * 
	 * @param valueCollection
	 * @return
	 */
	public static String convertCollectionToCSV(Collection<String> valueCollection) {

		if (CollectionUtils.isEmpty(valueCollection)) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		for (String value : valueCollection) {
			builder.append(value);
			builder.append(",");
		}
		builder.deleteCharAt(builder.length() - 1);

		return builder.toString();

	}

	/**
	 * 将CSV（Comma-seprate Value）转换为List
	 * 
	 * @param valueCSV
	 * @return
	 */
	public static List<String> convertCSVToList(String valueCSV) {

		List<String> valueList = Lists.newArrayList();
		if (StringUtils.isBlank(valueCSV)) {
			return valueList;
		}

		String[] valueStrAry = valueCSV.split(",");
		for (String valueStr : valueStrAry) {
			if (StringUtils.isBlank(valueCSV)) {
				continue;
			}

			valueList.add(valueStr);
		}

		return valueList;
	}

	/**
	 * 从CSV中去除某个值
	 * 
	 * @param value
	 * @param fromCsv
	 * @return
	 */
	public static String removeValue(String value, String fromCsv) {
		Set<String> valueSet = convertCSVToSet(fromCsv);
		valueSet.remove(value);
		return convertCollectionToCSV(valueSet);
	}

	/**
	 * 将某个值补充到Csv
	 * 
	 * @param value
	 * @param toCsv
	 * @return
	 */
	public static String appendValue(String value, String toCsv) {
		Set<String> valueSet = convertCSVToSet(toCsv);
		valueSet.add(value);
		return convertCollectionToCSV(valueSet);
	}

	/**
	 * 获取csv中的value值数量
	 * 
	 * @param valueCsv
	 * @return
	 */
	public static int countValues(String valueCsv) {
		return convertCSVToSet(valueCsv).size();
	}

	private static Set<String> convertCSVToSet(String valueCSV) {
		Set<String> valueSet = Sets.newLinkedHashSet();
		if (StringUtils.isBlank(valueCSV)) {
			return valueSet;
		}

		String[] valueStrAry = valueCSV.split(",");
		for (String valueStr : valueStrAry) {
			if (StringUtils.isBlank(valueCSV)) {
				continue;
			}

			valueSet.add(valueStr);
		}

		return valueSet;
	}

}
