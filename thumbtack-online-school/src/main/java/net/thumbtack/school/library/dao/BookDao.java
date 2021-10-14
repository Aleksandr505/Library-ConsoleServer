package net.thumbtack.school.library.dao;

import net.thumbtack.school.library.model.Book;
import net.thumbtack.school.library.model.User;

import java.util.Collection;
import java.util.List;

public interface BookDao {
    void insertBook(User user, Book book);
    void receiveBook(User user, Book book);
    void recallBook(User user, Book book);
    Book getBookById(int id);

    Collection<Book> getAllBooks();
    Collection<Book> getBooksByCategories(List<String> categories, boolean isAll);
    Collection<Book> getBooksByAuthors(List<String> authors, boolean isAll);
    Collection<Book> getBooksByName(String bookName);
}
