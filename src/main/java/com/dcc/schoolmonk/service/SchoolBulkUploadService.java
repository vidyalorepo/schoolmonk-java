package com.dcc.schoolmonk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.common.CommmonFunction;
import com.dcc.schoolmonk.common.UniqueIDGenerator;
import com.dcc.schoolmonk.dao.SchoolMstBulkDao;
import com.dcc.schoolmonk.dao.SchoolMstDao;
import com.dcc.schoolmonk.vo.SchoolMstBulkVo;
import com.dcc.schoolmonk.vo.SchoolMstVo;
import com.dcc.schoolmonk.vo.UserVo;

@Service

public class SchoolBulkUploadService {

	private static final Logger LOGGER = Logger.getLogger(SchoolBulkUploadService.class);

	@Autowired
	SchoolMstBulkDao schoolMstBulkDao;

	@Autowired
	UniqueIDGenerator uniqueIDGenerator;

	@Autowired
	SchoolMstDao schoolMstDao;

	public ResponseEntity<ApiResponse> saveSchoolBulkUpload(MultipartFile excelInput) {

		LOGGER.info("SchoolBulkUploadService:: saveSchoolBulkUpload() called......");

		ApiResponse apiResponse = null;
		ResponseEntity<ApiResponse> responseEntity = null;
		int numberOfRecord = 0;

		String line = "";
		String splitBy = ",";

		StringBuilder msg = new StringBuilder();
		List<Integer> blankListEeCode = new ArrayList<>();
		List<Integer> blankListDate = new ArrayList<>();
		List<Integer> blankListUpdate = new ArrayList<>();
		List<Integer> innputDateCheck = new ArrayList<>();
		// int i = 1;
		try {
			// parsing a CSV file into BufferedReader class constructor
			// InputStream is = readExcelDataFile.getInputStream();
			// BufferedReader br = new BufferedReader(new InputStreamReader(is));
			// br.readLine();
			// while ((line = br.readLine()) != null) // returns a Boolean value
			// {
			// i++;
			// String[] schoolDetails = line.split(splitBy, -1); // use comma as separator
			// LOGGER.info("SchoolBulkUploadService:: line :" + line);
			// LOGGER.info("SchoolBulkUploadService:: schoolDetails :" + schoolDetails);
			// numberOfRecord += 1;
			// }

			XSSFWorkbook workbook = new XSSFWorkbook(excelInput.getInputStream());
			XSSFSheet worksheet = workbook.getSheetAt(0);
			DataFormatter format = new DataFormatter();
			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				XSSFRow row = worksheet.getRow(i);
				String helperCategory = row.getCell(1).toString();
				LOGGER.info("SchoolBulkUploadService:: helperCategory :" + helperCategory);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("SchoolBulkUploadService:: Exception :" + e.getMessage());
		}
		return null;

	}

	public ResponseEntity<ApiResponse> uploadSchools(MultipartFile excelInput, UserVo userVo) {
		ResponseEntity<ApiResponse> responseEntity = null;
		ApiResponse apiResponse = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(excelInput.getInputStream());
			XSSFSheet worksheet = workbook.getSheetAt(0);
			DataFormatter format = new DataFormatter();
			StringBuilder msg = new StringBuilder();

			List<SchoolMstVo> schoolBulk = new ArrayList<SchoolMstVo>();
			// to get the empty list
			List<Integer> blankSchoolName = new ArrayList<>();
			List<Integer> blankContactPhoneNumber = new ArrayList<>();
			List<Integer> blankContactEmail = new ArrayList<>();
			List<Integer> blankAddressLineOne = new ArrayList<>();
			List<Integer> blankCity = new ArrayList<>();
			List<Integer> blankState = new ArrayList<>();
			List<Integer> blankDistrict = new ArrayList<>();
			List<Integer> blankPostalCode = new ArrayList<>();
			List<Integer> dataNotSaveList = new ArrayList<>();

			/*
			 * List<Integer> blankPrincipalName = new ArrayList<>();
			 * List<Integer> blankPrincipalQualification = new ArrayList<>();
			 * List<Integer> blankContactPersonFistName = new ArrayList<>();
			 * List<Integer> blankContactPersonLastName = new ArrayList<>();
			 * List<Integer> blankEstablishmentYear = new ArrayList<>();
			 * List<Integer> blankSchoolType= new ArrayList<>();
			 * List<Integer> blankSchoolBoard = new ArrayList<>();
			 * List<Integer> blankSchoolMedium = new ArrayList<>();
			 * List<Integer> blankPrincipalMessage = new ArrayList<>();
			 * List<Integer> blankAboutSchool = new ArrayList<>();
			 * List<Integer> blankFeaturedSchool = new ArrayList<>();
			 * List<Integer> blankCustomAdmissionForm = new ArrayList<>();
			 * List<Integer> blankAddressLineTwo = new ArrayList<>();
			 * List<Integer> blankLandMark = new ArrayList<>();
			 * List<String> dataDuplicateList = new ArrayList<>();
			 * List<String> dataDuplicateLicenceList = new ArrayList<>();
			 */

			int count = 0;
			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				SchoolMstVo vo = null;
				XSSFRow row = worksheet.getRow(i);

				try {
					String schoolName = format.formatCellValue(row.getCell(0));
					if (schoolName.length() == 0 || schoolName.equalsIgnoreCase("null")) {
						blankSchoolName.add(i);
						break;
					}

					// String principalName = format.formatCellValue(row.getCell(1));
					// String principalQualification = format.formatCellValue(row.getCell(2));
					// String contactPersonFistName = format.formatCellValue(row.getCell(3));
					// String contactPersonLastName = format.formatCellValue(row.getCell(4));
					String contactPhoneNumber = format.formatCellValue(row.getCell(1));
					// if (contactPhoneNumber.length() == 0 ||
					// contactPhoneNumber.equalsIgnoreCase("null")) {
					// blankContactPhoneNumber.add(i);
					// continue;
					// }
					String contactEmail = format.formatCellValue(row.getCell(2));
					// String establishmentYear = format.formatCellValue(row.getCell(7));
					String schoolType = format.formatCellValue(row.getCell(3));
					String schoolBoard = format.formatCellValue(row.getCell(4));
					String schoolMedium = format.formatCellValue(row.getCell(5));
					// String principalMessage = format.formatCellValue(row.getCell(11));
					String aboutSchool = format.formatCellValue(row.getCell(6));
					String featuredSchool = format.formatCellValue(row.getCell(7));
					// String customAdmissionForm = format.formatCellValue(row.getCell(14));
					String addressLineOne = format.formatCellValue(row.getCell(8));
					// LOGGER.info("addressLineOne ----------------------------" + addressLineOne);
					// if (addressLineOne.length() == 0 || addressLineOne.equalsIgnoreCase("null"))
					// {
					// blankAddressLineOne.add(i);
					// continue;
					// }

					String addressLineTwo = format.formatCellValue(row.getCell(9));
					String landMark = format.formatCellValue(row.getCell(10));
					String city = format.formatCellValue(row.getCell(11));
					// LOGGER.info("city ----------------------------" + city);
					// if (city.length() == 0 || city.equalsIgnoreCase("null")) {
					// blankCity.add(i);
					// continue;
					// }
					Long state = schoolMstDao.getStateID(format.formatCellValue(row.getCell(12)));
					// if (state == 0 || state == null) {
					// blankState.add(i);
					// continue;
					// }

					Long district = schoolMstDao.getDistrictID(format.formatCellValue(row.getCell(13)));
					// LOGGER.info("District ID -------------------------- : "+district);
					// if (district == 0 || district == null) {
					// blankDistrict.add(i);
					// continue;
					// }

					String postalCode = format.formatCellValue(row.getCell(14));
					// if (postalCode.length() == 0 || postalCode == null) {
					// blankPostalCode.add(i);
					// continue;
					// }
					String latitude = format.formatCellValue(row.getCell(15));
					String longitude = format.formatCellValue(row.getCell(16));

					vo = new SchoolMstVo();
					vo.setSchoolName(schoolName);
					// vo.setSchoolPrincipalName(principalName);

					// vo.setSchoolPrincipalQualification(principalQualification);
					// vo.setContactPersonFirstName(contactPersonFistName);
					// vo.setContactPersonLastName(contactPersonLastName);
					vo.setCreatedOn(new Date());
					vo.setContactPhone((contactPhoneNumber.isEmpty() ? null : ((contactPhoneNumber))));
					if (contactEmail.length() != 0) {
						vo.setContactEmail(contactEmail);
					}
					// vo.setEstablishmentYear(establishmentYear);
					vo.setSchoolType(schoolType);
					vo.setSchoolBoard(schoolBoard);
					vo.setSchoolMedium(schoolMedium);
					// vo.setMessageFromPrincipal(principalName);
					vo.setAboutSchool(aboutSchool);
					vo.setFeaturedSchool(featuredSchool);
					// vo.setCustomAdmissionForm(customAdmissionForm);
					vo.setSchoolAddress(addressLineOne);
					vo.setAddressLineTwo(addressLineTwo);
					vo.setLandMark(landMark);
					vo.setCity(city);
					vo.setState((state));
					vo.setDistrict(district);
					vo.setPostalCode((postalCode.isEmpty() ? null : (Long.parseLong(postalCode))));
					vo.setLatitude(latitude);
					vo.setLongitude(longitude);
					String uniqueID = uniqueIDGenerator.generateSchoolID();
					LOGGER.info("uniqueID---------------------------------->" + uniqueID);
					vo.setSchoolId(uniqueID);
					vo.setCreatedBy(userVo.getUserId());
					vo.setCreationMode("Bulk");
					// also add slug
					vo.setSchoolNameSlug(CommmonFunction.slugNameGeneration(schoolName));

					schoolBulk.add(vo);
					LOGGER.info(vo);
				} catch (Exception e) {
					LOGGER.info("Exception 2 ---------------------------------------------");
					LOGGER.error(e.getMessage());
				}
			}
			msg.append((blankSchoolName.size() > 0) ? "School Name missing in rows :" + blankSchoolName + "." : " ");
			msg.append((blankContactPhoneNumber.size() > 0)
					? "Contact Phone No. missing in rows :" + blankContactPhoneNumber + "."
					: " ");
			msg.append(
					(blankContactEmail.size() > 0) ? "Contact Email missing in rows :" + blankContactEmail + "." : " ");
			if (blankSchoolName.size() > 0 || blankContactPhoneNumber.size() > 0 || blankContactEmail.size() > 0) {
				String msgMod = msg.toString();
				String finalMsg = msgMod;
				apiResponse = new ApiResponse(400, "error", "Total " + count + " schools saved successfully." + finalMsg, null);
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
			} else {
				for (int index = 0; index < schoolBulk.size(); index++) {
					try {
						SchoolMstVo resultVo = schoolMstDao.save(schoolBulk.get(index));
						LOGGER.info("resultVo ----------------:" + resultVo);
						if (resultVo != null) {
							count++;
							LOGGER.info("COUNT --------------------------" + count);
							LOGGER.info("School Data saved in DB -----------");
						} else {
							LOGGER.info("School Data NOT saved in DB -----------" + schoolBulk.get(index));
							dataNotSaveList.add(index);
						}
					} catch (DataIntegrityViolationException exp) {
						LOGGER.error("Exception 4 ---------------------------------------------");
						LOGGER.error(exp.getMessage());
					} catch (Exception e) {
						LOGGER.error("Exception 1 ---------------------------------------------");
						LOGGER.error(e.getMessage());
						dataNotSaveList.add(index);
					}
				}
				String msgMod = msg.toString();
				String finalMsg = msgMod;
				apiResponse = new ApiResponse(200, "success", "Total " + count + " schools saved successfully." + finalMsg,
						null);
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
			}

		} catch (Exception exp) {
			LOGGER.info("Exception 3 ---------------------------------------------");
			apiResponse = new ApiResponse(401, "ERROR", "Not a valid excel format.", "");
			LOGGER.error(exp.getMessage());
			return responseEntity;
		}
	}

    public ResponseEntity<ApiResponse> CheckDuplicateSchool(MultipartFile excelInput) {
	     ResponseEntity<ApiResponse> responseEntity = null;
		 ApiResponse apiResponse = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(excelInput.getInputStream());
			XSSFSheet worksheet = workbook.getSheetAt(0);
			DataFormatter format = new DataFormatter();
			StringBuilder msg = new StringBuilder();

			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
               
				XSSFRow row = worksheet.getRow(i);
				String schoolName = format.formatCellValue(row.getCell(0));
				String postalCode = format.formatCellValue(row.getCell(14));
				SchoolMstVo schoolmst=schoolMstDao.findbySchoolName(schoolName,postalCode);

				if (schoolmst !=null) {
                  
					msg.append("School Name: "+schoolName+"Postal Code: "+postalCode+" Row: "+i+" ,");
				}

				schoolmst=null;

               	apiResponse = new ApiResponse(200, "success", msg.toString(),null);
				responseEntity= new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);			 	
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(500, "error", "Processing Failed.", null);
			responseEntity= new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        return responseEntity;
    }
}
