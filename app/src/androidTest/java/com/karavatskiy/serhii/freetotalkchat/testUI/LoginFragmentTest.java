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
import java.util.UUID;
import org.junit.*;

/**
 * Created by Serhii on 15.02.2019.
 */
public class LoginFragmentTest {

    private static final String EMAIL_WITH_SPACE = "i @i.i";
    private static final String EMAIL_VALID = "i@a.com";

    private static final String PASSWORD_SHORT = "123";
    private static final String PASSWORD_WITH_SPACE = "12345 6";
    private static final String PASSWORD_VALID = "123456";


    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginTestWithSpacesMailAndSpacesInPassword() {
        onView(withId(R.id.tvSignIn)).check(matches(isDisplayed()));
        onView(withId(R.id.etEmail)).perform(typeText(EMAIL_WITH_SPACE));
        onView(withId(R.id.etPassword)).perform(typeText(PASSWORD_WITH_SPACE));
        onView(withId(R.id.btnSignIn)).perform(click());
        onView(withId(R.id.etEmail)).check(matches(hasErrorText("Don`t use spaces in email")));
        onView(withId(R.id.etPassword)).check(matches(hasErrorText("Don`t use spaces in password")));
    }

    @Test
    public void loginTestWithInvalidInEmailAndShortPassword() {
        onView(withId(R.id.etEmail)).perform(typeText(UUID.randomUUID().toString()));
        onView(withId(R.id.etPassword)).perform(typeText(PASSWORD_SHORT));
        onView(withId(R.id.btnSignIn)).perform(click());
        onView(withId(R.id.etEmail)).check(matches(hasErrorText("Enter a valid email")));
        onView(withId(R.id.etPassword)).check(matches(hasErrorText("Password must contain 6-12 characters")));
    }

    @Test
    public void loginTestWithEmptyEmailAndEmptyPassword() {
        onView(withId(R.id.btnSignIn)).perform(click());
        onView(withId(R.id.etEmail)).check(matches(hasErrorText("Email is required")));
        onView(withId(R.id.etPassword)).check(matches(hasErrorText("Password is required")));
    }

    @Test
    public void loginTestWithValidEmailAndValidPassword() {
        onView(withId(R.id.etEmail)).perform(typeText(EMAIL_VALID));
        onView(withId(R.id.etPassword)).perform(typeText(PASSWORD_VALID));
        onView(withId(R.id.btnSignIn)).perform(click());
    }

    @Test
    public void loginTestCheckGoToRegistrationFragment() {
        onView(withId(R.id.tvCreateAcc)).perform(click());
        onView(withId(R.id.tvSignUp)).check(matches(isDisplayed()));
    }

}

// TODO: 16.02.2019 registration test handle error messages
