package com.example.demo.models;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

	List<User> listeOfUsers;
	@Autowired
	UserRepository base;
	
	public void replaceConstruct() {
		User myUser = new User();
		myUser.setUsername("jaouad");
		myUser.setPassword("jaouad");
		
		User myUser1 = new User();
		myUser1.setUsername("admin");
		myUser1.setPassword("admin");
		
		User myUser2 = new User();
		myUser2.setUsername("autre");
		myUser2.setPassword("autre");
		
		base.save(myUser);
		base.save(myUser1);
		base.save(myUser2);
	}
	
	public boolean existLogin(User usr) {
		boolean exist = false;
		Iterator<User> it = listeOfUsers.iterator();
		while(it.hasNext()) {
			User userList = (User)it.next();
			if(userList.getUsername().equals(usr.getUsername()) && userList.getPassword().equals(usr.getPassword())) {
				exist = true;
			}
		}
		return exist;
	}
	
	public void addUser(User usr){
		base.save(usr);
	}
	
	public boolean existUserInBase(User usr){
		boolean exist = false;
		if(base.existsById(usr.getUsername())) {
			Optional<User> user =  base.findById(usr.getUsername());
			if(user.get().getPassword().equals(usr.getPassword())) {
				exist = true;
			}
		}
		return exist;
	}
	
	
}
