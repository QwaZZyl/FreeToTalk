package testUnit;

import static junit.framework.TestCase.assertFalse;

import com.karavatskiy.serhii.babushkachat.utils.ValidatorSignIn;
import java.util.UUID;
import org.junit.*;

/**
 * Created by Serhii on 05.02.2019.
 */
public class ValidatorTestNegative {

    private static final String PASSWORD_SHORT = "123";

    private ValidatorSignIn validatorSignIn;


    @Test
    public void validatorTestEmptyString() {
        String email = "";
        String password = "";
        validatorSignIn = new ValidatorSignIn();
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
        validatorSignIn = new ValidatorSignIn();
        validatorSignIn.validateEmail(email, error -> {
        });
        validatorSignIn.validatePass(password, error -> {
        });
        validatorSignIn.isLoginValid();
        assertFalse(validatorSignIn.isLoginValid());
    }

    @Test
    public void validatorTestBadString() {
        String email = UUID.randomUUID().toString();
        String password = PASSWORD_SHORT;

        validatorSignIn.validateEmail(email, error -> {
        });
        validatorSignIn.validatePass(password, error -> {
        });
        assertFalse(validatorSignIn.isLoginValid());
    }
}
