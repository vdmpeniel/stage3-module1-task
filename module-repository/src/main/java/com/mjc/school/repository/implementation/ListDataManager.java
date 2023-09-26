package com.mjc.school.repository.implementation;

import com.mjc.school.common.implementation.utils.FileUtils;
import com.mjc.school.common.implementation.utils.IdGeneratorUtils;
import com.mjc.school.common.implementation.utils.PropertyLoader;
import com.mjc.school.repository.interfaces.DataManagerInterface;
import com.mjc.school.repository.interfaces.ModelInterface;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListDataManager implements DataManagerInterface {
    PropertyLoader propertyLoader;
    private String authorFilePath;
    private String newsFilePath;

    private String testVariant;

    public ListDataManager(){
        try {
            propertyLoader = PropertyLoader.getInstance();
            testVariant = isTest() ? ".test" : "";
            authorFilePath = propertyLoader.getProperty("application.news.file.path" + testVariant);
            newsFilePath = propertyLoader.getProperty("application.author.file.path" + testVariant);

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
        return ((AuthorModel.class.isAssignableFrom(tableType))? authorFilePath : newsFilePath);
    }

    @Override
    public List<ModelInterface> load(Class<? extends ModelInterface> clazz) throws Exception {
        IdGeneratorUtils.reset(clazz);
        String text = FileUtils.readFile(getModelFilePath(clazz));
        List<String> textList = List.of(text.split("/n"));

        List<ModelInterface> data;
        if(NewsModel.class.isAssignableFrom(clazz)){
            String content = FileUtils.readFile(propertyLoader.getProperty("application.content.file.path" + testVariant));
            List<String> contentList = List.of(content.split("/n"));

            data = IntStream.range(0, textList.size())
                .mapToObj(
                i -> (ModelInterface) NewsModel.builder()
                    .title(textList.get(i))
                    .content(contentList.get(i))
                    .createDate(LocalDateTime.now())
                    .lastUpdateDate(LocalDateTime.now())
                    .authorId((long) i + 1)
                    .build()
            ).collect(Collectors.toList());

        } else {
            data = textList.stream().map(
                    name -> (ModelInterface) AuthorModel.builder()
                        .name(name)
                        .build()
            ).collect(Collectors.toList());
        }
        data.forEach(ModelInterface::generateId);
        return data;
    }

    @Override
    public void persist(Class<? extends ModelInterface> clazz, List<ModelInterface> modelTable) throws Exception {

    }
}
