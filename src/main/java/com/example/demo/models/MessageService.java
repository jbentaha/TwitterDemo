package com.example.demo.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

	@Autowired
	MessageRepository base;
	
	private final DateFormat dateF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public void addMsg(Message msg) {
		msg.setId(UUID.randomUUID().toString());
		msg.setDate(dateF.format(new Date()));
		base.save(msg);
	}
	
	public JsonArrayBuilder getAllJson() {
		List<Message> allMsgs = (List<Message>) base.findAllByOrderByDateAsc();
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for(Message msg : allMsgs) {
			System.out.println("id: "+msg.getId()+" contenu: "+msg.getMessage());
			JsonObjectBuilder obj = Json.createObjectBuilder();
			obj.add("id", msg.getId());
			obj.add("msg", msg.getMessage());
			obj.add("msgUser", msg.getIdUser());
			arrayBuilder.add(obj);
		}
		
		return arrayBuilder;
	}
	
	
	public JsonObjectBuilder deleteById(String id) {
		boolean bool = false;
		JsonObjectBuilder obj = Json.createObjectBuilder();
		if(base.findOne(id) != null) {
			Message msg = base.findOne(id);
			base.delete(msg);
			bool = true;
		}
		obj.add("isDeleted", bool);
		return obj;
	}

}
