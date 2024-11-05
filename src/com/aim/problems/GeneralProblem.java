package com.aim.problems;

public abstract class GeneralProblem {

    /**
     * Abstract method to evaluate the function at a given (x, y) position.
     */
    public abstract double evaluate(int x, int y);

    /**
     * Optional method to clamp x and y values if you want bounded variables.
     */
    public int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
