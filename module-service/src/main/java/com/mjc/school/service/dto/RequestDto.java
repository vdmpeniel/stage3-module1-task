package com.mjc.school.service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDto implements ModelDtoInterface {
    private Long lookupId;
    private ModelDtoInterface inputData;
}
