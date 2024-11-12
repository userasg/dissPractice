package com.aim.metaheuristics;

public class LundyAndMees implements CoolingSchedule {

    private double currentTemperature;
    private final double beta;

    public LundyAndMees(double initialTemperature, double beta) {
        this.currentTemperature = initialTemperature;
        this.beta = beta;
    }

    @Override
    public double getCurrentTemperature() {
        return currentTemperature;
    }

    @Override
    public void advanceTemperature() {
        currentTemperature = currentTemperature / (1 + beta * currentTemperature);
    }

    @Override
    public String toString() {
        return "Lundy and Mees Cooling (beta=" + beta + ")";
    }
}
