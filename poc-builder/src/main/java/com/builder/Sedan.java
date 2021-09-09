package com.builder;

//import com.example.poc_interface.Car;
import com.example.poc_interface.Car;
import com.example.poc_interface.Engine;
import com.example.poc_interface.Vehicle;

public class Sedan implements Vehicle, Car
{
    public static final Model MODEL = Model.Sedan;
    public static final double ENGINE_CC = 1099.00;

    @Override
    public String start() {
        return "Sedan started";
    }

    @Override
    public String stop() {
        return  "Sedan stopped";
    }

    @Override
    public double setEngineCC() {
        System.out.println("Started car model = " + MODEL);
        System.out.println("Car Engine CC set to 1199");
        return 1099;
    }

    @Override
    public double getEngineCC() {
        return ENGINE_CC;
    }

    @Override
    public boolean setEngine(Engine engine) {
        System.out.println("engine = " + engine.getEngineCC());
        System.out.println("standarf = " + engine.Standard);
        return true;
    }
}
