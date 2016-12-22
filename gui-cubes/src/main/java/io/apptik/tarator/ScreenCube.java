package io.apptik.tarator;

/**
 * Represent a particular Screen that a User can interact with.
 * <p>This doesn't extend {@link GUIElementCube} on purpose in order to have clean implementations
 * of specific UI Screen Concepts which abstracts from Specific Ui components but interact with
 * business concepts. One however can combine those.
 * </p>
 */
public interface ScreenCube extends Cube {

    <S extends ScreenCube> S goBack();

    <S extends ScreenCube> S goHome();

    <S extends OptionListCube> S openMenu();

}
