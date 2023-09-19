package com.mjc.school.service.dto;

import com.mjc.school.service.validator.Exist;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NewsDto {
    private Long id;

    @NotNull(message = "Title must be populated.")
    @Size(min=5, max=30, message = "Tittle must be between 5 and 30 characters long.")
    private String title;

    @NotNull(message = "Title must be populated.")
    @Size(min=5, max=255, message = "Content must be between 5 and 255 characters long.")
    private String newsContent;

    @NotNull(message = "Author id Date must be populated.")
    @Digits(integer = 10, fraction = 0, message = "Author id must be a number.{fieldValue}")
    @Min(value = 0, message = "Field must be greater than -1")
    @Exist(message = "Author with id: {fieldValue} doesn't exist.")
    private Long authorId;

    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    @Override
    public String toString(){
        return "NewsDtoResponse[" +
            "id=" + id + ", " +
            "title=" + title + ", " +
            "content=" + newsContent + ", " +
            "createdDate=" + createDate + ", " +
            "lastUpdatedDate=" + lastUpdateDate + ", " +
            "authorId=" + authorId +
        "]";
    }
}
