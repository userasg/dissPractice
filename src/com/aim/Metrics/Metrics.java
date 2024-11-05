package com.aim.Metrics;

import java.util.ArrayList;
import java.util.List;

public class Metrics {

    private final List<int[]> visitedPoints;
    private final List<Double> objectiveValues;
    private final List<Double> functionValueChanges;
    private final List<Double> distances;
    private final List<Integer> acceptedMoves;

    public Metrics() {
        this.visitedPoints = new ArrayList<>();
        this.objectiveValues = new ArrayList<>();
        this.functionValueChanges = new ArrayList<>();
        this.distances = new ArrayList<>();
        this.acceptedMoves = new ArrayList<>();
    }

    public void addVisitedPoint(int x, int y) {
        visitedPoints.add(new int[]{x, y});
    }

    public void addObjectiveValue(double value) {
        objectiveValues.add(value);
    }

    public void addFunctionValueChange(double change) {
        functionValueChanges.add(change);
    }

    public void addDistance(double distance) {
        distances.add(distance);
    }

    public void addAcceptedMove(boolean accepted) {
        acceptedMoves.add(accepted ? 1 : 0);
    }

    public List<int[]> getVisitedPoints() {
        return visitedPoints;
    }

    public List<Double> getObjectiveValues() {
        return objectiveValues;
    }

    public List<Double> getFunctionValueChanges() {
        return functionValueChanges;
    }

    public List<Double> getDistances() {
        return distances;
    }

    public List<Integer> getAcceptedMoves() {
        return acceptedMoves;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Metrics:\n");

        sb.append("Visited Points: ");
        for (int[] point : visitedPoints) {
            sb.append("(").append(point[0]).append(", ").append(point[1]).append(") ");
        }
        sb.append("\n");

        sb.append("Objective Values: ");
        for (Double value : objectiveValues) {
            sb.append(value).append(" ");
        }
        sb.append("\n");

        sb.append("Function Value Changes: ");
        for (Double change : functionValueChanges) {
            sb.append(change).append(" ");
        }
        sb.append("\n");

        sb.append("Distances: ");
        for (Double distance : distances) {
            sb.append(distance).append(" ");
        }
        sb.append("\n");

        sb.append("Accepted Moves (1 = Accepted, 0 = Rejected): ");
        for (Integer move : acceptedMoves) {
            sb.append(move).append(" ");
        }
        sb.append("\n");

        return sb.toString();
    }
}
