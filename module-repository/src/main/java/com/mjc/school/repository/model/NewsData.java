package com.mjc.school.repository.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewsData {
    private static NewsData instance;
    private List<NewsModel> data;

    private NewsData(){
        init();
    }
    private void init(){
        data = new ArrayList<>();
    }
    public static NewsData getInstance(){
        synchronized (NewsData.class){
            if(Objects.isNull(instance)){
                instance = new NewsData();
            }
            return instance;
        }
    }
    public List<NewsModel> getData(){
        return data;
    }
}
