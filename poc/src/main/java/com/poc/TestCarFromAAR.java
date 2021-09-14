package com.poc;

import lmn.learning.CarClient;
// import com.example.poc_interface.CarClient;

import java.util.Arrays;

public class TestCarFromAAR {
    public static void main(String[] args) {
        Test();
        System.out.println("args = ");
    }
    public static String Test() {

        CarClient client = new CarClient();
        return CarClient.testCar();
    }
}
