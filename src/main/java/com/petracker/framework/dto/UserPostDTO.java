package com.petracker.framework.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload to register a new user")
public class UserPostDTO {

    @Schema(description = "Full name of the user", example = "John Doe")
    private String name;

    @Schema(description = "Email address used as login username", example = "john@example.com")
    private String mailId;

    @Schema(description = "Mobile number of the user", example = "+91-9876543210")
    private String mobileNo;

    @Schema(description = "Login password (will be stored encrypted)", example = "P@ssw0rd!")
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", mailId='" + mailId + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
