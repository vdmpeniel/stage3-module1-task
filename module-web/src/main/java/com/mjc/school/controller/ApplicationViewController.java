package com.mjc.school.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApplicationViewController {
    private volatile static ApplicationViewController instance;

    private ApplicationViewController(){}

    public static ApplicationViewController getInstance(){
        synchronized (ApplicationViewController.class) {
            if (Objects.isNull(instance)) {
                instance = new ApplicationViewController();
            }
            return instance;
        }
    }

    boolean doNextLoop = true;

    ApplicationView view = new ApplicationView();

    public void controlMenuView(){
        int index = convertInputToIntegerIndex(view.renderMenuView()) + 1;
        List<Runnable> operations = new ArrayList<>() {{
            add((Runnable) () -> defaultBehavior());
            add((Runnable) () -> exit());
            add((Runnable) () -> getAllNews());
            add((Runnable) () -> getNewsById());
            add((Runnable) () -> createNews());
            add((Runnable) () -> updateNews());
            add((Runnable) () -> removeNewsById());
        }};
        operations.get(index).run();
    }
    private int convertInputToIntegerIndex(String input){
        try {
            int index = Integer.parseInt(input);
            if(index < 0 || index > 5) { throw new Exception("Input out of bounds"); }
            return index;

        } catch(Exception e){
            return -1;
        }
    }
    private void exit(){
        doNextLoop = false;
    }

    public void getAllNews(){
        view.renderOperationTittle();
        // call getAllNews Service
        // renderAllNewsView
    }
    private void getNewsById(){
        view.renderOperationTittle();
        // renderNewsSelectionById
        // call getNewsById Service
        // renderSingleNewsView
    }
    private void createNews(){
        view.renderOperationTittle();
        // renderNewsCreationView
        // call createNew Service
    }
    private void updateNews(){
        view.renderOperationTittle();
        // renderNewsUpdate
        // call updateNews Service
    }
    private void removeNewsById(){
        view.renderOperationTittle();
        // renderNewsRemovalById
        // call removeNewsById Service
    }
    private void defaultBehavior(){
        view.renderDefaultOption();
    }
}

