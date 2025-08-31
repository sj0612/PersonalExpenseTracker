package com.petracker.framework.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserGetDTO {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    private String name;
    private String mailId;
    private String mobileNo;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    private String password;

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", mailId='" + mailId + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                '}';
    }
}
