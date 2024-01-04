package model;

import java.math.BigDecimal;

public class Charity {

    private Long id;
    private String title;
    private String host;
    private String description;
    private BigDecimal charityAmount;
    private String category;
    private BigDecimal fundedMoney;
    private boolean isVerified;
    private boolean isComplete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setHostName(String hostName) {
        this.host = hostName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCharityAmount() {
        return charityAmount;
    }

    public void setCharityAmount(BigDecimal charityAmount) {
        this.charityAmount = charityAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getFundedMoney() {
        return fundedMoney;
    }

    public void setFundedMoney(BigDecimal fundedMoney) {
        this.fundedMoney = fundedMoney;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
