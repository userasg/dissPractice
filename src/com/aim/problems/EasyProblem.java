package com.aim.problems;

public class EasyProblem extends GeneralProblem {

    @Override
    public double evaluate(int x, int y) {
        // Paraboloid function: f(x, y) = x^2 + y^2
        return Math.pow(x, 2) + Math.pow(y, 2);
    }

    @Override
    public double getGlobalOptimum() {
        return 0.0; // Minimum value of f(x, y) = x^2 + y^2
    }

    @Override
    public int[] getGlobalOptimumPoint() {
        return new int[]{0, 0}; // The global minimum is at (0, 0)
    }
}