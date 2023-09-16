package com.mjc.school.controller;

public class ConsoleApplication {
    ApplicationViewController viewController = ApplicationViewController.getInstance();

    public ConsoleApplication() throws Exception{}

    public void run(){
        try {
            runApplicationMainLoop();

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void runApplicationMainLoop(){
        while(viewController.doNextLoop) {
            runConsoleGUI();
        }
    }

    private void runConsoleGUI(){
        viewController.controlMenuView();
    }


}
