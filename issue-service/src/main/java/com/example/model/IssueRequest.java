package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
//@Schema(name = "Issue request")
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Schema(name = "Id of Issue request")
    private long id;

    @Column(name = "readerId")
//    @Schema(name = "Id of the Reader in issue request")
    private long readerId;

    @Column(name = "bookId")
//    @Schema(name = "Id of the Book in issue request")
    private long bookId;
}
