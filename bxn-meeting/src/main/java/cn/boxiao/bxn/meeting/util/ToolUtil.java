/*******************************************************************************
 *    系统名称   ： 博校文达
 *    开发部门   ： 开发部
 *    文件名     ： ToolUtil.java
 *             
 ******************************************************************************/
package cn.boxiao.bxn.meeting.util;

import java.io.File;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.boxiao.bxn.common.FileOperator;
import cn.boxiao.bxn.common.util.IDPhotoUtil;
import cn.boxiao.bxn.common.util.PropertiesAccessorUtil;

/**
 * 公共方法
 * @author zhangfuxun 
 * @since  2016年4月16日
 */
public class ToolUtil {
	/**
	 * 
	 * 判断字符串是否为空。
     * <p>宽松判断字符串是否为空。为空的标准  [str==null or str.length()==0 && 空格]</p>
	 * @param str
	 * @return
	 *
	 * @author zhangfuxun 
	 * @since 2016年4月16日下午4:22:09
	 */
	public static boolean isBlank(String str) {
	    	if("null".equals(str)){
	    		return true;
	    	}
        return org.apache.commons.lang.StringUtils.isBlank(str);
    }
	/**
	 * 
	 * 判断传入的对象是否为空。
	 * @param o
	 * @return
	 *
	 * @author zhangfuxun 
	 * @since 2016年4月16日下午4:23:30
	 */
	public static boolean isEmpty(Object o) {
		if (o == null)
			return true;

		if (o instanceof String) {
			if (((String) o).length() == 0 || "".equals(o) || "null".equals(o)) {
				return true;
			}
		} else if (o instanceof Collection) {
			if (((Collection) o).isEmpty()) {
				return true;
			}
		} else if (o.getClass().isArray()) {
			if (Array.getLength(o) == 0) {
				return true;
			}
		} else if (o instanceof Map) {
			if (((Map) o).isEmpty()) {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}
	public static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}
	/**
	 * 获取当前时间  yyyy-MM-dd HH:mm:ss
	 * @return
	 *
	 * @author zhangfuxun 
	 * @since 2016年4月16日下午1:33:30
	 */
	public static Date getDateTime(){
		Date nowDate = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat(nowDate);
//		try {
//			nowDate = sdf.parse("yyyy-MM-dd HH:mm:ss");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		return nowDate;
	}
	/**
	 * 获取yyyy-MM-dd HH:mm:ss 时间 string型
	 * %方法功能的一句话概括%。
	 * <p>%方法详述（简单方法可不必）%</p>
	 * @param dateString
	 * @return
	 *
	 * @author zhangfuxun 
	 * @since 2016年4月22日上午11:34:01
	 */
	public static String formatDate(Date dateString) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = sf.format(dateString);
		return s;
	}
	/** 
     * 使用参数Format将字符串转为Date 
     */  
    public static Date parse(String strDate, String pattern)throws ParseException{  
        return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(  
                pattern).parse(strDate);  
    } 
	/**
	 * 获取当前年份
	 * @return
	 *
	 * @author zhangfuxun 
	 * @since 2016年4月18日下午2:38:20
	 */
	public static int getYear() {
        GregorianCalendar gt = new GregorianCalendar();
        int year = gt.get(Calendar.YEAR);
        return year;
    }
	/**
	 * 获取当前日期 YYYY-MM-DD
	 * %方法功能的一句话概括%。
	 * <p>%方法详述（简单方法可不必）%</p>
	 * @return
	 *
	 * @author zhangfuxun 
	 * @since 2016年4月19日上午8:58:49
	 */
	public static String getDateAsSep() {
        GregorianCalendar gt = new GregorianCalendar();
        String lsdate = "";
        int year = gt.get(Calendar.YEAR);
        int month = gt.get(Calendar.MONTH) + 1;
        int date = gt.get(Calendar.DATE);
        lsdate = "" + year + "-";
        if (month < 10)
            lsdate = lsdate + "0" + month;
        else
            lsdate += month;
        lsdate += "-";
        if (date < 10)
            lsdate = lsdate + "0" + date;
        else
            lsdate += date;
        return lsdate;
    }
	/**
	 * 将String 转成double
	 * @param strInt
	 * @param defaultInt
	 * @return
	 *
	 * @author zhangfuxun 
	 * @since 2016年4月19日上午11:09:30
	 */
	public static Double convertDouble(String strInt, double defaultInt) {
		Double outInt = new Double(defaultInt);
		if (isNotEmpty(strInt)) {
			try {
				outInt = new Double(Double.parseDouble(strInt));
			} catch (Exception e) {
				outInt = new Double(defaultInt);
			}
		}
		if (outInt <= 0) {
			outInt = new Double(defaultInt);
		}
		return outInt;
	}
	
	public static double convertDouble(BigDecimal outInt) {
		if (outInt != null) {
			return outInt.doubleValue();
		}
		return 0.00;
	}
	/**
	 * 获取星期map 
	 * @return
	 *
	 * @author zhangfuxun 
	 * @since 2016年4月25日上午10:20:13
	 */
	public static Map<String,String> getWeekMap(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("2", "星期一");
		map.put("3", "星期二");
		map.put("4", "星期三");
		map.put("5", "星期四");
		map.put("6", "星期五");
		map.put("7", "星期六");
		map.put("1", "星期日");
		return map;
	}
	/**
	 * 根据传入日期 返回当前是周几或者第几天
	 * @param date YYYY-MM-DD
	 * @param flag "chinese/number"
	 * @return
	 *
	 * @author zhangfuxun 
	 * @since 2016年4月25日上午10:39:03
	 */
	public static String getWeekByDate(String date,String flag){
		int year = Integer.valueOf(date.substring(0, 4));
	    int month = Integer.valueOf(date.substring(5, 7));
	    int day = Integer.valueOf(date.substring(8,10));;
	    Calendar calendar = Calendar.getInstance();//获得一个日历
	    calendar.set(year, month-1, day);//设置当前时间,月份是从0月开始计算
	    int number = calendar.get(Calendar.DAY_OF_WEEK);//星期表示1-7，是从星期日开始，   
	    if ("number".equals(flag)) {
			return number + "";
		}else{
			String [] weeks = {"","星期日","星期一","星期二","星期三","星期四","星期五","星期六",};
		    return weeks[number];
		}
	}
	/**
	 * 判断compareTime是否在startTime与endTime的范围内
	 * @param compareTime
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static Boolean isDateInRangeTime(String compareTime,String startTime,String endTime){
		if(startTime!=null && !"".equals(startTime)
				&& endTime!=null && !"".equals(endTime)){
			String[] compareTimeArray = compareTime.split(":");
			String[] startTimeArray = startTime.split(":");
			String[] endTimeArray = endTime.split(":");
			int compareTimeMunites = Integer.parseInt(compareTimeArray[0]) * 60+Integer.parseInt(compareTimeArray[1]);
			int startTimeMunites = Integer.parseInt(startTimeArray[0]) * 60+Integer.parseInt(startTimeArray[1]);
			int endTimeMunites = Integer.parseInt(endTimeArray[0]) * 60+Integer.parseInt(endTimeArray[1]);
			if(compareTimeMunites>=startTimeMunites && compareTimeMunites<=endTimeMunites){
				return true;
			}
		}
		return false;
	}
	/**
	 * 格式化时间
	 * @param dateTime
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date dateTime,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(dateTime);
	}
	
	public static int daysBetween(String startDateTime,String endDateTime) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(startDateTime));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(endDateTime));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }
	
	/**
	 * 获取证件照的网络URL
	 * @param userId
	 * @return
	 */
	public static String buildURL(Long userId){
        String rootContext = PropertiesAccessorUtil.getProperty(IDPhotoUtil.ID_PHOTO_CONTEXT_URl_KEY)+ File.separator + IDPhotoUtil.ID_PHOTO_DIR;
		return (rootContext + IDPhotoUtil.buildFileContextPath(userId)).replaceAll("\\\\", "/");
		
	}
	
	public static String firstDayOfMonthyyyyMMddHHmm(){
    	Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH)+1;
		String monthOfYear = "";
		if(month<10){
			monthOfYear = "0"+month;
		}else{
			monthOfYear = String.valueOf(month);
		}
		String firstDayOfMonth = year+"-"+monthOfYear+"-"+"01"+" 00:00";
		return firstDayOfMonth;
    }
	/**
	 * 方法说明：两个日期之间相差的秒数
	 *
	 * @param startDate
	 * @return
	 * @author zhangfuxun 
	 * @since 2016年6月24日上午10:13:22
	 */
	public static int calLastedTime(String date1, String date2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int c = 0;
		try {
			Date dates = df.parse(date1);
			Date datee = df.parse(date2);
			long a = datee.getTime();
			long b = dates.getTime();
			c = (int)((a - b) / 1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	 }
	public static void main(String[] args){
		System.out.println(calLastedTime("2016-09-12 12:13:55","2016-09-12 12:15:15"));
	}
}

