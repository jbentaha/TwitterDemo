package com.example.demo.models;

import java.io.Serializable;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Likee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String msgid;

	private String userid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String idMsg) {
		this.msgid = idMsg;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserId(String userId) {
		this.userid = userId;
	}

	public JsonObjectBuilder toJson() {
		JsonObjectBuilder obj = Json.createObjectBuilder();
		return obj.add("id", id).add("idMsg", msgid).add("idUser", userid);
	}

}
