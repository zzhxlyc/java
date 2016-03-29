package datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeUtils {
	
	private static Logger m_logger = Logger.getLogger(DateTimeUtils.class);
	
	private static final String dateFormat = "yyyy-MM-dd";
	private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	private static final DateTimeFormatter dateJFormat = DateTimeFormat.forPattern(dateFormat);
	private static final DateTimeFormatter dateTimeJFormat = DateTimeFormat.forPattern(dateTimeFormat);
	private static final DateTimeFormatter dbJFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S");
	private static final DateTimeFormatter dateTimeMillJFormat = 
								DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static long getPrevDaysTimetag(long timetag, int days) throws Exception{
		return timetag - days * 1000L * 3600 * 24;
	}
	
	public static String getDateTimeStringFromSeconds(int timetag) throws ParseException{
		if(timetag < 0){
			timetag = 0;
		}
		long ts = (long)timetag * 1000;
		return getDateTimeStringFromMillSeconds(ts);
	}
	
	public static String getDateTimeStringFromMillSeconds(long timetag) throws ParseException{
		if(timetag < 0L){
			timetag = 0L;
		}
		DateTime dateTime = new DateTime(timetag);
		return dateTime.toString(dateTimeFormat);
	}
	
	public static long getTimestampFromDateString(String time){
		if(StringUtils.isEmpty(time)){
			return 0L;
		}
		int len = time.length();
		try {
			DateTime dateTime = null;
			if(len == 10){
				dateTime = DateTime.parse(time, dateJFormat);
			}
			else if(len == 19){
				dateTime = DateTime.parse(time, dateTimeJFormat);
			}
			else if(len == 21){
				dateTime = DateTime.parse(time, dbJFormat);
			}
			else if(len == 23){
				dateTime = DateTime.parse(time, dateTimeMillJFormat);
			}
			if(dateTime != null){
				return dateTime.getMillis();
			}
			else{
				m_logger.error("getTimestampFromDateString joda miss : " + time);
				return new SimpleDateFormat(dateTimeFormat).parse(time).getTime();
			}
		} catch (Exception e) {
			m_logger.error("getTimestampFromDateString error : " + time, e);
			return 0L;
		}
	}
	
	public static String getTimeDuration(long mills){
		return getTimeDuration((int)(mills / 1000));
	}
	
	public static String getTimeDuration(int seconds){
		int hour = seconds / 3600;
		int min = (seconds - hour * 3600) / 60;
		int sec = seconds % 60;
		if(hour > 0){
			return String.format("%d:%02d:%02d", hour, min, sec);
		}
		else if(min > 0){
			return String.format("%02d:%02d", min, sec);
		}
		else{
			return String.format("00:%02d", sec);
		}
	}
	
	public static void main(String[] args) throws ParseException{
		System.out.println(getTimestampFromDateString(null));
		System.out.println(getTimestampFromDateString(""));
		System.out.println(getTimestampFromDateString("2013-02-14"));
		System.out.println(getTimestampFromDateString("2016-02-29"));
		System.out.println(getTimestampFromDateString("2013-03-14 17:26:01"));
		System.out.println(getTimestampFromDateString("2013-03-14 17:26:01.001"));
		
		System.out.println(getDateTimeStringFromSeconds(-1));
		System.out.println(getDateTimeStringFromSeconds(0));
		System.out.println(getDateTimeStringFromSeconds(1363253161));
		System.out.println(getDateTimeStringFromMillSeconds(1363253161000L));
		System.out.println(getDateTimeStringFromMillSeconds(1363253161001L));
	}

}
