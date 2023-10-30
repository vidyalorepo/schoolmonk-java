package com.dcc.schoolmonk.vo;

import java.util.List;

public class SendsmsVo {

	private String flow_id;
	private String sender;
	private List<SendsmsRecipientsVO> recipients;
	public String getFlow_id() {
		return flow_id;
	}
	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public List<SendsmsRecipientsVO> getRecipients() {
		return recipients;
	}
	public void setRecipients(List<SendsmsRecipientsVO> recipients) {
		this.recipients = recipients;
	}
	
	
}
