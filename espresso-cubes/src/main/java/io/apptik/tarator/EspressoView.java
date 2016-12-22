package io.apptik.tarator;


import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class EspressoView implements GUIElementCube {

    Matcher<View> viewMatcher;

    public EspressoView(Matcher<View> viewMatcher) {
        this.viewMatcher = viewMatcher;
    }

    @Override
    public <S extends GUIElementCube> S screenshot() {
        throw new UnsupportedOperationException("opearation not available in this implementation");
    }

    @Override
    public <S extends GUIElementCube> S screenshot(String path) {
        throw new UnsupportedOperationException("opearation not available in this implementation");
    }

    @Override
    public EspressoView sendKeys(String keys) {
        return sendKeys(keys, true);
    }

    @Override
    public EspressoView sendKeyCode(int keyCode) {
        return sendKeyCode(keyCode, true);
    }

    @Override
    public EspressoView sendKeys(String keys, boolean focus) {
        if (focus) {
            onView(viewMatcher).perform(typeText(keys));
        } else {
            onView(viewMatcher).perform(typeTextIntoFocusedView(keys));
        }

        return this;
    }

    @Override
    public EspressoView sendKeyCode(int keyCode, boolean focus) {
        if (focus) {
            tap();
        }
        onView(viewMatcher).perform(pressKey(keyCode));
        return this;
    }

    @Override
    public EspressoView tap() {
        onView(viewMatcher).perform(click());
        return this;
    }

    @Override
    public EspressoView tap(int x, int y) {
        return null;
    }

    @Override
    public <S extends GUIElementCube> S doubleTap() {
        return null;
    }

    @Override
    public <S extends GUIElementCube> S doubleTap(int x, int y) {
        return null;
    }

    @Override
    public <S extends GUIElementCube> S longPress() {
        return null;
    }

    @Override
    public <S extends GUIElementCube> S longPress(int x, int y) {
        return null;
    }

    @Override
    public <S extends GUIElementCube> S tapOnText(String keys) {
        return null;
    }

    @Override
    public <S extends GUIElementCube> S doubleTapOnText(String keys) {
        return null;
    }

    @Override
    public <S extends GUIElementCube> S longPressOnText(String keys) {
        return null;
    }

    @Override
    public <S extends GUIElementCube> S dragTo(int destX, int destY, int steps) {
        return null;
    }

    @Override
    public EspressoView selfCheck() {
        onView(viewMatcher).check(matches(isDisplayed()));
        return this;
    }
}
