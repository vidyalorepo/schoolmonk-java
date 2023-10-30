package com.dcc.schoolmonk.vo;

public class TestimonialDtlVo {
    private String name;
    private String designation;
    private String institution;
    private String message;

    public TestimonialDtlVo(String name, String designation, String institution, String message) {
        this.name = name;
        this.designation = designation;
        this.institution = institution;
        this.message = message;
    }

    public TestimonialDtlVo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
