package com.lbaeza.watson;

//import java.util.Base64;
import org.apache.commons.codec.binary.Base64;

import java.util.List;

import javax.json.JsonObject;
import javax.json.JsonValue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.ibm.watson.developer_cloud.conversation.v1.model.Entity;
import com.ibm.watson.developer_cloud.conversation.v1.model.Intent;
import com.ibm.watson.developer_cloud.document_conversion.v1.*;
import com.ibm.watson.developer_cloud.document_conversion.v1.model.Answers;
import com.ibm.watson.developer_cloud.document_conversion.v1.model.Answers.AnswerUnits;
import com.lbaeza.utils.TravelInfo;
import com.lbaeza.utils.Utils;

public class WatsonClient {

	public static String traduceTexto(String texto, String idioma)
	{
		String res = "[texto traducido con Watson Translator]";
		
		return res;
	}
	
	public static List<AnswerUnits> convertDocument(InputStream fileContent, String fileName){
		
		List<AnswerUnits> res = null;
		
		try {
			DocumentConversion docConv = new DocumentConversion("2015-12-15");
			docConv.setEndPoint("https://gateway.watsonplatform.net/document-conversion/api");
			docConv.setUsernameAndPassword("{your-username}", "{your-password}");
			
			String fileExt = fileName.substring(fileName.lastIndexOf('.'));
			File tmpFile = File.createTempFile("fileUpload-doc-conv", fileExt);
			OutputStream outputStream = new FileOutputStream(tmpFile);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = fileContent.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.close();
			
			Answers docAnsw = docConv.convertDocumentToAnswer(tmpFile).execute();
			tmpFile.delete();
			
			res = docAnsw.getAnswerUnits();
			
		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		
		return res;
	}
	
	public static WMessage converseWatson(WMessage inputMessage, TravelInfo tripInfo) throws UnsupportedEncodingException
	{
		String apiEndp = "https://watson-api-explorer.mybluemix.net/conversation/api/v1/workspaces/{your-workspace-id}/message?version=2016-07-11";
		String user = "{your-username}";
		String pass = "{your-password}";
				
		String body = "";
		WMessage res = new WMessage();
		JsonObject watsonSays = null;
		
		if (inputMessage.nodeId == "") 
			body = "{\r\n  \"input\": {\r\n    \"text\": \"\"\r\n  }," +
					"\r\n \"context\": {" +
					"  \"destino_viaje\": \"" + tripInfo.destino + "\", " +
					"  \"numero_reservacion\": \"" + tripInfo.itinerary + "\", " +
					"  \"vuelo:ida\": \"" + tripInfo.goingFlight + "\", " +
					"  \"vuelo_vuelta\": \"" + tripInfo.returningFlight + "\", " +
					"  \"info_hospedaje\": \"" + tripInfo.lodgement + "\", " +
					"  \"direccion_hotel\": \"" + tripInfo.hotelAddress + "\", " +
					"  \"telefono_hotel\": \"" + tripInfo.hotelPhone + "\", " +
					"  \"fecha_viaje\": \"" + tripInfo.tripDate + "\" " +
					"}" +
					"\r\n}";
		else
		{
			body = "{\r\n  \"input\": {\r\n    \"text\": \"" + 
					inputMessage.message +
					"\"\r\n  },\r\n  \"context\": \r\n" +
					inputMessage.context.toString() +
					"  \r\n}";
		}
		
		try
		{
			URL url = new URL(apiEndp);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			
			String userCredentials = user + ":" + pass;
			
			String basicAuth = "Basic " + new String(Base64.encodeBase64(userCredentials.getBytes()));
			
			conn.setRequestProperty ("Authorization", basicAuth);
			conn.setRequestProperty("Content-Type", "application/json");
	
			OutputStream os = conn.getOutputStream();
			os.write(body.getBytes());
			os.flush();
			watsonSays = Utils.parseJSON(conn);
			conn.disconnect();
		}catch(IOException ex)
		{
			res = null;
			System.out.println("Error: " + ex.getMessage());
		}
		
		if(watsonSays == null)
		{
			res.message = "Trata de ir un poco más despacio amigo.";
			res.error = true;
		}
		else
		{
			res.conversationId = watsonSays.getJsonObject("context").getString("conversation_id");
			res.message = URLDecoder.decode(watsonSays.getJsonObject("output").getJsonArray("text").getString(0), "UTF-8");
			res.nodeId = watsonSays.getJsonObject("context").getJsonObject("system").getJsonArray("dialog_stack").get(0).toString().replace("\"", "");
			res.user = false;
			res.cont = String.valueOf(watsonSays.getJsonObject("context").getJsonObject("system").getInt("dialog_turn_counter"));
			res.context = watsonSays.getJsonObject("context");
			
			for(int i = 0; i < watsonSays.getJsonArray("entities").size(); i++)
			{
				JsonObject o = watsonSays.getJsonArray("entities").getJsonObject(i);
				String Entidad = o.get("entity").toString().replace("\"", "");
				String value = o.get("value").toString();
	
				res.entities.add(new Entity(Entidad, value, null));
				
			}
			for(int i = 0; i < watsonSays.getJsonArray("intents").size(); i++)
			{
				JsonObject o = watsonSays.getJsonArray("intents").getJsonObject(i);
				String Intent = o.get("intent").toString().replace("\"", "");
	
				res.intents.add(new Intent(Intent, null));
				
			}    
			if(res.context.containsKey("instrucciones") && res.context.getString("instrucciones") != null)
			{
				res.instrucciones = res.context.getString("instrucciones");
			}
			if(res.context.containsKey("recordar"))
				res.recordatorio = res.context.getString("recordar");
		}
		
		return res;		
	}
	
}
