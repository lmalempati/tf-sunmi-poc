package com.example.poc_interface;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class CarClient {
    private static final String PATH = "D:\\Android\\poc-builder\\build\\libs\\poc-builder.jar";
    private static final String CLASS = "com.poc_builder.";
    public static Car getCar(String name) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        File jarFile = new File(PATH);
        URLClassLoader cl = new URLClassLoader(new URL[]{jarFile.toURI().toURL()});
        Class carClass = Class.forName(CLASS + name, true, cl);
        return (Car) carClass.getDeclaredConstructor().newInstance();
    }

    public static void main(String[] args) {

        try {
            Car Sedan = getCar("Sedan");
            Sedan.start();
            Sedan.stop();

            Car SUV = getCar("SUV");
            SUV.start();
            SUV.stop();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
