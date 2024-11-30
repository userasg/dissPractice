package com.aim.metaheuristics;

public class LundyAndMees implements CoolingSchedule {

    private double currentTemperature;
    private final double beta;
    //constructor for the lundy and mees cooling schedule
    public LundyAndMees(double initialTemperature, double beta) {
        this.currentTemperature = initialTemperature;
        this.beta = beta;
    }
    //gets the current temperature
    @Override
    public double getCurrentTemperature() {
        return currentTemperature;
    }
    //this bit actually does the lundy mees calculation
    @Override
    public void advanceTemperature() {
        currentTemperature = currentTemperature / (1 + beta * currentTemperature);
    }
    //to string for printing
    @Override
    public String toString() {
        return "Lundy and Mees Cooling (beta=" + beta + ")";
    }
}
