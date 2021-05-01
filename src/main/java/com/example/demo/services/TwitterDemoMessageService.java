package com.example.demo.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.TwitterDemoMessage;
import com.example.demo.models.TwitterDemoMessageRepository;

@Service
public class TwitterDemoMessageService {

	@Autowired
	private TwitterDemoMessageRepository base;
	
	private final DateFormat dateF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public void addMsg(TwitterDemoMessage msg) {
		msg.setId(UUID.randomUUID().toString());
		msg.setDate(dateF.format(new Date()));
		base.save(msg);
	}
	
	public JsonArrayBuilder getAllJson() {
		List<TwitterDemoMessage> allMsgs = base.findAllByOrderByDateAsc();
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for(TwitterDemoMessage msg : allMsgs) {
			JsonObjectBuilder obj = Json.createObjectBuilder();
			obj.add("id", msg.getId()).add("msg", msg.getMessage()).add("msgUser", msg.getIdUser());
			arrayBuilder.add(obj);
		}
		
		return arrayBuilder;
	}
	
	
	public JsonObjectBuilder deleteById(String id) {
		boolean bool = false;
		JsonObjectBuilder obj = Json.createObjectBuilder();
		Optional<TwitterDemoMessage> optional = base.findById(id);
		if(optional.isPresent()) {
			base.delete(optional.get());
			bool = true;
		}
		obj.add("isDeleted", bool);
		return obj;
	}

}
