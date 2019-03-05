package com.karavatskiy.serhii.freetotalkchat.data;

/**
 * Created by Serhii on 19.02.2019.
 */
public class UserCard {

    private String userName;
    private String email;
    private String userPhotoPath;

    public String getUserName() {
        return userName;
    }
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(final String email) {
        this.email = email;
    }

    public String getUserPhotoPath() {
        return userPhotoPath;
    }
    public void setUserPhotoPath(final String userPhotoPath) {
        this.userPhotoPath = userPhotoPath;
    }
}
