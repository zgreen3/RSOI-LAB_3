package smirnov.bn.web_spring_app_1.model;

import java.util.UUID;

public class UserInfo {

    private Integer userId;

    private String userLogin;

    private String userPasswordHash;

    private String userEmail;

    private UUID userUuid;

    public UserInfo() {}

    public UserInfo(String userLogin, String userPasswordHash, String userEmail) {
        this.userLogin = userLogin;
        this.userPasswordHash = userPasswordHash;
        this.userEmail = userEmail;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPasswordHash() {
        return userPasswordHash;
    }

    public void setUserPasswordHash(String userPasswordHash) {
        this.userPasswordHash = userPasswordHash;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }
}