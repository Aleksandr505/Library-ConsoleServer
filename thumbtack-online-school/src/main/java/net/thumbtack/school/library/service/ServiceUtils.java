package net.thumbtack.school.library.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.library.dto.response.GetBooksDtoResponse;
import net.thumbtack.school.library.exception.ServerErrorCode;
import net.thumbtack.school.library.exception.ServerException;
import net.thumbtack.school.library.model.Book;
import net.thumbtack.school.library.server.ServerResponse;

import java.util.Collection;

public class ServiceUtils {

    protected static final int CODE_SUCCESS = 200;
    protected static final int CODE_FAIL = 400;

    private static final Gson gson = new Gson();

    protected static <T> T getClassFromJson(String requestJson, Class<T> template) throws ServerException {
        try {
            return gson.fromJson(requestJson, template);
        } catch (JsonSyntaxException e) {
            throw new ServerException(ServerErrorCode.WRONG_JSON);
        }
    }

    protected static ServerResponse getBooksServerResponse(Collection<Book> books) {
        if (books != null && books.size() > 0) {
            GetBooksDtoResponse response = new GetBooksDtoResponse();
            response.setBooks(books);
            return new ServerResponse(ServiceUtils.CODE_SUCCESS, gson.toJson(response));
        }
        return new ServerResponse(ServiceUtils.CODE_FAIL, ServerErrorCode.NO_BOOKS);
    }

}
