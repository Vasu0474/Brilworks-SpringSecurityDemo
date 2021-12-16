package com.example.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.repository.support.Repositories;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class JWTAuthenticationExceptionHandler implements AuthenticationEntryPoint,AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setStatus(401);
		//response.sendError(401,"jjj");
	
		Map<String, String> map=new HashMap<>();
		map.put("status", String.valueOf(401));
		map.put("message", "authorization denied");		
	        OutputStream out = response.getOutputStream();
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.writeValue(out,map);
	        out.flush();
	
		
	}

	@Override
	@ExceptionHandler(AuthenticationException.class)
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(403);
		Map<String, String> map=new HashMap<>();
		map.put("status", String.valueOf(403));
		map.put("message", "unauthorised");
	        OutputStream out = response.getOutputStream();
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.writeValue(out, map);
	        out.flush();
    
	
		
	}

	

}
