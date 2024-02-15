package com.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "readers")
//@Schema(name = "Reader")
@Data
@NoArgsConstructor
public class Reader{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    @Schema(name = "Id of the reader")
    private long id;

//    @Column(name = "booklist")
////    @Schema(name = "List of books the reader is holding")
//    @OneToMany
//    private List<Book> bookList;

    @Column(name = "name")
//    @Schema(name = "Reader name")
    private String name;

    public Reader(String name) {
        this.name = name;
//        this.bookList = new ArrayList<>();
    }

//    public void addBook(Book book){
//        bookList.add(book);
//    }

//    public void removeBook(Book book){
//        bookList.remove(book);
//    }
}
