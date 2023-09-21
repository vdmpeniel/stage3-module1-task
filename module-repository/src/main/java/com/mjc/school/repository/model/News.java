package com.mjc.school.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjc.school.common.utils.DateUtils;
import com.mjc.school.repository.model.modelhelper.AutoIncrementIdGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class News implements ModelInterface, Serializable {
    @JsonIgnore
    @Serial
    private static final long serialVersionUID = 123456789L;

    @JsonIgnore
    private Long id;

    @Size(min=5, max=30, message = "Tittle must be between 5 and 30 characters long.")
    private String title;

    @Size(min=5, max=255, message = "Content must be between 5 and 255 characters long.")
    private String content;

    @NotNull(message = "Creation Date must be populated")
    private LocalDateTime createDate;

    @NotNull(message = "Creation Date must be populated")
    private LocalDateTime lastUpdateDate;

    @NotNull(message = "Creation Date must be populated")
    private Long authorId;

    @Override
    @JsonProperty("id")
    public synchronized void generateId() {
        id = AutoIncrementIdGenerator.generateId(this.getClass());
    }

    @Override
    public String toString(){
        try {
            return  "News Object: " + "\n" +
                    "id: " + id + "\n" +
                    "title: " + title + "\n" +
                    "content: " + content + "\n" +
                    "createdDate: " + DateUtils.LocalDateTimeToISO8601(createDate) + "\n" +
                    "lastUpdatedDate: " + DateUtils.LocalDateTimeToISO8601(lastUpdateDate) + "\n" +
                    "authorId: " + authorId + "\n";

        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
