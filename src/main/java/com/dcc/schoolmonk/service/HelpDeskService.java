package com.dcc.schoolmonk.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.amazonaws.services.xray.model.Http;
import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.common.FileStorageProperties;
import com.dcc.schoolmonk.common.GlobalConstants;
import com.dcc.schoolmonk.common.UniqueIDGenerator;
import com.dcc.schoolmonk.dao.AttachmentDao;
import com.dcc.schoolmonk.dao.HelpDeskDao;
import com.dcc.schoolmonk.exception.FileStorageException;
import com.dcc.schoolmonk.vo.AttachmentVo;
import com.dcc.schoolmonk.vo.FileResponseVo;
import com.dcc.schoolmonk.vo.HelpDeskDtlVo;
import com.dcc.schoolmonk.vo.IssueSearchInputVo;
import com.dcc.schoolmonk.vo.UserVo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class HelpDeskService {

    private static final Logger LOGGER = Logger.getLogger(HelpDeskService.class);

    @Autowired
    HelpDeskDao helpDeskdao;

    @Autowired
    AttachmentDao attachmentDao;
    @Autowired
    UniqueIDGenerator uniqueIDGenerator;

    public HelpDeskDtlVo raiseTicket(HelpDeskDtlVo vo) {
        LOGGER.info("HelpDesk service:: raiseTicket Method :: entering");
        try {
            String ticketId = uniqueIDGenerator.getUniqueID();
            vo.setTicketId(ticketId);
            return helpDeskdao.save(vo);

        } catch (Exception e) {
            e.printStackTrace();
        }

        LOGGER.info("HelpDesk service:: raiseTicket Method :: exiting");
        return null;
    }

    public HelpDeskDtlVo getIssueDetailsById(Long id) {
        HelpDeskDtlVo result = null;
        List<AttachmentVo> docList = null;
        try {
            result = helpDeskdao.findByIssueId(id);
            docList = attachmentDao.findByTxIdAndFormCode(id, "helpdesk_media");
            result.setDocList(docList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResponseEntity<ApiResponse> getIssuesByCustomSearch(IssueSearchInputVo inputVo) {
        LOGGER.info("SchoolMstService:: getUserByCustomSearch:: Entering getUserByCustomSearch method:: ");

        ApiResponse apiResponse = null;
        List<HelpDeskDtlVo> entry = null;
        String whereClause = "";
        String limitStr = "";
        String orderByStr = "";
        if (null != inputVo.getOrderByColumn() && !inputVo.getOrderByColumn().trim().isEmpty()) {
            orderByStr = " order by " + inputVo.getOrderByColumn().trim() + " " + inputVo.getSort();
        } else {
            orderByStr = " order by created_on DESC";
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

        if (null != inputVo.getIssuerName() && !inputVo.getIssuerName().trim().isEmpty()) {
            whereClause += " and issuer_name like '%" + inputVo.getIssuerName().trim() + "%'";
        }

        if (null != inputVo.getIssuerEmail() && !inputVo.getIssuerEmail().trim().isEmpty()) {
            whereClause += " and issuer_email like '%" + inputVo.getIssuerEmail().trim() + "%'";
        }
        if (null != inputVo.getIssueState() && !inputVo.getIssueState().trim().isEmpty()) {
            whereClause += " and issue_state like '%" + inputVo.getIssueState().trim() + "%'";
        }
        if (null != inputVo.getTicketId() && !inputVo.getTicketId().trim().isEmpty()) {
            whereClause += " and ticket_id like '%" + inputVo.getTicketId().trim() + "%'";
        }

        LOGGER.info("whereClause ------------- " + whereClause);

        entry = helpDeskdao.customSearchIssuesByInput(whereClause, orderByStr, limitStr);

        int totalNo = helpDeskdao.getTotalIssuesCountByInput(whereClause, "HELP_DESK_DTL").intValue();
        apiResponse = new ApiResponse(200, "success", "success", entry, totalNo);
        LOGGER.info("SchoolMstService:: getUserBySearch:: Exiting getUserBySearch method:: ");
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> updateIssueStatus(HelpDeskDtlVo inputVo, UserVo user) {
        LOGGER.info("HelpDesk Service:: updateIssueStatus method:: Entering");
        ApiResponse apiResponse = null;
        ResponseEntity<ApiResponse> responseEntity = null;

        try {
            helpDeskdao.updateIssueStatus(inputVo.getIssueIdList(), inputVo.getIssueState(), user.getUserId());

            apiResponse = new ApiResponse(200, "success", "Updated Successfully", null, 0);
            responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            apiResponse = new ApiResponse(400, "error", null, 0);
            responseEntity = new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
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
    public HelpDeskService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }
}
