package com.aim.metaheuristics;

import com.aim.problems.GeneralProblem;
import com.aim.Metrics.Metrics;
import java.util.Random;

public class SimulatedAnnealing {

    private final GeneralProblem problem;
    private final double initialTemperature;
    private final double finalTemperature;
    private final double alpha;
    private final int maxIters;
    private final Random random;

    public SimulatedAnnealing(GeneralProblem problem, double initialTemperature, double finalTemperature, double alpha, int maxIters) {
        this.problem = problem;
        this.initialTemperature = initialTemperature;
        this.finalTemperature = finalTemperature;
        this.alpha = alpha;
        this.maxIters = maxIters;
        this.random = new Random();
    }

    public Metrics run(int startX, int startY) {
        double temperature = initialTemperature;
        int currentX = startX;
        int currentY = startY;
        double currentScore = problem.evaluate(currentX, currentY);
        Metrics metrics = new Metrics();

        int bestX = currentX;
        int bestY = currentY;
        double bestValue = currentScore;

        // Initial metrics recording
        metrics.addVisitedPoint(currentX, currentY);
        metrics.addObjectiveValue(currentScore);
        metrics.addFunctionValueChange(0.0);
        metrics.addDistance(0.0);
        metrics.addAcceptedMove(true); // Initial move is accepted

        while (temperature > finalTemperature) {
            for (int i = 0; i < maxIters; i++) {
                int oldX = currentX;
                int oldY = currentY;
                double oldScore = currentScore;

                // Random neighboring move
                int deltaX = random.nextInt(3) - 1;
                int deltaY = random.nextInt(3) - 1;
                currentX = problem.clamp(currentX + deltaX, -5, 5);
                currentY = problem.clamp(currentY + deltaY, -5, 5);

                double newScore = problem.evaluate(currentX, currentY);
                double delta = newScore - oldScore;
                double distance = Math.sqrt(Math.pow(currentX - oldX, 2) + Math.pow(currentY - oldY, 2));

                // Acceptance criteria
                boolean accepted = delta < 0 || Math.exp(-delta / temperature) > random.nextDouble();
                if (accepted) {
                    currentScore = newScore;
                    metrics.addVisitedPoint(currentX, currentY);
                    metrics.addFunctionValueChange(delta);
                    metrics.addDistance(distance);
                    metrics.addAcceptedMove(true);

                    if (newScore < bestValue) {
                        bestValue = newScore;
                        bestX = currentX;
                        bestY = currentY;
                    }
                } else {
                    metrics.addFunctionValueChange(0.0); // No change if not accepted
                    metrics.addDistance(0.0);            // No movement if not accepted
                    metrics.addAcceptedMove(false);       // Move not accepted
                    currentX = oldX;
                    currentY = oldY;
                }

                metrics.addObjectiveValue(currentScore);
                temperature *= alpha;

                if (temperature < finalTemperature) break;
            }
        }

        System.out.println("Best solution found at (" + bestX + ", " + bestY + ") with value: " + bestValue);
        System.out.println("Metrics: " + metrics);
        return metrics;
    }
}
