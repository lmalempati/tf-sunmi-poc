package com.builder;

import lmn.learning.Car;
import lmn.learning.Engine;
import lmn.learning.Vehicle;

public class SUV implements Vehicle, Car {
    private static final Model MODEL = Model.SUV;
    public static final double ENGINE_CC = 1199.00;

    @Override
    public String start() {
        return "sUV started";
    }

    @Override
    public String stop() {
        return  "SUV stopped";
    }

    @Override
    public double setEngineCC() {
        System.out.println("Started car model = " + MODEL);
        System.out.println("Car Engine CC set to 1199");
        return 1199;
    }

    @Override
    public double getEngineCC() {
        return ENGINE_CC;
    }

    @Override
    public boolean setEngine(Engine engine) {
        System.out.println("engine = " + engine.getEngineCC());
        System.out.println("standard = " + engine.Standard);
        return true;
    }


}