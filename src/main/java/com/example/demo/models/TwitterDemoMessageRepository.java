package com.example.demo.models;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TwitterDemoMessageRepository extends CrudRepository<TwitterDemoMessage, String>{

	public List<TwitterDemoMessage> findAllByOrderByDateAsc();
}
