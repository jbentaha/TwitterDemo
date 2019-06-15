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

import com.example.demo.Main;
import com.example.demo.models.Likee;
import com.example.demo.models.LikeeService;
import com.example.demo.models.Message;
import com.example.demo.models.MessageService;
import com.example.demo.models.User;
import com.example.demo.models.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class ApiController {

	@Autowired
	private HttpSession httpSession;

	@Autowired
	MessageService mService = Main.messageService;

	@Autowired
	UserService userService = Main.userService;

	@Autowired
	LikeeService lService = Main.likeService;

	@RequestMapping(value = "/api", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String Submit(@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "idMsg", required = false) String idMsg,
			@RequestParam(value = "idUser", required = false) String idUser) {
		JsonObjectBuilder obj = Json.createObjectBuilder();
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		if (content != null)
			System.out.println("content: " + content);

		userService.replaceConstruct();

		// ObjectMapper mapper = new ObjectMapper();
		// ObjectNode root = mapper.createObjectNode();

		if (action != null) {
			switch (action) {
			case "login": {
				User usr = new User();
				usr.setUsername(username);
				usr.setPassword(password);
				if (userService.existUserInBase(usr)) {
					httpSession.setAttribute("loginSir", username);
					obj.add("login", "loginOk");
				}
			}
				break;
			case "deconnect": {
				httpSession.invalidate();
				obj.add("deconnect", true);
			}
				break;
			case "add": {
				String msgUser = (String) httpSession.getAttribute("loginSir");
				Message msg = new Message();
				msg.setMessage(content);
				msg.setIdUser(msgUser);
				mService.addMsg(msg);
				// root.set("message", mapper.convertValue(msg, JsonNode.class));
				// root.set("userSession",
				// mapper.convertValue((String)httpSession.getAttribute("loginSir"),
				// JsonNode.class));
				obj = msg.toJson().add("userSession", (String) httpSession.getAttribute("loginSir"));
			}
				break;
			case "remove": {
				obj = mService.deleteById(id);
			}
				break;
			case "lookup": {
				// root.set("messages", mapper.convertValue(mService.getAllJson(),
				// JsonNode.class));
				// root.set("likes", mapper.convertValue(lService.getAllLikes(),
				// JsonNode.class));
				arrayBuilder = mService.getAllJson();
				// root.set("userSession",
				// mapper.convertValue((String)httpSession.getAttribute("loginSir"),
				// JsonNode.class));
				obj.add("messages", arrayBuilder).add("userSession", (String) httpSession.getAttribute("loginSir"))
						.add("likes", lService.getAllLikes());
			}
				break;
			case "likee": {
				Likee likee = new Likee();
				likee.setMsgid(idMsg);
				likee.setUserId(idUser);
				boolean added = lService.addLike(likee);

				obj.add("likes", lService.getAllLikesByMsg(idMsg)).add("isAdded", added).add("nbLikes",
						lService.getNbLikes(idMsg));
			}
				;
				break;
			case "isUserLiked": {
				boolean result = lService.isMsgLikesByUser(idMsg, idUser);
				arrayBuilder = lService.usersLikeMsg(idMsg);
				obj.add("isLiked", result).add("LikedBy", arrayBuilder).add("nbLikes", lService.getNbLikes(idMsg));
			}
				;
				break;
			}
		}
		return obj.build().toString();
	}
}