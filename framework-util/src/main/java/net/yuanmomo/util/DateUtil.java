/** Copyright (c) 2013 MoMo, yuanhb@fusionskye.com All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Project Name : Tools
 * Package Name : net.yuanmomo.tools.time
 * Created on   : Jun 24, 20131:47:23 PM
 * File Name    : TimeUtil.java
 *
 * Author       : yuanmomo
 * Blog         : yuanmomo.net
 * Company      : 北京华青融天技术有限责任公司  
 */

package net.yuanmomo.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName : TimeUtil 
 * Function  : TODO ADD FUNCTION. 
 * Reason    : TODO ADD REASON. 
 * Date      : Jun 24, 2013 1:47:23 PM 
 *
 * @author   : MoMo
 * @version  
 * @since      JDK 1.7
 * @see 	 
 */
public class DateUtil {
	public static long MILLISECOND_PER_SECOND = 1000;
	public static long MILLISECOND_PER_MINUTE = 60 * 1000;
	public static long MILLISECOND_PER_HOUR = 60 * 60 * 1000;
	public static long MILLISECOND_PER_DAY = 24 * 60 * 60 * 1000;
	
	/**
	 * getString: 将指定的Date对象转换为字符串. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @param date
	 * @param format
	 * @return
	 * @since JDK 1.7
	 */
	public static String getString(Date date,String format){
		DateFormat dateFormat = DateFormatEnu.valueOfFormat(format);
		if(dateFormat == null){
			dateFormat = new SimpleDateFormat(format);
		}
		return getString(date, dateFormat);
	}
	
	/**
	 * getString: 将指定的Date对象转换为字符串. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @param date
	 * @param format
	 * @return
	 * @since JDK 1.7
	 */
	public static String getString(Date date,DateFormat format){
		if(date == null){
			return null;
		}
		return format.format(date);
	}

	/**
	 * getString: 将指定的Date对象转换为字符串. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @param date
	 * @param format
	 * @return
	 * @since JDK 1.7
	 */
	public static String getString(Date date,DateFormatEnu format){
		return getString(date, format.getFormat());
	}
	
	/**
	 * getString: 将指定的Date对象转换为字符串. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @param date
	 * @return
	 * @since JDK 1.7
	 */
	public static String getString(Date date){
		DateFormat dateFormat = DateFormatEnu.DEFATUL.getSdf();
		return  getString(date, dateFormat);
	}

	/**
	 * getTodayString: 将指定的Date对象转换为字符串. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @param dateFormatEnu
	 * @return
	 * @since JDK 1.7
	 */
	public static String getTodayString(DateFormatEnu dateFormatEnu){
		return getString(getCurrentDate(),dateFormatEnu.getFormat());
	}
	
	/**
	 * getDate: 将指定的字符串转换为Date对象. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @param str
	 * @param format
	 * @return
	 * @throws ParseException
	 * @since JDK 1.7
	 */
	public static Date getDate(String str,String format) throws ParseException{
		DateFormat dateFormat = DateFormatEnu.valueOfFormat(format);
		return getDate(str, dateFormat);
	}
	
	/**
	 * getDate: 将指定的字符串转换为Date对象. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @param str
	 * @param format
	 * @return
	 * @throws ParseException
	 * @since JDK 1.7
	 */
	public static Date getDate(String str,DateFormat format) throws ParseException{
		if(str==null){
			return new Date();
		}
		return format.parse(str);
	}
	/**
	 * getDate: 将指定的字符串转换为Date对象. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @param str
	 * @return
	 * @throws ParseException
	 * @since JDK 1.7
	 */
	public static Date getDate(String str) throws ParseException{
		DateFormat dateFormat = DateFormatEnu.DEFATUL.getSdf();
		return getDate(str, dateFormat);
	}
	
	/**
	 * getCurrentDate: 得到当前日期. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @return
	 * @since JDK 1.7
	 */
	public static Date getCurrentDate(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * getDate: 得到当前指定日期的年月日. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @param date
	 * @return
	 * @since JDK 1.7
	 */
	public static Date getDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	public static int getHour(){
		return getHour(new Date());
	}
	public static int getHour(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}
	/**
	 * getDate: 得到当前指定日期的年月日. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @return
	 * @since JDK 1.7
	 */
	public static Date getDate(){
		return getCurrentDate();
	}
	
	/**
	 * addDays: 对指定的日期加上相应的天数. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @param date		指定的日期
	 * @param days		加上相应的天数，如果为负数则相减<br/>
	 * @return
	 * @since JDK 1.7
	 */
	public static Date addDays(Date date, int days){
		if(date == null){
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, days);
		return c.getTime();
	}
	/**
	 * addDays: 对指定的日期加上相应的天数. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @param days		在当前的时间上加上相应的天数，如果为负数则相减<br/>
	 * @return
	 * @since JDK 1.7
	 */
	public static Date addDays(int days){
		return addDays(null, days);
	}

	/**
	 * 	当前年份加上指定的年数。
	 *
	 * @param years
	 * @return
	 */
	public static Date addYears(int years){
		return addYears(null, years);
	}

	/**
	 * 在指定的年份加上指定的年数。
	 *
	 * @param date
	 * @param years
	 * @return
	 */
	public static Date addYears(Date date, int years){
		if(date == null){
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, years);
		return c.getTime();
	}


	/**
	 *
	 * 在当前时间加上指定的小时数。
	 *
	 * @param hours
	 * @return
	 */
	public static Date addHours(int hours){
		return addHours(null, hours);
	}

	/**
	 * 在指定的时间上加上指定的小时数。
	 *
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date addHours(Date date, int hours){
		if(date == null){
			date = new Date();
		}
		return  new Date(date.getTime() + hours * MILLISECOND_PER_HOUR);
	}

	/**
	 *
	 * 在当前时间加上指定的分钟数。
	 *
	 * @param hours
	 * @return
	 */
	public static Date addMinute(int hours){
		return addMinute(null, hours);
	}
	/**
	 * 在指定的时间上加上指定的分钟数。
	 *
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date date, int minute){
		if(date == null){
			date = new Date();
		}
		return  new Date(date.getTime() + minute * MILLISECOND_PER_MINUTE);
	}

	/**
	 *
	 * 在当前时间加上指定的秒数。
	 *
	 * @param hours
	 * @return
	 */
	public static Date addSecond(int hours){
		return addSecond(null, hours);
	}
	/**
	 * 在指定的时间上加上指定的秒数。
	 *
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date addSecond(Date date, int seconds){
		if(date == null){
			date = new Date();
		}
		return  new Date(date.getTime() + seconds * MILLISECOND_PER_SECOND);
	}
	
	/**
	 * getCalendar: 返回当前date对象对应的calendar对象. <br/>
	 *
	 * @author Hongbin.Yuan
	 * @param date
	 * @return
	 * @since JDK 1.7
	 */
	public static Calendar getCalendar(Date date){
		if(date != null){
			Calendar c=Calendar.getInstance();
			c.setTime(date);
			return c;
		}
		return null;
	}
	/**
	 * 判断指定时间是否当天。
	 * @param date
	 * @return
	 */
	public static boolean isCurrentDay(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		Calendar current=Calendar.getInstance();
		return current.get(Calendar.YEAR)==cal.get(Calendar.YEAR)&&current.get(Calendar.MONTH)==cal.get(Calendar.MONTH)&&current.get(Calendar.DAY_OF_MONTH)==cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 判断当前是否在指定时间之后，按自然日计算。
	 * @param date
	 * @return
	 */
	public static boolean isCurrentAfterDay(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Calendar current=Calendar.getInstance();
		current.set(Calendar.HOUR_OF_DAY, 0);
		current.set(Calendar.MINUTE, 0);
		current.set(Calendar.SECOND, 0);
		current.set(Calendar.MILLISECOND, 0);
		return current.after(cal);
	}
	
	/**
	 * 判断当前是否在指定时间之后1天，按自然日计算
	 * @param date
	 * @return
	 */
	public static boolean isAurrentAfterDay1(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Calendar current=Calendar.getInstance();
		current.set(Calendar.HOUR_OF_DAY, 0);
		current.set(Calendar.MINUTE, 0);
		current.set(Calendar.SECOND, 0);
		current.set(Calendar.MILLISECOND, 0);
		return current.equals(cal);
	}

	/**
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1,Date date2){
		if(date1 == null && date2 == null ){
			return true;
		}
		if(date1 == null || date2 == null ){
			return false;
		}
		
		Calendar cal1 = getCalendar(getDate(date1));
		Calendar cal2 = getCalendar(getDate(date2));
		if(cal1 == null && cal2 == null){
			return true;
		}
		if(cal1 != null && cal1.compareTo(cal2) == 0){
			return true;
		}
		return false;
	}

	/**
	 * 获取当前天数总共的毫秒数。
	 *
	 * @param days
	 * @return
	 */
	public static long getMicrosecondsOfDays(int days){
		return MILLISECOND_PER_DAY * days;
	}

	/**
	 * 取得 剩余的 毫秒数。(缓存当天有效的数据使用。比如一天几次机会，24：00重置)
	 *
	 * @return
	 */
	public static long getRestMicrosecondsOfToDay(){
		Date now = new Date();
		Date currentDate = getDate(now);
		return MILLISECOND_PER_DAY - (now.getTime() - currentDate.getTime());
	}

	/**
	 * 	第一个时间是否在第二个时间之前。
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	public static boolean isBefore(Date first, Date second){
		if(first == null){
			return false;
		}
		if(second == null){
			second = new Date();
		}
		if(first.getTime() < second.getTime()){
			return true;
		}
		return false;
	}
	/**
	 * 	第一个时间是否在第二个时间之后。
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	public static boolean isAfter(Date first, Date second){
		return !isBefore(first,second);
	}

	/**
	 * 第一个时间是否在第二个时间和第三个时间中。
	 * @param time
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isBetween(Date time, Date start, Date end){
		if(time == null){
			return true;
		}
		if(start == null || end == null){
			throw new NullPointerException("start date or end date is null. startDate = "+start +", endDate = "+end);
		}
		long timeLong = time.getTime();
		long startLong = start.getTime();
		long endLong = end.getTime();
		
		if(timeLong >  startLong && timeLong < endLong){
			return true;
		}
		return false;
	}

	/**
	 * 现在时间是否在第二个时间和第三个时间中。
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isBetween(Date start, Date end){
		return isBetween(new Date(),start, end);
	}

	/**
	 * 判断两个时间是否小时数相等。
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameHour(Date date1,Date date2){
		if(date1 == null || date2 == null){
			return false;
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date2);
		
		if(cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY)){
			return true;
		}
		return false;
	}
}
