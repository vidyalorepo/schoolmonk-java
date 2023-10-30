package com.dcc.schoolmonk.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommmonFunction {
	
	public static boolean isDateBetween(String oeStartDateStr, String oeEndDateStr){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	    try {
			/*Date startDate = sdf.parse(sdf.format(oeStartDateStr));
			Date endDate = sdf.parse(sdf.format(oeEndDateStr));*/
	    	Date startDate = sdf.parse(oeStartDateStr);
			Date endDate = sdf.parse(oeEndDateStr);
			Date d = new Date();
			String currDt = sdf.format(d);

			if ((d.after(startDate) && (d.before(endDate)))
			    || (currDt.equals(sdf.format(startDate)) || currDt.equals(sdf
			        .format(endDate)))) {
			  return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    return false;
	}
	
	public static String slugNameGeneration(String inputStr){
		String slug = inputStr.replaceAll("\\s{2,}", " ").trim().toLowerCase().replaceAll(" ", "-");
		return slug;
	}
}
