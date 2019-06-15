package com.example.demo.models;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LikeeRepository extends CrudRepository<Likee, String>{

	public List<Likee> findAllByMsgid(String msgid);
	public Likee findByMsgidAndUserid(String msgid, String userid);
}
