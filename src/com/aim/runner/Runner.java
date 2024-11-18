package com.aim.runner;

import com.aim.problems.SpecificProblem;
import com.aim.problems.ComplexProblem;
import com.aim.problems.GeneralProblem;
import com.aim.metaheuristics.SimulatedAnnealing;
import com.aim.metaheuristics.CoolingSchedule;
import com.aim.metaheuristics.GeometricCooling;
import com.aim.Metrics.Metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Runner {

    public static void main(String[] args) {
        // Choose which problem to use
        GeneralProblem problem;
        String problemType = "complex"; // Change to "specific" or "complex"

        if (problemType.equals("specific")) {
            problem = new SpecificProblem();
        } else if (problemType.equals("complex")) {
            problem = new ComplexProblem();
        } else {
            System.out.println("Unknown problem type. Defaulting to SpecificProblem.");
            problem = new SpecificProblem();
        }

        double globalOptimum = problem.getGlobalOptimum();
        int numRuns = 50; // Number of runs for each maxIters setting
        int[] maxItersArray = {10, 100, 1000}; // Different maxIters settings

        Random random = new Random();

        for (int maxIters : maxItersArray) {
            List<Metrics> allMetrics = new ArrayList<>();

            System.out.println("Running with maxIters = " + maxIters);

            for (int run = 0; run < numRuns; run++) {
                CoolingSchedule coolingSchedule = new GeometricCooling(100.0, 0.95);

                // Generate a random starting point within the bounds [-50, 50] for both x and y
                int startX = random.nextInt(101) - 50;
                int startY = random.nextInt(101) - 50;

                SimulatedAnnealing sa = new SimulatedAnnealing(problem, coolingSchedule, maxIters, globalOptimum);
                Metrics metrics = sa.run(startX, startY);
                allMetrics.add(metrics);

                System.out.println("Run " + (run + 1) + " Metrics with maxIters = " + maxIters);
                System.out.println(metrics.toString());
                System.out.println("Objective Values Over Time: " + metrics.getObjectiveValues());
                System.out.println("Visited Points Over Time: ");
                for (int[] point : metrics.getVisitedPoints()) {
                    System.out.print("(" + point[0] + ", " + point[1] + ") ");
                }
                System.out.println("\n------------------------------------------------\n");
            }

            // Export each maxIters run set to a separate CSV file
            exportMetricsToCSV(allMetrics, "metrics_results_maxIters_" + maxIters + ".csv");
        }
    }

    private static void exportMetricsToCSV(List<Metrics> metricsList, String fileName) {
        try (java.io.FileWriter writer = new java.io.FileWriter(fileName)) {
            writer.write("Run,Best Value,Best Iteration,Total Accepted Moves,Total Rejected Moves,Total Distance Traveled," +
                    "Total Deviation from Optimum,Distinct Positions Visited,End-to-End Distance,Average Move Distance," +
                    "Standard Deviation Objective,Acceptance Rate,Best Solution Improvements,Total Function Value Change," +
                    "Exploration-Exploitation Ratio\n");
            int runNumber = 1;
            for (Metrics metrics : metricsList) {
                writer.write(runNumber++ + "," + metrics.getBestValue() + "," + metrics.getBestIterationNumber() + "," +
                        metrics.getTotalAcceptedMoves() + "," + metrics.getTotalRejectedMoves() + "," +
                        metrics.getTotalDistanceTraveled() + "," + metrics.getTotalDeviationFromOptimum() + "," +
                        metrics.getDistinctPositionsCount() + "," + metrics.getEndToEndDistance() + "," +
                        metrics.getAverageMoveDistance() + "," + metrics.getStandardDeviationObjective() + "," +
                        metrics.getAcceptanceRate() + "," + metrics.getBestSolutionImprovements() + "," +
                        metrics.getTotalFunctionValueChange() + "," + metrics.getExplorationExploitationRatio() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
