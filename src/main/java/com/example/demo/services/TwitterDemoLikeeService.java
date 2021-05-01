package com.example.demo.services;

import java.util.List;
import java.util.UUID;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.TwitterDemoLikee;
import com.example.demo.models.TwitterDemoLikeeRepository;

@Service
public class TwitterDemoLikeeService {
	
	@Autowired
	private TwitterDemoLikeeRepository base;

	public boolean addLike(TwitterDemoLikee likee) {
		likee.setId(UUID.randomUUID().toString());
		TwitterDemoLikee like = base.findByMsgidAndUserid(likee.getMsgid(), likee.getUserid());
		if(like == null) {
			base.save(likee);
			return true;
		}
		else {
			base.deleteById(like.getId());
			System.out.println("deleted");
			return false;
		}
	}
	
	public JsonArrayBuilder getAllLikes() {
		List<TwitterDemoLikee> allLikees = (List<TwitterDemoLikee>) base.findAll();
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for(TwitterDemoLikee likee : allLikees) {
			JsonObjectBuilder obj = Json.createObjectBuilder();
			obj.add("id", likee.getId());
			obj.add("msg", likee.getMsgid());
			obj.add("user", likee.getUserid());
			arrayBuilder.add(obj);
		}
		
		return arrayBuilder;
	}
	
	public JsonArrayBuilder getAllLikesByMsg(String idMsg) {
		List<TwitterDemoLikee> allLikees = (List<TwitterDemoLikee>) base.findAllByMsgid(idMsg);
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for(TwitterDemoLikee likee : allLikees) {
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
		List<TwitterDemoLikee> allLikeofMsg = (List<TwitterDemoLikee>) base.findAllByMsgid(idMsg);
		for(TwitterDemoLikee likee : allLikeofMsg) {
			if(likee.getUserid().equals(user))
				isExist = true;
		}
		return isExist;
	}
	
	public JsonArrayBuilder usersLikeMsg(String idMsg) {
		List<TwitterDemoLikee> allLikeofMsg = (List<TwitterDemoLikee>) base.findAllByMsgid(idMsg);
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for(TwitterDemoLikee likee : allLikeofMsg) {
			JsonObjectBuilder obj = Json.createObjectBuilder();
			obj.add("user", likee.getUserid());
			arrayBuilder.add(obj);
		}
		return arrayBuilder;
	}
	
	public int getNbLikes(String idMsg) {
		List<TwitterDemoLikee> allLikeofMsg = (List<TwitterDemoLikee>) base.findAllByMsgid(idMsg);
		return allLikeofMsg.size();
	}
}
