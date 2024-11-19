package com.aim.problems;

public class EggholderFunction extends GeneralProblem {

    @Override
    public double evaluate(int x, int y) {
        double term1 = -(y + 47) * Math.sin(Math.sqrt(Math.abs(x / 2.0 + (y + 47))));
        double term2 = -x * Math.sin(Math.sqrt(Math.abs(x - (y + 47))));
        return term1 + term2;
    }

    @Override
    public int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    public double getGlobalOptimum() {
        // The global minimum value for the Eggholder function
        return -959.6407;
    }

    @Override
    public int[] getGlobalOptimumPoint() {
        // The coordinates of the global minimum
        return new int[]{512, 404}; // Rounded to integers
    }
}