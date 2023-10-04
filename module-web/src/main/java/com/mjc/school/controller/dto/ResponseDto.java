package com.mjc.school.controller.dto;

import com.mjc.school.service.dto.ErrorDto;
import com.mjc.school.service.interfaces.ModelDtoInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto implements ModelDtoInterface {
    private String status;
    private List<ModelDtoInterface> resultSet;
    private ErrorDto error;

    @Override
    public String toString(){
        return "status=" + status + ",\n" +
                "resultSet=" + resultSet + ",\n" +
                "error=" + error;
    }
}
