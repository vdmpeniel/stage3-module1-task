package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.ResponseDto;

import java.util.Objects;
import java.util.Scanner;

class ApplicationView implements ViewInterface{

    private volatile static ApplicationView instance;

    public static ApplicationView getInstance(){
        synchronized (ApplicationView.class) {
            if (Objects.isNull(instance)) {
                instance = new ApplicationView();
            }
            return instance;
        }
    }

    public String renderMenuView() {
        System.out.println(
            "Enter the number of operation: \n" +
            "1 - Get all news. \n" +
            "2 - Get news by id. \n" +
            "3 - Create news. \n" +
            "4 - Update news. \n" +
            "5 - Remove news by id."
        );
        return getInput("0 - Exit");
    }

    public void renderResponse(ResponseDto responseDto){
        if("OK".equals(responseDto.getStatus()) && Objects.nonNull(responseDto.getResultSet())) {
            responseDto.getResultSet().stream().map(model -> (NewsDto) model).forEach(this::renderSingleNews);

        } else {
            renderErrors(responseDto);
        }
    }

    private void renderSingleNews(NewsDto newsDto){
        System.out.println(newsDto);
    }

    public String renderNewsIdInputForm(){
        return getInput("Enter news id:");
    }

    public NewsDto renderNewsInputForm(){
        return NewsDto.builder()
            .title(getInput("Enter news title:"))
            .newsContent(getInput("Enter news content:"))
            .authorId(getInput("Enter author id:"))
            .build();
    }
    public void renderDeleteResponse(ResponseDto responseDto){
        System.out.println("OK".equals(responseDto.getStatus())? "True": responseDto.getError());
    }

    public void renderOperationTittle(){
        System.out.printf("Operation: %s.\n", getOperationTitle());
    }

    public void renderMenuDefaultOption(){
        System.out.println("Command not found.");
    }

    public void renderErrors(ResponseDto responseDto){
        System.out.println(responseDto.getError());
    }

    private String getInput(String message){
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private String getOperationTitle(){
        String operationTitle = Thread.currentThread().getStackTrace()[3].getMethodName()
            .replaceAll("(\\p{javaLowerCase})(\\p{javaUpperCase})", "$1 $2")
            .toLowerCase();
        return operationTitle.substring(0, 1).toUpperCase() + operationTitle.substring(1);
    }
}