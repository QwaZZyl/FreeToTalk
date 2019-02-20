package com.karavatskiy.serhii.babushkachat.testUnit.testUnit;

import static org.junit.jupiter.api.Assertions.*;

import com.karavatskiy.serhii.babushkachat.utils.ValidatorSignIn;
import org.junit.jupiter.api.*;

/**
 * Created by Serhii on 14.02.2019.
 */
public class ValidatorTestPositive {

    private ValidatorSignIn validatorSignIn;

    @Test
    public void validatorTestValidLogin() throws Exception {
        String email = "i@i.com";
        String password = "123456";
        validatorSignIn = new ValidatorSignIn();
        validatorSignIn.validateEmail(email, error -> {
        });
        validatorSignIn.validatePass(password, error -> {
        });
        validatorSignIn.isLoginValid();
        assertFalse(validatorSignIn.isLoginValid());
    }
}
