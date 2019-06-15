package com.example.demo.models;

import java.io.Serializable;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String idUser;
	private String date;
	private String message;

	public String getIdUser() {
		return idUser;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JsonObjectBuilder toJson() {
		JsonObjectBuilder obj = Json.createObjectBuilder();
		return obj.add("id", id).add("msg", message).add("msgUser", idUser);
	}

}
