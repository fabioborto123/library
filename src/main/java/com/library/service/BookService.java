package com.library.service;


import com.library.dto.request.BookRequestDto;
import com.library.dto.response.BookResponseDto;
import com.library.entity.Book;
import com.library.exception.IdNotFoundException;
import com.library.mapper.BookMapper;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    @Autowired
    BookMapper mapper;

    public Book save(BookRequestDto bookDto) {
        Book book = mapper.toEntity(bookDto);
        book.setAvailableQuantity(bookDto.getQuantity());
        return repository.save(book);
    }

    public BookResponseDto findById(Long id) {
        return mapper.toResponseDto(getBook(id));
    }

    public List<BookResponseDto> findAllNotDeleted() {
        return mapper.toResponseDtoList(repository.findAllNotDeleted());
    }

    public List<BookResponseDto> findAllDeleted() {
        return mapper.toResponseDtoList(repository.findAllDeleted());
    }

    public List<BookResponseDto> findAllAvailable() {
        return mapper.toResponseDtoList(repository.findAllAvailable());
    }

    public List<BookResponseDto> findAllUnavailable() {
        return mapper.toResponseDtoList(repository.findAllUnavailable());
    }

    public List<BookResponseDto> findByTitle(String title) {
        return mapper.toResponseDtoList(repository.findByTitle(title));
    }

    public List<BookResponseDto> findByAuthor(String author) {
        return mapper.toResponseDtoList(repository.findByAuthor(author));
    }

    public BookResponseDto update(BookRequestDto dto, Long savedBookId) {
        Book savedBook = getBook(savedBookId);

        savedBook.setTitle(dto.getTitle());
        savedBook.setAuthor(dto.getAuthor());
        savedBook.setCategory(dto.getCategory());
        savedBook.setTotalQuantity(savedBook.getTotalQuantity() + dto.getQuantity());
        savedBook.setAvailableQuantity(savedBook.getAvailableQuantity() + dto.getQuantity());
        savedBook.setDailyPrice(dto.getDailyPrice());

        repository.save(savedBook);
        return mapper.toResponseDto(savedBook);
    }

    public void delete(Long id) {
        Book book = getBook(id);
        repository.delete(book);
    }

    public BookResponseDto restore(Long id) {
        Book book = getBook(id);
        book.setDeleted(false);
        repository.save(book);
        return mapper.toResponseDto(book);
    }

    private Book getBook(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id));
    }

}
