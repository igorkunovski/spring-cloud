package com.example.controller;

import org.example.Timer;
import com.example.model.Reader;
import com.example.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
//@Tag(name="Reader")
@RequestMapping("/api/reader")
public class ReaderController {

    @Autowired
    private final ReaderService readerService;
//    private final IssueService issueService;

    public ReaderController(ReaderService readerService
//            , IssueService issueService
    ) {
        this.readerService = readerService;
//        this.issueService = issueService;
    }

    @Timer
    @GetMapping("/all")
//    @Operation(summary = "get all readers", description = "load all readers from repository")
    public ResponseEntity<List<Reader>> getAllReaders() {
        return new ResponseEntity<>(readerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
//    @Operation(summary = "find by id", description = "search reader from repository by its id")
//    @ApiResponse(responseCode = "200")
//    @ApiResponse(responseCode = "404")
    public ResponseEntity<Reader> getReader(@PathVariable Long id) {
        Optional<Reader> reader= readerService.findById(id);
        return reader.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

//    @GetMapping("/{id}/issue")
////    @Operation(summary = "reader issues", description = "searches for reader's issued booklist")
////    @ApiResponse(responseCode = "200")
////    @ApiResponse(responseCode = "404")
//    public ResponseEntity<List<Issue>> getReaderIssueList(@PathVariable Long id) {
//        Optional<Reader> reader= readerService.findById(id);
//        if (reader.isPresent()){
//            return new ResponseEntity<>(issueService.getUserIssues(id), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/{id}")
//    @Operation(summary = "delete reader by id", description = "delete book from repository by its id")
//    @ApiResponse(responseCode = "200")
//    @ApiResponse(responseCode = "401")
    public ResponseEntity<Reader> deleteReader(@PathVariable Long id) {

        Optional<Reader> reader = readerService.findById(id);
        readerService.deleteById(id);
        return reader.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping
//    @Operation(summary = "create new reader", description = "create new reader and save it to repository")
//    @ApiResponse(responseCode = "200")
//    @ApiResponse(responseCode = "401")
    public ResponseEntity<Reader> createReader(@RequestBody Reader reader) {

        try {
            readerService.save(reader);
            return new ResponseEntity<>(reader, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
