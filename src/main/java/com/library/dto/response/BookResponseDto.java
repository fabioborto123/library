package com.library.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookResponseDto {

    private Long id;

    private String title;

    private String author;

    private int totalQuantity;

    private int availableQuantity;

    private String category;

    private Double dailyPrice;
}
