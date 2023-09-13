package com.mjc.school.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class News implements Serializable {
    @JsonIgnore
    @Serial
    private static final long serialVersionUID = 123456789L;

    private Long id;

    @Size(min=5, max=30, message = "Tittle must be between 5 and 30 characters long.")
    private String tittle;

    @Size(min=5, max=255, message = "Content must be between 5 and 255 characters long.")
    private String content;

    @NotNull(message = "Creation Date must be populated")
    private LocalDateTime createDate;

    @NotNull(message = "Creation Date must be populated")
    private LocalDateTime lastUpdateDate;

    @NotNull(message = "Creation Date must be populated")
    private Long authorId;

    public News(){
        id = generateUniqueId();
    }
    public News(String tittle, String content, LocalDateTime createDate, LocalDateTime lastUpdateDate, Long authorId){
        this();
        this.tittle = tittle;
        this.content = content;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.authorId = authorId;
    }

    @JsonProperty("id")
    public void generateId() {
        this.id = generateUniqueId();
    }

    public void setId(Long id){
        throw new UnsupportedOperationException("The id of a record can't be altered.");
    }

    private long generateUniqueId() {
        return new Random().nextLong(1_000_000L);
    }
}
