package cn.boxiao.bxn.meeting.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.boxiao.bxn.common.BXQBusinessRuntimeException;
import cn.boxiao.bxn.common.Constants;

/**
 * 
 * 日期处理工具
 *
 * @author tanxy
 * @since 2014-5-15
 */
public class DateUtil {
	static Logger log = LoggerFactory.getLogger(DateUtil.class);

	public static Date parseYYYY_MM_DDDate(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			log.error(String.format("Input string strDate(%s) can't be parsed with yyyy-MM-dd format.", strDate), e);
			throw new BXQBusinessRuntimeException(Constants.ERR_ILLEGALMETHODPARAMEXCEPTION,
					"Input string strDate(%s) can't be parsed with yyyy-MM-dd format.", strDate);
		}
	}

	public static Date parseYYYYMMDDDate(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			log.error(String.format("Input string strDate(%s) can't be parsed with yyyyMMdd format.", strDate), e);
			throw new BXQBusinessRuntimeException(Constants.ERR_ILLEGALMETHODPARAMEXCEPTION,
					"Input string strDate(%s) can't be parsed with yyyyMMdd format.", strDate);
		}
	}

	public static Date parseYYYY_MM_DD_HH_MM_SSDate(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			log.error(String.format("Input string strDate(%s) can't be parsed with yyyyMMdd format.", strDate), e);
			throw new BXQBusinessRuntimeException(Constants.ERR_ILLEGALMETHODPARAMEXCEPTION,
					"Input string strDate(%s) can't be parsed with yyyy-MM-dd HH:mm:ss format.", strDate);
		}
	}

	public static Date parseLong(Long longDate) {
		return new Date(longDate);
	}

	public static Long toLong(Date date) {
		return date.getTime();
	}

	public static String toString_YYYY_MM_DD(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static String toString_YYYY_MM_DD_HH_MM_SS(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static Date beforeDayDate(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_MONTH, -1);

		return c.getTime();
	}

	/**
	 * 获取一周前的日期
	 * 
	 * @param d
	 * @return
	 */
	public static Date beforeWeekDate(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_MONTH, -7);

		return c.getTime();
	}

	/**
	 * 获取一个月前的日期
	 * 
	 * @param d
	 * @return
	 */
	public static Date beforeMonthDate(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH, -1);

		return c.getTime();
	}
	
	
	       /**
	        * 获取字符串时间的相减值
	        * @param time
	        * @param minutes
	        * @return
	        */
	       public  static String getReductionMinutesTime(String time, Integer minutes){  
		     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		     
		     Calendar calendar = null;
		     Date dateOld = null;
		  try {         
		   dateOld = df.parse(time);
		   calendar = Calendar.getInstance();
		   calendar.setTime(dateOld);
		   calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE) - minutes);   
		  } catch (java.text.ParseException e) {
			  log.error("getReductionMinutesTime failed",e);
		  }
		  return df.format(calendar.getTime());
		    }
		    
		    
		    /**
		     * 获取字符串时间相加
		     * @param time
		     * @param minutes
		     * @return
		     */
		    public static  String getAddMinutesTime(String time, Integer minutes){  
			     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			     
			     
			     Calendar calendar = null;
			     Date dateOld = null;
			  try {         
			   dateOld = df.parse(time);
			   calendar = Calendar.getInstance();
			   calendar.setTime(dateOld);
			   calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE) + minutes);   
			  } catch (java.text.ParseException e) {
				  log.error("getAddMinutesTime failed",e);

			  }
			  return df.format(calendar.getTime());
			    }
		    

}
