package com.mjc.school.service;

import com.mjc.school.common.implementation.exceptions.IllegalFieldValueException;
import com.mjc.school.repository.implementation.DataSourceFileBased;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.implementation.NewsService;
import com.mjc.school.service.dto.RequestDto;
import com.mjc.school.service.dto.ResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void create() {
        ResponseDto responseDto = service.create(
            RequestDto.builder()
                .inputData(
                    NewsDto.builder()
                        .title("This is a test!")
                        .newsContent("Some cheesy text here.")
                        .authorId("1")
                        .build()
                )
                .build()
        );
        assertInstanceOf(NewsDto.class, responseDto.getResultSet().get(0));
    }

    @Test
    void getAll() {
        ResponseDto responseDto = service.getAll();
        System.out.println(responseDto);
        assertFalse(responseDto.getResultSet().isEmpty());
    }

    @Test
    void getById() {
        ResponseDto all = service.getAll();
        Long id = (new Random()).nextLong(all.getResultSet().size() - 1);
        ResponseDto responseDto = service.getById(
           RequestDto.builder()
               .lookupId(id.toString())
               .build()
        );
        assertFalse(responseDto.getResultSet().isEmpty());
    }

    @Test
    void updateById() {
        ResponseDto responseDto = service.updateById(
            RequestDto.builder()
                .lookupId("1")
                .inputData(
                    NewsDto.builder()
                        .title("Hello Test!")
                        .newsContent("This is just a test.")
                        .authorId("1")
                        .build()
                )
                .build()
        );
        assertEquals("1", ((NewsDto) responseDto.getResultSet().get(0)).getId());
    }

    @Test
    void removeById() {


        ResponseDto all = service.getAll();
        String id = (
            (NewsDto) all.getResultSet().get(
                (new Random()).nextInt(all.getResultSet().size())
            )
        ).getId();
        ResponseDto responseDto = service.removeById(
                RequestDto.builder().lookupId(id).build()
        );
        assertEquals("OK", responseDto.getStatus());
    }

    @Test
    void buildErrorResponse() {
        ResponseDto responseDto = service.buildErrorResponse(
            new IllegalFieldValueException("Test exception", "010101")
        );
        assertEquals("010101", responseDto.getError().getCode());
    }


}