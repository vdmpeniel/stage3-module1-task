package com.mjc.school.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

@Getter
@Setter
public class Author implements Serializable {
    @JsonIgnore
    @Serial
    private static final long serialVersionUID = 123456789L;
    private Long id;

    public Author(){
        this.id = generateUniqueId();
    }

    public Author(String name){
        this();
        this.name = name;
    }

    @Size(min=5, max=50, message = "")
    String name;

    public void setId(Long id){
        throw new UnsupportedOperationException("The id of a record can't be altered.");
    }
    private long generateUniqueId() {
        return new Random().nextLong(1_000_000L);
    }
}
