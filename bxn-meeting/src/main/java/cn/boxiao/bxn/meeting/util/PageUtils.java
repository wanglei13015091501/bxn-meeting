/**
 * Copyright 2016-2018 boxiao.cn
 */
package cn.boxiao.bxn.meeting.util;

import java.util.Iterator;

import cn.boxiao.bxn.base.model.Pageable;
import cn.boxiao.bxn.base.model.Sort.Order;

/**
 * @author liumeng
 * @since bxc-space 1.0 2016年8月16日
 */
public class PageUtils {

	/**
	 * 填充分页信息
	 * 
	 * @param sql
	 * @param page
	 */
	public static void appendPage(StringBuilder sql, Pageable page) {
		if (page.getSort() != null) {
			sql.append(" order by "); // order
			for (Iterator<Order> orderIt = page.getSort().iterator(); orderIt.hasNext();) {
				Order order = orderIt.next();
				sql.append(order.getProperty()).append(" ").append(order.getDirection());
				if (orderIt.hasNext()) {
					sql.append(",");
				}
			}
		}
		sql.append(" limit ").append(page.getOffset()).append(" , ").append(page.getPageSize());
	}
}
