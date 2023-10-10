package com.mjc.school.controller.interfaces;

import com.mjc.school.controller.dto.ResponseDto;

public interface ViewManagerInterface {
    void controlMenuView();
    void getAllNews();
    void exit();
    void getNewsById();
    void createNews();
    void updateNews();
    void removeNewsById();
    void defaultBehavior();

    ResponseDto buildResponse(Object responseObject);
    ResponseDto buildErrorResponse(Exception e);
}
