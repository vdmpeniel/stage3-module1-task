package com.mjc.school.controller;

import com.mjc.school.service.dto.ResponseDto;

public interface ViewInterface {
    public String renderMenuView();
    public void renderResponse(ResponseDto responseDto);
    public Long renderNewsIdInputForm();
    public void renderDeleteResponse(ResponseDto responseDto);
    public void renderOperationTittle();
    public void renderMenuDefaultOption();
    public void renderErrors(ResponseDto responseDto);

}





