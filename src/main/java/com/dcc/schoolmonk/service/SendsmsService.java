package com.dcc.schoolmonk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dcc.schoolmonk.vo.SendsmsVo;

@Service
public class SendsmsService {

	private RestTemplate restTemplate;
	
	@Autowired
	public void ServiceLayer(RestTemplate restTemplate) {
		this.restTemplate=restTemplate;
	}
	
	public String consumeAPI(SendsmsVo sendsmsvo) {
		HttpHeaders headers=new HttpHeaders();
		headers.add("authkey","357692AkQnoSD2F6062ba0cP1");
		HttpEntity<SendsmsVo> entity=new HttpEntity<SendsmsVo>(sendsmsvo,headers);
		
		RestTemplate restTemplate =new RestTemplate();
	    return	restTemplate.exchange("https://api.msg91.com/api/v5/flow/",HttpMethod.POST,entity,String.class).getBody();
	}
	
}
