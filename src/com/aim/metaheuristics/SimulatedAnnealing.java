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

        int iteration = 0; // Total iteration count

        while (temperature > 0.001) {
            for (int i = 0; i < maxIters; i++) {
                iteration++;
                int oldX = currentX;
                int oldY = currentY;
                double oldScore = currentScore;

                int deltaX = random.nextInt(3) - 1;
                int deltaY = random.nextInt(3) - 1;
                currentX = problem.clamp(currentX + deltaX, -20, 20);
                currentY = problem.clamp(currentY + deltaY, -20, 20);

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

                    if (newScore < bestValue) {
                        bestValue = newScore;
                        bestX = currentX;
                        bestY = currentY;
                        metrics.setBestIterationNumber(iteration);
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
                if (currentScore == globalOptimumValue) break;
            }
            coolingSchedule.advanceTemperature();
            temperature = coolingSchedule.getCurrentTemperature();
        }

        metrics.setBestSolution(bestX, bestY);
        metrics.setBestValue(bestValue);
        return metrics;
    }
}
