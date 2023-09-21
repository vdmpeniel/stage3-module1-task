package com.mjc.school.repository.datasource;

import com.mjc.school.common.utils.FileUtils;
import com.mjc.school.common.utils.JsonUtils;
import com.mjc.school.common.utils.PropertyLoader;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.ModelInterface;
import com.mjc.school.repository.model.modelhelper.AutoIncrementIdGenerator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JsonDataFileManager implements DataManager{
    PropertyLoader propertyLoader;
    private String authorFilePath;
    private String newsFilePath;

    public JsonDataFileManager(){
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

    private String getModelFilePath(Class<? extends ModelInterface> tableType) throws IOException {
        return ((Author.class.isAssignableFrom(tableType))? authorFilePath : newsFilePath);
    }

    @Override
    public List<ModelInterface> load(Class<? extends ModelInterface> clazz) throws Exception{
        AutoIncrementIdGenerator.reset(clazz);
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
