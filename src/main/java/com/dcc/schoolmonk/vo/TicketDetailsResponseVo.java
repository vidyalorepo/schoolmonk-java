package com.dcc.schoolmonk.vo;

public class TicketDetailsResponseVo {
    private String ticketId;
    private Long issueId;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public TicketDetailsResponseVo(String ticketId, Long issueId) {
        this.ticketId = ticketId;
        this.issueId = issueId;
    }

    public TicketDetailsResponseVo() {
    }

    @Override
    public String toString() {
        return "TicketDetailsResponseVo [issueId=" + issueId + ", ticketId=" + ticketId + "]";
    }

}
