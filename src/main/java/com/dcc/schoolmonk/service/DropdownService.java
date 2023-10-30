package com.dcc.schoolmonk.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.dao.DropdownMasterDao;
import com.dcc.schoolmonk.vo.DropdownMasterVo;

@Service
public class DropdownService {
	
	private static final Logger LOGGER = Logger.getLogger(DropdownService.class);
	
	@Autowired
    DropdownMasterDao dropdownDao;

	public Iterable<DropdownMasterVo> getAllEntries()
	{
		LOGGER.info("DropdownService:getAllEntries:" +"Entering");
		
		Iterable<DropdownMasterVo> entries = dropdownDao.findAll();
		
		return entries;
	}
	
	/*public String getDropDownValue(long formCode, String fieldName, long listId, long parentId) {
		
		LOGGER.info("DropdownService:: getDropDownValue:: Entering");
		
		String dropdownValue = null;
		try {
			
			dropdownValue = dropdownDao.findListValue(formCode, fieldName, listId);
			
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		 
		LOGGER.info("DropdownService:: getDropDownValue:: Exiting");
		
		return dropdownValue;
	}*/
}
