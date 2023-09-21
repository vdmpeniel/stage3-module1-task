package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDto implements ModelDtoInterface {
    private String code;
    private String message;

    @Override
    public String toString(){
        return "ERROR_CODE: " + code + " ERROR_MESSAGE: " + message;
    }
}
