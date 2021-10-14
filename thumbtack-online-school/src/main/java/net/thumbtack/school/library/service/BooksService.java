package net.thumbtack.school.library.service;

import com.google.gson.Gson;
import net.thumbtack.school.library.daoimpl.BookDaoImpl;
import net.thumbtack.school.library.daoimpl.UserDaoImpl;
import net.thumbtack.school.library.dto.request.*;
import net.thumbtack.school.library.dto.response.GetBooksDtoResponse;
import net.thumbtack.school.library.exception.ServerErrorCode;
import net.thumbtack.school.library.exception.ServerException;
import net.thumbtack.school.library.mapstruct.BookMapper;
import net.thumbtack.school.library.model.Book;
import net.thumbtack.school.library.model.User;
import net.thumbtack.school.library.server.ServerResponse;

import java.time.LocalDate;
import java.util.Collection;

public class BooksService {

    private final Gson gson = new Gson();
    private final UserDaoImpl userDao = new UserDaoImpl();
    private final BookDaoImpl bookDao = new BookDaoImpl();

    public ServerResponse insertBook(String token, String requestJsonString) {
        try {
            InsertBookDtoRequest insertRequest = ServiceUtils.getClassFromJson(requestJsonString, InsertBookDtoRequest.class);
            User user = userDao.getUserByToken(token);

            userIsNull(user);
            insertValidate(insertRequest);

            Book validBook = BookMapper.INSTANCE.fromInsertDtoToBook(insertRequest);
            validBook.setProvider(user);
            user.getProvidedBooks().add(validBook);
            bookDao.insertBook(user, validBook);
            return new ServerResponse(ServiceUtils.CODE_SUCCESS);
        } catch (ServerException e) {
            return new ServerResponse(ServiceUtils.CODE_FAIL, e.getErrorCode());
        }
    }

    public ServerResponse receiveBook(String token, String requestJsonString) {
        try {
            ReceiveBookDtoRequest receiveRequest = ServiceUtils.getClassFromJson(requestJsonString, ReceiveBookDtoRequest.class);
            User recipient = userDao.getUserByToken(token);
            Book validBook = bookDao.getBookById(receiveRequest.getId());

            userIsNull(recipient);
            bookIsNull(validBook);
            receiveValidate(receiveRequest);

            if (validBook.getProvider().equals(recipient)) {
                throw new ServerException(ServerErrorCode.RECEIVE_INCORRECT);
            }
            recipient.getTakenBooks().add(validBook);
            validBook.setRecipient(recipient);
            validBook.setDeadline(receiveRequest.getDeadline());
            bookDao.receiveBook(recipient, validBook);
            return new ServerResponse(ServiceUtils.CODE_SUCCESS);
        } catch (ServerException e) {
            return new ServerResponse(ServiceUtils.CODE_FAIL, e.getErrorCode());
        }
    }

    public ServerResponse recallBook(String token, String requestJsonString) {
        try {
            RecallBookDtoRequest recallRequest = ServiceUtils.getClassFromJson(requestJsonString, RecallBookDtoRequest.class);
            User user = userDao.getUserByToken(token);
            Book book = bookDao.getBookById(recallRequest.getId());

            userIsNull(user);
            bookIsNull(book);

            if (!book.getProvider().equals(user)) {
                throw new ServerException(ServerErrorCode.RECALL_INCORRECT);
            }
            bookDao.recallBook(user, book);
            return new ServerResponse(ServiceUtils.CODE_SUCCESS);
        } catch (ServerException e) {
            return new ServerResponse(ServiceUtils.CODE_FAIL, e.getErrorCode());
        }
    }

    public ServerResponse getAllBooks() {
        Collection<Book> books = bookDao.getAllBooks();
        return ServiceUtils.getBooksServerResponse(books);
    }

    public ServerResponse getBooksByBookName(String requestJsonString) {
        try {
            GetBooksByBookNameDtoRequest request = ServiceUtils.getClassFromJson(requestJsonString, GetBooksByBookNameDtoRequest.class);
            Collection<Book> books = bookDao.getBooksByName(request.getBookName());
            return ServiceUtils.getBooksServerResponse(books);
        } catch (ServerException e) {
            return new ServerResponse(ServiceUtils.CODE_FAIL, e.getErrorCode());
        }
    }

    public ServerResponse getBooksByAuthors(String requestJsonString) {
        try {
            GetBooksByAuthorsDtoRequest request = ServiceUtils.getClassFromJson(requestJsonString, GetBooksByAuthorsDtoRequest.class);
            Collection<Book> books = bookDao.getBooksByAuthors(request.getAuthors(), request.isAll());
            return ServiceUtils.getBooksServerResponse(books);
        } catch (ServerException e) {
            return new ServerResponse(ServiceUtils.CODE_FAIL, e.getErrorCode());
        }
    }

    public ServerResponse getBooksByCategories(String requestJsonString) {
        try {
            GetBooksByCategoriesDtoRequest request = ServiceUtils.getClassFromJson(requestJsonString, GetBooksByCategoriesDtoRequest.class);
            Collection<Book> books = bookDao.getBooksByCategories(request.getCategories(), request.isAll());
            return ServiceUtils.getBooksServerResponse(books);
        } catch (ServerException e) {
            return new ServerResponse(ServiceUtils.CODE_FAIL, e.getErrorCode());
        }
    }

    //               +++ Валидация данных +++
    private void insertValidate(InsertBookDtoRequest requestBook) throws ServerException {
        if (requestBook.getBookName() == null || requestBook.getBookName().equals("")) {
            throw new ServerException(ServerErrorCode.ABSENT_BOOK_NAME);
        }
        if (requestBook.getAuthors() == null || requestBook.getAuthors().size() == 0) {
            throw new ServerException(ServerErrorCode.ABSENT_AUTHORS);
        }
        if (requestBook.getCategories() == null || requestBook.getCategories().size() == 0) {
            throw new ServerException(ServerErrorCode.ABSENT_CATEGORIES);
        }
    }

    private void receiveValidate(ReceiveBookDtoRequest bookRequest) throws ServerException {
        Book book = bookDao.getBookById(bookRequest.getId());
        if (book.getRecipient() != null) {
            if (book.getDeadline().isAfter(LocalDate.now())) {
                throw new ServerException(ServerErrorCode.BOOK_BUSY);
            }
        }
        if (bookRequest.getDeadline() == null) {
            throw new ServerException(ServerErrorCode.ABSENT_DEADLINE);
        }
        if (bookRequest.getDeadline().isBefore(LocalDate.now())) {
            throw new ServerException(ServerErrorCode.EXPIRED_DEADLINE);
        }
    }

    private void userIsNull(User user) throws ServerException {
        if (user == null) {
            throw new ServerException(ServerErrorCode.USER_OFFLINE);
        }

    }

    private void bookIsNull(Book book) throws ServerException {
        if (book == null) {
            throw new ServerException(ServerErrorCode.NO_BOOKS);
        }
    }

}
