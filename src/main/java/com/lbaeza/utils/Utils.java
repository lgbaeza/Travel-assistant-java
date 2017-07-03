package com.lbaeza.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.Part;

public class Utils {

	public static String getFileName(final Part part) {
	    
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	
	public static JsonObject parseJSON(HttpURLConnection conn) throws IOException
	{
		JsonObject res = null;
		
		try 
		{
			InputStream is = conn.getInputStream();
	        JsonReader rdr = Json.createReader(is);
			res = rdr.readObject();
		}
        catch(Exception ex)
        {
        	System.out.println("Error: " + ex.getMessage());
        }
		return res;
	}
	
	public static JsonObject restKeyCall(String apiEndp, String method, String apikey, String headerkey, String body)
	{
		JsonObject res = null;
		
		try
		{
			URL url = new URL(apiEndp);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			
			conn.setRequestProperty (headerkey, apikey);
			conn.setRequestProperty("content-type", "application/json");

			res = parseJSON(conn);
			conn.disconnect();
		}catch(IOException ex)
		{
			res = null;
		}
		
		return res;
	}
	
	
}
