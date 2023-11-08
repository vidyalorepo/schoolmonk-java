package com.dcc.schoolmonk.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

//import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
					String schoolName = format.formatCellValue(row.getCell(0)).trim();
					if (schoolName.length() == 0 || schoolName.equalsIgnoreCase("null")) {
						blankSchoolName.add(i);
						break;
					}

					// String principalName = format.formatCellValue(row.getCell(1));
					// String principalQualification = format.formatCellValue(row.getCell(2));
					// String contactPersonFistName = format.formatCellValue(row.getCell(3));
					// String contactPersonLastName = format.formatCellValue(row.getCell(4));
					String contactPhoneNumber = format.formatCellValue(row.getCell(1)).trim();
					// if (contactPhoneNumber.length() == 0 ||
					// contactPhoneNumber.equalsIgnoreCase("null")) {
					// blankContactPhoneNumber.add(i);
					// continue;
					// }
					String contactEmail = format.formatCellValue(row.getCell(2)).trim();
					// String establishmentYear = format.formatCellValue(row.getCell(7));
					String schoolType = format.formatCellValue(row.getCell(3)).trim();
					String schoolBoard = format.formatCellValue(row.getCell(4)).trim();
					String schoolMedium = format.formatCellValue(row.getCell(5)).trim();
					// String principalMessage = format.formatCellValue(row.getCell(11));
					String aboutSchool = format.formatCellValue(row.getCell(6)).trim();
					String featuredSchool = format.formatCellValue(row.getCell(7)).trim();
					// String customAdmissionForm = format.formatCellValue(row.getCell(14));
					String addressLineOne = format.formatCellValue(row.getCell(8)).trim();
					// LOGGER.info("addressLineOne ----------------------------" + addressLineOne);
					// if (addressLineOne.length() == 0 || addressLineOne.equalsIgnoreCase("null"))
					// {
					// blankAddressLineOne.add(i);
					// continue;
					// }

					String addressLineTwo = format.formatCellValue(row.getCell(9)).trim();
					String landMark = format.formatCellValue(row.getCell(10)).trim();
					String city = format.formatCellValue(row.getCell(11)).trim();
					// LOGGER.info("city ----------------------------" + city);
					// if (city.length() == 0 || city.equalsIgnoreCase("null")) {
					// blankCity.add(i);
					// continue;
					// }
					Long state = schoolMstDao.getStateID(format.formatCellValue(row.getCell(12)).trim());
					// if (state == 0 || state == null) {
					// blankState.add(i);
					// continue;
					// }

					Long district = schoolMstDao.getDistrictID(format.formatCellValue(row.getCell(13)).trim());
					// LOGGER.info("District ID -------------------------- : "+district);
					// if (district == 0 || district == null) {
					// blankDistrict.add(i);
					// continue;
					// }

					String postalCode = format.formatCellValue(row.getCell(14)).trim();
					// if (postalCode.length() == 0 || postalCode == null) {
					// blankPostalCode.add(i);
					// continue;
					// }
					String latitude = format.formatCellValue(row.getCell(15)).trim();
					String longitude = format.formatCellValue(row.getCell(16)).trim();

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

    public Object CheckDuplicateSchool(MultipartFile excelInput,HttpServletResponse response) {
		 ApiResponse apiResponse = null;
		 int rowNum = 0;
		 OutputStream out = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(excelInput.getInputStream());
			XSSFSheet worksheet = workbook.getSheetAt(0);
			DataFormatter format = new DataFormatter();
			StringBuilder msg = new StringBuilder();

			// for export excel unique school values
			String FILE_NAME = "UNIQUE_SCHOOL.xlsx";
            Workbook workbook1 = new XSSFWorkbook();
			CellStyle cellStyle = workbook1.createCellStyle();
			Sheet sheet = workbook1.createSheet("SCHOOL_DETAILS");

			//set border to table
            cellStyle.setBorderTop(BorderStyle.NONE);
            cellStyle.setBorderRight(BorderStyle.NONE);
            cellStyle.setBorderBottom(BorderStyle.NONE);
            cellStyle.setBorderLeft(BorderStyle.NONE);

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + FILE_NAME);
	
			Row rowSchool=sheet.createRow(rowNum);
			Cell cell = rowSchool.createCell(0);
			cell.setCellValue("School_Name");
			cell.setCellStyle(cellStyle);

			Cell cell1 = rowSchool.createCell(1);
			cell1.setCellValue("Phone");
			cell1.setCellStyle(cellStyle);

			Cell cell2 = rowSchool.createCell(2);
			cell2.setCellValue("Contact_Email");
			cell2.setCellStyle(cellStyle);

			Cell cell3 = rowSchool.createCell(3);
			cell3.setCellValue("School_Type");
			cell3.setCellStyle(cellStyle);

			
			Cell cell4 = rowSchool.createCell(4);
			cell4.setCellValue("School_Board");
			cell4.setCellStyle(cellStyle);

			Cell cell5 = rowSchool.createCell(5);
			cell5.setCellValue("School_Medium");
			cell5.setCellStyle(cellStyle);

			Cell cell6 = rowSchool.createCell(6);
			cell6.setCellValue("About_School");
			cell6.setCellStyle(cellStyle);

			Cell cell7 = rowSchool.createCell(7);
			cell7.setCellValue("Featured_School");
			cell7.setCellStyle(cellStyle);

			Cell cell8 = rowSchool.createCell(8);
			cell8.setCellValue("Address_Line_One");
			cell8.setCellStyle(cellStyle);

			Cell cell9 = rowSchool.createCell(9);
			cell9.setCellValue("Address_Line_Two");
			cell9.setCellStyle(cellStyle);

			
			Cell cell10 = rowSchool.createCell(10);
			cell10.setCellValue("Land_Mark");
			cell10.setCellStyle(cellStyle);

			Cell cell11 = rowSchool.createCell(11);
			cell11.setCellValue("City");
			cell11.setCellStyle(cellStyle);

			Cell cell12 = rowSchool.createCell(12);
			cell12.setCellValue("State");
			cell12.setCellStyle(cellStyle);

			Cell cell13 = rowSchool.createCell(13);
			cell13.setCellValue("District");
			cell13.setCellStyle(cellStyle);

			Cell cell14 = rowSchool.createCell(14);
			cell14.setCellValue("Postal_Code");
			cell14.setCellStyle(cellStyle);

			Cell cell15 = rowSchool.createCell(15);
			cell15.setCellValue("Latitude");
			cell15.setCellStyle(cellStyle);

			Cell cell16 = rowSchool.createCell(16);
			cell16.setCellValue("Longitude");
			cell16.setCellStyle(cellStyle);

			sheet.autoSizeColumn(rowNum); 
		
			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                SchoolMstVo schoolmst=null;
				XSSFRow row = worksheet.getRow(i);
				String schoolName = format.formatCellValue(row.getCell(0)).trim();
				String contactPhoneNumber = format.formatCellValue(row.getCell(1)).trim();
				String contactEmail = format.formatCellValue(row.getCell(2)).trim();
				String schoolType = format.formatCellValue(row.getCell(3)).trim();
				String schoolBoard = format.formatCellValue(row.getCell(4)).trim();
				String schoolMedium = format.formatCellValue(row.getCell(5)).trim();
				String aboutSchool = format.formatCellValue(row.getCell(6)).trim();
				String featuredSchool = format.formatCellValue(row.getCell(7)).trim();
				String addressLineOne = format.formatCellValue(row.getCell(8)).trim();
				String addressLineTwo = format.formatCellValue(row.getCell(9)).trim();
				String landMark = format.formatCellValue(row.getCell(10)).trim();
				String city = format.formatCellValue(row.getCell(11)).trim();
				String state = format.formatCellValue(row.getCell(12)).trim();
			    String district = format.formatCellValue(row.getCell(13)).trim();
				String postalCode = format.formatCellValue(row.getCell(14)).trim();
				String latitude = format.formatCellValue(row.getCell(15)).trim();
				String longitude = format.formatCellValue(row.getCell(16)).trim();

				schoolmst=schoolMstDao.findbySchoolName(schoolName,postalCode);
				if (schoolmst ==null) {
			        Row copyRow=sheet.createRow(++rowNum);

					Cell cell0 = copyRow.createCell(0);
		            cell0.setCellValue(schoolName);
		     	    cell0.setCellStyle(cellStyle);

					cell1 = copyRow.createCell(1);
		            cell1.setCellValue(contactPhoneNumber );
		     	    cell1.setCellStyle(cellStyle);

					cell2 = copyRow.createCell(2);
		            cell2.setCellValue(contactEmail);
		     	    cell2.setCellStyle(cellStyle);

					cell3 = copyRow.createCell(3);
		            cell3.setCellValue(schoolType);
		     	    cell3.setCellStyle(cellStyle);

					cell4 = copyRow.createCell(4);
		            cell4.setCellValue(schoolBoard);
		     	    cell4.setCellStyle(cellStyle);

					cell5 = copyRow.createCell(5);
		            cell5.setCellValue(schoolMedium);
		     	    cell5.setCellStyle(cellStyle);

					cell6 = copyRow.createCell(6);
		            cell6.setCellValue(aboutSchool);
		     	    cell6.setCellStyle(cellStyle);

					cell7 = copyRow.createCell(7);
		            cell7.setCellValue(featuredSchool);
		     	    cell7.setCellStyle(cellStyle);

					cell8 = copyRow.createCell(8);
		            cell8.setCellValue(addressLineOne);
		     	    cell8.setCellStyle(cellStyle);

					cell9 = copyRow.createCell(9);
		            cell9.setCellValue(addressLineTwo);
		     	    cell9.setCellStyle(cellStyle);

					cell10 = copyRow.createCell(10);
		            cell10.setCellValue(landMark);
		     	    cell10.setCellStyle(cellStyle);

					cell11 = copyRow.createCell(11);
		            cell11.setCellValue(city);
		     	    cell11.setCellStyle(cellStyle);

					cell12 = copyRow.createCell(12);
		            cell12.setCellValue(state);
		     	    cell12.setCellStyle(cellStyle);

					cell13 = copyRow.createCell(13);
		            cell13.setCellValue(district);
		     	    cell13.setCellStyle(cellStyle);

					cell14 = copyRow.createCell(14);
		            cell14.setCellValue(postalCode);
		     	    cell14.setCellStyle(cellStyle);

					cell15 = copyRow.createCell(15);
		            cell15.setCellValue(latitude);
		     	    cell15.setCellStyle(cellStyle);

					cell16 = copyRow.createCell(16);
		            cell16.setCellValue(longitude);
		     	    cell16.setCellStyle(cellStyle);		
					
					sheet.autoSizeColumn(rowNum); 
				}
			}
		try {
			out = response.getOutputStream();
			workbook1.write(out);
			workbook1.close();

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Success";
    }
}
