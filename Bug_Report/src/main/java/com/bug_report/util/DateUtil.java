package com.bug_report.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	private DateFormat dateFormatter=new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
	
	public String getCurrentDate()
	{
		Date date=new Date();
		
		return dateFormatter.format(date);
 	}
}
