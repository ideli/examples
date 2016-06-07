package _basic.time;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeExamples {

	public static void main(String[] args) {
		Instant start = Instant.now();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {}
		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);
		System.out.println("time elapsed: " + timeElapsed.toMillis());
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		
		LocalDate day = LocalDate.now();
		System.out.println("LocalDate.now()=" + day.format(dateFormatter));
		day = day.plusDays(1);
		System.out.println("LocalDate.now().plusDays(1)=" + day.format(dateFormatter));
		day = LocalDate.of(2016, 5, 21);
		System.out.println("LocalDate.of(2016, 5, 21)=" + day.format(dateFormatter));
		System.out.println("LocalDate.of(2016, 5, 21).until(day.minusMonths(100), ChronoUnit.DAYS)=" + day.until(day.minusMonths(100), ChronoUnit.DAYS));
		
		LocalDate nextMonth = day.plusMonths(1);
		System.out.println("LocalDate.now().getMonth();LocalDate.now().plusMonths(1).getMonth();compareTo=" + day.getMonth() + ";" + nextMonth.getMonth() + ";" + day.getMonth().compareTo(nextMonth.getMonth()));
		
		day = LocalDate.parse("2016-05-10");
		System.out.println("LocalDate.parse(\"2016-05-10\")=" + day.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		
		Date legacyDay = new Date(System.currentTimeMillis());
		Instant instant = legacyDay.toInstant();
		LocalDateTime newDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		System.out.println("LocalDateTime.ofInstant(instant, ZoneId.systemDefault())=" + newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		
		
		
		LocalDateTime now = LocalDateTime.now();
		String ns = now.format(dateTimeFormatter);
		LocalDateTime dateTime = LocalDateTime.parse(ns, dateTimeFormatter);
		System.out.println("LocalDateTime.parse=" + dateTime.format(dateTimeFormatter));
		
		day = LocalDate.parse(ns, dateTimeFormatter);
		System.out.println("LocalDate.parse=" + day.format(dateFormatter));
	}
}
