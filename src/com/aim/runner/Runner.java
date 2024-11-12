package com.aim.runner;

import com.aim.problems.SpecificProblem;
import com.aim.metaheuristics.SimulatedAnnealing;
import com.aim.metaheuristics.CoolingSchedule;
import com.aim.metaheuristics.GeometricCooling;
// import com.aim.metaheuristics.LundyAndMees; // Uncomment if using LundyAndMees
import com.aim.Metrics.Metrics;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        SpecificProblem problem = new SpecificProblem();
        double globalOptimum = problem.getGlobalOptimum(); // Should be 0.0

        int numRuns = 10; // Number of runs (can increase as needed)
        List<Metrics> allMetrics = new ArrayList<>();

        for (int run = 0; run < numRuns; run++) {
            CoolingSchedule coolingSchedule = new GeometricCooling(100.0, 0.95);
            // Or use LundyAndMees
            // double beta = 0.001;
            // CoolingSchedule coolingSchedule = new LundyAndMees(100.0, beta);

            SimulatedAnnealing sa = new SimulatedAnnealing(problem, coolingSchedule, 100, globalOptimum);
            Metrics metrics = sa.run(0, 0);
            allMetrics.add(metrics);

            System.out.println("Run " + (run + 1) + " Metrics:");
            System.out.println("------------------------------------------------");
            System.out.println("Best Solution Coordinates: (" + metrics.getBestSolution()[0] + ", " + metrics.getBestSolution()[1] + ")");
            System.out.println("Best Objective Value: " + metrics.getBestValue());
            System.out.println("Iteration of Best Solution: " + metrics.getBestIterationNumber());

            System.out.println("Total Accepted Moves: " + metrics.getTotalAcceptedMoves());
            System.out.println("Total Rejected Moves: " + metrics.getTotalRejectedMoves());
            System.out.println("Total Distance Traveled: " + metrics.getTotalDistanceTraveled());
            System.out.println("Total Deviation from Optimum: " + metrics.getTotalDeviationFromOptimum());
            System.out.println("Distinct Positions Visited: " + metrics.getDistinctPositionsCount());
            System.out.println("End-to-End Distance: " + metrics.getEndToEndDistance());

            System.out.println("Objective Values Over Time: " + metrics.getObjectiveValues());
            System.out.println("Visited Points Over Time: ");
            for (int[] point : metrics.getVisitedPoints()) {
                System.out.print("(" + point[0] + ", " + point[1] + ") ");
            }
            System.out.println("\n------------------------------------------------\n");
        }
    }
}
