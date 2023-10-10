package com.mjc.school.controller.application;

import com.mjc.school.common.exceptions.IllegalFieldValueException;
import com.mjc.school.controller.dto.ErrorDto;
import com.mjc.school.controller.dto.RequestDto;
import com.mjc.school.controller.dto.ResponseDto;
import com.mjc.school.controller.factory.ModelControllerFactory;
import com.mjc.school.controller.interfaces.ModelControllerInterface;
import com.mjc.school.controller.interfaces.ViewManagerInterface;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.interfaces.ModelDtoInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ApplicationViewManager implements ViewManagerInterface {
    private volatile static ApplicationViewManager instance;

    boolean doNextLoop = true;

    private final ApplicationView view = ApplicationView.getInstance();
    private final ModelControllerInterface<RequestDto, NewsDto> newsController;

    private ApplicationViewManager(){
        newsController = ModelControllerFactory.getInstance().getNewsController();
    }

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
        try {
            view.renderOperationTittle();
            view.renderResponse(buildResponse(newsController.readAll()));

        } catch (Exception e){
            view.renderResponse(buildErrorResponse(e));
        }
    }

    public void getNewsById(){
        view.renderOperationTittle();
        try{
            view.renderResponse(buildResponse(
                newsController.readById(
                        Long.parseLong(view.renderNewsIdInputForm())
                    )
                )
            );
        } catch (Exception e){
            view.renderResponse(buildErrorResponse(e));
        }
    }


    private final Function<String, NewsDto> newsDtoWithIdSupplier = (id) -> {
        NewsDto newsDto = view.renderNewsInputForm();
        newsDto.setId(Long.parseLong(id));
        return newsDto;
    };
    public void createNews(){
        view.renderOperationTittle();
        try{
            view.renderResponse(buildResponse(
                    newsController.create(
                        RequestDto.builder().inputData(view.renderNewsInputForm()).build()
                    )
                )
            );
        } catch (Exception e){
            view.renderResponse(buildErrorResponse(e));
        }
    }

    public void updateNews(){
        view.renderOperationTittle();
        try{
            view.renderResponse(buildResponse(
                    newsController.updateById(
                        RequestDto.builder()
                            .lookupId(view.renderNewsIdInputForm())
                            .inputData(newsDtoWithIdSupplier.apply("-1"))
                            .build()
                    )
                )
            );
        } catch (Exception e){
            view.renderResponse(buildErrorResponse(e));
        }
    }

    public void removeNewsById(){
        view.renderOperationTittle();
        try{
            view.renderDeleteResponse(buildResponse(
                    newsController.deleteById(
                        Long.parseLong(view.renderNewsIdInputForm())
                    )
                )
            );

        } catch (Exception e){
            view.renderResponse(buildErrorResponse(e));
        }
    }

    public void defaultBehavior(){
        view.renderMenuDefaultOption();
    }

    public ResponseDto buildResponse(Object responseObject){
        return ResponseDto.builder()
                .resultSet(
                        (responseObject instanceof List) ?
                                (List<ModelDtoInterface>) responseObject
                                : List.of((ModelDtoInterface) responseObject)
                )
                .status("OK")
                .build();
    }

    public ResponseDto buildErrorResponse(Exception e) {
        if (e instanceof IllegalFieldValueException ifve) {
            return ResponseDto.builder()
                    .status("Failed")
                    .error(ErrorDto.builder().code(ifve.getErrorCode()).message(ifve.getMessage()).build())
                    .build();

        } else {
            return ResponseDto.builder()
                    .status("Failed")
                    .error(ErrorDto.builder().code("0000123").message(e.getMessage()).build())
                    .build();
        }
    }
}

