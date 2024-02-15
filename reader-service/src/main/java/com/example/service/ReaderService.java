package com.example.service;

import com.example.model.Reader;
import com.example.repository.ReaderRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReaderService{

    private final ReaderRepository readerRepository;

    @Autowired
    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @PostConstruct
    public void generateReader() {
        for (int i = 100; i < 106; i++){
            save(new Reader("Reader #" + i));
        }
    }

    public List<Reader> findAll() {
        return readerRepository.findAll();
    }

    public Optional<Reader> findById(Long id) {
        return readerRepository.findById(id);
    }

    public void save(Reader reader) {
        if (readerRepository.findAll().contains(reader)) {
            throw new NoSuchElementException("Такой читатель уже введен \"" + reader + "\"");
        } else {
            readerRepository.save(reader);
        }
    }

    public void deleteById(Long id) {
        readerRepository.deleteById(id);
    }

//    public void addBook(Reader reader, Book book) {
//        readerRepository.findById(reader.getId()).get().addBook(book);
//    }
}
