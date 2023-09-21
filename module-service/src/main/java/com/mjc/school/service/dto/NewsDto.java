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
public class NewsDto implements ModelDtoInterface {
    private Long id;

    @NotNull(message = "validation.error.news.title.notnull")
    @Size(min=5, max=30, message = "validation.error.news.title.size")
    private String title;

    @NotNull(message = "validation.error.news.newscontent.notnull")
    @Size(min=5, max=255, message = "validation.error.news.newscontent.size")
    private String newsContent;

    @NotNull(message = "validation.error.news.authorid.notnull")
    @Digits(integer = 10, fraction = 0, message = "validation.error.news.authorid.isanumber")
    @Min(value = 1, message = "validation.error.news.authorid.min")
    @Exist(message = "validation.error.news.authorid.exist")
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
