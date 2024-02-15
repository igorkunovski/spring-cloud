package com.example.controller;

import com.example.model.Book;
import com.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

//    @Timer
    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
//    @Operation(summary = "find by id", description = "search book from repository by its id")
//    @ApiResponse(responseCode = "200")
//    @ApiResponse(responseCode = "404")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        Optional<Book> book = bookService.findById(id);
        return book.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
//    @Operation(summary = "delete book by id", description = "delete book from repository by book id")
//    @ApiResponse(responseCode = "200")
//    @ApiResponse(responseCode = "401")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {

        Optional<Book> book = bookService.findById(id);
        bookService.deleteById(id);
        return book.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping
//    @Operation(summary = "create new book", description = "create new book and save it to repository")
//    @ApiResponse(responseCode = "201")
//    @ApiResponse(responseCode = "400")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {

        if (bookService.findAll().contains(book)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            bookService.save(book);
        }
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }
}
