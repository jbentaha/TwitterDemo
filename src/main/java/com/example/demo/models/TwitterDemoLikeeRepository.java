package com.example.demo.models;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TwitterDemoLikeeRepository extends CrudRepository<TwitterDemoLikee, String>{

	public List<TwitterDemoLikee> findAllByMsgid(String msgid);
	public TwitterDemoLikee findByMsgidAndUserid(String msgid, String userid);
}
