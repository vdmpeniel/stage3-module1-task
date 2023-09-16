package com.mjc.school.service.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorDto {
    @Size(min=5, max=15, message = "Name must be between 5 and 15 characters long")
    private String fullName;
}
