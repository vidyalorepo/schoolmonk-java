package com.dcc.schoolmonk.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "SCHOOL_STREAM_DTL")
@EntityListeners(AuditingEntityListener.class)
public class SchoolStreamDtlVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "Admission_Id", nullable = false )
    @JsonBackReference
	private SchoolAdmissionDtlVo schoolAdmissionDtlVo;
	
	@Column(name = "stream_name", length = 100)
	private String streamName;			//science, arts
	
	@JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "schoolStreamDtlVo")
    private List<SchoolStreamSubjectDtlVo> schoolStreamSubjectDtlVo = new ArrayList<>();
	
	@Transient
	private Map<String, List<SchoolStreamSubjectDtlVo>> subjectOptionsVo = new HashMap<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreamName() {
		return streamName;
	}

	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	public List<SchoolStreamSubjectDtlVo> getSchoolStreamSubjectDtlVo() {
		return schoolStreamSubjectDtlVo;
	}

	public void setSchoolStreamSubjectDtlVo(List<SchoolStreamSubjectDtlVo> schoolStreamSubjectDtlVo) {
		this.schoolStreamSubjectDtlVo = schoolStreamSubjectDtlVo;
	}

	public SchoolAdmissionDtlVo getSchoolAdmissionDtlVo() {
		return schoolAdmissionDtlVo;
	}

	public void setSchoolAdmissionDtlVo(SchoolAdmissionDtlVo schoolAdmissionDtlVo) {
		this.schoolAdmissionDtlVo = schoolAdmissionDtlVo;
	}

	public Map<String, List<SchoolStreamSubjectDtlVo>> getSubjectOptionsVo() {
		return subjectOptionsVo;
	}

	public void setSubjectOptionsVo(Map<String, List<SchoolStreamSubjectDtlVo>> subjectOptionsVo) {
		this.subjectOptionsVo = subjectOptionsVo;
	}
}
