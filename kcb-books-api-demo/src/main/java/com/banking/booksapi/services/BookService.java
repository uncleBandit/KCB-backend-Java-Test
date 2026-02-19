package com.banking.booksapi.services;


import com.banking.booksapi.model.Book;
import com.banking.booksapi.repository.BookRepository;
import com.banking.masking.MaskingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;
    private final MaskingService maskingService;

    public BookService(BookRepository repository, MaskingService maskingService) {
        this.repository = repository;
        this.maskingService = maskingService;
    }

    public Book save(Book book) {
        return repository.save(book);
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    public Book update(Book book) {
        return repository.save(book);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Masked copy for logging
    public Book maskForLogging(Book book) {
        return maskingService.mask(book);
    }
}
