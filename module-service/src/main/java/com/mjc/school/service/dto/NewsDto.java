package com.mjc.school.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsDto {
    @NotNull(message = "Title must be populated.")
    @Size(min=5, max=30, message = "Tittle must be between 5 and 30 characters long.")
    private String title;

    @NotNull(message = "Title must be populated.")
    @Size(min=5, max=255, message = "Content must be between 5 and 255 characters long.")
    private String newsContent;

    @NotNull(message = "Author id Date must be populated.")
    @Pattern(regexp = "\\d+", message="Author id must be a number.")
    //@Exist(message = "Author with id: {fieldValue} doesn't exist.")
    private String authorId;
}
