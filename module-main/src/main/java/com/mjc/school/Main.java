package com.mjc.school;

import com.mjc.school.controller.application.ConsoleApplication;

class Main {
    public static void main(String[] args) {
        try {
            ConsoleApplication consoleApplication = new ConsoleApplication();
            consoleApplication.run();

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}