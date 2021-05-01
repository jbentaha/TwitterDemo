package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TwitterDemoUser {

	@Id
	private String username;

	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object usr) {
		boolean exist = false;
		TwitterDemoUser myUser = (TwitterDemoUser) usr;
		if (this.getUsername().equals(myUser.getUsername()) && this.getPassword().equals(myUser.getPassword())) {
			exist = true;
		}

		return exist;
	}

}
