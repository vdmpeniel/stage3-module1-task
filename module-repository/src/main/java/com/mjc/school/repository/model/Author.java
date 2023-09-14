package com.mjc.school.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class Author implements ModelInterface, Serializable {
    @JsonIgnore
    @Serial
    private static final long serialVersionUID = 123456789L;

    @JsonIgnore
    private static Long idSequentialGenerator = 0L;

    private Long id;

    @Size(min=5, max=15, message = "Name must be between 5 and 15 characters long")
    String name;


    public Author(Long id){ id = -1L; }
    public Author(){ id = generateUniqueId(); }

    public Author(String name){
        this();
        this.name = name;
    }

    @Override
    public void getIdSequentialGenerator(Long id) {
        throw new UnsupportedOperationException("This field can't be accessed.");
    }

    @Override
    public void setIdSequentialGenerator(Long id) {
        throw new UnsupportedOperationException("This field can't be altered.");
    }

    @JsonProperty("id")
    public void generateId() {
        id = generateUniqueId();
    }

    public void setId(Long id){
        this.id = id;
    }

    private long generateUniqueId() {
        return idSequentialGenerator++;
    }

    @Override
    public String toString(){
        try {
            return  "\n" + "Author Object: " + "\n" +
                    "id: " + id + "\n" +
                    "name: " + name;
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
