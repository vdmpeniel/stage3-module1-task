package com.mjc.school.controller;

import com.mjc.school.service.NewsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApplicationViewController {
    private volatile static ApplicationViewController instance;

    private ApplicationViewController() throws Exception{}

    public static ApplicationViewController getInstance() throws Exception{
        synchronized (ApplicationViewController.class) {
            if (Objects.isNull(instance)) {
                instance = new ApplicationViewController();
            }
            return instance;
        }
    }

    boolean doNextLoop = true;

    private ApplicationView view = new ApplicationView();
    private NewsService newsService = new NewsService();

    public void controlMenuView(){
        int index = validateIndexInput(view.renderMenuView()) + 1;
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

    private int validateIndexInput(String input){
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
        view.renderAllNews(newsService.getAllNews());

    }

    private void getNewsById(){
        view.renderOperationTittle();
        view.renderSingleNews(newsService.getNewsById(view.renderNewsSelectionById()));

    }

    private void createNews(){
        view.renderOperationTittle();
        newsService.createNews(view.renderNewsCreationView());
    }

    private void updateNews(){
        view.renderOperationTittle();
        view.renderSingleNews(
                newsService.updateNewsById(
                        view.renderNewsSelectionById(),
                        view.renderNewsUpdate()
                )
        );
    }

    private void removeNewsById(){
        view.renderOperationTittle();
        newsService.removeNewsById(
                view.renderNewsSelectionById()
        );

    }

    private void defaultBehavior(){
        view.renderDefaultOption();
    }
}

