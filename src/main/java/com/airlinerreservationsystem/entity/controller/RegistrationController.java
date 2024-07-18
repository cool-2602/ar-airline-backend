package com.airlinerreservationsystem.entity.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.airlinerreservationsystem.entity.registrations;
import com.airlinerreservationsystem.repository.registrationRepository;

import jakarta.servlet.Registration;
import jakarta.servlet.http.HttpSession;



@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
@RestController
public class RegistrationController {
	
	@Autowired
	private registrationRepository repo;
	

//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/registration/add")
	registrations newRegistration(@RequestBody registrations registration)
	{
//		registration.setPswd(passwordEncoder.encode(registration.getPswd()));
		registration.setRole("ROLE_USER");
		System.out.println(registration);
		return repo.save(registration);
	}
	
	@GetMapping("/registration")
	List<registrations> getAllRegistration()
	{
		List<registrations>u = repo.findAll();
		return u;
	}
	@GetMapping("registration/{reg_id}")
	registrations getRegistration(@PathVariable int reg_id)
	{
		registrations registration = repo.findById(reg_id).get();
		System.out.println(registration);
		return registration;	
	}
	
	
	@GetMapping("registration/pswd/{pswd}")
	registrations getRegistrationByPswd(@PathVariable String pswd)
	{
		registrations registration = repo.findByPswd(pswd);
		return registration;	
	}
	
	
//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody registrations user) {
//        Optional<registrations> optionalUser = repo.findByEmailID(user.getEmailID());
//        if (optionalUser.isPresent()) {
//            registrations dbUser = optionalUser.get();
//            if (dbUser.getPswd().equals(user.getPswd())) {
//                return ResponseEntity.ok(dbUser);
//            }
//        }
//        return ResponseEntity.status(401).body("Invalid email or password");
//    }
			
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody registrations loginRequest, HttpSession session) {
        Optional<registrations> user = repo.findByEmailID(loginRequest.getEmailID());

        if (user.isPresent() && user.get().getPswd().equals(loginRequest.getPswd())) {
            session.setAttribute("user", user.get());
            session.setAttribute("role", user.get().getRole());
            return ResponseEntity.ok(user.get().getRole());
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/checkSession")
    public ResponseEntity<?> checkSession(HttpSession session) {
    	registrations user = (registrations) session.getAttribute("user");
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("Not authenticated");
        }
    }

}

