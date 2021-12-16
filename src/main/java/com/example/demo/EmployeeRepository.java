package com.example.demo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	public Employee findByUsername(String username);
	
	public ArrayList<Employee> findAll();
	
	//@Query(value = "select * from employee where username=?1 and password=?2",nativeQuery = true)
	public Employee findByUsernameAndPassword(String username,String password);
}
