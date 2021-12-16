package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    JWTUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       System.out.println("inside authFilter");
    	String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken=null;
        //check null and formet
        if (requestTokenHeader != null && requestTokenHeader.startsWith("basic ")) {
            jwtToken = requestTokenHeader.substring(6);
           
                username = jwtUtil.getUsernameFromToken(jwtToken);
                if(username==null)
                {
                	throw new UsernameNotFoundException("username not found");
                }
                System.out.println(username);
            
          
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
            {
            
            	 UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
            	 
            	 if(jwtUtil.validateToken(jwtToken, userDetails))
            	 {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            	 }
            }
            else
            {
               
            }
        }
        filterChain.doFilter(request, response);
    }
}
