package net.thumbtack.school.library.daoimpl;

import net.thumbtack.school.library.dao.BookDao;
import net.thumbtack.school.library.database.DataBase;
import net.thumbtack.school.library.model.Book;
import net.thumbtack.school.library.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class BookDaoImpl implements BookDao {

    private final DataBase dataBase = DataBase.getDataBase();

    @Override
    public void insertBook(User user, Book book) {
        dataBase.insertBook(user, book);
    }

    @Override
    public void receiveBook(User user, Book book) {
        dataBase.receiveBook(user, book);
    }

    @Override
    public void recallBook(User user, Book book) {
        dataBase.recallBook(user, book);
    }

    @Override
    public Book getBookById(int id) {
        return dataBase.getBookById(id);
    }

    @Override
    public Collection<Book> getAllBooks() {
        return dataBase.getAllBooks();
    }

    @Override
    public Collection<Book> getBooksByCategories(List<String> categories, boolean isAll) {
        return dataBase.getBooksByCategories(categories, isAll);
    }

    @Override
    public Collection<Book> getBooksByAuthors(List<String> authors, boolean isAll) {
        return dataBase.getBooksByAuthors(authors, isAll);
    }

    @Override
    public Collection<Book> getBooksByName(String bookName) {
        return dataBase.getBooksByName(bookName);
    }
}
