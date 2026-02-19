package com.banking.booksapi;


import com.banking.booksapi.controller.BookController;
import com.banking.booksapi.model.Book;
import com.banking.booksapi.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BooksApiApplication.class)
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setup() {
        bookRepository.deleteAll();

        // Capture logs
        Logger logger = (Logger) LoggerFactory.getLogger(BookController.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    void testCreateBookLogsMaskedFields() throws Exception {
        String bookJson = """
            {
                "title": "Spring Boot Mastery",
                "author": "John Doe",
                "email": "john.doe@gmail.com",
                "phoneNumber": "0712345678",
                "publisher": "TechPress"
            }
        """;

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isOk());

        // Check captured logs
        boolean maskedEmail = listAppender.list.stream()
                .anyMatch(event -> event.getFormattedMessage().contains("jo") && event.getFormattedMessage().contains("****"));
        boolean maskedPhone = listAppender.list.stream()
                .anyMatch(event -> event.getFormattedMessage().contains("07") && 
                                event.getFormattedMessage().contains("******"));

        assertThat(maskedEmail).isTrue();
        assertThat(maskedPhone).isTrue();

        // Ensure DB value is unmasked
        Book saved = bookRepository.findAll().get(0);
        assertThat(saved.getEmail()).isEqualTo("john.doe@gmail.com");
        assertThat(saved.getPhoneNumber()).isEqualTo("0712345678");
    }
}
