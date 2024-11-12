package com.aim.metaheuristics;

public class GeometricCooling implements CoolingSchedule {

    private double currentTemperature;
    private final double alpha;

    public GeometricCooling(double initialTemperature, double alpha) {
        this.currentTemperature = initialTemperature;
        this.alpha = alpha; // Typically close to 1, e.g., 0.95
    }

    @Override
    public double getCurrentTemperature() {
        return currentTemperature;
    }

    @Override
    public void advanceTemperature() {
        currentTemperature *= alpha;
    }

    @Override
    public String toString() {
        return "Geometric Cooling (alpha=" + alpha + ")";
    }
}
