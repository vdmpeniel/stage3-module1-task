package com.mjc.school;

import com.mjc.school.common.utils.PropertyLoader;

public class Main {
    public static void main(String[] args) {

        System.out.printf("Hello and welcome!");
        try{
            PropertyLoader propertyLoader = PropertyLoader.getInstance();
            System.out.println("application.just.a.test: " + propertyLoader.getProperty("application.just.a.test"));

        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }


    }
}