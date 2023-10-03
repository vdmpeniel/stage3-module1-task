package com.mjc.school.service.dto;

import com.mjc.school.service.interfaces.ModelDtoInterface;
import com.mjc.school.service.annotations.Exist;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorDto implements ModelDtoInterface {
    @Digits(integer = 10, fraction = 0, message = "validation.error.author.authorid.isanumber")
    @Min(value = 1, message = "validation.error.author.authorid.min")
    @Exist(message = "validation.error.author.authorid.exist")
    private Long id;

    @Size(min=5, max=15, message = "validation.error.author.name.size")
    private String fullName;

    @Override
    public String toString(){
        return "AuthorDtoResponse[" +
                "id=" + id + ", " +
                "name=" + fullName +
                "]";
    }
}
