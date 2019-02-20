package com.karavatskiy.serhii.babushkachat.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Serhii on 16.01.2019.
 */
public class ValidatorSignIn implements Validator {

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9._]{3,10}$";

    private static final String EMAIL_ADDRESS =
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+";


    private static final int VALID_REGISTRATION = 4;

    private static final int VALID_LOGIN = 2;

    private static final int INVALID = 0;

    private int validatedFlags = 0;

    public boolean isValidatedRegistration() {
        if (validatedFlags != VALID_REGISTRATION) {
            validatedFlags = INVALID;
            return false;
        } else {
            validatedFlags = INVALID;
            return true;
        }
    }

    public boolean isLoginValid() {
        if (validatedFlags == VALID_LOGIN) {
            validatedFlags = INVALID;
            return true;
        } else {
            validatedFlags = INVALID;
            return false;
        }
    }

    public void validateEmail(String email, OnErrorListener onErrorListener) {
        if (email == null) {
            return;
        } else if (isEmpty(email)) {
            onErrorListener.onError("Email is required");
        } else if (email.contains(" ")) {
            onErrorListener.onError("Don`t use spaces in email");
        } else if (!isEmailValid(email)) {
            onErrorListener.onError("Enter a valid email");
        } else {
            validatedFlags += 1;
            onErrorListener.onError(null);
        }
    }

    public void validateUserName(String userName, OnErrorListener onErrorListener) {
        if (userName == null) {
            return;
        } else if (isEmpty(userName)) {
            onErrorListener.onError("Enter user name");
        } else if (userName.contains(" ")) {
            onErrorListener.onError("Don`t use spaces in name");
        } else if (!isUserNameValid(userName)) {
            onErrorListener.onError("Enter valid user name 3-10 characters");
        } else {
            validatedFlags += 1;
            onErrorListener.onError(null);
        }
    }

    public void validatePass(String password, OnErrorListener onErrorListener) {
        if (password == null) {
            return;
        } else if (isEmpty(password)) {
            onErrorListener.onError("Password is required");
        } else if (password.contains(" ")) {
            onErrorListener.onError("Don`t use spaces in password");
        } else if (!isPasswordValid(password)) {
            onErrorListener.onError("Password must contain 6-12 characters");
        } else {
            validatedFlags += 1;
            onErrorListener.onError(null);
        }
    }

    public void validateConfirmPass(String password, String confirmPass, OnErrorListener onErrorListener) {
        if (password == null && confirmPass == null) {
            return;
        } else if (confirmPass.contains(" ")) {
            onErrorListener.onError("Don`t use spaces in password");
        } else if (!isPasswordValid(confirmPass)) {
            onErrorListener.onError("Password must contain 6-12 characters");
        } else if (isEmpty(confirmPass)) {
            onErrorListener.onError("Password is required");
        } else if (!isPasswordsMatch(password, confirmPass)) {
            onErrorListener.onError("Passwords not match");
        } else {
            validatedFlags += 1;
            onErrorListener.onError(null);
        }
    }

    boolean summaryValid() {
        return true;
    }

    @Override
    public boolean isValid() {
        return summaryValid();
    }

    private static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_ADDRESS);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static boolean isUserNameValid(String userName) {
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }

    private static boolean isPasswordsMatch(String password, String confirmPass) {
        return password.equals(confirmPass);
    }
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    private static boolean isPasswordValid(String password) {
        return password.length() >= 6 && password.length() <= 12;
    }

    public interface OnErrorListener {

        void onError(String error);
    }

}
