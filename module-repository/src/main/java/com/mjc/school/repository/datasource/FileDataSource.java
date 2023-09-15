package com.mjc.school.repository.datasource;

import com.mjc.school.common.utils.FileUtils;
import com.mjc.school.common.utils.JsonUtils;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.modelinterface.ModelInterface;
import com.mjc.school.repository.model.News;

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


    public String getModelFilePath(Class<? extends ModelInterface> tableType){
        return (Author.class.isAssignableFrom(tableType))? authorFilePath : newsFilePath;
    }
    private List<ModelInterface> setFromTable(Class<? extends ModelInterface> tableType){
        return (Author.class.isAssignableFrom(tableType))? authorTable : newsTable;
    }

    private List<ModelInterface> loadModelData(Class<? extends ModelInterface> tableType) throws Exception{
        String json = FileUtils.readFile(getModelFilePath(tableType));
        json = json.replaceAll("/n", "");
        return JsonUtils.deserializeList(json, tableType)
                .stream().map(tableType::cast).collect(Collectors.toList());
    }

    public void persistModelData(Class<? extends ModelInterface> tableType, List<ModelInterface> modelTable) throws Exception{
            String json = JsonUtils.serialize(
                    modelTable.stream().map(tableType::cast).toList()
            );
            FileUtils.writeFile(getModelFilePath(tableType), json);
    }

    public List<ModelInterface> executeSelectQuery(Class<? extends ModelInterface> tableType, Predicate<ModelInterface> predicate) throws Exception{
        List<ModelInterface> modelTable = setFromTable(tableType);
        return (Objects.isNull(predicate)) ?
                new ArrayList<>(modelTable)
                : modelTable.stream()
                .filter(predicate)
                .toList();
    }

    public boolean executeDeleteQuery(Class<? extends ModelInterface> tableType, Predicate<ModelInterface> predicate) throws Exception {
        try{
            List<ModelInterface> modelTable = setFromTable(tableType);

            if (Objects.isNull(predicate)) {
                throw new Exception("Filter must not be null.");
            }

            List<ModelInterface> resultTable = modelTable.stream().filter(predicate.negate()).collect(Collectors.toList());
            if (resultTable.size() == modelTable.size()) {
                throw new Exception("No row was affected.");
            }
            modelTable = resultTable;
            persistModelData(tableType, resultTable);
            return true;

        } catch(Exception e){
            if (List.of("Filter must not be null.", "No row was affected.").contains(e.getMessage())){
                return false;
            }
            throw e;
        }
    }

    public void executeInsertQuery(Class<? extends ModelInterface> tableType, ModelInterface model) throws Exception{
        List<ModelInterface> modelTable = loadModelData(tableType);
        modelTable.add(model);
        persistModelData(tableType, modelTable);
    }

    public void executeUpdateQuery(Class<? extends ModelInterface> tableType, ModelInterface model, Predicate<ModelInterface> predicate) throws Exception{
        List<ModelInterface> modelTable = loadModelData(tableType);

        if (modelTable.stream().filter(predicate).toList().size() == 0) {
            throw new Exception("Filter returned no results");
        }

        modelTable = modelTable.stream().map(original -> {
            try {
                Long id = original.getId();
                LocalDateTime createdDate = (tableType.isAssignableFrom(News.class))?
                    ((News) original).getCreateDate()
                    : null;

                if (predicate.test(original)) {
                     original = tableType.cast(JsonUtils.deserialize(JsonUtils.serialize(model), tableType));
                     original.setId(id);
                     if (tableType.isAssignableFrom(News.class)){
                         ((News) original).setCreateDate(createdDate);
                         ((News) original).setLastUpdateDate(LocalDateTime.now());
                     }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return original;
        }).toList();
        persistModelData(tableType, modelTable);

    }

    public static void main(String[] args) {

        try {
            FileDataSource fileDataSource = FileDataSource.getInstance();
            News news1 = new News();
            news1.setTitle("azucaaa");
            news1.setAuthorId(2L);
            news1.setContent("La mulata tiene tumbao...");

            fileDataSource.executeInsertQuery(News.class, news1);
//            News news = new News(0L);
//            news.setAuthorId(4L);
//            news.setContent("lalalala....");
//
//            Predicate<ModelInterface> newsById = model -> model.getId().equals(1L);
//
//            fileDataSource.executeUpdateQuery(News.class, news, newsById);

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
