package com.mjc.school.controller.implementation;

public class ConsoleApplication {
    ApplicationViewManager viewController = ApplicationViewManager.getInstance();

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
