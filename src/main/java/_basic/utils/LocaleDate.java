package _basic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocaleDate {

	private Date date;
	//private Locale locale;
	
	public LocaleDate() {
		date = new Date();
	}
	
	public String toString() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
	}
	
	public static void main(String[] args) {
		LocaleDate date = new LocaleDate();
		System.out.println(date.toString());
	}
}
