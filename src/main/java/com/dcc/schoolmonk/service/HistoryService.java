package com.dcc.schoolmonk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.dao.HistoryDao;
import com.dcc.schoolmonk.dao.UserDao;
import com.dcc.schoolmonk.vo.HistoryVo;

@Service
public class HistoryService {
	
	private static final Logger LOGGER = Logger.getLogger(HistoryService.class);
	
	@Autowired
	HistoryDao historyDao;
	
	@Autowired
	UserDao userDao;

	public HistoryService() {
		super();
	}
	
	public static HashMap<String, String> designationMap = new HashMap<>();
	
	static {
		designationMap.put("2", "Asst. Manager - Information System");
		designationMap.put("4", "Manager - Information System");
		designationMap.put("7", "General Manager - Information System");
		designationMap.put("8", "CIO, Head-IT, Vice President");
	}
	
	public List<HistoryVo> getHistoryByTx(long txId, long formId)
	{
		LOGGER.info("HisoryService:getHistoryByTx:" +"Entering");
		
//		List<HistoryVo> history = new ArrayList<HistoryVo>();
		List<HistoryVo> entries = historyDao.getAllHistory(txId, formId);
//		for (HistoryVo historyVo: entries) {
//			if (txId == historyVo.getTxId() && formId == historyVo.getFormCode()) {
//				
//				UserVo user = userDao.findById(historyVo.getCreatedBy()).get();
//				historyVo.setEmpName(user.getFirstName() + " " +user.getLastName());
//				
//				historyVo.setDesignation(designationMap.get(String.valueOf(user.getGrade())));
//				history.add(historyVo);
//			}
//		}
	
		return entries;
	}
	
	public HistoryVo save(HistoryVo historyVo)
	{
		LOGGER.info("HistoryService:save:Entering");
		LOGGER.info(historyVo);
		
		HistoryVo persistedHistoryVo = historyDao.save(historyVo);
		return persistedHistoryVo;
	}
	
	public List<HistoryVo> getWfActions(String formCode, String txId) {
		
		//Hard Code
		List<HistoryVo> historyVoList = new ArrayList<HistoryVo>();
		
		HistoryVo historyVo = null;
		for (int i =0; i <= 5; i++) {
			historyVo = new HistoryVo();
			Date date = new Date();
			
			historyVo.setComment("Some Comments " +i);
			historyVo.setEmpName("User Name " +i);
			historyVo.setCreatedOn(date);
			historyVo.setAction("Forrward");
			historyVo.setDesignation("Manager");
			
			historyVoList.add(historyVo);
			
		}
		
		return historyVoList;
		
	}

}
