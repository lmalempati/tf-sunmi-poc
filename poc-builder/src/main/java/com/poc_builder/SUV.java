package com.poc_builder;

import com.example.poc_interface.Car;

public class SUV implements Car {
    private static final Model MODEL = Model.SUV;

    @Override
    public boolean start() {
        System.out.println("Started car model = " + MODEL);
        System.out.println();
        return false;
    }

    @Override
    public boolean stop() {
        return false;
    }
}