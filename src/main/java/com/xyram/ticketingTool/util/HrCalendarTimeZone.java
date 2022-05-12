package com.xyram.ticketingTool.util;

public interface HrCalendarTimeZone {
	
	public static int getDate(String zone)
	{
		  String plus=zone.contains("+")?"+":"-";
		  String timeZone[]=  zone.split(":");
		  String minutes=timeZone[1];
		  String hours=timeZone[0];
		  minutes=plus+minutes;
				  
		  long hrs=Long.valueOf(hours);
		  long min=Long.valueOf(minutes);
		  int sec=(int) ((hrs*60*60)+(min*60));
	   	  return sec;
	}

}
