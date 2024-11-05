package com.aim.problems;

public class SpecificProblem extends GeneralProblem {

    private final String functionName;

    public SpecificProblem(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public double evaluate(int x, int y) {
        // Define specific functions based on the name
        switch (functionName) {
            case "Function1 f(x, y) = (x - 1)^2 + (y + 2)^2":
                return Math.pow(x - 1, 2) + Math.pow(y + 2, 2); // f(x, y) = (x - 1)^2 + (y + 2)^2
            case "Function2 f(x, y) = x^2 + 3*(y - 1)^2":
                return Math.pow(x, 2) + 3 * Math.pow(y - 1, 2); // f(x, y) = x^2 + 3*(y - 1)^2
            case "Function3 f(x, y) = |x| + |y|":
                return Math.abs(x) + Math.abs(y); // f(x, y) = |x| + |y|
            default:
                throw new IllegalArgumentException("Undefined function name: " + functionName);
        }
    }

    public String getFunctionName() {
        return functionName;
    }
}
