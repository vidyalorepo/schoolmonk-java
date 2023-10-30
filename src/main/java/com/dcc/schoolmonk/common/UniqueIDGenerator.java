package com.dcc.schoolmonk.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.dao.SchoolMstDao;
import com.dcc.schoolmonk.dao.SchoolStudentMappingDao;

@Service
public class UniqueIDGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(UniqueIDGenerator.class);

	private static final String ZEROS = "000000000000";
	
	@Autowired
	SchoolStudentMappingDao schoolStudentMappingDao;
	
	@Autowired
	SchoolMstDao schoolMstDao;
	
	public String getUniqueID() {
		return generate();
	}

	private String generate() {
		StringBuilder strRetVal = new StringBuilder();
		String strTemp;
		try {
			// IPAddress segment
			InetAddress addr = InetAddress.getLocalHost();
			byte[] ipaddr = addr.getAddress();
			for (byte anIpaddr : ipaddr) {
				Byte b = new Byte(anIpaddr);
				strTemp = Integer.toHexString(b.intValue() & 0x000000ff);
				strRetVal.append(ZEROS.substring(0, 2 - strTemp.length()));
				strRetVal.append(strTemp);
			}
			// CurrentTimeMillis() segment
			strTemp = Long.toHexString(System.currentTimeMillis());
			strRetVal.append(ZEROS.substring(0, 12 - strTemp.length()));
			// random segment
			SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
			strTemp = Integer.toHexString(prng.nextInt());
			while (strTemp.length() < 8) {
				strTemp = '0' + strTemp;
			}
			// IdentityHash() segment
			strTemp = Long.toHexString(System.identityHashCode(this));
			strRetVal.append(ZEROS.substring(0, 8 - strTemp.length()));
			strRetVal.append(strTemp);
		} catch (NoSuchAlgorithmException nsaex) {
			throw new RuntimeException("Algorithm 'SHA1PRNG' is unavailiable.", nsaex);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return strRetVal.toString().toUpperCase();
	}
	/*
	 * public static void main(String args[]){ UniqueIDGenerator u = new
	 * UniqueIDGenerator(); System.out.println(u.getUniqueID());}
	 */

	public static char[] OTP(int len) { 
        // Using numeric values 
        String numbers = "0123456789"; 
  
        // Using random method 
        Random rndm_method = new Random(); 
  
        char[] otp = new char[len]; 
  
        for (int i = 0; i < len; i++) { 
            // Use of charAt() method : to get character value 
            // Use of nextInt() as it is scanning the value as int 
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length())); 
        } 
        return otp; 
    } 

	public String getAdmissionId(Long studentId, Long schoolId) {
		LOGGER.info("UniqueIdService:: getAdmissionId method");

		long j = 0;
		String prefix = "";
		String uniqueId = "";

		try {
			Calendar calendar = Calendar.getInstance();
			FiscalDate fiscalDate = new FiscalDate(calendar);
			int financialYear = fiscalDate.getFiscalYear();
			String finalFyear = Integer.toString(financialYear) + "-" + Integer.toString(financialYear + 1);

			String[] year = finalFyear.split("-");
			String year1 = year[0];
			String year2 = year[1];

			String last2yr1 = year1.substring(year1.length() - 2);
			String last2yr2 = year2.substring(year2.length() - 2);

			String substractYear = last2yr1 + "-" + last2yr2;

			long countId = schoolStudentMappingDao.noOfData();

			j = countId;
			prefix = "ADM/"+ substractYear + "/";

			int k = String.valueOf(j).length();

			if (k == 1)
				uniqueId = prefix + "000" + j;

			if (k == 2)
				uniqueId = prefix + "00" + j;

			if (k == 3)
				uniqueId = prefix + "0" + j;

			if (k == 4)
				uniqueId = prefix + j;
		} catch (Exception e) {
			e.printStackTrace();
		}

		LOGGER.info("UniqueIdService:: Uniqui Id save:: " + "Exiting getUniqueId method");

		return uniqueId;
	}	
	
	// Added by Akash	
	public String generateSchoolID() {
		
		String schoolID = "";
		String pattern = "yyyyMMdd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(new Date());
		Long totalSchoolCount = (schoolMstDao.getTotalSchoolCount())+1;
		int countLength = String.valueOf(totalSchoolCount).length();
		LOGGER.info("countLength------------------------------------------>"+countLength);
		if(countLength == 1) 
			schoolID = (date+"00000"+totalSchoolCount);
		else if(countLength == 2)
			schoolID = (date+"0000"+totalSchoolCount);
		else if(countLength == 3)
			schoolID = (date+"000"+totalSchoolCount);
		else if(countLength == 4)
			schoolID = (date+"00"+totalSchoolCount);
		else if(countLength == 5)
			schoolID = (date+"0"+totalSchoolCount);
		else if(countLength == 6)
			schoolID = (date+totalSchoolCount);
			
		return schoolID;
	}
}
