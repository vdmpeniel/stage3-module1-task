package com.mjc.school.repository.implementation;

import com.mjc.school.common.implementation.utils.FileUtils;
import com.mjc.school.common.implementation.utils.IdGeneratorUtils;
import com.mjc.school.common.implementation.utils.JsonUtils;
import com.mjc.school.common.implementation.utils.PropertyLoader;
import com.mjc.school.repository.interfaces.DataManagerInterface;
import com.mjc.school.repository.interfaces.ModelInterface;
import com.mjc.school.repository.model.AuthorModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JsonDataManager implements DataManagerInterface {
    PropertyLoader propertyLoader;
    private String authorFilePath;
    private String newsFilePath;

    public JsonDataManager(){
        try {
            propertyLoader = PropertyLoader.getInstance();
            String testVariant = isTest() ? ".test" : "";
            authorFilePath = propertyLoader.getProperty("application.news.json.file.path" + testVariant);
            newsFilePath = propertyLoader.getProperty("application.author.json.file.path" + testVariant);

        } catch(IOException ioe){
            System.out.println("Error: " + ioe.getMessage());
        }
    }

    private boolean isTest(){
        String stackTrace = Arrays.stream(Thread.currentThread().getStackTrace())
                .map(StackTraceElement::getClassName)
                .collect(Collectors.joining(",\n"));
        return stackTrace.toLowerCase().contains("test");
    }

    private String getModelFilePath(Class<? extends ModelInterface> tableType){
        return ((AuthorModel.class.isAssignableFrom(tableType))? authorFilePath : newsFilePath);
    }

    @Override
    public List<ModelInterface> load(Class<? extends ModelInterface> clazz) throws Exception{
        IdGeneratorUtils.reset(clazz);
        String json = FileUtils.readFile(getModelFilePath(clazz));
        json = json.replaceAll("/n", "");
        List<ModelInterface> data = JsonUtils.deserializeList(json, clazz)
                .stream().map(clazz::cast).collect(Collectors.toList());
        data.forEach(ModelInterface::generateId);
        return data;
    }

    @Override
    public void persist(Class<? extends ModelInterface> clazz, List<ModelInterface> modelTable) throws Exception{
        String json = JsonUtils.serialize(
                modelTable.stream().map(clazz::cast).toList()
        );
        FileUtils.writeFile(getModelFilePath(clazz), json);
    }
}
