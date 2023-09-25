package com.mjc.school.controller.implementation;

import com.mjc.school.controller.ViewControllerInterface;
import com.mjc.school.service.implementation.NewsService;
import com.mjc.school.service.implementation.NewsDto;
import com.mjc.school.service.implementation.RequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ApplicationViewController implements ViewControllerInterface {
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
    public void getAllNews(){
        view.renderOperationTittle();
        view.renderResponse(newsService.getAll());
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

    public void exit(){
        doNextLoop = false;
    }



    public void getNewsById(){
        view.renderOperationTittle();
        view.renderResponse(newsService.getById(
            RequestDto.builder().lookupId(
                    view.renderNewsIdInputForm()
            ).build())
        );
    }


    private final Function<String, NewsDto> newsDtoWithIdSupplier = (id) -> {
        NewsDto newsDto = view.renderNewsInputForm();
        newsDto.setId(id);
        return newsDto;
    };
    public void createNews(){
        view.renderOperationTittle();
        view.renderResponse(newsService.create(
            RequestDto.builder().inputData(view.renderNewsInputForm()).build()
        ));
    }

    public void updateNews(){
        view.renderOperationTittle();
        view.renderResponse(
            newsService.updateById(
                RequestDto.builder()
                    .lookupId(view.renderNewsIdInputForm())
                    .inputData(newsDtoWithIdSupplier.apply("-1"))
                    .build()
            )
        );
    }

    public void removeNewsById(){
        view.renderOperationTittle();
        view.renderDeleteResponse(newsService.removeById(
            RequestDto.builder().lookupId(view.renderNewsIdInputForm()).build()
        ));
    }

    public void defaultBehavior(){
        view.renderMenuDefaultOption();
    }
}

