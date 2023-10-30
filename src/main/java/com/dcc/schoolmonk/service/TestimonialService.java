package com.dcc.schoolmonk.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.amazonaws.regions.AwsProfileRegionProvider;
import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.common.FileStorageProperties;
import com.dcc.schoolmonk.common.GlobalConstants;
import com.dcc.schoolmonk.dao.AttachmentDao;
import com.dcc.schoolmonk.dao.TestimonialDao;
import com.dcc.schoolmonk.exception.FileStorageException;
import com.dcc.schoolmonk.vo.AttachmentVo;
import com.dcc.schoolmonk.vo.FileResponseVo;
import com.dcc.schoolmonk.vo.TestimonialPublishDto;
import com.dcc.schoolmonk.vo.TestimonialSearchInputVo;
import com.dcc.schoolmonk.vo.TestimonialVo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class TestimonialService {

    protected final static Logger LOGGER = Logger.getLogger(TestimonialService.class);

    @Autowired
    TestimonialDao testimonialDao;

    @Autowired
    AttachmentDao attachmentDao;

    public TestimonialVo addTestimonial(TestimonialVo inputVo) {
        try {
            return testimonialDao.save(inputVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResponseEntity<ApiResponse> getTestimonialsByCustomSearch(TestimonialSearchInputVo inputVo) {
        LOGGER.info("SchoolMstService:: getUserByCustomSearch:: Entering getUserByCustomSearch method:: ");

        ApiResponse apiResponse = null;
        List<TestimonialVo> entry = null;
        String whereClause = "";
        String limitStr = "";
        String orderByStr = "";
        if (null != inputVo.getOrderByColumn() && !inputVo.getOrderByColumn().trim().isEmpty()) {
            orderByStr = " order by " + inputVo.getOrderByColumn().trim() + " " + inputVo.getSort();
        } else {
            orderByStr = " order by name ASC";
        }
        LOGGER.info("orderByStr ------------ " + orderByStr);

        if (null == inputVo.getPage() && null == inputVo.getSize()) {

        } else {
            Integer size = (null != inputVo.getSize()) ? inputVo.getSize()
                    : GlobalConstants.DEFAULT_DATA_SIZE_PAGINATION;
            Integer page = (null != inputVo.getPage() && inputVo.getPage() > 1) ? (inputVo.getPage() - 1) * size : 0;
            limitStr = " limit " + size + " offset " + page;
            LOGGER.info("limitStr..." + limitStr);
        }

        if (null != inputVo.getName() && !inputVo.getName().trim().isEmpty()) {
            whereClause += " and name like '%" + inputVo.getName().trim() + "%'";
        }

        if (null != inputVo.getDesignation() && !inputVo.getDesignation().trim().isEmpty()) {
            whereClause += " and designation like '%" + inputVo.getDesignation().trim() + "%'";
        }
        if (null != inputVo.getInstitution() && !inputVo.getInstitution().trim().isEmpty()) {
            whereClause += " and institution like '%" + inputVo.getInstitution().trim() + "%'";
        }

        LOGGER.info("whereClause ------------- " + whereClause);

        try {

            entry = testimonialDao.customSearchTestimonialsByInput(whereClause, orderByStr, limitStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (TestimonialVo testimonial : entry) {
            Long id = testimonial.getId();
            List<AttachmentVo> attachment = attachmentDao.findByTxIdAndFormCode(id, "testimonial_media");
            testimonial.setDocList(attachment);
        }

        int totalNo = testimonialDao.getTotalTestimonialsCountByInput(whereClause, "TESTIMONIALS").intValue();
        apiResponse = new ApiResponse(200, "success", "success", entry, totalNo);
        LOGGER.info("SchoolMstService:: getUserBySearch:: Exiting getUserBySearch method:: ");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    public FileResponseVo storeFile(MultipartFile file, String formCode, long txId, long userId, String docType,
            Long fileId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        UUID uuid = UUID.randomUUID(); // Generates random UUID
        LOGGER.info("random uuid ============= " + uuid);
        fileName = uuid + "-ZQZGTHYRDRT-" + fileName;
        LOGGER.info("file name in storage location ============= " + fileName);
        AttachmentVo attachmentDetailVO = new AttachmentVo();

        FileResponseVo resTemp = new FileResponseVo();
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            LOGGER.info("targetLocation :::: " + targetLocation);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            // Save in attachment table
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/fe/downloadMedia/" + fileName).toUriString();

            if (fileId != null) {
                attachmentDetailVO.setFileId(fileId);
            }
            attachmentDetailVO.setFileName(fileName); // absfile.getName()
            attachmentDetailVO.setFilePath(fileDownloadUri);
            attachmentDetailVO.setFormCode(formCode);
            attachmentDetailVO.setTxId(txId);
            attachmentDetailVO.setCreatedBy(String.valueOf(userId));
            attachmentDetailVO.setCreatedOn(new Date());
            attachmentDetailVO.setDocType(docType);
            attachmentDetailVO = attachmentDao.save(attachmentDetailVO);

            // return fileName;
            // added by kousik -->> for return fileId
            resTemp.setFileName(fileName);
            resTemp.setFileId(attachmentDetailVO.getFileId());
            return resTemp;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    private final Path fileStorageLocation;

    @Autowired
    public TestimonialService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }

    public TestimonialVo getTestimonialById(Long id) {
        try {
            TestimonialVo testimonial = testimonialDao.findById(id).orElse(null);
            List<AttachmentVo> attachment = attachmentDao.findByTxIdAndFormCode(id, "testimonial_media");
            testimonial.setDocList(attachment);
            return testimonial;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResponseEntity<ApiResponse> toggleTestimonialByPublish(TestimonialPublishDto inputVo) {
        ApiResponse apiResponse = null;

        try {
            List<TestimonialVo> testimonials = testimonialDao.findAllById(inputVo.getIds());
            for (TestimonialVo testimonial : testimonials) {

                if (inputVo.getCode() == 1)
                    testimonial.setIsActive(1);
                else
                    testimonial.setIsActive(0);
            }
            testimonialDao.saveAll(testimonials);
            apiResponse = new ApiResponse("success", 200, "Saved Successfully");
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	public ResponseEntity<ApiResponse> getAllTestimonials() {
		ApiResponse apiResponse = null;

    	List<TestimonialVo> testimonialOb = new ArrayList<TestimonialVo>();
        try {
            List<TestimonialVo> testimonials = testimonialDao.findAll();
            for (TestimonialVo testimonial : testimonials) {
                Long id = testimonial.getId();
                List<AttachmentVo> attachment = attachmentDao.findByTxIdAndFormCode(id, "testimonial_media");
                testimonial.setDocList(attachment);
                testimonialOb.add(testimonial);
            }
            apiResponse = new ApiResponse(200, "success", "Fetched Successfully", testimonialOb);
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
}
