package com.phonegap.plugin;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.util.Log;

public class CalendarEventPlugin extends Plugin {

	public static final String NATIVE_ACTION_STRING="calendarEventAction";
	public static final String SUCCESS_PARAMETER="success";
	
	@Override
	public PluginResult execute(String action, JSONArray data, String callbackId) {
		
		 
		 //only perform the action if it is the one that should be invoked
		 if (NATIVE_ACTION_STRING.equals(action)) {
			 
			 String resultType = null;
			 String receivedString = null;
			 
			 try {
//				 resultType = data.getString(0);
				receivedString = data.getString(0);
				System.out.println("receivedString: " + receivedString);
				JSONObject jsonObject = new JSONObject(receivedString);
				
				Calendar cal = Calendar.getInstance();              
		        Intent intent = new Intent(Intent.ACTION_EDIT);
		        intent.setType("vnd.android.cursor.item/event");
		        intent.putExtra("title", jsonObject.getString("title"));
		        if(parseDate(jsonObject.getString("beginTime")) != 0){
		        	intent.putExtra("beginTime", parseDate(jsonObject.getString("beginTime")));
		        }
		        if(parseDate(jsonObject.getString("endTime")) != 0){
		        	intent.putExtra("endTime", parseDate(jsonObject.getString("endTime")));
		        }
		        intent.putExtra("description", jsonObject.getString("description"));
		        intent.putExtra("location", jsonObject.getString("location"));
		        
		        this.ctx.startActivity(intent);
			 }
			 catch (Exception ex) {
				 Log.d("CalendarEventPlugin", ex.toString());
			 }
			 
//			 return new PluginResult(PluginResult.Status.OK, "Event Added!");
			 			 
//			 if (resultType.equals(SUCCESS_PARAMETER)) {
//				 return new PluginResult(PluginResult.Status.OK, "Yay, Success!!!");
//			 }
//			 else {
//				 return new PluginResult(PluginResult.Status.ERROR, "Oops, Error :(");
//			 }
		 }
		 
		 return null;
	}
	
	
	
	
	public long parseDate(String dateString){
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("Europe/Helsinki"));
		long longDate = 0;
		
		Date date = null;
		try {
			date = (Date)formatter.parse(dateString);
			System.out.println("Parsed date is: " + date);
			longDate = date.getTime();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return longDate;
	}
	
	
	
}
