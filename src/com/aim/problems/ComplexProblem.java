package com.aim.problems;

public class ComplexProblem extends GeneralProblem {

    @Override
    public double evaluate(int x, int y) {
        // Rastrigin function: f(x, y) = 20 + x^2 + y^2 - 10 * (cos(2 * PI * x) + cos(2 * PI * y))
        double A = 10;
        double term1 = Math.pow(x, 2) - A * Math.cos(2 * Math.PI * x);
        double term2 = Math.pow(y, 2) - A * Math.cos(2 * Math.PI * y);
        return A * 2 + term1 + term2;
    }

    @Override
    public double getGlobalOptimum() {
        return 0.0;
    }

    @Override
    public int[] getGlobalOptimumPoint() {
        return new int[]{0, 0};
    }
}
