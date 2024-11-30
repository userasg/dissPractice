package com.aim.metaheuristics;
//interface for the cooling schedules
public interface CoolingSchedule {
    double getCurrentTemperature();
    void advanceTemperature();
}
