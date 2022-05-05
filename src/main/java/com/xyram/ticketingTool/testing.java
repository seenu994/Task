package com.xyram.ticketingTool;

public class testing {

	
	public static Long getDate(String zone)
	{
		String plus=zone.contains("+")?"+":"-";
		  String timeZone[]=  zone.split(":");
		  String minutes=timeZone[1];
		  String hours=timeZone[0];
		 minutes=plus+minutes;
				  
		  long hrs=       Long.valueOf(hours);
		  long min=Long.valueOf(minutes);
		  Long sec=(hrs*60*60)+(min*60);
		return null;
	}
	
	public static void main(String args[]) {
		
		testing.getDate("-05:30");
	}
}
