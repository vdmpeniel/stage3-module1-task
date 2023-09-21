package com.mjc.school.service;

import com.mjc.school.repository.datasource.FileDataSource;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.RequestDto;
import com.mjc.school.service.dto.ResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewsServiceTest {
    NewsService service;

    @BeforeEach
    void setUp() {
        try {
            FileDataSource.getInstance();
             service = new NewsService();

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setModelId() {
    }

    @Test
    void createNews() {
        try {
            ResponseDto responseDto = service.create(
                RequestDto.builder()
                    .inputData(
                        NewsDto.builder()
                            .title("This is a test")
                            .newsContent("Some crazy stuff goes here.")
                            .authorId(1L)
                            .build()
                    )
                    .build()
            );
            System.out.println(responseDto);

            assertNotNull(responseDto.getResultSet().get(0));

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getAllNews() {
        ResponseDto responseDto = service.getAll();
        System.out.println(responseDto);
        assertFalse(responseDto.getResultSet().isEmpty());
    }

    @Test
    void getNewsById() {
        ResponseDto responseDto = service.getById(
               RequestDto.builder()
                   .lookupId(1L)
                   .build()
        );
        System.out.println(responseDto);
    }

    @Test
    void updateNewsById() {
    }

    @Test
    void removeNewsById() {
    }

    @Test
    void buildErrorResponse() {
    }
}