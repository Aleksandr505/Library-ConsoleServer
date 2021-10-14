package net.thumbtack.school.library.database;

import net.thumbtack.school.library.exception.ServerErrorCode;
import net.thumbtack.school.library.exception.ServerException;
import net.thumbtack.school.library.model.Book;
import net.thumbtack.school.library.model.User;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import java.util.*;

public final class DataBase {

    private static DataBase dataBase;

    private int autoIncrementUsers;
    private int autoIncrementBooks;

    private final Map<Integer, User> idToUser;
    private final Map<String, User> registeredUsers;
    private final BidiMap<String, User> loggedUsers;

    private final Map<Integer, Book> idToBook;
    private final MultiValuedMap<User, Book> allBooks;
    private final MultiValuedMap<User, Book> busyBooks;

    private final MultiValuedMap<String, Book> mapBookNames;
    private final MultiValuedMap<String, Book> mapAuthors;
    private final MultiValuedMap<String, Book> mapCategories;


    private DataBase() {
        idToUser = new HashMap<>();
        registeredUsers = new HashMap<>();
        loggedUsers = new DualHashBidiMap<>();
        idToBook = new HashMap<>();
        allBooks = new HashSetValuedHashMap<>();
        busyBooks = new HashSetValuedHashMap<>();
        mapBookNames = new HashSetValuedHashMap<>();
        mapAuthors = new HashSetValuedHashMap<>();
        mapCategories = new HashSetValuedHashMap<>();
    }

    public static DataBase getDataBase() {
        if (dataBase == null) {
            dataBase = new DataBase();
        }
        return dataBase;
    }

    public void insertUser(User user) throws ServerException {
        User busyUser = registeredUsers.putIfAbsent(user.getLogin(), user);
        if (busyUser == null) {
            autoIncrementUsers++;
            user.setId(autoIncrementUsers);
            idToUser.put(autoIncrementUsers, user);
        } else {
            throw new ServerException(ServerErrorCode.LOGIN_BUSY);
        }
    }

    public User getUserById(int id) {
        return idToUser.get(id);
    }

    public User getUserByLogin(String login) {
        return registeredUsers.get(login);
    }

    public void setUserToken(String token, User user) {
        if (loggedUsers.containsValue(user)) {
            loggedUsers.remove(loggedUsers.getKey(user));
        }
        loggedUsers.put(token, user);
    }

    public void logoutUserByToken(String token) throws ServerException {
        if (loggedUsers.remove(token) == null) {
            throw new ServerException(ServerErrorCode.USER_OFFLINE);
        }
    }

    public void deleteUserByToken(String token) throws ServerException {
        User user = loggedUsers.get(token);
        if (user != null) {
            autoIncrementUsers--;
            loggedUsers.remove(token);
            idToUser.remove(user.getId());
            registeredUsers.remove(user.getLogin());

            Collection<Book> books =  allBooks.remove(user);
            busyBooks.remove(user);
            for (Book book : books) {
                if(idToBook.remove(book.getId()) != null) {
                    autoIncrementBooks--;
                }
            }
        } else {
            throw new ServerException(ServerErrorCode.USER_OFFLINE);
        }
    }

    public void clearDatabase() {
        autoIncrementUsers = 0;
        autoIncrementBooks = 0;
        idToUser.clear();
        registeredUsers.clear();
        loggedUsers.clear();
        idToBook.clear();
        allBooks.clear();
        busyBooks.clear();
        mapBookNames.clear();
        mapAuthors.clear();
        mapCategories.clear();
    }

    public User getUserByToken(String token) throws ServerException {
        if (token == null) {
            throw new ServerException(ServerErrorCode.ABSENT_TOKEN);
        }
        return loggedUsers.get(token);
    }

    public Book getBookById(int id) {
        return idToBook.get(id);
    }

    public void insertBook(User user, Book book) {
        autoIncrementBooks++;
        book.setId(autoIncrementBooks);

        idToBook.put(autoIncrementBooks, book);
        allBooks.put(user, book);

        mapBookNames.put(book.getBookName(), book);
        List<String> authors = book.getAuthors();
        for (String a : authors) {
            mapAuthors.put(a, book);
        }
        Set<String> categories = book.getCategories();
        for (String c : categories) {
            mapCategories.put(c, book);
        }
    }

    public void receiveBook(User user, Book book)  {
        busyBooks.put(user, book);
    }

    public void recallBook(User user, Book book) {
        busyBooks.get(book.getRecipient()).remove(book);
        allBooks.get(user).remove(book);
        idToBook.remove(book.getId());

        mapBookNames.removeMapping(book.getBookName(), book);
        List<String> authors = book.getAuthors();
        for (String a : authors) {
            mapAuthors.removeMapping(a, book);
        }
        Set<String> categories = book.getCategories();
        for (String c : categories) {
            mapCategories.removeMapping(c, book);
        }
    }


    public Collection<Book> getAllBooks() {
        return allBooks.values();
    }

    public Set<Book> getBooksByCategories(List<String> categories, boolean isAll) {
        Set<Book> result = new HashSet<>();
        if (isAll) {
            Collection<Book> books = mapCategories.get(categories.get(0));
            for (Book book : books) {
                if (categories.containsAll(book.getCategories())) {
                    result.add(book);
                }
            }
            return result;
        }

        for (String c : categories) {
            result.addAll(mapCategories.get(c));
        }
        return result;
    }

    public Set<Book> getBooksByAuthors(List<String> authors, boolean isAll) {
        Set<Book> result = new HashSet<>();
        if (isAll) {
            Collection<Book> books = mapAuthors.get(authors.get(0));
            for (Book book : books) {
                if (authors.containsAll(book.getAuthors())) {
                    result.add(book);
                }
            }
            return result;
        }

        for (String a : authors) {
            result.addAll(mapAuthors.get(a));
        }
        return result;
    }

    public Collection<Book> getBooksByName(String bookName) {
        return mapBookNames.get(bookName);
    }


}
