package com.karavatskiy.serhii.babushkachat.ui.login;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;

/**
 * Created by Serhii on 16.01.2019.
 */
public class Validator {

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9._]{3,10}$";

    private int validatedFlags = 0;

    public boolean isValidatedRegistration() {
        return validatedFlags == 4;
    }

    public boolean isValidatedLogin() {
        return validatedFlags == 2;
    }

    public String validateEmail(String email) {
        if (isEmpty(email)) {
            return "Email is required";
        } else if (email.contains(" ")) {
            return "Don`t use spaces in email";
        } else if (!isEmailValid(email)) {
            return "Enter a valid email";
        } else {
            validatedFlags += 1;
            return null;
        }
    }

    public String validateUserName(String userName) {
        if (isEmpty(userName)) {
            return "Enter user name";
        } else if (!isUserNameValid(userName)) {
            return "Enter valid user name 3-10 characters";
        } else {
            validatedFlags += 1;
            return null;
        }
    }

    public String validatePass(String password) {
        if (isEmpty(password)) {
            return "Password is required";
        } else if (password.contains(" ")) {
            return "Don`t use spaces in password";
        } else if (!isPasswordValid(password)) {
            return "Password must contain 6-12 characters";
        } else {
            validatedFlags += 1;
            return null;
        }
    }

    public String validateConfirmPass(String password, String confirmPass) {
        if (isEmpty(confirmPass)) {
            return "Password is required";
        } else if (!isPasswordMatch(password, confirmPass)) {
            return "Passwords not match";
        } else {
            validatedFlags += 1;
            return null;
        }
    }

    private static boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static boolean isUserNameValid(String userName) {
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }

    private static boolean isPasswordMatch(String password, String confirmPass) {
        return password.equals(confirmPass);
    }

    private static boolean isPasswordValid(String password) {
        return password.length() >= 6 && password.length() <= 12;
    }
}
