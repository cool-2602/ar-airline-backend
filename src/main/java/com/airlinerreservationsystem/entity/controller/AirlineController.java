package com.airlinerreservationsystem.entity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.airlinerreservationsystem.entity.airline;

import com.airlinerreservationsystem.repository.airlineRepository;




@CrossOrigin("http://localhost:3000")
@RestController
public class AirlineController {
	
	@Autowired
	airlineRepository repo;
	
	@PostMapping("/airlines/add")
	airline newAirlines(@RequestBody airline air) {
		return repo.save(air);
	}

	// get
	@GetMapping("/airlines")
	List<airline> getAllFlights() {
		List<airline> air = repo.findAll();
		return air;
	}

	// update
	@GetMapping("airlines/{a_id}")
	airline getAirlines(@PathVariable int a_id) {
		airline air = repo.findById(a_id).get();
		return air;
	}
}
