package com.banking.booksapi.controller;


import com.banking.booksapi.model.Book;
import com.banking.booksapi.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book saved = service.save(book);
        log.info("Created book: {}", service.maskForLogging(saved));
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Book> getAll() {
        List<Book> books = service.findAll();
        books.forEach(b -> log.info("Book: {}", service.maskForLogging(b)));
        return books;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {
        return service.findById(id)
                .map(book -> {
                    log.info("Book: {}", service.maskForLogging(book));
                    return ResponseEntity.ok(book);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);
        Book updated = service.update(book);
        log.info("Updated book: {}", service.maskForLogging(updated));
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        log.info("Deleted book with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
