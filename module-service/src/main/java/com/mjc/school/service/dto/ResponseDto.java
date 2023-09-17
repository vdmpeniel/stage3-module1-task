package com.mjc.school.service.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto {
    private String status;
    private List<NewsDto> resultSet;
    private List<ErrorDto> errorList;
}
