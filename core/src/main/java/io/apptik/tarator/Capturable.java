package io.apptik.tarator;



public interface Capturable extends Cube {

    <C extends Cube> C capture();
}
