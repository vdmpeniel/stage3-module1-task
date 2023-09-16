package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDto;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

class ApplicationView {

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

    public void renderAllNews(List<NewsDto> newsDtoList){
        newsDtoList.stream().forEach(this::renderSingleNews);
    }

    public void renderSingleNews(NewsDto newsDto){
        System.out.println(newsDto);
    }

    public Long renderNewsSelectionById(){
        return Long.parseLong(getInput("Enter news id:"));
    }

    public NewsDto renderNewsCreationView(){
        return renderNewsUpdate();
    }

    public NewsDto renderNewsUpdate(){
        return NewsDto.builder()
            .title(getInput("Enter news title:"))
            .newsContent(getInput("Enter news content:"))
            .authorId(getInput("Enter author id:"))
            .build();
    }

    public void renderOperationTittle(){
        System.out.printf("Operation: %s.\n", getOperationTitle());
    }

    public void renderDefaultOption(){
        System.out.println("Command not found.");
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