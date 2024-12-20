package com.aim.runner;

import com.aim.metaheuristics.LundyAndMees;
import com.aim.problems.*;
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
        String problemType = "easy"; // Change to "specific" or "complex"

        if (problemType.equals("specific")) {
            problem = new SpecificProblem();
        } else if (problemType.equals("complex")) {
            problem = new ComplexProblem();
        } else if (problemType.equals("easy")) {
            problem = new EasyProblem();
        } else if (problemType.equals("egg")) {
            problem = new EggholderFunction();
        }else {
            System.out.println("Unknown problem type. Defaulting to SpecificProblem.");
            problem = new SpecificProblem();
        }

        double globalOptimum = problem.getGlobalOptimum();
        int numRuns =  500; // Number of runs for each maxIters setting
        int[] maxItersArray = {100,1000,10000}; // Different maxIters settings

        Random random = new Random();

        for (int maxIters : maxItersArray) {
            List<Metrics> allMetrics = new ArrayList<>();

            System.out.println("Running with maxIters = " + maxIters);

            for (int run = 0; run < numRuns; run++) {
                CoolingSchedule coolingSchedule = new LundyAndMees(5000.0, 0.05);

                // Generate a random starting point within the bounds [-50, 50] for both x and y
                int startX = random.nextInt(2001) - 1000;
                int startY = random.nextInt(2001) - 1000;

                SimulatedAnnealing sa = new SimulatedAnnealing(problem, coolingSchedule, maxIters, globalOptimum);
                Metrics metrics = sa.run(startX, startY);
                allMetrics.add(metrics);

                System.out.println("Run " + (run + 1) + " Metrics with maxIters = " + maxIters);
                System.out.println(metrics.toString());
                System.out.println("Exact Sequence (Compressed): " + metrics.getCompressedSequence());
                System.out.println("Objective Values Over Time: " + metrics.getObjectiveValues());
                System.out.println("Visited Points Over Time: ");

                System.out.println("\n------------------------------------------------\n");
            }

            // Export each maxIters run set to a separate CSV file
            exportMetricsToCSV(allMetrics, "metrics_results_maxIters_" + maxIters + ".csv");
        }
    }

    private static void exportMetricsToCSV(List<Metrics> metricsList, String fileName) {
        try (java.io.FileWriter writer = new java.io.FileWriter(fileName)) {
            // Include headers for both metrics and the exact sequence
            writer.write("Run,Best Value,Best Iteration,Total Accepted Moves,Total Rejected Moves,Total Distance Traveled," +
                    "Total Deviation from Optimum,Distinct Positions Visited,End-to-End Distance,Average Move Distance," +
                    "Standard Deviation Objective,Acceptance Rate,Best Solution Improvements,Total Function Value Change," +
                    "Exploration-Exploitation Ratio,Exact Sequence (Compressed)\n");

            int runNumber = 1;
            for (Metrics metrics : metricsList) {
                // Join compressed sequence symbols as a single string
                String exactSequenceCompressed = String.join("-", metrics.getCompressedSequence());

                // Write metrics and exact sequence to CSV
                writer.write(runNumber++ + "," + metrics.getBestValue() + "," + metrics.getBestIterationNumber() + "," +
                        metrics.getTotalAcceptedMoves() + "," + metrics.getTotalRejectedMoves() + "," +
                        metrics.getTotalDistanceTraveled() + "," + metrics.getTotalDeviationFromOptimum() + "," +
                        metrics.getDistinctPositionsCount() + "," + metrics.getEndToEndDistance() + "," +
                        metrics.getAverageMoveDistance() + "," + metrics.getStandardDeviationObjective() + "," +
                        metrics.getAcceptanceRate() + "," + metrics.getBestSolutionImprovements() + "," +
                        metrics.getTotalFunctionValueChange() + "," + metrics.getExplorationExploitationRatio() + "," +
                        "\"" + exactSequenceCompressed + "\"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}