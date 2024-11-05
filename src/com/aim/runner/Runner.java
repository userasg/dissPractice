package com.aim.runner;

import com.aim.problems.SpecificProblem;
import com.aim.metaheuristics.SimulatedAnnealing;
import com.aim.Metrics.Metrics;

public class Runner {
    public static void main(String[] args) {
        String[] functionNames = {
                "Function1 f(x, y) = (x - 1)^2 + (y + 2)^2",
                "Function2 f(x, y) = x^2 + 3*(y - 1)^2",
                "Function3 f(x, y) = |x| + |y|"
        };

        for (String functionName : functionNames) {
            SpecificProblem problem = new SpecificProblem(functionName);
            SimulatedAnnealing sa = new SimulatedAnnealing(problem, 100.0, 0.01, 0.95, 100);
            Metrics metrics = sa.run(0, 0);

            System.out.println("Final Metrics for " + functionName + ":\n" + metrics);
            System.out.println("------------------------------------------------");
        }
    }
}
