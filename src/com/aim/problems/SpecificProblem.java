package com.aim.problems;

public class SpecificProblem extends GeneralProblem {

    @Override
    public double evaluate(int x, int y) {
        // Function1: f(x, y) = (x - 1)^2 + (y + 2)^2
        return Math.pow(x - 1, 2) + Math.pow(y + 2, 2);
    }

    @Override
    public double getGlobalOptimum() {
        return 0.0;
    }

    @Override
    public int[] getGlobalOptimumPoint() {
        return new int[]{1, -2};
    }
}
