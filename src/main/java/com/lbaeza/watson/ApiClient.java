package com.lbaeza.watson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.json.JsonObject;

import com.lbaeza.utils.Utils;

public class ApiClient {
	
	private static String apiKey  = "{your-client-key}";
	
	public static String getWeather(String city)
	{
		String res = "";
		
		try {
			String apiEndp = "{WEATHER-api-endpoint}" + "?city=" + URLEncoder.encode(city, "UTF-8");
			JsonObject demog = Utils.restKeyCall(apiEndp, "GET", apiKey, "x-ibm-client-id", "");
			
			if(demog.get("temperature") != null)
				res = "<b>Temperatura:</b> " + demog.get("temperature") + " ";
			if(demog.get("max") != null)
				res += "<b>Máx:</b> " + demog.get("max") + " ";
			if(demog.get("min") != null)
				res += "<b>Min:</b> " + demog.get("min") + " ";
			res += "<br>";
		} catch (UnsupportedEncodingException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		
		return res;
	}
	
	public static String getLocation(String city)
	{
		String res = "";
		
		try {
			String apiEndp = "{GMAP-api-endpoint}" + "?city=" + URLEncoder.encode(city, "UTF-8");
			JsonObject demog = Utils.restKeyCall(apiEndp, "GET", apiKey, "x-ibm-client-id", "");
			
			if(demog != null)
				res= demog.get("lat") + "," + demog.get("lng");
			
		} catch (UnsupportedEncodingException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		
		return res;
	}
	
	public static String getDemographics(String city)
	{
		String res = "";
		
		try {
			String apiEndp = "{WIKIPEDIA-api-endpoint}" + "?city=" + URLEncoder.encode(city, "UTF-8");
			JsonObject demog = Utils.restKeyCall(apiEndp, "GET", apiKey, "x-ibm-client-id", "");
			
			if(demog != null)
			{
				res = demog.getString("description").replace("<p>","").replace("</p>", "") + "... ";
				res += "<a href=\"" + demog.getString("url") + "\" target=\"_blank\" style=\"font-size:18px\">Fuente: Wikipedia</a>";
			}
		} catch (UnsupportedEncodingException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		
		return res;
	}
}
