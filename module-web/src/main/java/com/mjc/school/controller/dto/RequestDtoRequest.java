package com.mjc.school.controller.dto;

import com.mjc.school.service.interfaces.ModelDtoInterface;
import com.mjc.school.service.annotations.Exist;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDtoRequest implements ModelDtoInterface {
    @Digits(integer = 10, fraction = 0, message = "validation.error.news.lookupid.isanumber")
    @Min(value = 1, message = "validation.error.news.lookupid.min")
    @Exist(message = "validation.error.news.lookupid.exist")
    private String lookupId;
    private ModelDtoInterface inputData;
}
