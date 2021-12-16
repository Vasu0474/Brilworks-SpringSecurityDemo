package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private JWTUtil jwtUtil;
	
	
	@GetMapping("/demo")
	public String demo()
	{
		return "this is demo";
	}
	
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllUser() {
		
		List<Employee> list = employeeRepository.findAll();
		return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> setSignUp(@RequestBody Employee user) {
		
		Employee user1=new Employee();
		user1.setId(user.getId());
		user1.setUsername(user.getUsername());
		user1.setPassword(user.getPassword());
		user1.setRole(user.getRole());
		
		
		UserDetails userDetails=new UserDetails(user1);
		String token=jwtUtil.generateToken(userDetails);
		user1.setToken(token);
		employeeRepository.save(user1);
		return new ResponseEntity<String>("saved...", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> setLogIn(@RequestBody EmployeeLogin employee) throws Exception
	{
		Employee e=employeeRepository.findByUsernameAndPassword(employee.getUsername(),employee.getPassword());
		if(e==null)
		{
			throw new UsernameNotFoundException("user not found");
		}
		Map<String , String > map=new HashMap<>();
		map.put("id", String.valueOf(e.getId()));
		map.put("username", e.getUsername());
		map.put("token",e.getToken());
		map.put("role",e.getRole());
//		try
//		{
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(employee.getUserName(), employee.getPassword()));
//		}
//		catch (BadCredentialsException e) {
//			throw new Exception("Incorrect username or password",e);
//		}
//		
//		final UserDetails userDetails=(UserDetails) customUserDetailsService.loadUserByUsername(employee.getUserName());
		return new ResponseEntity<Map<String,String>>(map,HttpStatus.OK);
	}
	
	@GetMapping("/employee")
	public ResponseEntity<?> findById()
	{
		List<Employee> employee=employeeRepository.findAll();
		return new ResponseEntity<List<Employee>>(employee,HttpStatus.OK);
	}
	

}
