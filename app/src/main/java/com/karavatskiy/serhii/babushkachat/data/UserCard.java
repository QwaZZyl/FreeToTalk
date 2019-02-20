package com.karavatskiy.serhii.babushkachat.data;

/**
 * Created by Serhii on 19.02.2019.
 */
public class UserCard {

    private String UserName;

    private String eMail;

    private String userPhotoPath;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(final String userName) {
        UserName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(final String eMail) {
        this.eMail = eMail;
    }

    public String getUserPhotoPath() {
        return userPhotoPath;
    }

    public void setUserPhotoPath(final String userPhotoPath) {
        this.userPhotoPath = userPhotoPath;
    }
}
