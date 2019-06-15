package com.example.demo.models;

import java.util.List;
import java.util.UUID;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeeService {
	@Autowired
	LikeeRepository base;

	public boolean addLike(Likee likee) {
		likee.setId(UUID.randomUUID().toString());
		Likee like = base.findByMsgidAndUserid(likee.getMsgid(), likee.getUserid());
		if(like == null) {
			base.save(likee);
			return true;
		}
		else {
			base.delete(like.getId());
			System.out.println("deleted");
			return false;
		}
	}
	
	public JsonArrayBuilder getAllLikes() {
		List<Likee> allLikees = (List<Likee>) base.findAll();
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for(Likee likee : allLikees) {
			JsonObjectBuilder obj = Json.createObjectBuilder();
			obj.add("id", likee.getId());
			obj.add("msg", likee.getMsgid());
			obj.add("user", likee.getUserid());
			arrayBuilder.add(obj);
		}
		
		return arrayBuilder;
	}
	
	public JsonArrayBuilder getAllLikesByMsg(String idMsg) {
		List<Likee> allLikees = (List<Likee>) base.findAllByMsgid(idMsg);
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for(Likee likee : allLikees) {
			JsonObjectBuilder obj = Json.createObjectBuilder();
			obj.add("id", likee.getId());
			obj.add("msg", likee.getMsgid());
			obj.add("user", likee.getUserid());
			arrayBuilder.add(obj);
		}
		
		return arrayBuilder;
	}
	
	public boolean isMsgLikesByUser(String idMsg, String user) {
		boolean isExist = false;
		List<Likee> allLikeofMsg = (List<Likee>) base.findAllByMsgid(idMsg);
		for(Likee likee : allLikeofMsg) {
			if(likee.getUserid().equals(user))
				isExist = true;
		}
		return isExist;
	}
	
	public JsonArrayBuilder usersLikeMsg(String idMsg) {
		List<Likee> allLikeofMsg = (List<Likee>) base.findAllByMsgid(idMsg);
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for(Likee likee : allLikeofMsg) {
			JsonObjectBuilder obj = Json.createObjectBuilder();
			obj.add("user", likee.getUserid());
			arrayBuilder.add(obj);
		}
		return arrayBuilder;
	}
	
	public int getNbLikes(String idMsg) {
		List<Likee> allLikeofMsg = (List<Likee>) base.findAllByMsgid(idMsg);
		return allLikeofMsg.size();
	}
}
