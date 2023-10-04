package com.mjc.school.controller.interfaces;

import com.mjc.school.controller.dto.ResponseDto;

public interface ViewInterface {
    String renderMenuView();
    void renderResponse(ResponseDto responseDto);
    String renderNewsIdInputForm();
    void renderDeleteResponse(ResponseDto responseDto);
    void renderOperationTittle();
    void renderMenuDefaultOption();
    void renderErrors(ResponseDto responseDto);

}





