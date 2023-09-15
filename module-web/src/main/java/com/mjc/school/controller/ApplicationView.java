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
            "5 - Remove news by id. \n" +
            "0 - Exit"
        );
        return getInput();
    }

    public void renderAllNews(List<NewsDto> newsDtoList){
        newsDtoList.stream().forEach(this::renderSingleNews);
    }

    public void renderSingleNews(NewsDto newsDto){
        System.out.println(newsDto);
    }

    public String renderNewsSelectionById(){
        System.out.println("Enter news id:");
        return getInput();
    }

    public NewsDto renderNewsCreationView(){
        NewsDto newsDto = new NewsDto();

        System.out.println("Enter news title:");
        newsDto.setTitle(getInput());

        System.out.println("Enter news content:");
        newsDto.setNewsContent(getInput());

        System.out.println("Enter news content:");
        newsDto.setAuthorId(getInput());
        return newsDto;
    }

    public void renderOperationTittle(){
        System.out.printf("Operation: %s.\n", getOperationTitle());
    }

    public void renderDefaultOption(){
        System.out.println("Command not found.");
    }

    private String getInput(){
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