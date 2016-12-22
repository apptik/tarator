package io.apptik.tarator;


import org.hamcrest.Matcher;

/**
 * Can represent menu, dropdown, listview
 */
public interface OptionListCube extends Cube {

    <S extends ScreenCube> S dismiss();

    <S extends ScreenCube> S select(int menuElement);

    <S extends ScreenCube> S select(String menuElement);

    <S extends ScreenCube> S select(Matcher<?> menuElementMatcher);

    <S extends OptionListCube> S expand(int menuElement);

    <S extends OptionListCube> S expand(String menuElement);

    <S extends OptionListCube> S expand(Matcher<?> menuElementMatcher);

    <S extends OptionListCube> S collapse(int menuElement);

    <S extends OptionListCube> S collapse(String menuElement);

    <S extends OptionListCube> S collapse(Matcher<?> menuElementMatcher);


}
