package com.example.demo.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.TwitterDemoUser;
import com.example.demo.models.TwitterDemoUserRepository;


@Service
public class TwitterDemoUserService {

	@Autowired
	private TwitterDemoUserRepository base;
	
	public void replaceConstruct() {
		TwitterDemoUser myUser = new TwitterDemoUser();
		myUser.setUsername("jaouad");
		myUser.setPassword("jaouad");
		
		TwitterDemoUser myUser1 = new TwitterDemoUser();
		myUser1.setUsername("admin");
		myUser1.setPassword("admin");
		
		TwitterDemoUser myUser2 = new TwitterDemoUser();
		myUser2.setUsername("autre");
		myUser2.setPassword("autre");
		
		base.save(myUser);
		base.save(myUser1);
		base.save(myUser2);
	}
	
	public void addUser(TwitterDemoUser usr){
		base.save(usr);
	}
	
	public boolean existUserInBase(TwitterDemoUser usr){
		boolean exist = false;
		if(base.existsById(usr.getUsername())) {
			Optional<TwitterDemoUser> optional = base.findById(usr.getUsername());
			if(optional.isPresent()) {
				TwitterDemoUser userInBase = optional.get();
				if(userInBase.getPassword().equals(usr.getPassword())) {
					exist = true;
				}
			}
		}
		return exist;
	}
	
	
}
