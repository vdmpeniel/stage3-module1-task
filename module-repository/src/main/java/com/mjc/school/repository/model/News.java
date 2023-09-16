package com.mjc.school.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjc.school.common.utils.DateUtils;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class News implements ModelInterface, Serializable {
    @JsonIgnore
    @Serial
    private static final long serialVersionUID = 123456789L;


    @JsonIgnore
    private static Long nextId = 0L;

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



    public News(){
        generateId();
        createDate = LocalDateTime.now();
        lastUpdateDate = createDate;
    }

    public News(String title, String content, LocalDateTime createDate, LocalDateTime lastUpdateDate, Long authorId){
        this();
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.authorId = authorId;
    }

    public News(Long id){
        this.id = id;
        createDate = LocalDateTime.now();
        lastUpdateDate = createDate;
    }
    public News(Long id, String title, String content, LocalDateTime createDate, LocalDateTime lastUpdateDate, Long authorId){
        this(id);
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.authorId = authorId;
    }

    @JsonProperty("id")
    public void generateId() {
        id = nextId++;
    }

    public void setId(Long id){
        if (id.equals(-1L)) { this.id = id; }
        else { throw new UnsupportedOperationException("The id of this record can't be altered."); }
    }

    @Override
    public String toString(){
        try {
            return  "\n" + "News Object: " + "\n" +
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
