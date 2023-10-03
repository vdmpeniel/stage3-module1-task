package com.mjc.school.repository.factory;

import com.mjc.school.repository.implementation.JsonDataManager;
import com.mjc.school.repository.implementation.ListDataManager;
import com.mjc.school.repository.interfaces.DataManagerInterface;

import java.util.Objects;

public class DataManagerFactory {
    private static volatile DataManagerFactory instance;
    private volatile JsonDataManager jsonDataManager;
    private volatile ListDataManager listDataManager;

    private DataManagerFactory(){
        jsonDataManager = new JsonDataManager();
        listDataManager = new ListDataManager();
    }

    public static DataManagerFactory getInstance(){
        synchronized (DataManagerFactory.class){
            if(Objects.isNull(instance)){
                instance = new DataManagerFactory();
            }
            return instance;
        }
    }
    public DataManagerInterface getJsonDataManager(){
        return jsonDataManager;
    }
    public DataManagerInterface getListDataManager(){
        return listDataManager;
    }
}
