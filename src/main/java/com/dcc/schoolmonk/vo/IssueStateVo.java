package com.dcc.schoolmonk.vo;

public class IssueStateVo {
    private Long typeCount;
    private String issueState;

    public Long getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(Long typeCount) {
        this.typeCount = typeCount;
    }

    public String getIssueState() {
        return issueState;
    }

    public void setIssueState(String issueState) {
        this.issueState = issueState;
    }

    public IssueStateVo(Long typeCount, String issueState) {
        this.typeCount = typeCount;
        this.issueState = issueState;
    }

    public IssueStateVo() {
    }

    @Override
    public String toString() {
        return "IssueStateVo [issueState=" + issueState + ", typeCount=" + typeCount + "]";
    }

}
