package net.thumbtack.school.library.server;

import net.thumbtack.school.library.service.BooksService;
import net.thumbtack.school.library.service.UserService;

public class Server {

    private final UserService userService = new UserService();
    private final BooksService booksService = new BooksService();

    public ServerResponse registerUser(String requestJsonString) {
        return userService.registerUser(requestJsonString);
    }

    public ServerResponse loginUser(String requestJsonString) {
        return userService.loginUser(requestJsonString);
    }

    public ServerResponse logoutUser(String token) {
        return userService.logoutUser(token);
    }

    public ServerResponse deleteLoggedUser(String token) {
        return userService.deleteLoggedUser(token);
    }

    public ServerResponse deleteAllUsers() {
        return userService.deleteAllUsers();
    }

    public ServerResponse addBook(String token, String requestJsonString) {
        return booksService.insertBook(token, requestJsonString);
    }

    public ServerResponse receiveBook(String token, String requestJsonString) {
        return booksService.receiveBook(token, requestJsonString);
    }

    public ServerResponse recallBook(String token, String requestJsonString) {
        return booksService.recallBook(token, requestJsonString);
    }

    public ServerResponse getAllBooks() {
        return booksService.getAllBooks();
    }

    public ServerResponse getBooksByBookName(String requestJsonString) {
        return booksService.getBooksByBookName(requestJsonString);
    }

    public ServerResponse getBooksByAuthors(String requestJsonString) {
        return booksService.getBooksByAuthors(requestJsonString);
    }

    public ServerResponse getBooksByCategories(String requestJsonString) {
        return booksService.getBooksByCategories(requestJsonString);
    }
}
