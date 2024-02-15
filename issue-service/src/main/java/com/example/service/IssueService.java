package com.example.service;

import com.example.model.Issue;
import com.example.model.IssueRequest;
import com.example.provider.BookProvider;
import com.example.provider.ReaderProvider;
import com.example.repository.IssueRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;


@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final ReaderProvider readerProvider;
    private final BookProvider bookProvider;


    @Autowired
    public IssueService( BookProvider bookProvider, ReaderProvider readerProvider, IssueRepository issueRepository
    ) {
        this.bookProvider = bookProvider;
        this.readerProvider = readerProvider;
        this.issueRepository = issueRepository;

    }

    public void addNewOpenedIssue(Issue issue) {
        issueRepository.save(issue);
    }

    public List<Issue> getUserIssues(long id) {
        return issueRepository.findAll().stream()
                .filter(issue -> issue.getReaderId()==id)
                .toList();
    }

    @PostConstruct
    public void generateIssue() {
        for (int i = 1; i < 4; i++){
            issueRepository.save(new Issue(i,i));
        }
    }

//    public Issue issue(IssueRequest request) {
//        if (bookProvider.getBook(request.getBookId())==null) {
//            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
//        }
//        if (readerProvider.getReader(request.getReaderId())==null) {
//            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
//        }
//
//        return new Issue(request.getReaderId(), request.getBookId());
//    }

    public void closeIssue(Issue issue) {

        issueRepository.findAll().stream()
                .filter(i -> Objects.equals(i.getId(), issue.getId()))
                .findFirst()
                .ifPresent(searched -> {searched.setReturnedDate(LocalDateTime.now().withNano(0));
                    issueRepository.save(searched); });
    }

    public Optional<Issue> findById(Long id) {
        return issueRepository.findById(id);
    }

    public List<Issue> getOpenedIssues() {
        return issueRepository.findAll().stream()
                .filter(issueEntity -> issueEntity.getReturnedDate()==null)
                .toList();
    }

    public List<Issue> findAll() {
        return issueRepository.findAll();
    }
}
