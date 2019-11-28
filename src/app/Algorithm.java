package app;

import models.Point;

/**
 * @author mouctar
 */
abstract class Algorithm<E> {
    abstract void execute(E dataStructure, Point departure, Point arrival);

}
