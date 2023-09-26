package com.mjc.school.repository.implementation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjc.school.common.implementation.utils.IdGeneratorUtils;
import com.mjc.school.repository.interfaces.ModelInterface;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorModel implements ModelInterface, Serializable {
    @JsonIgnore
    @Serial
    private static final long serialVersionUID = 123456789L;

    private Long id;

    @Size(min=5, max=15, message = "Name must be between 5 and 15 characters long")
    String name;


    @JsonProperty("id")
    public synchronized void generateId() {
        id = IdGeneratorUtils.generateId(this.getClass());
    }

    @Override
    public String toString(){
        try {
            return  "\n" + "AuthorModel Object: " + "\n" +
                    "id: " + id + "\n" +
                    "name: " + name;
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
