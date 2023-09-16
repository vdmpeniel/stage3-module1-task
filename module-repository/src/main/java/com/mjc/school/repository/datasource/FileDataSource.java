package com.mjc.school.repository.datasource;

import com.mjc.school.common.utils.FileUtils;
import com.mjc.school.common.utils.JsonUtils;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.ModelInterface;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.modelhelper.AutoIncrementIdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class FileDataSource {
    private volatile static FileDataSource instance;
    private FileDataSource() throws Exception{
        newsTable = loadModelData(News.class);
        authorTable = loadModelData(Author.class);
    }


    public static FileDataSource getInstance() throws Exception{
        synchronized (FileDataSource.class){
            if (Objects.isNull(instance)){
                instance = new FileDataSource();
            }
            return instance;
        }
    }

    private final String newsFilePath = "module-repository/src/main/resources/news.txt";
    private final String authorFilePath = "module-repository/src/main/resources/author.txt";

    private final List<ModelInterface> newsTable;
    private final List<ModelInterface> authorTable;


    private String getModelFilePath(Class<? extends ModelInterface> tableType){
        return (Author.class.isAssignableFrom(tableType))? authorFilePath : newsFilePath;
    }
    private List<ModelInterface> setFromTable(Class<? extends ModelInterface> tableType){
        return (Author.class.isAssignableFrom(tableType))? authorTable : newsTable;
    }

    private List<ModelInterface> loadModelData(Class<? extends ModelInterface> clazz) throws Exception{
        AutoIncrementIdGenerator.reset(clazz);
        String json = FileUtils.readFile(getModelFilePath(clazz));
        json = json.replaceAll("/n", "");
        return JsonUtils.deserializeList(json, clazz)
                .stream().map(clazz::cast).collect(Collectors.toList());
    }

    public void persistModelData(Class<? extends ModelInterface> clazz, List<ModelInterface> modelTable) throws Exception{
            String json = JsonUtils.serialize(
                    modelTable.stream().map(clazz::cast).toList()
            );
            FileUtils.writeFile(getModelFilePath(clazz), json);
    }

    public List<ModelInterface> executeSelectQuery(Class<? extends ModelInterface> clazz, Predicate<ModelInterface> predicate) throws Exception{
        List<ModelInterface> modelTable = setFromTable(clazz);
        return (Objects.isNull(predicate)) ?
                new ArrayList<>(modelTable)
                : modelTable.stream()
                .filter(predicate)
                .toList();
    }

    public boolean executeDeleteQuery(Class<? extends ModelInterface> clazz, Predicate<ModelInterface> predicate) throws Exception {
        try{
            List<ModelInterface> modelTable = setFromTable(clazz);

            if (Objects.isNull(predicate)) {
                throw new Exception("Filter must not be null.");
            }

            List<ModelInterface> resultTable = modelTable.stream().filter(predicate.negate()).collect(Collectors.toList());
            if (resultTable.size() == modelTable.size()) {
                throw new Exception("No row was affected.");
            }
            modelTable = resultTable;
            persistModelData(clazz, resultTable);
            return true;

        } catch(Exception e){
            if (List.of("Filter must not be null.", "No row was affected.").contains(e.getMessage())){
                return false;
            }
            throw e;
        }
    }

    // The problem is I need to reset the id generator before I load the data.
    public void executeInsertQuery(Class<? extends ModelInterface> clazz, ModelInterface model) throws Exception{
        List<ModelInterface> modelTable = loadModelData(clazz);
        modelTable.add(model);
        persistModelData(clazz, modelTable);
    }

    public void executeUpdateQuery(Class<? extends ModelInterface> clazz, ModelInterface model, Predicate<ModelInterface> predicate) throws Exception{
        List<ModelInterface> modelTable = loadModelData(clazz);

        if (modelTable.stream().filter(predicate).toList().size() == 0) {
            throw new Exception("Filter returned no results");
        }

        modelTable = modelTable.stream().map(original -> {
            try {
                Long id = original.getId();
                LocalDateTime createdDate = (clazz.isAssignableFrom(News.class))?
                    ((News) original).getCreateDate()
                    : null;

                if (predicate.test(original)) {
                     original = clazz.cast(JsonUtils.deserialize(JsonUtils.serialize(model), clazz));
                     original.setId(id);
                     if (clazz.isAssignableFrom(News.class)){
                         ((News) original).setCreateDate(createdDate);
                         ((News) original).setLastUpdateDate(LocalDateTime.now());
                     }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return original;
        }).toList();
        persistModelData(clazz, modelTable);

    }


}
