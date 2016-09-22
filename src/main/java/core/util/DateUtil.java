/**
 * =============================================
 * Copyright 2005 TransFar
 *
 * Change Revision
 * --------------------------------
 *   Date       Author      Remarks
 *   Nov 29, 2005
 * =============================================
 */
package core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * 
 * 
 */
public class DateUtil {
	/**
	 * 获得日志类log的实例
	 */
	private static Log log = LogFactory.getLog(DateUtil.class);

	/**
	 * "JAN"
	 */
	public static final String MTH_JAN = "Jan";

	/**
	 * "Feb"
	 */
	public static final String MTH_FEB = "Feb";

	/**
	 * "MAR"
	 */
	public static final String MTH_MAR = "Mar";

	/**
	 * "APR"
	 */
	public static final String MTH_APR = "Apr";

	/**
	 * "MAY"
	 */
	public static final String MTH_MAY = "May";

	/**
	 * "JUNE"
	 */
	public static final String MTH_JUN = "Jun";

	/**
	 * "JUL"
	 */
	public static final String MTH_JUL = "Jul";

	/**
	 * "AUG"
	 */
	public static final String MTH_AUG = "Aug";

	/**
	 * "SEP"
	 */
	public static final String MTH_SEP = "Sep";

	/**
	 * "OCT"
	 */
	public static final String MTH_OCT = "Oct";

	/**
	 * "NOV"
	 */
	public static final String MTH_NOV = "Nov";

	/**
	 * "DEC"
	 */
	public static final String MTH_DEC = "Dec";

	/**
	 * 时间格式'ddMMyyyy HHmm'
	 */
	public static String DATETIME_FORMAT = "ddMMyyyy  HHmm";
	/**
	 * 日历格式'dd MMM yyyy'
	 */
	public static String DATE_FORMAT = "dd MMM yyyy";
	/**
	 * 时间戳'dd MMM yyyy HH:mm:ss'
	 */
	public static String TIMESTAMP_FORMAT = "dd MMM yyyy HH:mm:ss";
	/**
	 * 日历格式'yyyy-MM-dd'
	 */
	public static String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
	/**
	 * 时间格式'dd MMM yyyy HH:mm:ss'
	 */
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 通过传入年、月、日的整型数字得到Date对象
	 * 
	 * @param year
	 *            Year Indicator
	 * @param month
	 *            Month indicator, 1=jan 2=feb ...
	 * @param day
	 *            Date indicator eg: any day from 1...31.
	 * @return 日期对象
	 */
	public static Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, 0, 0, 0);

		return cal.getTime();
	}

	/**
	 * 比较两个日期对象是否相等
	 * 
	 * @param date1
	 *            待比较日期对象
	 * @param date2
	 *            待比较日期对象
	 * @return 布尔型，如果两个日期对象相等返回true
	 */
	public static boolean isDateEqual(Date date1, Date date2) {
		if ((date1 == null) || (date2 == null)) {
			return false;
		}

		return resetTime(date1).compareTo(resetTime(date2)) == 0;
	}

	/**
	 * 修改默认的时区
	 * 
	 * @param timeZoneID
	 *            时区ID. 例如: "America/Los_Angeles", "CCT" which stands for
	 *            China/Taiwan = S'pore
	 */
	public static void setDefaultTimeZone(String timeZoneID) {
		TimeZone.setDefault(TimeZone.getTimeZone(timeZoneID));
	}

	/**
	 * 计算两个日期之间的时间差，可以按照年|月|日进行计算。
	 * 
	 * @param type
	 *            type决定了运行时间的计算是根据年、月或天。默认情况下,运行时间以天计算
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return 运行时间(int)
	 */
	public static int getElapsedTime(int type, Date startDate, Date endDate) {
		int elapsed = 0;

		if ((startDate == null) || (endDate == null)) {
			return -1;
		}

		if (startDate.after(endDate)) {
			return -1;
		}

		GregorianCalendar gc1 = (GregorianCalendar) GregorianCalendar
				.getInstance();
		GregorianCalendar gc2 = (GregorianCalendar) gc1.clone();
		gc1.setTime(startDate);
		gc2.setTime(endDate);

		gc1.clear(Calendar.MILLISECOND);
		gc1.clear(Calendar.SECOND);
		gc1.clear(Calendar.MINUTE);
		gc1.clear(Calendar.HOUR_OF_DAY);
		gc2.clear(Calendar.MILLISECOND);
		gc2.clear(Calendar.SECOND);
		gc2.clear(Calendar.MINUTE);
		gc2.clear(Calendar.HOUR_OF_DAY);

		if ((type != Calendar.MONTH) && (type != Calendar.YEAR)) {
			type = Calendar.DATE;
		}

		if (type == Calendar.MONTH) {
			gc1.clear(Calendar.DATE);
			gc2.clear(Calendar.DATE);
		}

		if (type == Calendar.YEAR) {
			gc1.clear(Calendar.DATE);
			gc2.clear(Calendar.DATE);
			gc1.clear(Calendar.MONTH);
			gc2.clear(Calendar.MONTH);
		}

		while (gc1.before(gc2)) {
			gc1.add(type, 1);
			elapsed++;
		}

		return elapsed;
	}

	/**
	 * 判断日期是否是月份最后一天
	 * 
	 * @param date
	 *            待判断日期
	 * @return 如果日期是月份最后一天返回true
	 */
	public static boolean isEndOfTheMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		return cal.get(Calendar.DATE) == maxDay;

	}

	/**
	 * 判断日期是否是年最后一天
	 * 
	 * @param date
	 *            待判断日期
	 * @return 如果日期是年最后一天返回true
	 */
	public static boolean isEndOfTheYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return (cal.get(Calendar.MONTH) == 11)
				&& (cal.get(Calendar.DATE) == 31);

	}

	/**
	 * 获取日期所在月最后一天
	 * 
	 * @param date
	 *            待判断日期
	 * @return 所在月最后一天
	 */
	public static int getLastDayOfTheMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回月份数字
	 * 
	 * @param month
	 *            月份的字母简写，例如："Jan", "Feb", etc
	 * @return 月份数字
	 */
	public static int getMthInInt(String month) {
		if (month.equalsIgnoreCase(MTH_JAN)) {
			return 1;
		} else if (month.equalsIgnoreCase(MTH_FEB)) {
			return 2;
		} else if (month.equalsIgnoreCase(MTH_MAR)) {
			return 3;
		} else if (month.equalsIgnoreCase(MTH_APR)) {
			return 4;
		} else if (month.equalsIgnoreCase(MTH_MAY)) {
			return 5;
		} else if (month.equalsIgnoreCase(MTH_JUN)) {
			return 6;
		} else if (month.equalsIgnoreCase(MTH_JUL)) {
			return 7;
		} else if (month.equalsIgnoreCase(MTH_AUG)) {
			return 8;
		} else if (month.equalsIgnoreCase(MTH_SEP)) {
			return 9;
		} else if (month.equalsIgnoreCase(MTH_OCT)) {
			return 10;
		} else if (month.equalsIgnoreCase(MTH_NOV)) {
			return 11;
		} else if (month.equalsIgnoreCase(MTH_DEC)) {
			return 12;
		} else {
			return 0;
		}
	}

	/**
	 * 返回下一个工作日
	 * 
	 * @return 下一个工作日的日期对象
	 */
	public static Date getNextWorkingDay() {
		Date nextWorkingDay = DateUtil.addDaysToDate(DateUtil.getSystemDate(),
				1);
		Calendar c = Calendar.getInstance();
		c.setTime(nextWorkingDay);

		int day = c.get(Calendar.DAY_OF_WEEK);

		if (day == Calendar.SUNDAY) {
			nextWorkingDay = DateUtil.addDaysToDate(nextWorkingDay, 1);
		}

		return nextWorkingDay;
	}

	/**
	 * 判断开始时间是否在结束时间之前
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return 布尔值
	 */
	public static boolean isStartBeforeEndDate(Date startDate, Date endDate) {
		if ((startDate == null) || (endDate == null)) {
			return false;
		}

		return resetTime(startDate).compareTo(resetTime(endDate)) < 0;
	}

	/**
	 * 判断日期是否是当月第一天
	 * 
	 * @param date
	 *            带判断日期
	 * @return 布尔值
	 */
	public static boolean isStartOfTheMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.DATE) == 1;

	}

	/**
	 * 返回日期所在月份
	 * 
	 * @param date
	 *            待判断日期
	 * @return 月份（int）
	 */
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.MONTH);

	}

	/**
	 * 返回日期所在年
	 * 
	 * @param date
	 *            待判断日期
	 * @return 年份（int）
	 */
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.YEAR);

	}

	/**
	 * 判断日期是否当年第一天
	 * 
	 * @param date
	 *            待判断日期
	 * @return 布尔值
	 */
	public static boolean isStartOfTheYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return (cal.get(Calendar.MONTH) == 1) && (cal.get(Calendar.DATE) == 1);

	}

	/**
	 * 根据月份数字返回月份字母简写
	 * 
	 * @param month
	 *            月份数字
	 * @return 月份的字母简写，例如："Jan", "Feb", etc
	 */
	public static String getStrMth(int month) {
		if (month == 1) {
			return MTH_JAN;
		} else if (month == 2) {
			return MTH_FEB;
		} else if (month == 3) {
			return MTH_MAR;
		} else if (month == 4) {
			return MTH_APR;
		} else if (month == 5) {
			return MTH_MAY;
		} else if (month == 6) {
			return MTH_JUN;
		} else if (month == 7) {
			return MTH_JUL;
		} else if (month == 8) {
			return MTH_AUG;
		} else if (month == 9) {
			return MTH_SEP;
		} else if (month == 10) {
			return MTH_OCT;
		} else if (month == 11) {
			return MTH_NOV;
		} else if (month == 12) {
			return MTH_DEC;
		} else {
			return "";
		}
	}

	/**
	 * 计算两个日期之间的时间差，返回结果数组（年、月、日）。
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return 结果数组（年、月、日）
	 */
	public static int[] computeDuration(Date startDate, Date endDate) {
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.setTime(startDate);
		to.setTime(endDate);

		int birthYYYY = from.get(Calendar.YEAR);
		int birthMM = from.get(Calendar.MONTH);
		int birthDD = from.get(Calendar.DAY_OF_MONTH);
		int asofYYYY = to.get(Calendar.YEAR);
		int asofMM = to.get(Calendar.MONTH);
		int asofDD = to.get(Calendar.DAY_OF_MONTH);
		int ageInYears = asofYYYY - birthYYYY;
		int ageInMonths = asofMM - birthMM;
		int ageInDays = asofDD - birthDD + 1;

		if (ageInDays < 0) {
			/*
			 * Guaranteed after this single treatment, ageInDays will be >= 0.
			 * that is ageInDays = asofDD - birthDD + daysInBirthMM.
			 */
			ageInDays += from.getActualMaximum(Calendar.DAY_OF_MONTH);
			ageInMonths--;
		}

		if (ageInDays == to.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			ageInDays = 0;
			ageInMonths++;
		}

		if (ageInMonths < 0) {
			ageInMonths += 12;
			ageInYears--;
		}

		if ((birthYYYY < 0) && (asofYYYY > 0)) {
			ageInYears--;
		}

		if (ageInYears < 0) {
			ageInYears = 0;
			ageInMonths = 0;
			ageInDays = 0;
		}

		int[] result = new int[3];
		result[0] = ageInYears;
		result[1] = ageInMonths;
		result[2] = ageInDays;

		return result;
	}

	/**
	 * 返回java.sql.Date类型的系统当前时间
	 * 
	 * @return Date 数据库Date类型的当前时间
	 */
	public static java.sql.Date getSystemDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return new java.sql.Date(cal.getTime().getTime());
	}

	/**
	 * 返回java.sql.Timestamp类型的系统当前时间
	 * 
	 * @return Timestamp Timestamp类型的系统当前时间
	 */
	public static Timestamp getSystemTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 判断输入的年份是否合法
	 * 
	 * @param s
	 *            输入的年份
	 * @return 如果合法返回true
	 */
	public static boolean isValidYearFormat(String s) {
		return DateUtil.toDate(s, "yyyy") != null;
	}

	/**
	 * 将日期格式化
	 * 
	 * @param date
	 *            待格式化日期
	 * @param strFormat
	 *            字符串格式
	 * @return 格式化后字符串日期
	 */
	public static String getDate(Date date, String strFormat) {
		return DateUtil.parseDate(date, strFormat);
	}

	/**
	 * 判断输入的日期是否合法，默认格式是"ddMMyyyy"
	 * 
	 * @param strDate
	 *            输入的日期
	 * @return 如果合法返回true
	 */
	public static boolean isValidDate(String strDate) {
		return DateUtil.toDate(strDate, "ddMMyyyy") != null;
	}

	/**
	 * 判断输入的日期是否符合指定格式
	 * 
	 * @param strDate
	 *            输入的日期
	 * @param dateStrFormat
	 *            指定格式
	 * @return 如果合法返回true
	 */
	public static boolean isValidDate(String strDate, String dateStrFormat) {
		return DateUtil.toDate(strDate, dateStrFormat) != null;
	}

	/**
	 * 返回相加后的日期
	 * 
	 * @param type
	 *            年/月/日，（Calendar.YEAR/Calendar.MONTH/Calendar.DATE）
	 * @param date
	 *            日期
	 * @param num
	 *            整数
	 * @return 相加后的日期
	 */
	public static Date addDate(int type, Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(type, num);

		return new Date(cal.getTime().getTime());
	}

	/**
	 * 返回相加后的日期
	 * 
	 * @param date
	 *            日期
	 * @param numDays
	 *            天数
	 * @return 相加后的日期
	 */
	public static Date addDaysToDate(Date date, int numDays) {
		if (date == null) {
			return null;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, numDays);

		return c.getTime();
	}

	/**
	 * 返回相加后的日期
	 * 
	 * @param date
	 *            日期
	 * @param numHours
	 *            小时数
	 * @return 相加后的日期
	 */
	public static Date addHoursToDate(Date date, int numHours) {
		if (date == null) {
			return null;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, numHours);

		return c.getTime();
	}

	/**
	 * 返回相加后的日期
	 * 
	 * @param date
	 *            日期
	 * @param numMins
	 *            分钟数
	 * @return 相加后的日期
	 */
	public static Date addMinutesToDate(Date date, int numMins) {
		if (date == null) {
			return null;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, numMins);

		return c.getTime();
	}

	/**
	 * 返回相加后的日期
	 * 
	 * @param date
	 *            日期
	 * @param numMonths
	 *            月数
	 * @return 相加后的日期
	 */
	public static Date addMonthsToDate(Date date, int numMonths) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, numMonths);
		return c.getTime();
	}

	/**
	 * 返回相加后的日期
	 * 
	 * @param date
	 *            日期
	 * @param numYears
	 *            年数
	 * @return 相加后的日期
	 */
	public static Date addYearsToDate(Date date, int numYears) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, numYears);
		return c.getTime();
	}

	/**
	 * 比较两个日期
	 * 
	 * @param date1
	 *            待比较日期1
	 * @param date2
	 *            待比较日期2
	 * @return 如果相等返回0,如果日期1>日期2返回1，如果日期1<日期2返回-1
	 */
	public static int compareDates(Date date1, Date date2) {
		if ((date1 == null) && (date2 == null)) {
			return 0;
		}

		if (date1 == null) {
			return -1;
		}

		if (date2 == null) {
			return 1;
		}

		String strFormat = "yyyyMMdd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);

		int intDate1 = Integer.parseInt(dateFormat.format(date1));
		int intDate2 = Integer.parseInt(dateFormat.format(date2));

		if (intDate1 == intDate2) {
			return 0;
		}

		if (intDate1 > intDate2) {
			return 1;
		}

		return -1;
	}

	/**
	 * 格式化输出日期
	 * 
	 * @param date
	 *            日期
	 * @param formatStr
	 *            格式
	 * @return 返回字符型日期
	 */
	public static String parseDate(Date date, String formatStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);

		if (date == null) {
			return null;
		} else {
			return dateFormat.format(date);
		}
	}

	/**
	 * 按照默认格式"ddMMyyyy  HHmm"输出日期
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型日期
	 */
	public static String parseDate(Date date) {
		return parseDate(date, DATETIME_FORMAT);
	}

	/**
	 * 将日期的时间重置为00:00
	 * @param date 日期
	 * @return date 重置后日期
	 */
	public static Date resetTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * 将指定的日期字符串根据指定的日期时间格式转换为日期对象。如果指定的日期无效返回null。
	 * @param strDateTime 日期字符串
	 * @param dateTimeFormat 日期时间格式
	 * @return 日期对象
	 */
	public static Date toDate(String strDateTime, String dateTimeFormat) {
		if ((strDateTime == null) || (strDateTime.length() == 0)
				|| (dateTimeFormat == null) || (dateTimeFormat.length() == 0)) {
			return null;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(dateTimeFormat);
		Date date = dateFormat.parse(strDateTime, new ParsePosition(0));

		if (date == null) {
			return null;
		}

		String dateStr = parseDate(date, dateTimeFormat);

		if (!strDateTime.equals(dateStr)) {
			return null;
		}

		return date;
	}

	/**
	 * 将指定的日期字符串（默认格式'ddMMyyyy HHmm'）转换为日期对象。如果指定的日期无效返回null。
	 * @param strDateTime 日期字符串
	 * @return 日期对象
	 */
	public static Date toDate(String strDateTime) {
		return toDate(strDateTime, DATETIME_FORMAT);
	}

	/**
	 * 根据简写字母返回月份两位数字字符串，如"01"
	 * 
	 * @param mthMMM 月份的字母简写，例如："Jan", "Feb", etc
	 * @return 月份两位数字字符串
	 */
	public static String toMMFormat(String mthMMM) {
		if (mthMMM.equalsIgnoreCase(MTH_JAN)) {
			return "01";
		} else if (mthMMM.equalsIgnoreCase(MTH_FEB)) {
			return "02";
		} else if (mthMMM.equalsIgnoreCase(MTH_MAR)) {
			return "03";
		} else if (mthMMM.equalsIgnoreCase(MTH_APR)) {
			return "04";
		} else if (mthMMM.equalsIgnoreCase(MTH_MAY)) {
			return "05";
		} else if (mthMMM.equalsIgnoreCase(MTH_JUN)) {
			return "06";
		} else if (mthMMM.equalsIgnoreCase(MTH_JUL)) {
			return "07";
		} else if (mthMMM.equalsIgnoreCase(MTH_AUG)) {
			return "08";
		} else if (mthMMM.equalsIgnoreCase(MTH_SEP)) {
			return "09";
		} else if (mthMMM.equalsIgnoreCase(MTH_OCT)) {
			return "10";
		} else if (mthMMM.equalsIgnoreCase(MTH_NOV)) {
			return "11";
		} else if (mthMMM.equalsIgnoreCase(MTH_DEC)) {
			return "12";
		}

		return null;
	}

	/**
	 * 根据月份数字符串字返回月份字母简写
	 * 
	 * @param mthMM 月份数字字符串，例如"01", "02"
	 * @return 月份的字母简写，例如："Jan", "Feb", etc
	 */
	public static String toMMMFormat(String mthMM) {
		if ("01".equals(mthMM)) {
			return MTH_JAN;
		} else if ("02".equals(mthMM)) {
			return MTH_FEB;
		} else if ("03".equals(mthMM)) {
			return MTH_MAR;
		} else if ("04".equals(mthMM)) {
			return MTH_APR;
		} else if ("05".equals(mthMM)) {
			return MTH_MAY;
		} else if ("06".equals(mthMM)) {
			return MTH_JUN;
		} else if ("07".equals(mthMM)) {
			return MTH_JUL;
		} else if ("08".equals(mthMM)) {
			return MTH_AUG;
		} else if ("09".equals(mthMM)) {
			return MTH_SEP;
		} else if ("10".equals(mthMM)) {
			return MTH_OCT;
		} else if ("11".equals(mthMM)) {
			return MTH_NOV;
		} else if ("12".equals(mthMM)) {
			return MTH_DEC;
		}

		return null;
	}

	/**
	 * 将指定的日期字符串根据指定的日期时间格式转换为SQL日期对象。如果指定的日期无效返回null。
	 * @param strDateTime 日期字符串
	 * @param dateTimeFormat 日期时间格式
	 * @return SQL日期对象
	 */
	public static java.sql.Date toSQLDate(String strDateTime,
			String dateTimeFormat) {
		Date date = toDate(strDateTime, dateTimeFormat);

		if (date == null) {
			return null;
		}

		return new java.sql.Date(date.getTime());
	}

	/**
	 * 将日期对象转化为SQL日期对象
	 * @param date
	 *            日期对象
	 * @return SQL日期对象
	 */
	public static java.sql.Date toSQLDate(Date date) {
		if (date == null) {
			return null;
		}

		return new java.sql.Date(date.getTime());
	}

	/**
	 * 将日期字符串转化为SQL日期对象
	 * @param strDateTime
	 *            日期字符串
	 * @return SQL日期对象
	 */
	public static java.sql.Date toSQLDate(String strDateTime) {
		return toSQLDate(strDateTime, DATETIME_FORMAT);
	}

	/**
	 * 将日期字符串（默认格式"ddMMyyyy HHmm"）转化为Timestamp对象
	 * @param dateTimeStr
	 *            日期字符串
	 * @return Timestamp对象
	 */
	public static Timestamp toTimestamp(String dateTimeStr) {
		return toTimestamp(toDate(dateTimeStr));
	}

	/**
	 * 将日期字符串按照指定日期时间格式转化为Timestamp对象
	 * @param dateTimeStr
	 *            日期字符串
	 * @param dateTimeFormat 日期时间格式
	 * @return Timestamp对象
	 */
	public static Timestamp toTimestamp(String dateTimeStr,
			String dateTimeFormat) {
		return toTimestamp(toDate(dateTimeStr, dateTimeFormat));
	}

	/**
	 * 将日期对象转化为Timestamp对象
	 * @param date
	 *            日期对象
	 * @return Timestamp对象
	 */
	public static Timestamp toTimestamp(Date date) {
		if (date == null) {
			return null;
		}

		return new Timestamp(date.getTime());
	}

	/**
	 * 将Timestamp对象转化为日期对象
	 * @param timeStamp
	 *            Timestamp对象
	 * @return 日期对象
	 */
	public static Date toDate(Timestamp timeStamp) {
		if (timeStamp == null) {
			return null;
		}

		return new Date(timeStamp.getTime());
	}

	/**
	 * 判断日期是否是无穷大，9999用来表示最大日期。
	 * 
	 * @param dateToCheck 日期对象
	 * @return 如果大约等于9999返回true
	 */
	public static Boolean isInfinity(Date dateToCheck) {
		if (dateToCheck == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToCheck);
		int year = cal.get(Calendar.YEAR);
		log.info("The given year :" + year);
		if (year >= 9999) {
			log.info("The date is infinity");
			return Boolean.TRUE;
		}

		log.info("The date is not infinity");
		return Boolean.FALSE;

	}

	/**
	 * 返回当前周的第一/最后一天
	 * 
	 * @return Map对象
	 * <pre>
	 *                         key             value
	 * <br>
	 *                         startOfWeek     周第一天日期对象
	 * <br>
	 *                         endOfWeek       周最后一天日期对象
	 * <br>
	 *                         </pre>
	 */
	public static Map calcCurrentWeek() {
		Calendar curCal = Calendar.getInstance();
		int i = curCal.get(Calendar.DAY_OF_WEEK);
		Date startOfWeek = addDaysToDate(curCal.getTime(), -i + 2);
		Date endOfWeek = addDaysToDate(curCal.getTime(), 7 - i + 1);

		Map resMap = new HashMap();
		resMap.put("startOfWeek", startOfWeek);
		resMap.put("endOfWeek", endOfWeek);
		return resMap;
	}

	/**
	 * 时间格式
	 */
	private static String timePattern = "HH:mm";

	/**
	 * 返回默认日期格式
	 */
	public static synchronized String getDatePattern() {
		return "dd/MM/yyyy";
	}

	/**
	 * 按照默认日期格式返回日期字符串
	 */
	public static String getDate(Date aDate) {
		SimpleDateFormat df;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * 将指定的日期字符串根据指定的日期时间格式转换为日期对象。如果指定的日期无效返回null。
	 * @param strDate 日期字符串
	 * @param aMask 日期时间格式
	 * @return 日期对象
	 */
	public static Date convertStringToDate(String aMask, String strDate) {
		SimpleDateFormat df;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			log.error("ParseException: " + pe);
		}

		return (date);
	}

	/**
	 * 返回日期时间字符串，默认格式: MM/dd/yyyy HH:MM
	 * 
	 * @param theTime 日期对象
	 * @return 日期时间字符串
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}
	/**
	 * 返回当前日期的日历对象
	 */
	public static Calendar getToday() {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * 按照指定日期格式化输出日期字符串
	 * @param aMask
	 *           日期格式
	 * @param aDate
	 *           日期对象
	 * @return 返回字符型日期
	 */
	public static String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df;
		String returnValue = "";

		if (aDate == null) {
			// log.error("aDate is null!"); modify by timothy
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * 默认模式格式化输出日期字符串
	 * 
	 * @param aDate
	 *            日期对象
	 * @return 返回字符型日期
	 */
	public static String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	/**
	 * 按照指定日期格式化输出日期字符串
	 * @param format
	 *           日期格式
	 * @param aDate
	 *           日期对象
	 * @return 返回字符型日期
	 */
	public static String convertDateToString(String format, Date aDate) {
		return getDateTime(format, aDate);
	}

	/**
	 * 将指定的日期字符串根据默认日期时间格式转换为日期对象。
	 * @param strDate 日期字符串
	 * @return 日期对象
	 */
	public static Date convertStringToDate(String strDate) {
		if (log.isDebugEnabled()) {
			log.debug("converting date with pattern: " + getDatePattern());
		}

		return convertStringToDate(getDatePattern(), strDate);
	}


	/**
	 * 返回日历信息
	 * 
	 * @param date 日期对象
	 * @return Map对象。 
	 * <pre>
	 *     key                      value<br>
	 *     Calendar.DAY_OF_WEEK     本周第几天<br>
	 *     Calendar.WEEK_OF_MONTH   本月第几周<br>
	 *     Calendar.Year			年<br>
	 *     Calendar.MONTH			月<br>
	 *     Calendar.DATE			日<br>
	 * </pre>
	 */
	public static Map getCalenderInfo(Date date) {
		Map resultMap = new HashMap();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, 1);
		int weekOfMonth = cal.get(Calendar.WEEK_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int dt = cal.get(Calendar.DATE);
		resultMap
				.put(new Integer(Calendar.DAY_OF_WEEK), new Integer(dayOfWeek));
		resultMap.put(new Integer(Calendar.WEEK_OF_MONTH), new Integer(
				weekOfMonth));
		resultMap.put(new Integer(Calendar.YEAR), new Integer(year));
		resultMap.put(new Integer(Calendar.MONTH), new Integer(month));
		resultMap.put(new Integer(Calendar.DATE), new Integer(dt));
		return resultMap;
	}

	/**
	 *得到完整日期 yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static String getFullDayTime() {

		Date d = new Date();// 获取当前系统的时间
		SimpleDateFormat s = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS"); // 格式化日期
		String dateStr = s.format(d); // 转为字符串

		return dateStr;
	}
	/**
	 *得到日期字符串：yyyyMMddHHmmssSSS
	 */
	public static String getDateToFilename() {
		return getFullDayTime().replace(":", "").replace("-", "").replace(" ",
				"").replace(".", "");
	}

}
