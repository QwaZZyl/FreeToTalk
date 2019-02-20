package testUnit;

import static junit.framework.TestCase.assertFalse;

import com.karavatskiy.serhii.babushkachat.utils.ValidatorSignIn;
import org.junit.*;

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
