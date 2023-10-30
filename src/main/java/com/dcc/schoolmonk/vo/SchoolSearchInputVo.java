package com.dcc.schoolmonk.vo;

import java.util.List;

public class SchoolSearchInputVo {

	private String schoolName;
	private List<String> board;
	private List<String> medium;
	private List<String> schoolType;
	private List<Long> state;
	private List<String> city;
	private List<Long> district;
	private List<String> schoolLevel;
	private Integer page;
	private Integer size;
	private String sort;
	
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public List<String> getBoard() {
		return board;
	}
	public void setBoard(List<String> board) {
		this.board = board;
	}
	public List<String> getMedium() {
		return medium;
	}
	public void setMedium(List<String> medium) {
		this.medium = medium;
	}
	public List<String> getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(List<String> schoolType) {
		this.schoolType = schoolType;
	}
	public List<Long> getState() {
		return state;
	}
	public void setState(List<Long> state) {
		this.state = state;
	}
	public List<String> getCity() {
		return city;
	}
	public void setCity(List<String> city) {
		this.city = city;
	}
	public List<Long> getDistrict() {
		return district;
	}
	public void setDistrict(List<Long> district) {
		this.district = district;
	}
	public List<String> getSchoolLevel() {
		return schoolLevel;
	}
	public void setSchoolLevel(List<String> schoolLevel) {
		this.schoolLevel = schoolLevel;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	@Override
	public String toString() {
		return "SchoolSearchInputVo [board=" + board + ", medium=" + medium + ", schoolType=" + schoolType + ", page="
				+ page + ", size=" + size + ", state=" + state + ", city=" + city + "]";
	}
}
