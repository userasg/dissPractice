package com.aim.metaheuristics;
//geometric cooling schedule overrides the interface
public class GeometricCooling implements CoolingSchedule {

    private double currentTemperature;
    private final double alpha;
    //constructor to set the initial temperature and alpha values
    public GeometricCooling(double initialTemperature, double alpha) {
        this.currentTemperature = initialTemperature;
        this.alpha = alpha; // Typically close to 1, e.g., 0.95
    }
    //gets the current temperature
    @Override
    public double getCurrentTemperature() {
        return currentTemperature;
    }
    //this bit actually changes the temperature based on alpha
    @Override
    public void advanceTemperature() {
        currentTemperature *= alpha;
    }
    //to string when printing
    @Override
    public String toString() {
        return "Geometric Cooling (alpha=" + alpha + ")";
    }
}
