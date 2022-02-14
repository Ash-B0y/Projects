package com.zoho.ars.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtility {
	
	private CalendarUtility()
	{
		
	}
	
	public static Calendar getCalendarFromString(String date) throws ParseException{
		
		SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy");
		Date dateReference = null;
		dateReference = simpleDate.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateReference);
		return calendar;
	}

}
