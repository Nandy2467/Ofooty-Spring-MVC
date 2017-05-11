package com.nandeep.ofooty.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NextDate {
	
	private List<String> dateList;

	public NextDate() {
		this.dateList = new ArrayList<>();
		populateNextDateList();
	}

	
	public List<String> getDateList() {
		return dateList;
	}

	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}

	private void populateNextDateList() {
		Date date = new Date();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		for (int i = 1; i < 6; i++) {
			dateList.add(dtf.format(localDateTime.plusDays(i)));
		}
	}
	
}
