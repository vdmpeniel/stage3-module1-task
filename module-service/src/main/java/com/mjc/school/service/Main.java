package com.mjc.school.service;


import com.mjc.school.common.utils.PropertyLoader;

class Main{
    public static void main(String[]args){
        System.out.println("Hello Service Module!");
        try{
            PropertyLoader propertyLoader = PropertyLoader.getInstance();
            System.out.println("application.just.a.test: " + propertyLoader.getProperty("application.just.a.test"));

        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }

    }
}