package com.mjc.school.service;

import com.mjc.school.repository.implementation.DataSourceFileBased;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.implementation.NewsService;
import com.mjc.school.service.interfaces.ModelDtoInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NewsModelServiceTest {
    NewsService service;
    DataSourceFileBased dataSourceFileBased;

    @BeforeEach
    void setUp() {
        try {
            dataSourceFileBased = new DataSourceFileBased();
            service = new NewsService();

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() throws Exception{
        ModelDtoInterface model = service.create(
                NewsDtoResponse.builder()
                        .title("This is a test!")
                        .newsContent("Some cheesy text here.")
                        .authorId("1")
                        .build()
        );
        assertInstanceOf(NewsDtoResponse.class, model);
    }

    @Test
    void getAll() throws Exception{
        List<NewsDtoResponse> all = service.readAll();
        assertFalse(all.isEmpty());
    }

    @Test
    void getById() throws Exception{
        List<NewsDtoResponse> all = service.readAll();
        Long id = (new Random()).nextLong(all.size() - 1);
        NewsDtoResponse newsDtoResponse = service.readById(id);
        assertFalse(Objects.isNull(newsDtoResponse.getId()));
    }

    @Test
    void updateById() throws Exception{
        List<NewsDtoResponse> all = service.readAll();
        Long id = (new Random()).nextLong(all.size() - 1);
        NewsDtoResponse newsDtoResponse = service.updateById(
            NewsDtoResponse.builder()
                .id(id)
                .title("Hello Test!")
                .newsContent("This is just a test.")
                .authorId("1")
                .build()
        );
        assertEquals(id, newsDtoResponse.getId());
    }

    @Test
    void deleteById() throws Exception{
        List<NewsDtoResponse> all = service.readAll();
        Long id = all.get((new Random()).nextInt(all.size())).getId();
        Boolean response = service.deleteById(id);
        assertEquals(true, response);
    }
}