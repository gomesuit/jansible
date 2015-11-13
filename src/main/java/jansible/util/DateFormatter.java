package jansible.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
	
	synchronized public static String getDateString(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(date);
	}
}
