package com.dcc.schoolmonk.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name ="DROPDOWN_VALUE")
@EntityListeners(AuditingEntityListener.class)
public class DropdownMasterVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "form_code")
	private String formCode;
	
	@Column(name = "field_name")
	private String fieldName;
	
	@Column(name = "list_id")
	private String listId;
	
	@Column(name = "list_value")
	private String listValue;
	
	@Column(name = "parent")
	private String parent;
	
	@Column(name = "image_path")
	private String imagePath;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getListId() {
		return listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getListValue() {
		return listValue;
	}

	public void setListValue(String listValue) {
		this.listValue = listValue;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}	

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "DropdownValueMaster [id=" + id + ", formCode=" + formCode + ", fieldName=" + fieldName + ", listId="
				+ listId + ", listValue=" + listValue + ", parent=" + parent + "]";
	}

}
