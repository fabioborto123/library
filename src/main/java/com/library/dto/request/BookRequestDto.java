package com.library.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookRequestDto {

    @NotBlank(message = "must not be blank")
    private String title;

    @NotBlank(message = "must not be blank")
    private String author;

    @NotBlank(message = "must not be blank")
    private String category;

    @NotNull(message = "must not be null")
    @Min(value = 0, message = "minimum value = 0")
    private Integer quantity;

    @NotNull(message = "must not be null")
    @Min(value = 0, message = "minimun value = 0")
    private Double dailyPrice;
}
