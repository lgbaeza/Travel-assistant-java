package com.lbaeza.watson;

import java.util.List;

import javax.json.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import com.ibm.watson.developer_cloud.conversation.v1.model.Entity;
import com.ibm.watson.developer_cloud.conversation.v1.model.Intent;

public class WMessage {

	public String getHTMLFormat() throws UnsupportedEncodingException
	{
		String res = "";
		
		String chatClass = "chatWatson";
		if(this.user)
			chatClass = "chatUser";
		
		res += "<div class=\"" + chatClass +"\">";
		if(this.user)
			res += "<b>@User</b><br>";
		else
			res += "<b>@Watson</b><br>";
		res += this.message;
		res += "</div>";
		
		return res;
	}
	
	public WMessage() {
		super();
		this.conversationId = "";
		this.nodeId = "";
		this.message = "";
		this.user = false;
		this.error = false;
		this.cont = "0";
		this.intents = new ArrayList<Intent>();
		this.entities = new ArrayList<Entity>();
		this.context = null;
		this.instrucciones = "";
		this.recordatorio = "";
	}

	public String conversationId;
	public String nodeId;
	public String message;
	public boolean user;
	public boolean error;
	public String cont;
	public List<Intent> intents;
	public List<Entity> entities;
	public JsonObject context;
	public String instrucciones;
	public String recordatorio;
}

