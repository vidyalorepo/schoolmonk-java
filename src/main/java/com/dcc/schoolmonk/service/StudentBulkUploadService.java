package com.dcc.schoolmonk.service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dao.SchoolMstDao;
import com.dcc.schoolmonk.dao.SchoolStudentMappingDao;
import com.dcc.schoolmonk.dao.StudentDao;
import com.dcc.schoolmonk.dao.UserDao;
import com.dcc.schoolmonk.vo.ResetPasswordVo;
import com.dcc.schoolmonk.vo.SchoolStudentMappingVo;
import com.dcc.schoolmonk.vo.SignUpDto;
import com.dcc.schoolmonk.vo.StudentMstVo;
import com.dcc.schoolmonk.vo.UserVo;

@Service
public class StudentBulkUploadService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    @Autowired
    UserDao userDao;

    @Autowired
    StudentDao studentDao;

    @Autowired
    SchoolStudentMappingDao schoolStudentMappingDao;

    @Autowired
    SchoolMstDao schoolMstDao;

    @Autowired
    UserService userService;

    public ResponseEntity<ApiResponse> uploadStudents(MultipartFile excelInput, UserVo userVo) {
        ResponseEntity<ApiResponse> responseEntity = null;
        ApiResponse apiResponse = null;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(excelInput.getInputStream());
            XSSFSheet worksheet = workbook.getSheet("students");

            int rows = worksheet.getLastRowNum();
            XSSFRow headerRow = worksheet.getRow(1);

            for (int i = 2; i <= rows; i++) {
                XSSFRow row = worksheet.getRow(i);

                StudentMstVo studentDetails = extractStudentDetails(headerRow, row, 0, 6);
                UserVo parentDetails = extractParentDetails(headerRow, row, 8, 12);
                SchoolStudentMappingVo mappingVo = extractClassDetails(headerRow, row, 13, 24);
                UserVo parentUser = getParentUser(parentDetails);
                if (parentUser == null) {
                    SignUpDto signUpDto = new SignUpDto();
                    UserVo newUser = new UserVo();
                    newUser.setFirstName(parentDetails.getFirstName());
                    newUser.setLastName(parentDetails.getLastName());
                    newUser.setEmail(parentDetails.getEmail());
                    newUser.setCountryCode(parentDetails.getCountryCode());
                    newUser.setPhone(parentDetails.getPhone());
                    newUser.setIsActive(false);
                    userDao.save(newUser);

                    signUpDto.setFirstName(parentDetails.getFirstName());
                    signUpDto.setLastName(parentDetails.getLastName());
                    signUpDto.setEmail(parentDetails.getEmail());
                    signUpDto.setCountryCode(parentDetails.getCountryCode());
                    signUpDto.setPhone(parentDetails.getPhone());
                    signUpDto.setPassword("London@01");
                    ApiResponse signUpResponse = userService.signUp(signUpDto);
                    parentUser = (UserVo) signUpResponse.getResult();
                    LOGGER.info("New Parent User created.");

                    ResetPasswordVo resetPasswordVo = new ResetPasswordVo();
                    resetPasswordVo.setEmail(parentUser.getEmail());
                    userService.sendResetPasswordLink(resetPasswordVo);
                    LOGGER.info("Reset Link Sent.");
                }

                studentDetails.setParentUser(parentUser);
                studentDetails.setCreatedBy(userVo.getUserId());
                StudentMstVo savedStudent = studentDao.save(studentDetails);
                mappingVo.setCreatedBy(userVo.getUserId());
                mappingVo.setUpdatedBy(userVo.getUserId());
                mappingVo.setSchoolId(userVo.getSchoolId());
                mappingVo.setStudentId(savedStudent.getId());
                mappingVo.setStudentMstVo(savedStudent);
                mappingVo.setSchoolMstVo(schoolMstDao.findByContactUserUserId(userVo.getUserId()));
                UUID uuid = UUID.randomUUID();
                mappingVo.setRegistrationToken(uuid.toString());
                SchoolStudentMappingVo savedMapping = schoolStudentMappingDao.save(mappingVo);

            }

            LOGGER.info("Bulk Student upload successful");
            apiResponse = new ApiResponse(200, "success", "successfull excel upload");
            responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace();
            apiResponse = new ApiResponse(500, "Error", "Internal Server Error");
            responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    private StudentMstVo extractStudentDetails(XSSFRow headerRow, XSSFRow dataRow, int firstColumn, int lastColumn) {

        StudentMstVo student = new StudentMstVo();
        Method[] voMethods = student.getClass().getMethods();
        DataFormatter formatter = new DataFormatter();
        for (int c = 0; c <=lastColumn; c++) {
            String headerName = headerRow.getCell(c).getStringCellValue().replace(" ", "").toLowerCase();
            String dataValue = formatter.formatCellValue(dataRow.getCell(c));

            for (Method method : voMethods) {
                String methodName = method.getName().toLowerCase();
                if (methodName.contains("set") && !(methodName.contains("getClass"))
                        && methodName.contains(headerName)) {

                    try {
                        method.invoke(student, dataValue);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;

                    }
                }
            }
        }

        return student;
    }

    private UserVo extractParentDetails(XSSFRow headerRow, XSSFRow dataRow, int firstColumn,
            int lastColumn) {
        UserVo parent = new UserVo();
        Method[] voMethods = parent.getClass().getMethods();
        DataFormatter formatter = new DataFormatter();
        for (int c = firstColumn; c <= lastColumn; c++) {
            String headerName = headerRow.getCell(c).getStringCellValue().replace(" ", "").toLowerCase();
            String dataValue = formatter.formatCellValue(dataRow.getCell(c));

            for (Method method : voMethods) {
                String methodName = method.getName().toLowerCase();
                if (methodName.contains("set") && !(methodName.contains("getClass"))
                        && methodName.replace("set", "").equals(headerName)) {

                    try {
                        method.invoke(parent, dataValue);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;

                    }
                }
            }
        }

        return parent;
    }

    private SchoolStudentMappingVo extractClassDetails(XSSFRow headerRow, XSSFRow dataRow, int firstColumn,
            int lastColumn) {
        SchoolStudentMappingVo mappingVo = new SchoolStudentMappingVo();
        Method[] voMethods = mappingVo.getClass().getMethods();
        DataFormatter formatter = new DataFormatter();
        for (int c = firstColumn; c <= lastColumn; c++) {
            String headerName = headerRow.getCell(c).getStringCellValue().replace(" ", "").toLowerCase();
            String dataValue = formatter.formatCellValue(dataRow.getCell(c));

            for (Method method : voMethods) {
                String methodName = method.getName().toLowerCase();
                if (methodName.contains("set") && !(methodName.contains("getClass"))
                        && methodName.contains(headerName)) {

                    try {
                        method.invoke(mappingVo, dataValue);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;

                    }
                }
            }
        }

        return mappingVo;
    }

    private UserVo getParentUser(UserVo guardian) {
        if (guardian == null)
            return null;

        String phoneNo = guardian.getPhone();
        String email = guardian.getEmail();
        List<UserVo> users = userDao.findByEmailOrPhone(email, phoneNo);

        if (users.size() == 0)
            return null;

        if (users.size() > 1)
            return null;

        return users.get(0);
    }

}
