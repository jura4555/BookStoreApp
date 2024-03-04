package com.intent.BookStore.service.impl;

import com.intent.BookStore.exception.BookNotFoundException;
import com.intent.BookStore.model.Book;
import com.intent.BookStore.repository.BookRepository;
import com.intent.BookStore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import static com.intent.BookStore.util.ExceptionMessageUtil.*;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Page<Book> getAllBooks(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                Sort.by("id"));
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format(BOOK_NOT_FOUND_BY_ID_ERROR_MESSAGE, id)));
    }

    @Override
    public Book getBookByTitle(String title) {
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new BookNotFoundException(String.format(BOOK_NOT_FOUND_BY_TITLE_ERROR_MESSAGE, title)));
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book updatedBook) {
        checkIfBookNotExist(id);
        updatedBook.setId(id);
        return bookRepository.save(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        checkIfBookNotExist(id);
        bookRepository.deleteById(id);
    }

    @Override
    public Page<Book> getAllBooksByCriteria(String authorName, String genre, BigDecimal minPrice, BigDecimal maxPrice, int quantity, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                Sort.by("id"));
        return bookRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(buildAuthorNamePredicate(root, criteriaBuilder, authorName));
            predicates.add(buildGenrePredicate(root, criteriaBuilder, genre));
            predicates.add(buildPricePredicate(root, criteriaBuilder, minPrice, maxPrice));
            predicates.add(buildQuantityPredicate(root, criteriaBuilder, quantity));
            predicates.removeIf(Objects::isNull);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    private Predicate buildAuthorNamePredicate(Root<Book> root, CriteriaBuilder criteriaBuilder, String authorName) {
        if (authorName != null && !authorName.isEmpty()) {
            return criteriaBuilder.equal(root.get("authorName"), authorName);
        }
        return null;
    }

    private Predicate buildGenrePredicate(Root<Book> root, CriteriaBuilder criteriaBuilder, String genre) {
        if (genre != null && !genre.isEmpty()) {
            return criteriaBuilder.equal(root.get("genre"), genre);
        }
        return null;
    }

    private Predicate buildPricePredicate(Root<Book> root, CriteriaBuilder criteriaBuilder, BigDecimal minPrice, BigDecimal maxPrice) {
        Predicate pricePredicate = null;
        if(minPrice != null && maxPrice != null) {
            pricePredicate = criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
        } else if(minPrice != null) {
            pricePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
        } else if(maxPrice != null) {
            pricePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
        }
        return pricePredicate;
    }

    private Predicate buildQuantityPredicate(Root<Book> root, CriteriaBuilder criteriaBuilder, int quantity) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), quantity);
    }

    private void checkIfBookNotExist(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(String.format(BOOK_NOT_FOUND_BY_ID_ERROR_MESSAGE, id));
        }
    }
}
