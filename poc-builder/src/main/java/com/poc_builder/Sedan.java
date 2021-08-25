package com.poc_builder;

import com.example.poc_interface.Car;

public class Sedan implements Car
{
    public static final Model MODEL = Model.Sedan;

    @Override
    public boolean start() {
        System.out.println("Started car model = " + MODEL);
        return false;
    }

    @Override
    public boolean stop() {
        System.out.println("Started car model = " + MODEL);
        return false;
    }
}
