package com.mjc.school.service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDto {
    private Long lookupId;
    private NewsDto inputData;
}
