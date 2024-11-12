package com.aim.runner;

import com.aim.problems.SpecificProblem;
import com.aim.metaheuristics.SimulatedAnnealing;
import com.aim.metaheuristics.CoolingSchedule;
import com.aim.metaheuristics.GeometricCooling;
import com.aim.Metrics.Metrics;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        SpecificProblem problem = new SpecificProblem();
        double globalOptimum = problem.getGlobalOptimum(); // Should be 0.0

        int numRuns = 30; // Number of runs (can increase as needed)
        List<Metrics> allMetrics = new ArrayList<>();

        for (int run = 0; run < numRuns; run++) {
            CoolingSchedule coolingSchedule = new GeometricCooling(100.0, 0.95);
            SimulatedAnnealing sa = new SimulatedAnnealing(problem, coolingSchedule, 100, globalOptimum);
            Metrics metrics = sa.run(0, 0);
            allMetrics.add(metrics);

            System.out.println("Run " + (run + 1) + " Metrics:");
            System.out.println(metrics.toString());
            System.out.println("Objective Values Over Time: " + metrics.getObjectiveValues());
            System.out.println("Visited Points Over Time: ");
            for (int[] point : metrics.getVisitedPoints()) {
                System.out.print("(" + point[0] + ", " + point[1] + ") ");
            }
            System.out.println("\n------------------------------------------------\n");
        }

        exportMetricsToCSV(allMetrics, "metrics_results.csv");
    }

    private static void exportMetricsToCSV(List<Metrics> metricsList, String fileName) {
        try (java.io.FileWriter writer = new java.io.FileWriter(fileName)) {
            writer.write("Run,Best Value,Best Iteration,Total Accepted Moves,Total Rejected Moves,Total Distance Traveled," +
                    "Total Deviation from Optimum,Distinct Positions Visited,End-to-End Distance,Average Move Distance," +
                    "Standard Deviation Objective,Acceptance Rate,Best Solution Improvements,Total Function Value Change," +
                    "Exploration-Exploitation Ratio\n");
            int runNumber=1;
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
