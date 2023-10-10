package com.mjc.school.controller.implementation;

import com.mjc.school.controller.interfaces.ViewControllerInterface;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.controller.dto.RequestDto;
import com.mjc.school.controller.dto.ResponseDto;
import com.mjc.school.controller.factory.ModelControllerFactory;
import com.mjc.school.controller.interfaces.ModelControllerInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ApplicationViewManager implements ViewControllerInterface {
    private volatile static ApplicationViewManager instance;

    boolean doNextLoop = true;

    private final ApplicationView view = ApplicationView.getInstance();
    private final ModelControllerInterface<RequestDto, ResponseDto> newsService =
            ModelControllerFactory.getInstance().getNewsService();

    private ApplicationViewManager(){}

    public static ApplicationViewManager getInstance(){
        synchronized (ApplicationViewManager.class) {
            if (Objects.isNull(instance)) {
                instance = new ApplicationViewManager();
            }
            return instance;
        }
    }


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

    public void exit(){
        doNextLoop = false;
    }

    public void getAllNews(){
        view.renderOperationTittle();
        view.renderResponse(newsService.readAll());
    }

    public void getNewsById(){
        view.renderOperationTittle();
        view.renderResponse(newsService.readById(
            RequestDto.builder().lookupId(
                    view.renderNewsIdInputForm()
            ).build())
        );
    }


    private final Function<String, NewsDto> newsDtoWithIdSupplier = (id) -> {
        NewsDto newsDto = view.renderNewsInputForm();
        newsDto.setId(Long.parseLong(id));
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
        view.renderDeleteResponse(newsService.deleteById(
            RequestDto.builder().lookupId(view.renderNewsIdInputForm()).build()
        ));
    }

    public void defaultBehavior(){
        view.renderMenuDefaultOption();
    }
}

