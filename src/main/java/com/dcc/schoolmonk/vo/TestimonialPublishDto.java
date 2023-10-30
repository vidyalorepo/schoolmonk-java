package com.dcc.schoolmonk.vo;

import java.util.List;

public class TestimonialPublishDto {
    private List<Long> ids;
    private int code;

    public TestimonialPublishDto(List<Long> ids, int code) {
        this.ids = ids;
        this.code = code;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
