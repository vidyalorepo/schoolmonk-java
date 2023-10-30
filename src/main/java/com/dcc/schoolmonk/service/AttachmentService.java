package com.dcc.schoolmonk.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.dcc.schoolmonk.SchoolmonkApp.ExtPropertyReader;
import com.dcc.schoolmonk.common.FileStorageProperties;
import com.dcc.schoolmonk.config.DBManager;
import com.dcc.schoolmonk.dao.AttachmentDao;
import com.dcc.schoolmonk.dao.SchoolMstDao;
import com.dcc.schoolmonk.exception.FileStorageException;
import com.dcc.schoolmonk.exception.MyFileNotFoundException;
import com.dcc.schoolmonk.vo.AttachmentVo;
import com.dcc.schoolmonk.vo.FileResponseVo;

@Service
public class AttachmentService {

	protected final static Logger LOGGER = Logger.getLogger(AttachmentService.class);

	// private String bucketName = "hpl-file-bucket";
	// private String bucketName = "dcc-file-store";
	// private String bucketName = "hpl-file-store-uat";

	@Autowired
	AttachmentDao attchmentDao;

	@Autowired
	SchoolMstDao schoolMstDao;

	// public AttachmentVo upload(MultipartFile file, String formCode, long txId,
	// long userId) {
	//
	// LOGGER.info("AttachmentService:: AttachmentVo:: attachfile start in
	// ApprovalApps server::");
	//
	// AttachmentVo attachmentDetailVO = new AttachmentVo();
	// try {
	// // FileMessageResource uploadFile = new
	// // FileMessageResource(file.getBytes(),file.getOriginalFilename());
	// String fileName = file.getOriginalFilename();
	// String fileName_1 = file.getOriginalFilename();
	//
	// // Replace all the spaces with "_"
	// fileName = fileName.replaceAll(" ", "_");
	//
	// // File absfile = new File(fileName);
	// // Create the file using the touch method of the FileUtils class.
	// // FileUtils.touch(absfile);
	//
	// // String fileContent = new String(file.getBytes());
	//
	// // Write bytes from the multipart file to disk.
	// // FileUtils.writeByteArrayToFile(absfile, file.getBytes());
	//
	// try {
	// // System.out.println("inputFile from user::" + absfile);
	//
	// DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
	//// String uploadfileName = fileName.split(Pattern.quote("."))[0] + "_" +
	// userId + "_"
	//// + df.format(new Date()) + "." + fileName.split(Pattern.quote("."))[1];
	// String uploadfileName = df.format(new Date()) + "." +
	// fileName.split(Pattern.quote("."))[1];
	// String bucketName = ExtPropertyReader.bucketName;
	//
	// LOGGER.info("AttachmentService:: upload() :: original file size :: " +
	// file.getSize());
	//
	// // creating the file in the server (temporarily)
	//// File tempFile = new File("D://" + uploadfileName);
	// // File tempFile = new File("/tmp/" + uploadfileName);
	//// File tempFile = new File("/opt/spring/upload/" + uploadfileName);
	//// FileOutputStream fos = new FileOutputStream(tempFile);
	//// fos.write(file.getBytes());
	//// fos.close();
	//
	// Path copyLocation = Paths.get(
	// "/opt/spring/upload/" + File.separator +
	// StringUtils.cleanPath(file.getOriginalFilename()));
	//
	// Files.copy(file.getInputStream(), copyLocation,
	// StandardCopyOption.REPLACE_EXISTING);
	//
	// File tempFile = new File("/opt/spring/upload/" +
	// StringUtils.cleanPath(file.getOriginalFilename()));
	//
	// // check file length in console
	// LOGGER.info("AttachmentService:: upload() :: uploaded file size :: " +
	// tempFile.length());
	//
	// BasicAWSCredentials awsCreds = new
	// BasicAWSCredentials(ExtPropertyReader.accessKey,
	// ExtPropertyReader.secretKey);
	//
	// AmazonS3 s3Client =
	// AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
	// .withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	//
	// // s3Client.putObject(new
	// PutObjectRequest(bucketName,uploadfileName,absfile));
	// // s3Client.putObject(bucketName, uploadfileName, fileContent);
	// // s3Client.putObject(bucketName, uploadfileName, tempFile);
	//
	// String resourceURL = "";// s3Client.getUrl(bucketName,
	// uploadfileName).toString();
	//
	// attachmentDetailVO.setFileName(fileName); // absfile.getName()
	// attachmentDetailVO.setFilePath(resourceURL);
	// attachmentDetailVO.setFormCode(formCode);
	// attachmentDetailVO.setTxId(txId);
	// attachmentDetailVO.setCreatedBy(String.valueOf(userId));
	// attachmentDetailVO.setCreatedOn(new Date());
	// attachmentDetailVO = attchmentDao.save(attachmentDetailVO);
	//
	// // removing the file created in the server
	// // tempFile.delete();
	//
	// } catch (AmazonServiceException e) {
	// // The call was transmitted successfully, but Amazon S3 couldn't
	// // process
	// // it, so it returned an error response.
	// e.printStackTrace();
	// } catch (SdkClientException e) {
	// // Amazon S3 couldn't be contacted for a response, or the client
	// // couldn't parse the response from Amazon S3.
	// e.printStackTrace();
	// }
	//
	// LOGGER.info("File uploaded successfully========");
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// LOGGER.error(" :: Technical problem in attaching file");
	// // return null;
	// }
	// return attachmentDetailVO;
	// }
	public AttachmentService() {

	}

	public AttachmentVo upload(MultipartFile file, String formCode, long txId, long userId) {

		LOGGER.info("AttachmentService:: AttachmentVo:: attachfile start in ApprovalApps server::");

		AttachmentVo attachmentDetailVO = new AttachmentVo();
		try {

			String fileName = file.getOriginalFilename();
			LOGGER.info("AttachmentService:: AttachmentVo:: fileName:: " + fileName);

			// Replace all the spaces with "_"
			fileName = fileName.replaceAll(" ", "_").toLowerCase();

			String fileContent = new String(file.getBytes());

			BasicAWSCredentials awsCreds = new BasicAWSCredentials(ExtPropertyReader.accessKey,
					ExtPropertyReader.secretKey);

			try {

				AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
						.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();

				DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
				String uploadfileName = fileName.split(Pattern.quote("."))[0] + "_" + userId + "_"
						+ df.format(new Date()) + "." + fileName.split(Pattern.quote("."))[1];

				String bucketName = ExtPropertyReader.bucketName;

				byte[] contentBytes = null;
				try {
					InputStream is = new BufferedInputStream(file.getInputStream());

					contentBytes = IOUtils.toByteArray(is);

				} catch (IOException e) {
					System.err.printf("Failed while reading bytes from %s", e.getMessage());
				}

				Long contentLength = Long.valueOf(contentBytes.length);

				ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentLength(contentLength);

				InputStream inputStream = new BufferedInputStream(file.getInputStream());

				try {

					s3Client.putObject(new PutObjectRequest(bucketName, uploadfileName, inputStream, metadata));

				} catch (AmazonServiceException ase) {
					System.out.println("Error Message:    " + ase.getMessage());
					System.out.println("HTTP Status Code: " + ase.getStatusCode());
					System.out.println("AWS Error Code:   " + ase.getErrorCode());
					System.out.println("Error Type:       " + ase.getErrorType());
					System.out.println("Request ID:       " + ase.getRequestId());
				} catch (AmazonClientException ace) {
					System.out.println("Error Message: " + ace.getMessage());
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
				}

				// s3Client.putObject(bucketName, uploadfileName, fileContent);

				String resourceURL = s3Client.getUrl(bucketName, uploadfileName).toString();

				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/fe/downloadMedia/" + resourceURL).toUriString();

				attachmentDetailVO.setFileName(fileName); // absfile.getName()
				attachmentDetailVO.setFilePath(fileDownloadUri);
				attachmentDetailVO.setFormCode(formCode);
				attachmentDetailVO.setTxId(txId);
				attachmentDetailVO.setCreatedBy(String.valueOf(userId));
				attachmentDetailVO.setCreatedOn(new Date());
				attachmentDetailVO = attchmentDao.save(attachmentDetailVO);

			} catch (AmazonServiceException e) {
				// The call was transmitted successfully, but Amazon S3 couldn't
				// process
				// it, so it returned an error response.
				e.printStackTrace();
			} catch (SdkClientException e) {
				// Amazon S3 couldn't be contacted for a response, or the client
				// couldn't parse the response from Amazon S3.
				e.printStackTrace();
			}

			LOGGER.info("File uploaded successfully========");

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(" :: Technical problem in attaching file");
			// return null;
		}
		return attachmentDetailVO;
	}

	public byte[] download(String s3FileName) {
		// First get the fileName from the DB
		LOGGER.info("AttachmentService:: download :: Entering :: S3FileName::" + s3FileName);

		// AttachmentVo attachmentVo = getAttachmentById(fileId);
		// String s3FileName = attachmentVo.getFilePath();

		// LOGGER.info("AttachmentService:: download :: s3FileName::" +s3FileName);

		byte[] bytes = null;
		try {
			String bucketName = ExtPropertyReader.bucketName;
			// for demo purpose fetch s3 object from production bucket,also changed the
			// bucket name in property file
			AmazonS3 s3Client = DBManager.getS3Client();
			S3Object s3object = s3Client.getObject(bucketName, s3FileName);

			S3ObjectInputStream objectInputStream = s3object.getObjectContent();

			bytes = IOUtils.toByteArray(objectInputStream);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		}

		LOGGER.info("File downloaded successfully========");

		return bytes;
	}

	public void delete(Long fileid) throws Exception {

		LOGGER.info("AttachmentService: delete: deletefile Entering");

		try {

			try {
				attchmentDao.deleteById(fileid);
			} catch (Exception exp) {
				throw new Exception("Not such object found in DB");
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("AttachmentService: delete :: Technical problem in deleting file");
		}

		LOGGER.info("AttachmentService: delete: File deleted successfully========");

	}

	// Simple update query might be more useful
	public void updateTxId(long txId, List<Long> attachmentIds) {

		Iterable<Long> attachmentIterable = attachmentIds;
		Iterable<AttachmentVo> attachmentVoList = attchmentDao.findAllById(attachmentIterable);

		for (AttachmentVo attachment : attachmentVoList) {
			attachment.setTxId(txId);
		}

		attchmentDao.saveAll(attachmentVoList);
	}

	public List<AttachmentVo> getAttachementsByTx(String formCode, String txId) {

		LOGGER.info("AttachmentService: getAttachementsByModule: Entering");
		List<AttachmentVo> result = new ArrayList<AttachmentVo>();

		try {
			System.out.println("input from user::" + formCode);

			Iterable<AttachmentVo> attachments = attchmentDao.findAll();
			for (AttachmentVo attachment : attachments) {
				if (formCode.equalsIgnoreCase(attachment.getFormCode())
						& txId.equalsIgnoreCase(String.valueOf(attachment.getTxId()))) {
					result.add(attachment);
				}
			}

		} catch (AmazonServiceException e) {
			LOGGER.error(
					"AttachmentService:: getAttachementsByModule :: Technical problem in preparing Attchment Detail List");
			e.printStackTrace();
		}

		return result;
	}

	public Iterable<AttachmentVo> getAttachementsByUser(@RequestParam("username") String userName,
			HttpServletResponse response, HttpServletRequest request) {

		LOGGER.info("AttachmentService: getAttachementsByModule: Entering");
		Iterable<AttachmentVo> attachments = null;

		try {
			System.out.println("input from user::" + userName);

			// attachments = attchmentDao.findByUser(userName);

		} catch (AmazonServiceException e) {
			LOGGER.error(
					"AttachmentService:: getAttachementsByUser :: Technical problem in preparing Attchment Detail List");
			e.printStackTrace();
		}

		return attachments;
	}

	public AttachmentVo getAttachmentById(Long fileId) {

		AttachmentVo AttachmentVo = null;

		try {
			Optional<AttachmentVo> attachmentVoOps = attchmentDao.findById(fileId);
			AttachmentVo = attachmentVoOps.get();

		} catch (Exception exp) {
			LOGGER.error("AttachmentService:: getAttachmentById ::");
			exp.printStackTrace();
		}

		return AttachmentVo;

	}

	public List<String> findByTxId(long transactionId) {
		LOGGER.error("AttachmentService:: findByTxId :: Entering");

		List<String> fileId = attchmentDao.findFileByTxId(transactionId);

		LOGGER.error("AttachmentService:: findByTxId :: Exiting");

		return fileId;
	}

	public void upload1(MultipartFile file) {

		LOGGER.info("AttachmentService:: AttachmentVo:: attachfile start in ApprovalApps server::");

		try {

			String fileName = file.getOriginalFilename();
			String fileName_1 = file.getOriginalFilename();

			// String fileContent = new String(file.getBytes());

			try {

				DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
				// String uploadfileName = fileName.split(Pattern.quote("."))[0] + "_" +
				// df.format(new Date()) + "."
				// + fileName.split(Pattern.quote("."))[1];
				// String bucketName = ExtPropertyReader.bucketName;

				LOGGER.info("AttachmentService:: upload() :: original file size :: " + file.getSize());

				// creating the file in the server (temporarily)
				File tempFile = new File("D://" + fileName);
				// File tempFile = new File("/tmp/" + uploadfileName);
				// File tempFile = new File("/opt/spring/upload/" + fileName);

				FileOutputStream fos = new FileOutputStream(tempFile);
				fos.write(file.getBytes());
				fos.close();

				// check file length in console
				LOGGER.info("AttachmentService:: upload() :: uploaded file size :: " + tempFile.length());

				// removing the file created in the server
				// tempFile.delete();

			} catch (Exception e) {
				// The call was transmitted successfully, but Amazon S3 couldn't
				// process
				// it, so it returned an error response.
				e.printStackTrace();
			}

			LOGGER.info("File uploaded successfully========");

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(" :: Technical problem in attaching file");
			// return null;
		}

	}

	public FileResponseVo storeFile(MultipartFile file, String formCode, long txId, long userId, String docType,
			Long fileId, Long schoolId, String profileStep) {
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
			attachmentDetailVO.setSchoolId(schoolId);
			attachmentDetailVO = attchmentDao.save(attachmentDetailVO);

			// update stepper in SchoolMst
			if (null != profileStep && !profileStep.isEmpty()) {
				// first fetch existing profile step
				String existingProfileStep = schoolMstDao.getProfileStep(txId);
				String newProfileStep = (null != existingProfileStep && !existingProfileStep.contains(profileStep))
						? existingProfileStep + "," + profileStep
						: (null != existingProfileStep && existingProfileStep.contains(profileStep))
								? existingProfileStep
								: profileStep;
				schoolMstDao.updateProfileStep(txId, newProfileStep);
				LOGGER.info(
						" -------------------------- updateProfileStep called successfully -------------------- newProfileStep ->"
								+ newProfileStep);
				Set<String> setSteps = Stream.of(newProfileStep.trim().split("\\s*,\\s*")).collect(Collectors.toSet());
				resTemp.setProfileStepSet(setSteps);
			}

			// return fileName;
			// added by kousik -->> for return fileId
			resTemp.setFileName(fileName);
			resTemp.setFileId(attachmentDetailVO.getFileId());
			return resTemp;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public FileResponseVo storeAdvertisementFile(MultipartFile file, String formCode, long txId, long userId, String docType,
			Long fileId, Long adsId, String adsUrl) {
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
			attachmentDetailVO.setAdsid(adsId);
			attachmentDetailVO.setSchoolId(0L);
			attachmentDetailVO.setAdsUrl(adsUrl);
			attachmentDetailVO = attchmentDao.save(attachmentDetailVO);

			// return fileName;
			// added by kousik -->> for return fileId
			resTemp.setFileName(fileName);
			resTemp.setFileId(attachmentDetailVO.getFileId());
			return resTemp;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	private Path fileStorageLocation;

	@Autowired
	public AttachmentService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			LOGGER.info("loadFileAsResource : filePath : " + filePath);
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + fileName, ex);
		}
	}

}
