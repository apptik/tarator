package io.apptik.tarator;


public interface GUIElementCube extends Cube {

    //    <S extends GUIElementCube> S nextOnFocus();
//    <S extends GUIElementCube> S prevOnFocus();
//    <S extends GUIElementCube> S setOnFocus();

    <S extends GUIElementCube> S screenshot();

    <S extends GUIElementCube> S screenshot(String path);

    <S extends GUIElementCube> S sendKeys(String keys);

    <S extends GUIElementCube> S sendKeyCode(int keyCode);

    <S extends GUIElementCube> S sendKeys(String keys, boolean focus);

    <S extends GUIElementCube> S sendKeyCode(int keyCode, boolean focus);

    <S extends GUIElementCube> S tap();

    <S extends GUIElementCube> S tap(int x, int y);

    <S extends GUIElementCube> S doubleTap();

    <S extends GUIElementCube> S doubleTap(int x, int y);

    <S extends GUIElementCube> S longPress();

    <S extends GUIElementCube> S longPress(int x, int y);

    <S extends GUIElementCube> S tapOnText(String keys);

    <S extends GUIElementCube> S doubleTapOnText(String keys);

    <S extends GUIElementCube> S longPressOnText(String keys);

    <S extends GUIElementCube> S dragTo(int destX, int destY, int steps);


}
