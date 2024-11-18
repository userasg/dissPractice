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

        int iteration = 0;

        while (temperature > 0.001 && iteration < maxIters) {
            for (int i = 0; i < maxIters && iteration < maxIters; i++) {
                iteration++;
                int oldX = currentX;
                int oldY = currentY;
                double oldScore = currentScore;

                int deltaX = random.nextInt(3) - 1;
                int deltaY = random.nextInt(3) - 1;
                currentX = problem.clamp(currentX + deltaX, -50, 50);
                currentY = problem.clamp(currentY + deltaY, -50, 50);

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

                    // Update the best value and iteration if a better solution is found
                    if (newScore < bestValue) {
                        bestValue = newScore;
                        bestX = currentX;
                        bestY = currentY;
                        metrics.setBestIterationNumber(iteration); // Update the iteration here
                        metrics.incrementBestSolutionImprovements();
                    }
                } else {
                    metrics.addFunctionValueChange(0.0);
                    metrics.addDistance(0.0);
                    metrics.addDeviationFromOptimum(Math.abs(currentScore - globalOptimumValue));
                    metrics.addAcceptedMove(false);
                    currentX = oldX;
                    currentY = oldY;
                }

                metrics.addObjectiveValue(currentScore);
            }

            coolingSchedule.advanceTemperature();
            temperature = coolingSchedule.getCurrentTemperature();
        }

        metrics.setBestSolution(bestX, bestY);
        metrics.setBestValue(bestValue);

        // Ensure Best Iteration is set to the final best iteration found
        if (metrics.getBestIterationNumber() == -1) {
            metrics.setBestIterationNumber(0); // Default to 0th iteration if no improvement
        }

        return metrics;
    }
}
