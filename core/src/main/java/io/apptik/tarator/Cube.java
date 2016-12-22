package io.apptik.tarator;


interface Cube {

    /**
     * Performs a self-check to verify that the current state is as expected.
     * In case we are in a state not known by the Cube then it should fail.
     * @param <C>
     * @return this normally should return the same instance that was invoked.
     */
    <C extends Cube> C selfCheck();
}
