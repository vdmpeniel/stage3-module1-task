package com.mjc.school.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class NewsDto {
    @NotNull(message = "Title must be populated")
    @Size(min=5, max=30, message = "Tittle must be between 5 and 30 characters long.")
    private String title;

    @NotNull(message = "Title must be populated")
    @Size(min=5, max=255, message = "Content must be between 5 and 255 characters long.")
    private String newsContent;

    @NotNull(message = "Creation Date must be populated")
    @Pattern(
        regexp = "^([\\+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24\\:?00)([\\.,]\\d+(?!:))?)?(\\17[0-5]\\d([\\.,]\\d+)?)?([zZ]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?$",
        message = "Create update value must be in ISO8601"
    )
    private String createDate;

    @NotNull(message = "Last update Date must be populated")
    @Pattern(
        regexp = "^([\\+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24\\:?00)([\\.,]\\d+(?!:))?)?(\\17[0-5]\\d([\\.,]\\d+)?)?([zZ]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?$",
        message = "Last update value must be in ISO8601"
    )
    private String lastUpdateDate;

    @NotNull(message = "Author id Date must be populated")
    private Long authorId;
}
