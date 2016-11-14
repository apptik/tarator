package io.apptik.tarator.test.feature;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.apptik.tarator.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class FirstGlues {

    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity
            .class);

    public FirstGlues() {
    }

    @Before
    public void setUp() {
        mActivityTestRule.launchActivity(null);
    }

    @After
    public void tearDown() {
        mActivityTestRule.getActivity().finish();
    }

    @Given("^there is a rule$")
    public void there_is_have_a_rule() throws Throwable {
        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"), isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withText("Settings"), isDisplayed()));
        appCompatTextView.perform(click());
    }

    @When("^we tap on a button$")
    public void we_tap_on_a_button() throws Throwable {
//        ViewInteraction floatingActionButton = onView(
//                allOf(withId(R.id.fab), isDisplayed()));
//        floatingActionButton.perform(click());
    }

    @Then("^we should see the rule$")
    public void we_should_see_the_rule() throws Throwable {
//        ViewInteraction imageButton = onView(
//                allOf(withId(R.id.fab), isDisplayed()));
//        imageButton.check(matches(isDisplayed()));
    }


}
