package com.dcc.schoolmonk.vo;

import java.util.Set;

public class FileResponseVo {
	
	private String respMsg;
    private String fileName;
    private String filePath;
    private Long fileId;
    private Long schoolId;
    private Set<String> profileStepSet;
    private Long adsId;
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public Set<String> getProfileStepSet() {
		return profileStepSet;
	}
	public void setProfileStepSet(Set<String> profileStepSet) {
		this.profileStepSet = profileStepSet;
	}
	public FileResponseVo(String respMsg, String fileName, String filePath, Long fileId, Set<String> profileStepSet) {
		super();
		this.respMsg = respMsg;
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileId = fileId;
		this.profileStepSet = profileStepSet;
	}
	public FileResponseVo(String respMsg, String fileName, String filePath, Long fileId) {
		super();
		this.respMsg = respMsg;
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileId = fileId;
	}
	public FileResponseVo() {
		
	}
	public FileResponseVo(String errMsg) {
		// TODO Auto-generated constructor stub
		this.respMsg = errMsg;
	}
	@Override
	public String toString() {
		return "FileResponseVo [respMsg=" + respMsg + ", fileName=" + fileName + ", filePath=" + filePath + "]";
	}
	public Long getAdsId() {
		return adsId;
	}
	public void setAdsId(Long adsId) {
		this.adsId = adsId;
	}
	
	
    
  
}
