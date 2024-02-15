package com.example.service;

import com.example.model.Book;
import com.example.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookService{

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void generateBook() {
        for (int i = 100; i < 106; i++){
            save(new Book(("Book #" +i), ("Author #" +i)));
        }
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public void save(Book book) {

        for (Book b: bookRepository.findAll()) {
            if (b.getTitle().equals(book.getTitle())&& b.getAuthor().equals(book.getAuthor())){
                throw new NoSuchElementException("Такая книга уже существует \"" + book + "\"");
            }
        }
        bookRepository.save(book);
    }
}
