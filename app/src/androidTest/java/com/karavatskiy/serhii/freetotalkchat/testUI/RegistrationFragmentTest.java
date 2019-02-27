package com.karavatskiy.serhii.freetotalkchat.testUI;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.support.test.rule.ActivityTestRule;
import com.karavatskiy.serhii.freetotalkchat.R;
import com.karavatskiy.serhii.freetotalkchat.ui.login.LoginActivity;
import com.karavatskiy.serhii.freetotalkchat.ui.login.RegistrationFragment;
import java.util.UUID;
import org.junit.*;

/**
 * Created by Serhii on 19.02.2019.
 */
public class RegistrationFragmentTest {

    private static final String EMAIL_WITH_SPACE = "i @i.i";
    private static final String EMAIL_VALID = "i@a.com";

    private static final String USER_NAME_WITH_SPACE = "J ";
    private static final String USER_NAME_SHORT = "J";
    private static final String USER_NAME_VALID = "Jeronimo";

    private static final String PASSWORD_SHORT = "123";
    private static final String PASSWORD_WITH_SPACE = "12345 6";
    private static final String PASSWORD_VALID = "123456";

    private static final String CONFIRM_PASSWORD_SHORT = "123";
    private static final String CONFIRM_PASSWORD_WITH_SPACE = "12345 6";


    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        activityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentPlace, RegistrationFragment.newInstance()).commit();
    }

    @Test
    public void registrationTestWithInvalidMailAndSpacesInPassword() {
        onView(withId(R.id.tvSignUp)).check(matches(isDisplayed()));
        onView(withId(R.id.etEmail)).perform(typeText(EMAIL_WITH_SPACE));
        onView(withId(R.id.etUserName)).perform(typeText(USER_NAME_WITH_SPACE));
        onView(withId(R.id.etPassword)).perform(typeText(PASSWORD_WITH_SPACE));
        onView(withId(R.id.etConfirmPassword)).perform(typeText(CONFIRM_PASSWORD_WITH_SPACE));
        onView(withId(R.id.btnCreateNewAcc)).perform(click());
        onView(withId(R.id.etUserName)).check(matches(hasErrorText("Don`t use spaces in name")));
        onView(withId(R.id.etEmail)).check(matches(hasErrorText("Don`t use spaces in email")));
        onView(withId(R.id.etPassword)).check(matches(hasErrorText("Don`t use spaces in password")));
        onView(withId(R.id.etConfirmPassword)).check(matches(hasErrorText("Don`t use spaces in password")));
    }

    @Test
    public void registrationTestInvalidEmailAndShortPasswordsAndShortUserName() {
        onView(withId(R.id.etEmail)).perform(typeText(UUID.randomUUID().toString()));
        onView(withId(R.id.etPassword)).perform(typeText(PASSWORD_SHORT));
        onView(withId(R.id.etUserName)).perform(typeText(USER_NAME_SHORT));
        onView(withId(R.id.etConfirmPassword)).perform(typeText(CONFIRM_PASSWORD_SHORT));
        onView(withId(R.id.btnCreateNewAcc)).perform(click());
        onView(withId(R.id.etEmail)).check(matches(hasErrorText("Enter a valid email")));
        onView(withId(R.id.etUserName)).check(matches(hasErrorText("Enter valid user name 3-10 characters")));
        onView(withId(R.id.etPassword)).check(matches(hasErrorText("Password must contain 6-12 characters")));
        onView(withId(R.id.etConfirmPassword)).check(matches(hasErrorText("Password must contain 6-12 characters")));
    }

    @Test
    public void registrationTestWithEmptyEmailAndEmptyPassword() {
        onView(withId(R.id.btnCreateNewAcc)).perform(click());
        onView(withId(R.id.etEmail)).check(matches(hasErrorText("Email is required")));
        onView(withId(R.id.etUserName)).check(matches(hasErrorText("Enter user name")));
        onView(withId(R.id.etPassword)).check(matches(hasErrorText("Password is required")));
        onView(withId(R.id.etConfirmPassword)).check(matches(hasErrorText("Password is required")));
    }

    @Test
    public void registrationTestWithValidEmailAndValidPassword() {
        onView(withId(R.id.etEmail)).perform(typeText(EMAIL_VALID));
        onView(withId(R.id.etUserName)).perform(typeText(USER_NAME_VALID));
        onView(withId(R.id.etPassword)).perform(typeText(PASSWORD_VALID));
        onView(withId(R.id.etConfirmPassword)).perform(typeText(PASSWORD_VALID));
        onView(withId(R.id.btnCreateNewAcc)).perform(click());
    }


}