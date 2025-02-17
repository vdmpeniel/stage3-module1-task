package com.mjc.school.controller;

import com.mjc.school.service.implementation.ResponseDto;

public interface ViewInterface {
    public String renderMenuView();
    public void renderResponse(ResponseDto responseDto);
    public String renderNewsIdInputForm();
    public void renderDeleteResponse(ResponseDto responseDto);
    public void renderOperationTittle();
    public void renderMenuDefaultOption();
    public void renderErrors(ResponseDto responseDto);

}





