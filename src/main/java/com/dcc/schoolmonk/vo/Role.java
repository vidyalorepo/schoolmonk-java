package com.dcc.schoolmonk.vo;

import org.springframework.security.core.GrantedAuthority;
 
public class Role implements GrantedAuthority{
   
    private static final long serialVersionUID = 1L;
    private String name;
     
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getAuthority() {
        return this.name;
    }
}

