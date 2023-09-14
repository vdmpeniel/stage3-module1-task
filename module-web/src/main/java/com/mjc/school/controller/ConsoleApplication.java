package com.mjc.school.controller;

public class ConsoleApplication {


    ApplicationViewController viewController = ApplicationViewController.getInstance();
    public void run(){

        runApplicationMainLoop();
    }

    private void runApplicationMainLoop(){
        while(viewController.doNextLoop) {
            runConsoleGUI();
        }
    }

    private void runConsoleGUI(){
        viewController.controlMenuView();
    }

    public static void main(String[] args) {
        ConsoleApplication consoleApplication = new ConsoleApplication();
        consoleApplication.runApplicationMainLoop();
    }
}
