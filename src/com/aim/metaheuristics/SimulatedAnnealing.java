package com.aim.metaheuristics;

import com.aim.problems.GeneralProblem;
import com.aim.Metrics.Metrics;

import java.util.Random;

public class SimulatedAnnealing {

    private final GeneralProblem problem;
    private final CoolingSchedule coolingSchedule;
    private final int maxIters;
    private final Random random;
    private final double globalOptimumValue;

    public SimulatedAnnealing(GeneralProblem problem, CoolingSchedule coolingSchedule, int maxIters, double globalOptimumValue) {
        this.problem = problem;
        this.coolingSchedule = coolingSchedule;
        this.maxIters = maxIters;
        this.globalOptimumValue = globalOptimumValue;
        this.random = new Random();
    }

    public Metrics run(int startX, int startY) {
        double temperature = coolingSchedule.getCurrentTemperature();
        int currentX = startX;
        int currentY = startY;
        double currentScore = problem.evaluate(currentX, currentY);
        Metrics metrics = new Metrics();

        int bestX = currentX;
        int bestY = currentY;
        double bestValue = currentScore;

        metrics.addVisitedPoint(currentX, currentY);
        metrics.addObjectiveValue(currentScore);
        metrics.addFunctionValueChange(0.0);
        metrics.addDistance(0.0);
        metrics.addDeviationFromOptimum(Math.abs(currentScore - globalOptimumValue));
        metrics.addAcceptedMove(true);

        // Add the initial exact sequence symbol
        String positionType = determinePositionType(currentX, currentY, currentScore);
        metrics.addExactSequenceSymbol(currentX, currentY, Math.abs(currentScore - globalOptimumValue), positionType);

        int iteration = 0;

        while (temperature > 0.001 && iteration < maxIters) {
            for (int i = 0; i < maxIters && iteration < maxIters; i++) {
                iteration++;
                int oldX = currentX;
                int oldY = currentY;
                double oldScore = currentScore;

                // Generate new neighbour
                int deltaX = random.nextInt(3) - 1;
                int deltaY = random.nextInt(3) - 1;
                currentX = problem.clamp(currentX + deltaX, -1000, 1000);
                currentY = problem.clamp(currentY + deltaY, -1000, 1000);

                double newScore = problem.evaluate(currentX, currentY);
                double delta = newScore - oldScore;
                double distance = Math.sqrt(Math.pow(currentX - oldX, 2) + Math.pow(currentY - oldY, 2));

                boolean accepted = delta < 0 || Math.exp(-delta / temperature) > random.nextDouble();
                if (accepted) {
                    currentScore = newScore;
                    metrics.addVisitedPoint(currentX, currentY);
                    metrics.addFunctionValueChange(delta);
                    metrics.addDistance(distance);
                    metrics.addDeviationFromOptimum(Math.abs(currentScore - globalOptimumValue));
                    metrics.addAcceptedMove(true);
                    metrics.addObjectiveValue(currentScore); // Record objective value here

                    // Update the best value and iteration if a better solution is found
                    if (newScore < bestValue) {
                        bestValue = newScore;
                        bestX = currentX;
                        bestY = currentY;
                        metrics.setBestIterationNumber(iteration); // Update the iteration here
                        metrics.incrementBestSolutionImprovements();
                    }

                    // Add the exact sequence symbol
                    positionType = determinePositionType(currentX, currentY, currentScore);
                    metrics.addExactSequenceSymbol(currentX, currentY, Math.abs(currentScore - globalOptimumValue), positionType);

                } else {
                    // Revert to old solution
                    currentX = oldX;
                    currentY = oldY;
                    metrics.addFunctionValueChange(0.0);
                    metrics.addDistance(0.0);
                    metrics.addDeviationFromOptimum(Math.abs(oldScore - globalOptimumValue));
                    metrics.addAcceptedMove(false);
                    metrics.addObjectiveValue(currentScore); // Record objective value for rejected move

                    // Add the exact sequence symbol for rejection
                    positionType = determinePositionType(currentX, currentY, oldScore);
                    metrics.addExactSequenceSymbol(currentX, currentY, Math.abs(oldScore - globalOptimumValue), positionType);
                }
            }

            // Handle temperature reduction
            coolingSchedule.advanceTemperature();
            temperature = coolingSchedule.getCurrentTemperature();

//            // Detect random restart and add jump symbol--but SA doenst necessarily need this
//            if (temperature < 0.1 && iteration < maxIters) {
//                metrics.addJumpSymbol();
//                currentX = random.nextInt(101) - 50; // Random new start point
//                currentY = random.nextInt(101) - 50;
//                currentScore = problem.evaluate(currentX, currentY);
//                metrics.addVisitedPoint(currentX, currentY);
//                metrics.addObjectiveValue(currentScore);
//
//                // Add exact sequence symbol after restart
//                positionType = determinePositionType(currentX, currentY, currentScore);
//                metrics.addExactSequenceSymbol(currentX, currentY, Math.abs(currentScore - globalOptimumValue), positionType);
//            }
        }

        // Finalize metrics
        metrics.setBestSolution(bestX, bestY);
        metrics.setBestValue(bestValue);

        // Compress exact sequence
        metrics.compressExactSequence();

        // Ensure Best Iteration is set to the final best iteration found
        if (metrics.getBestIterationNumber() == -1) {
            metrics.setBestIterationNumber(0); // Default to 0th iteration if no improvement
        }
        System.out.println("Current Objective Value: " + currentScore);
        System.out.println("Objectiuve Values List: " + metrics.getObjectiveValues());
        return metrics;
    }

    private String determinePositionType(int x, int y, double score) {
        int better = 0, equal = 0, worse = 0;

        // Iterate over neighbors
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                int nx = problem.clamp(x + dx, -1000, 1000);
                int ny = problem.clamp(y + dy, -1000, 1000);
                double neighbourScore = problem.evaluate(nx, ny);

                if (neighbourScore < score) better++;
                else if (neighbourScore == score) equal++;
                else worse++;
            }
        }

        // Convert to binary encoding
        String binaryPosition = String.format("%d%d%d", (better > 0 ? 1 : 0), (equal > 0 ? 1 : 0), (worse > 0 ? 1 : 0));

        // Map binary encoding to position type
        switch (binaryPosition) {
            case "100":
                return "S"; // Strict Local Minimum
            case "110":
                return "M"; // Local Minimum
            case "010":
                return "I"; // Plateau
            case "101":
                return "P"; // Slope
            case "111":
                return "L"; // Ledge
            case "001":
                return "X"; // Local Maximum
            case "011":
                return "A"; // Strict Local Maximum
            default:
                return "U"; // Unknown
        }
    }
}