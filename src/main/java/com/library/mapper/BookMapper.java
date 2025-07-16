package com.library.mapper;

import com.library.dto.request.BookRequestDto;
import com.library.dto.response.BookResponseDto;
import com.library.entity.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public Book toEntity(BookRequestDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setCategory(dto.getCategory());
        book.setTotalQuantity(dto.getQuantity());

        return book;
    }

    public BookResponseDto toResponseDto(Book book) {
        BookResponseDto dto = new BookResponseDto();
        dto.setId(book.getId());
        dto.setAuthor(book.getAuthor());
        dto.setTitle(book.getTitle());
        dto.setCategory(book.getCategory());
        dto.setTotalQuantity(book.getTotalQuantity());
        dto.setAvailableQuantity(book.getAvailableQuantity());

        return dto;
    }

    public List<BookResponseDto> toResponseDtoList(List<Book> books) {
        return books.stream().map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
