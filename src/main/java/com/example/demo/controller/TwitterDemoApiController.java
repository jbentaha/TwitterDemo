package com.example.demo.controller;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.TwitterDemoMain;
import com.example.demo.models.TwitterDemoLikee;
import com.example.demo.models.TwitterDemoMessage;
import com.example.demo.models.TwitterDemoUser;
import com.example.demo.services.TwitterDemoLikeeService;
import com.example.demo.services.TwitterDemoMessageService;
import com.example.demo.services.TwitterDemoUserService;

@RestController
public class TwitterDemoApiController {

	@Autowired
	private HttpSession httpSession;

	@Autowired
	TwitterDemoMessageService mService;

	@Autowired
	TwitterDemoUserService userService;

	@Autowired
	TwitterDemoLikeeService lService;
	
	@RequestMapping(value = "/api", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String Submit ( //
			@RequestParam(value = "content", required = false) String content, //
			@RequestParam(value = "action", required = false) String action, //
			@RequestParam(value = "id", required = false) String id, //
			@RequestParam(value = "username", required = false) String username, //
			@RequestParam(value = "password", required = false) String password, //
			@RequestParam(value = "idMsg", required = false) String idMsg, //
			@RequestParam(value = "idUser", required = false) String idUser //
			) {
		
		JsonObjectBuilder obj = Json.createObjectBuilder();
		JsonArrayBuilder arrayBuilder;

		userService.replaceConstruct();

		if (action != null) {
			switch (action) {
				case "login": 
					TwitterDemoUser usr = new TwitterDemoUser();
					usr.setUsername(username);
					usr.setPassword(password);
					if (userService.existUserInBase(usr)) {
						httpSession.setAttribute(TwitterDemoMain.LOGED_USER, username);
						obj.add("login", "loginOk");
					}
					break;
	
				case "deconnect": 
					httpSession.invalidate();
					obj.add("deconnect", true);
					break;
	
				case "add": 
					String msgUser = (String) httpSession.getAttribute(TwitterDemoMain.LOGED_USER);
					TwitterDemoMessage msg = new TwitterDemoMessage();
					msg.setMessage(content);
					msg.setIdUser(msgUser);
					mService.addMsg(msg);
					obj = msg.toJson().add("userSession", (String) httpSession.getAttribute(TwitterDemoMain.LOGED_USER));
					break;
					
				case "remove": 
					obj = mService.deleteById(id);
					break;
				
				case "lookup": 
					arrayBuilder = mService.getAllJson();
					obj.add("messages", arrayBuilder).add("userSession", (String) httpSession.getAttribute(TwitterDemoMain.LOGED_USER))
							.add("likes", lService.getAllLikes());
					break;
					
				case "likee": 
					TwitterDemoLikee likee = new TwitterDemoLikee();
					likee.setMsgid(idMsg);
					likee.setUserId(idUser);
					boolean added = lService.addLike(likee);
	
					obj.add("likes", lService.getAllLikesByMsg(idMsg)).add("isAdded", added).add("nbLikes",
							lService.getNbLikes(idMsg));
					break;

				case "isUserLiked": 
					boolean result = lService.isMsgLikesByUser(idMsg, idUser);
					arrayBuilder = lService.usersLikeMsg(idMsg);
					obj.add("isLiked", result).add("LikedBy", arrayBuilder).add("nbLikes", lService.getNbLikes(idMsg));
					break;
					
					default: 
						break;
			}
		}
		
		return obj.build().toString();
	}
}