package com.example.demo;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
		
	@Autowired
	private EmployeeRepository employeeRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("user details service");
		// TODO Auto-generated method stub
		Employee employee=this.employeeRepository.findByUsername(username);
		if(employee==null)
		{
			
			throw new UsernameNotFoundException("no user is present");
		}
		
		return new com.example.demo.UserDetails(employee);
	}
	
	

}
   