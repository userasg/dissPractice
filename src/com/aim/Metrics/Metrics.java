package com.aim.Metrics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Metrics {

    private final List<int[]> visitedPoints;
    private final List<Double> objectiveValues;
    private final List<Double> functionValueChanges;
    private final List<Double> distances;
    private final List<Double> deviationsFromOptimum;
    private final List<Integer> acceptedMoves;
    private int bestIterationNumber = -1;
    private int[] bestSolution;
    private double bestValue;

    private int bestSolutionImprovements = 0;

    private double averageMoveDistance;
    private double standardDeviationObjective;
    private double acceptanceRate;
    private double totalFunctionValueChange;

    // New metrics
    private double totalDeviationFromOptimum;
    private double totalDistanceTraveled;
    private int totalAcceptedMoves;
    private int totalRejectedMoves;
    private Set<String> distinctPositions;
    private double endToEndDistance;

    public Metrics() {
        this.visitedPoints = new ArrayList<>();
        this.objectiveValues = new ArrayList<>();
        this.functionValueChanges = new ArrayList<>();
        this.distances = new ArrayList<>();
        this.deviationsFromOptimum = new ArrayList<>();
        this.acceptedMoves = new ArrayList<>();
        this.bestSolution = new int[2];
        this.bestValue = Double.POSITIVE_INFINITY;

        // Initialize new metrics
        this.totalDeviationFromOptimum = 0.0;
        this.totalDistanceTraveled = 0.0;
        this.totalAcceptedMoves = 0;
        this.totalRejectedMoves = 0;
        this.distinctPositions = new HashSet<>();
    }

    public void addVisitedPoint(int x, int y) {
        visitedPoints.add(new int[]{x, y});
        distinctPositions.add(x + "," + y); // Track distinct positions
    }

    public void addObjectiveValue(double value) {
        objectiveValues.add(value);
    }

    public void addFunctionValueChange(double change) {
        functionValueChanges.add(change);
    }

    public void addDistance(double distance) {
        distances.add(distance);
        totalDistanceTraveled += distance;
    }

    public void addDeviationFromOptimum(double deviation) {
        deviationsFromOptimum.add(deviation);
        totalDeviationFromOptimum += deviation;
    }

    public void addAcceptedMove(boolean accepted) {
        if (accepted) {
            acceptedMoves.add(1);
            totalAcceptedMoves++;
        } else {
            acceptedMoves.add(0);
            totalRejectedMoves++;
        }
    }

    public void setBestIterationNumber(int iteration) {
        bestIterationNumber = iteration;
    }

    public int getBestIterationNumber() {
        return bestIterationNumber;
    }

    public void setBestSolution(int x, int y) {
        bestSolution[0] = x;
        bestSolution[1] = y;
        calculateEndToEndDistance();
    }

    public int[] getBestSolution() {
        return bestSolution;
    }

    public void setBestValue(double value) {
        bestValue = value;
    }

    public double getBestValue() {
        return bestValue;
    }

    public List<int[]> getVisitedPoints() {
        return visitedPoints;
    }

    public List<Double> getObjectiveValues() {
        return objectiveValues;
    }

    public int getDistinctPositionsCount() {
        return distinctPositions.size();
    }

    // New metric methods
    public double getTotalDeviationFromOptimum() {
        return totalDeviationFromOptimum;
    }

    public double getTotalDistanceTraveled() {
        return totalDistanceTraveled;
    }

    public int getTotalAcceptedMoves() {
        return totalAcceptedMoves;
    }

    public int getTotalRejectedMoves() {
        return totalRejectedMoves;
    }

    public double getEndToEndDistance() {
        return endToEndDistance;
    }

    private void calculateEndToEndDistance() {
        if (!visitedPoints.isEmpty()) {
            int[] startPoint = visitedPoints.get(0);
            int[] endPoint = bestSolution;
            endToEndDistance = Math.sqrt(Math.pow(endPoint[0] - startPoint[0], 2) + Math.pow(endPoint[1] - startPoint[1], 2));
        }
    }

    // Method to calculate standard deviation of objective values
    public double getStandardDeviationObjective() {
        if (objectiveValues.isEmpty()) return 0.0;
        double mean = objectiveValues.stream().mapToDouble(val -> val).average().orElse(0.0);
        double variance = objectiveValues.stream().mapToDouble(val -> Math.pow(val - mean, 2)).average().orElse(0.0);
        return Math.sqrt(variance);
    }

    // Method to calculate acceptance rate
    public double getAcceptanceRate() {
        int totalMoves = totalAcceptedMoves + totalRejectedMoves;
        return totalMoves > 0 ? (double) totalAcceptedMoves / totalMoves : 0.0;
    }

    // Method to calculate average move distance
    public double getAverageMoveDistance() {
        return totalAcceptedMoves > 0 ? totalDistanceTraveled / totalAcceptedMoves : 0.0;
    }

    public void incrementBestSolutionImprovements() {
        bestSolutionImprovements++;
    }

    public int getBestSolutionImprovements() {
        return bestSolutionImprovements;
    }

    // Method to get total function value change
    public double getTotalFunctionValueChange() {
        return functionValueChanges.stream().mapToDouble(Math::abs).sum();
    }

    // Method to calculate exploration-exploitation ratio
    public double getExplorationExploitationRatio() {
        return getAcceptanceRate() > 0 ? getAverageMoveDistance() / getAcceptanceRate() : 0.0;
    }

    // Override toString to include new metrics
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Best Solution: (").append(bestSolution[0]).append(", ").append(bestSolution[1])
                .append(") with value: ").append(bestValue).append("\n");
        sb.append("Best Solution Iteration: ").append(bestIterationNumber).append("\n");
        sb.append("Total Accepted Moves: ").append(totalAcceptedMoves).append("\n");
        sb.append("Total Rejected Moves: ").append(totalRejectedMoves).append("\n");
        sb.append("Total Distance Traveled: ").append(totalDistanceTraveled).append("\n");
        sb.append("Total Deviation from Optimum: ").append(totalDeviationFromOptimum).append("\n");
        sb.append("Distinct Positions Visited: ").append(getDistinctPositionsCount()).append("\n");
        sb.append("End-to-End Distance: ").append(endToEndDistance).append("\n");
        sb.append("Average Move Distance: ").append(getAverageMoveDistance()).append("\n");
        sb.append("Standard Deviation of Objective Values: ").append(getStandardDeviationObjective()).append("\n");
        sb.append("Acceptance Rate: ").append(getAcceptanceRate()).append("\n");
        sb.append("Best Solution Improvements: ").append(bestSolutionImprovements).append("\n");
        sb.append("Total Function Value Change: ").append(getTotalFunctionValueChange()).append("\n");
        sb.append("Exploration-Exploitation Ratio: ").append(getExplorationExploitationRatio()).append("\n");
        return sb.toString();
    }

}
