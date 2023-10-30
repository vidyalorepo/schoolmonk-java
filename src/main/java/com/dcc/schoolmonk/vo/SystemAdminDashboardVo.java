package com.dcc.schoolmonk.vo;

import java.util.List;

public class SystemAdminDashboardVo {

	private Long activeSchoolCount;
	private Long inactiveSchoolCount;
	private Long featuredSchoolCount;
	private List<SchoolMstVo> inactiveSchoolList;
	public Long getActiveSchoolCount() {
		return activeSchoolCount;
	}
	public void setActiveSchoolCount(Long activeSchoolCount) {
		this.activeSchoolCount = activeSchoolCount;
	}
	public Long getInactiveSchoolCount() {
		return inactiveSchoolCount;
	}
	public void setInactiveSchoolCount(Long inactiveSchoolCount) {
		this.inactiveSchoolCount = inactiveSchoolCount;
	}
	public Long getFeaturedSchoolCount() {
		return featuredSchoolCount;
	}
	public void setFeaturedSchoolCount(Long featuredSchoolCount) {
		this.featuredSchoolCount = featuredSchoolCount;
	}
	public List<SchoolMstVo> getInactiveSchoolList() {
		return inactiveSchoolList;
	}
	public void setInactiveSchoolList(List<SchoolMstVo> inactiveSchoolList) {
		this.inactiveSchoolList = inactiveSchoolList;
	}
}
