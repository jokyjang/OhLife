package org.zzx.ohlife.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class Utils {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh-mm-ss";
	private static final String SEATTLE_TIME_ZONE = "US/Pacific";
	private static final String[] WEEK = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	
	public static String parseDayOfWeek(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(date));
		
		return WEEK[calendar.get(Calendar.DAY_OF_WEEK) - 1];
	}
	
	// parse DateTime from JodaTime to yyyy-MM-dd format String date.
	public static String parseSeattleDateString(DateTime dateTime) {
		return DateTimeFormat.forPattern(DATE_FORMAT)
				.withZone(DateTimeZone.forID(SEATTLE_TIME_ZONE))
				.print(dateTime);
	}
	
	// The format of dateString is yyyy-MM-dd, return jodaTime LocalDate
	public static LocalDate parseLocalDate(String dateString) {
		return DateTimeFormat.forPattern(DATE_FORMAT).parseLocalDate(dateString);
	}
	
	public static Map<String, AttributeValue> getTableJournalKey(String date) {
		Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
		key.put(Constants.ATTR_JOURNAL_USERNAME, new AttributeValue().withS(Constants.DEFAULT_USER_NAME));
		key.put(Constants.ATTR_JOURNAL_DATE, new AttributeValue().withS(date));
		return key;
	}
	
	public static Map<String, AttributeValue> getTableJournalItem(String date, String content) {
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
		
		String dayOfWeek = "";
		try {
			dayOfWeek = parseDayOfWeek(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		item.put(Constants.ATTR_JOURNAL_USERNAME, new AttributeValue().withS(Constants.DEFAULT_USER_NAME));
		item.put(Constants.ATTR_JOURNAL_DATE, new AttributeValue().withS(date));
		item.put(Constants.ATTR_JOURNAL_DAYOFWEEK, new AttributeValue().withS(dayOfWeek));
		if (content != null) {
			item.put(Constants.ATTR_JOURNAL_CONTENT, new AttributeValue().withS(content));
		}
		return item;
	}
}
