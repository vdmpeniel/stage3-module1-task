package com.mjc.school.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjc.school.repository.model.modelhelper.AutoIncrementIdGenerator;
import com.mjc.school.repository.model.modelhelper.IdGenerator;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author implements ModelInterface, Serializable {
    @JsonIgnore
    @Serial
    private static final long serialVersionUID = 123456789L;

    @JsonIgnore
    private IdGenerator idGenerator = new AutoIncrementIdGenerator(this.getClass());

    private Long id;

    @Size(min=5, max=15, message = "Name must be between 5 and 15 characters long")
    String name;

    public Author(Long id){ id = -1L; }
    public Author(){ generateId(); }

    public Author(String name){
        this();
        this.name = name;
    }

    @JsonProperty("id")
    public void generateId() {
        id = idGenerator.generateId(this.getClass());
    }

    public void setId(Long id){
        this.id = id;
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
