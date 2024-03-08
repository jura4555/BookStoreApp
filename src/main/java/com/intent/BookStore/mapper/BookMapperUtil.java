package com.intent.BookStore.mapper;


import com.intent.BookStore.dto.BookDTO;
import com.intent.BookStore.model.Book;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookMapperUtil {
    public static BookDTO toBookDto(Book book) {
        return new BookDTO()
                .setId(book.getId())
                .setTitle(book.getTitle())
                .setAuthorName(book.getAuthorName())
                .setGenre(book.getGenre())
                .setPrice(book.getPrice())
                .setQuantity(book.getQuantity())
                .setDescription(book.getDescription())
                .setImageURL(book.getImageURL());
    }

    public static Book toBook(BookDTO bookDto) {
        return new Book()
                .setId(bookDto.getId())
                .setTitle(bookDto.getTitle())
                .setAuthorName(bookDto.getAuthorName())
                .setGenre(bookDto.getGenre())
                .setPrice(bookDto.getPrice())
                .setQuantity(bookDto.getQuantity())
                .setDescription(bookDto.getDescription())
                .setImageURL(bookDto.getImageURL());
    }
}
