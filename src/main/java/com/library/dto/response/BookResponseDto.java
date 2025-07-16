package com.library.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponseDto {

    private Long id;

    private String title;

    private String author;

    private int totalQuantity;

    private int availableQuantity;

    private String category;
}
