package com.intent.BookStore.unit.util;

import com.intent.BookStore.dto.BookDTO;
import com.intent.BookStore.model.Book;

import java.math.BigDecimal;

public class TestBookDataUtil {
    public static final Long BOOK_ID_1 = 1L;
    public static final String TITLE_1 = "To Kill a Mockingbird";
    public static final String AUTHOR_NAME_1 = "Harper Lee";
    public static final String GENRE_1 = "Fiction";
    public static final BigDecimal PRICE_1 = BigDecimal.valueOf(10.99);
    public static final int QUANTITY_1 = 30;
    public static final String DESCRIPTION_1 = "To Kill a Mockingbird is a novel by Harper Lee published in 1960.";
    public static final String IMAGE_URL_1 = "https://book-images/To+Kill+a+Mockingbird.jpg";


    public static final Long BOOK_ID_2 = 2L;
    public static final String TITLE_2 = "The Great Gatsby";
    public static final String AUTHOR_NAME_2 = "F. Scott Fitzgerald";
    public static final String GENRE_2 = "Fiction";
    public static final BigDecimal PRICE_2 = BigDecimal.valueOf(12.99);
    public static final int QUANTITY_2 = 40;
    public static final String DESCRIPTION_2 = "The story primarily concerns the young and mysterious millionaire Jay Gatsby.";
    public static final String IMAGE_URL_2 = "https://book-images/The+Great+Gatsby.jpg";

    public static final Long BOOK_ID_3 = 3L;
    public static final String TITLE_3 = "Go Set a Watchman";
    public static final String AUTHOR_NAME_3 = "Harper Lee";
    public static final String GENRE_3 = "Fiction";
    public static final BigDecimal PRICE_3 = BigDecimal.valueOf(14.99);
    public static final int QUANTITY_3 = 40;
    public static final String DESCRIPTION_3 = "Go Set a Watchman is a novel by Harper Lee published in 2015.";
    public static final String IMAGE_URL_3 = "https:/book-images/Go+Set+a+Watchman.jpg";

    public static final int PAGE_NUMBER_DEFAULT = 0;
    public static final int PAGE_NUMBER = 1;
    public static final int PAGE_SIZE = 3;
    public static final int PAGE_SIZE_FOT_CRITERIA = 2;
    public static final String AUTHOR_NAME = "Harper Lee";
    public static final String GENRE = "Fiction";
    public static final BigDecimal MIN_PRICE = null;
    public static final BigDecimal MAX_PRICE = null;
    public static final int QUANTITY = 10;

    public static Book getBook1() {
        return new Book()
                .setId(BOOK_ID_1)
                .setTitle(TITLE_1)
                .setAuthorName(AUTHOR_NAME_1)
                .setGenre(GENRE_1)
                .setPrice(PRICE_1)
                .setQuantity(QUANTITY_1)
                .setDescription(DESCRIPTION_1)
                .setImageURL(IMAGE_URL_1);
    }

    public static Book getBook2() {
        return new Book()
                .setId(BOOK_ID_2)
                .setTitle(TITLE_2)
                .setAuthorName(AUTHOR_NAME_2)
                .setGenre(GENRE_2)
                .setPrice(PRICE_2)
                .setQuantity(QUANTITY_2)
                .setDescription(DESCRIPTION_2)
                .setImageURL(IMAGE_URL_2);
    }

    public static Book getBook3() {
        return new Book()
                .setId(BOOK_ID_3)
                .setTitle(TITLE_3)
                .setAuthorName(AUTHOR_NAME_3)
                .setGenre(GENRE_3)
                .setPrice(PRICE_3)
                .setQuantity(QUANTITY_3)
                .setDescription(DESCRIPTION_3)
                .setImageURL(IMAGE_URL_3);
    }

    public static BookDTO getBookDTO1() {
        return new BookDTO()
                .setId(BOOK_ID_1)
                .setTitle(TITLE_1)
                .setAuthorName(AUTHOR_NAME_1)
                .setGenre(GENRE_1)
                .setPrice(PRICE_1)
                .setQuantity(QUANTITY_1)
                .setDescription(DESCRIPTION_1)
                .setImageURL(IMAGE_URL_1);
    }

    public static BookDTO getBookDTO2() {
        return new BookDTO()
                .setId(BOOK_ID_2)
                .setTitle(TITLE_2)
                .setAuthorName(AUTHOR_NAME_2)
                .setGenre(GENRE_2)
                .setPrice(PRICE_2)
                .setQuantity(QUANTITY_2)
                .setDescription(DESCRIPTION_2)
                .setImageURL(IMAGE_URL_2);
    }

    public static BookDTO getBookDTO3() {
        return new BookDTO()
                .setId(BOOK_ID_3)
                .setTitle(TITLE_3)
                .setAuthorName(AUTHOR_NAME_3)
                .setGenre(GENRE_3)
                .setPrice(PRICE_3)
                .setQuantity(QUANTITY_3)
                .setDescription(DESCRIPTION_3)
                .setImageURL(IMAGE_URL_3);
    }


}
