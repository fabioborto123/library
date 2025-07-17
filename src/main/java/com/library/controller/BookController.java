package com.library.controller;

import com.library.dto.request.BookRequestDto;
import com.library.dto.response.BookResponseDto;
import com.library.entity.Book;
import com.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid BookRequestDto bookDto) {
        Book book = service.save(bookDto);
        return ResponseEntity.created(URI.create("/book" + book.getId())).build();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/books/title/{title}")
    public ResponseEntity<List<BookResponseDto>> findByTitle(@PathVariable String title) {
        return ResponseEntity.ok(service.findByTitle(title));
    }

    @GetMapping("/books/author/{author}")
    public ResponseEntity<List<BookResponseDto>> findBtAuthor(@PathVariable String author) {
        return ResponseEntity.ok(service.findByAuthor(author));
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookResponseDto>> findAllNotDeleted() {
        return ResponseEntity.ok(service.findAllNotDeleted());
    }

    @GetMapping("/books/deleted")
    public ResponseEntity<List<BookResponseDto>> findAllDeleted() {
        return ResponseEntity.ok(service.findAllDeleted());
    }

    @GetMapping("books/available")
    public ResponseEntity<List<BookResponseDto>> findAllAvailable() {
        return ResponseEntity.ok(service.findAllAvailable());
    }

    @GetMapping("books/unavailable")
    public ResponseEntity<List<BookResponseDto>> findAllUnavailable() {
        return ResponseEntity.ok(service.findAllUnavailable());
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<BookResponseDto> update(@RequestBody @Valid BookRequestDto dto, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(dto, id));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/books/{id}/restore")
    public ResponseEntity<BookResponseDto> restore(@PathVariable Long id) {
        return ResponseEntity.ok(service.restore(id));
    }
}
