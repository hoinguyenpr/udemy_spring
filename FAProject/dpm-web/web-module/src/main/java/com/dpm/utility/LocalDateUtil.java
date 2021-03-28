package com.dpm.utility;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class LocalDateUtil {
	
	//Modify NguyenND6 
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static LocalDate parse(String date) {
		return LocalDate.parse(date, DATE_FORMAT);
	}
	

	public static LocalDateTime toLocalDateTime(Long time) {
		return Instant.ofEpochMilli(time).atZone(ZoneId.of("UTC")).toLocalDateTime();
	}

	public static Long toMiliseconds(LocalDateTime dateTime) {
		return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public static LocalDateTime toStartLocalDateTime(Long milliseconds) {
		LocalDateTime localDateTime = Instant.ofEpochMilli(milliseconds)
				.atZone(ZoneId.systemDefault()).toLocalDateTime().withHour(0)
				.withMinute(0).withSecond(0);
		return localDateTime;
	}

	public static LocalDateTime toEndLocalDateTime(Long milliseconds) {
		LocalDateTime localDateTime = Instant.ofEpochMilli(milliseconds)
				.atZone(ZoneId.systemDefault()).toLocalDateTime().withHour(23)
				.withMinute(59).withSecond(59);
		return localDateTime;
	}

	public static Long toMilliseconds(LocalDateTime dateTime) {
		return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public static Long toMilliseconds(LocalDate dateTime) {
		return dateTime.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
	}

}
