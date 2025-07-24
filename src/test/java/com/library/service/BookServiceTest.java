package com.library.service;

import com.library.dto.request.BookRequestDto;
import com.library.dto.response.BookResponseDto;
import com.library.entity.Book;
import com.library.exception.IdNotFoundException;
import com.library.mapper.BookMapper;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    BookRepository repository;

    @Mock
    BookMapper mapper;

    @InjectMocks
    BookService service;

    Book bookEntity;
    BookRequestDto bookRequestDto;
    BookResponseDto bookResponseDto;
    List<BookResponseDto> bookResponseDtoList;
    List<Book> bookEntityList;


    @BeforeEach
    public void setUp() {
        bookEntity = new Book();
        bookEntity.setId(1L);
        bookEntity.setTitle("Test title");
        bookEntity.setAuthor("Test author");
        bookEntity.setCategory("Test category");
        bookEntity.setDailyPrice(1.0);

        bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle(bookEntity.getTitle());
        bookRequestDto.setAuthor(bookEntity.getAuthor());
        bookRequestDto.setCategory(bookEntity.getCategory());
        bookRequestDto.setDailyPrice(bookEntity.getDailyPrice());
        bookRequestDto.setQuantity(1);

        bookResponseDto = new BookResponseDto();
        bookResponseDto.setId(bookEntity.getId());
        bookResponseDto.setTitle(bookEntity.getTitle());
        bookResponseDto.setAuthor(bookEntity.getAuthor());
        bookResponseDto.setCategory(bookEntity.getCategory());
        bookResponseDto.setDailyPrice(bookEntity.getDailyPrice());

        bookEntityList = new ArrayList<>();
        bookResponseDtoList = new ArrayList<>();
        bookEntityList.add(bookEntity);
        bookResponseDtoList.add(bookResponseDto);

    }

    @Test
    void shouldSaveBook() {
        when(repository.save(bookEntity)).thenReturn(bookEntity);
        when(mapper.toEntity(bookRequestDto)).thenReturn(bookEntity);

        Book savedBook = service.save(bookRequestDto);

        verify(repository, times(1)).save(bookEntity);
        verify(mapper, times(1)).toEntity(bookRequestDto);

        assertEquals(bookEntity.getId(), savedBook.getId());
        assertEquals(bookRequestDto.getQuantity(), savedBook.getAvailableQuantity());
        assertEquals(bookRequestDto.getDailyPrice(), savedBook.getDailyPrice());
    }

    @Test
    void shouldFindBookById() {
        when(repository.findById(bookEntity.getId())).thenReturn(Optional.ofNullable(bookEntity));
        when(mapper.toResponseDto(bookEntity)).thenReturn(bookResponseDto);

        BookResponseDto bookDto = service.findById(bookEntity.getId());

        verify(repository, times(1)).findById(bookEntity.getId());
        verify(mapper, times(1)).toResponseDto(bookEntity);

        assertEquals(bookDto.getId(), bookEntity.getId());
        assertEquals(bookDto.getTitle(), bookEntity.getTitle());
        assertEquals(bookDto.getAuthor(), bookEntity.getAuthor());
        assertEquals(bookDto.getCategory(), bookEntity.getCategory());
        assertEquals(bookDto.getDailyPrice(), bookEntity.getDailyPrice());
        assertEquals(bookDto.getTotalQuantity(), bookEntity.getTotalQuantity());
    }

    @Test
    void shouldThrowWhenBookNotFound() {
        Long idUnavailable = 999L;
        when(repository.findById(idUnavailable)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> service.findById(idUnavailable));

        verify(repository, times(1)).findById(idUnavailable);
        verify(mapper, never()).toResponseDto(any());
    }


    @Test
    void shouldUpdateBook() {
        when(repository.findById(bookEntity.getId())).thenReturn(Optional.of(bookEntity));

        bookRequestDto.setTitle("New Title");
        bookRequestDto.setAuthor("New Author");
        bookRequestDto.setCategory("New Category");
        bookRequestDto.setDailyPrice(3.0);
        bookRequestDto.setQuantity(3);

        BookResponseDto expectedResponse = new BookResponseDto();
        expectedResponse.setTitle("New Title");
        expectedResponse.setAuthor("New Author");
        expectedResponse.setCategory("New Category");
        expectedResponse.setDailyPrice(3.0);
        expectedResponse.setAvailableQuantity(bookEntity.getAvailableQuantity() + 3);
        expectedResponse.setTotalQuantity(bookEntity.getTotalQuantity() + 3);

        when(mapper.toResponseDto(any(Book.class))).thenReturn(expectedResponse);

        BookResponseDto updatedBook = service.update(bookRequestDto, bookEntity.getId());

        verify(repository, times(1)).findById(bookEntity.getId());
        verify(repository, times(1)).save(bookEntity);
        verify(mapper, times(1)).toResponseDto(bookEntity);

        assertEquals("New Title", updatedBook.getTitle());
        assertEquals("New Author", updatedBook.getAuthor());
        assertEquals(3.0, updatedBook.getDailyPrice());
    }

    @Test
    void shouldDeleteBook() {
        when(repository.findById(bookEntity.getId())).thenReturn(Optional.ofNullable(bookEntity));
        service.delete(bookEntity.getId());

        verify(repository, times(1)).findById(bookEntity.getId());
        verify(repository, times(1)).delete(bookEntity);
    }

    @Test
    void shouldRestoreBook() {
        when(repository.findById(bookEntity.getId())).thenReturn(Optional.ofNullable(bookEntity));
        when(repository.save(bookEntity)).thenReturn(bookEntity);
        when(mapper.toResponseDto(bookEntity)).thenReturn(bookResponseDto);

        BookResponseDto restoredBook = service.restore(bookEntity.getId());

        verify(repository, times(1)).findById(bookEntity.getId());
        verify(repository, times(1)).save(bookEntity);
        verify(mapper, times(1)).toResponseDto(bookEntity);

        assertEquals(restoredBook, bookResponseDto);
        assertFalse(bookEntity.getDeleted());
    }
}
