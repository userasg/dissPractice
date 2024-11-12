package com.aim.problems;

public abstract class GeneralProblem {

    public abstract double evaluate(int x, int y);

    public abstract double getGlobalOptimum();

    public abstract int[] getGlobalOptimumPoint();

    public int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
