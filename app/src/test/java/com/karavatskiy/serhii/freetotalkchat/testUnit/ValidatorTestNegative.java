package com.karavatskiy.serhii.freetotalkchat.testUnit;

import static org.junit.jupiter.api.Assertions.*;

import com.karavatskiy.serhii.freetotalkchat.utils.ValidatorSignIn;
import java.util.UUID;
import org.junit.jupiter.api.*;

/**
 * Created by Serhii on 05.02.2019.
 */
public class ValidatorTestNegative {

    private static final String PASSWORD_SHORT = "123";

    private ValidatorSignIn validatorSignIn;

    @BeforeEach
    void setUp() {
        validatorSignIn = new ValidatorSignIn();

    }

    @Test
    public void validatorTestEmptyString() {
        String email = "";
        String password = "";
        validatorSignIn.validateEmail(email, error -> {
        });
        validatorSignIn.validatePass(password, error -> {
        });
        validatorSignIn.isLoginValid();
        assertFalse(validatorSignIn.isLoginValid());
    }

    @Test
    public void validatorTestNullString() {
        String email = null;
        String password = null;
        validatorSignIn.validateEmail(email, error -> {
        });
        validatorSignIn.validatePass(password, error -> {
        });
        validatorSignIn.isLoginValid();
        assertFalse(validatorSignIn.isLoginValid());
    }

    @Test
    public void validatorTestInvalidString() {
        String email = UUID.randomUUID().toString();
        String password = PASSWORD_SHORT;

        validatorSignIn.validateEmail(email, error -> {
        });
        validatorSignIn.validatePass(password, error -> {
        });
        assertFalse(validatorSignIn.isLoginValid());
    }
}
