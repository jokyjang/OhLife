package org.zzx.ohlife.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
	
	private static final String[] WEEK = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	
	public static String parseDateToDayOfWeek(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(date));
		
		return WEEK[calendar.get(Calendar.DAY_OF_WEEK) - 1];
	}
}
