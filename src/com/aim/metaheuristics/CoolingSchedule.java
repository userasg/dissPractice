package com.aim.metaheuristics;

public interface CoolingSchedule {
    double getCurrentTemperature();
    void advanceTemperature();
}
