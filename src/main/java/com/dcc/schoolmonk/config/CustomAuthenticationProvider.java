package com.dcc.schoolmonk.config;

import java.util.Collection;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.dcc.schoolmonk.common.Password;
import com.dcc.schoolmonk.service.UserService;
import com.dcc.schoolmonk.vo.UserVo;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
    @Autowired
    private UserService userServices; 
	
	private static final Logger LOGGER = Logger.getLogger(CustomAuthenticationProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
    	LOGGER.info("CustomAuthenticationProvider:: authenticate:: Entering");
    	
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
    	
		boolean checkPassword  = false;
//		UserVo user = userServices.findByUsername(username);
		UserVo user = userServices.findUserByEmail(username);
		
		
        if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
            throw new BadCredentialsException("Username not found.");
        }
		 
		String passwordfromui = new String(Base64.decodeBase64(password));
    	
		LOGGER.info("CustomAuthenticationProvider:: authenticate:: Password::" + passwordfromui);
		
		try {
			
			checkPassword = Password.check(password, user.getPassword());
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
        if (!checkPassword) {
            throw new BadCredentialsException("Wrong password.");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}

}
