package net.thumbtack.school.library;

import com.google.gson.Gson;
import net.thumbtack.school.library.dto.request.*;
import net.thumbtack.school.library.dto.response.GetBooksDtoResponse;
import net.thumbtack.school.library.dto.response.LoginUserDtoResponse;
import net.thumbtack.school.library.exception.ServerErrorCode;
import net.thumbtack.school.library.server.Server;
import net.thumbtack.school.library.server.ServerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class TestLibrary {

    Server server = new Server();
    private final Gson gson = new Gson();

    @BeforeEach
    public void testClearDatabase() {
        server.deleteAllUsers();
    }

    @Test
    public void testRegisterUser() {
        RegisterUserDtoRequest registerRequest1 = new RegisterUserDtoRequest(
                "Майк", "Санчез", "SanchezOOP27", "jjksaAh78");
        String jsonRegisterRequest1 = gson.toJson(registerRequest1);
        ServerResponse serverResponseRegister1 = server.registerUser(jsonRegisterRequest1);
        assertEquals(200, serverResponseRegister1.getResponseCode());

        RegisterUserDtoRequest registerRequest2 = new RegisterUserDtoRequest(
                "Рюк", "Лучезарный", "Lunatic1648", "LkhfaSX7ll");
        String jsonRegisterRequest2 = gson.toJson(registerRequest2);
        ServerResponse serverResponseRegister2 = server.registerUser(jsonRegisterRequest2);
        assertEquals(200, serverResponseRegister2.getResponseCode());

        RegisterUserDtoRequest registerRequest3 = new RegisterUserDtoRequest(
                "Хьюстон", "Питерсон", "", "cMzkldhaq47");
        String jsonRegisterRequest3 = gson.toJson(registerRequest3);
        ServerResponse serverResponseRegister3 = server.registerUser(jsonRegisterRequest3);
        assertEquals(ServerErrorCode.EMPTY_LOGIN, serverResponseRegister3.getErrorCode());

        RegisterUserDtoRequest registerRequest4 = new RegisterUserDtoRequest(
                "Стив", "Павлович", "Pavlovich25", "12");
        String jsonRegisterRequest4 = gson.toJson(registerRequest4);
        ServerResponse serverResponseRegister4 = server.registerUser(jsonRegisterRequest4);
        assertEquals(ServerErrorCode.PASSWORD_SHORT, serverResponseRegister4.getErrorCode());

        RegisterUserDtoRequest registerRequest5 = new RegisterUserDtoRequest(
                "Майк", "Санчез", "SanchezOOP27", "jjksaAh78");
        String jsonRegisterRequest5 = gson.toJson(registerRequest5);
        ServerResponse serverResponseRegister5 = server.registerUser(jsonRegisterRequest5);
        assertEquals(ServerErrorCode.LOGIN_BUSY, serverResponseRegister5.getErrorCode());

        RegisterUserDtoRequest registerRequest6 = new RegisterUserDtoRequest(
                "", "", "", "");
        String jsonRegisterRequest6 = gson.toJson(registerRequest6);
        ServerResponse serverResponseRegister6 = server.registerUser(jsonRegisterRequest6);
        assertEquals(ServerErrorCode.EMPTY_FIRST_NAME, serverResponseRegister6.getErrorCode());
    }

    @Test
    public void testLoginAndLogoutUser() {
        RegisterUserDtoRequest registerRequest1 = new RegisterUserDtoRequest(
                "Стив", "Павлович", "Pavlovich25", "753qsc951");
        String jsonRegisterRequest1 = gson.toJson(registerRequest1);
        ServerResponse serverResponseRegister1 = server.registerUser(jsonRegisterRequest1);
        assertEquals(200, serverResponseRegister1.getResponseCode());

        RegisterUserDtoRequest registerRequest2 = new RegisterUserDtoRequest(
                "Андрей", "Викторович", "AnvictorUUID", "HokU416POg");
        String jsonRegisterRequest2 = gson.toJson(registerRequest2);
        ServerResponse serverResponseRegister2 = server.registerUser(jsonRegisterRequest2);
        assertEquals(200, serverResponseRegister2.getResponseCode());

        LoginUserDtoRequest loginRequest1 = new LoginUserDtoRequest("Pavlovich25", "753qsc951");
        String jsonLoginRequest1 = gson.toJson(loginRequest1);
        ServerResponse serverResponseLogin1 = server.loginUser(jsonLoginRequest1);
        assertEquals(200, serverResponseLogin1.getResponseCode());

        LoginUserDtoRequest loginRequest2 = new LoginUserDtoRequest("AnvictorUUID", "404");
        String jsonLoginRequest2 = gson.toJson(loginRequest2);
        ServerResponse serverResponseLogin2 = server.loginUser(jsonLoginRequest2);
        assertEquals(ServerErrorCode.WRONG_PASSWORD, serverResponseLogin2.getErrorCode());

        LoginUserDtoRequest loginRequest3 = new LoginUserDtoRequest("AnvictorUUID", "HokU416POg");
        String jsonLoginRequest3 = gson.toJson(loginRequest3);
        ServerResponse serverResponseLogin3 = server.loginUser(jsonLoginRequest3);
        assertEquals(200, serverResponseLogin3.getResponseCode());

        LoginUserDtoResponse loginResponse1 = gson.fromJson(serverResponseLogin1.getResponseData(), LoginUserDtoResponse.class);
        assertNotNull(loginResponse1.getToken());

        ServerResponse serverResponseLogout1 = server.logoutUser(loginResponse1.getToken());
        assertEquals(200, serverResponseLogout1.getResponseCode());

        LoginUserDtoRequest loginRequest4 = new LoginUserDtoRequest("Pavlovich25", "753qsc951");
        String jsonLoginRequest4 = gson.toJson(loginRequest4);
        ServerResponse serverResponseLogin4 = server.loginUser(jsonLoginRequest4);
        assertEquals(200, serverResponseLogin4.getResponseCode());

        LoginUserDtoResponse loginResponse4 = gson.fromJson(serverResponseLogin4.getResponseData(), LoginUserDtoResponse.class);
        assertNotNull(loginResponse4.getToken());
        assertNotSame(loginResponse1.getToken(), loginResponse4.getToken());

        LoginUserDtoResponse loginResponse2 = gson.fromJson(serverResponseLogin3.getResponseData(), LoginUserDtoResponse.class);
        assertNotNull(loginResponse2.getToken());
        LoginUserDtoResponse loginResponse3 = gson.fromJson(serverResponseLogin4.getResponseData(), LoginUserDtoResponse.class);
        assertNotNull(loginResponse3.getToken());

        ServerResponse serverResponseLogout2 = server.logoutUser(loginResponse2.getToken());
        assertEquals(200, serverResponseLogout2.getResponseCode());
        ServerResponse serverResponseLogout3 = server.logoutUser(loginResponse3.getToken());
        assertEquals(200, serverResponseLogout3.getResponseCode());

        LoginUserDtoRequest loginRequest5 = new LoginUserDtoRequest("WrongLogin", "9999999");
        String jsonLoginRequest5 = gson.toJson(loginRequest5);
        ServerResponse serverResponseLogin5 = server.loginUser(jsonLoginRequest5);
        assertEquals(ServerErrorCode.WRONG_LOGIN, serverResponseLogin5.getErrorCode());

        LoginUserDtoRequest loginRequest6 = new LoginUserDtoRequest("Pavlovich25", "WrongPassword");
        String jsonLoginRequest6 = gson.toJson(loginRequest6);
        ServerResponse serverResponseLogin6 = server.loginUser(jsonLoginRequest6);
        assertEquals(ServerErrorCode.WRONG_PASSWORD, serverResponseLogin6.getErrorCode());

        ServerResponse serverResponseLogout4 = server.logoutUser(loginResponse3.getToken());
        assertEquals(ServerErrorCode.USER_OFFLINE, serverResponseLogout4.getErrorCode());

        ServerResponse serverResponseDeleteUser1 = server.deleteLoggedUser(loginResponse3.getToken());
        assertEquals(ServerErrorCode.USER_OFFLINE, serverResponseDeleteUser1.getErrorCode());

        LoginUserDtoRequest loginRequest7 = new LoginUserDtoRequest("Pavlovich25", "753qsc951");
        String jsonLoginRequest7 = gson.toJson(loginRequest7);
        ServerResponse serverResponseLogin7 = server.loginUser(jsonLoginRequest7);
        assertEquals(200, serverResponseLogin7.getResponseCode());
        LoginUserDtoResponse loginResponse5 = gson.fromJson(serverResponseLogin7.getResponseData(), LoginUserDtoResponse.class);
        ServerResponse serverResponseDeleteUser2 = server.deleteLoggedUser(loginResponse5.getToken());
        assertEquals(200, serverResponseDeleteUser2.getResponseCode());
    }

    @Test
    public void testBooks() {
        RegisterUserDtoRequest registerRequest1 = new RegisterUserDtoRequest(
                "Шурик", "Владимирович", "Shurik555", "juSti5ve");
        String jsonRegisterRequest1 = gson.toJson(registerRequest1);
        ServerResponse serverResponseRegister1 = server.registerUser(jsonRegisterRequest1);
        assertEquals(200, serverResponseRegister1.getResponseCode());

        RegisterUserDtoRequest registerRequest2 = new RegisterUserDtoRequest(
                "Данил", "Маркович", "Danilvector", "abs0Lut1789");
        String jsonRegisterRequest2 = gson.toJson(registerRequest2);
        ServerResponse serverResponseRegister2 = server.registerUser(jsonRegisterRequest2);
        assertEquals(200, serverResponseRegister2.getResponseCode());

        LoginUserDtoRequest loginRequest1 = new LoginUserDtoRequest("Shurik555", "juSti5ve");
        String jsonLoginRequest1 = gson.toJson(loginRequest1);
        ServerResponse serverResponseLogin1 = server.loginUser(jsonLoginRequest1);
        LoginUserDtoResponse loginResponse1 = gson.fromJson(serverResponseLogin1.getResponseData(), LoginUserDtoResponse.class);

        LoginUserDtoRequest loginRequest2 = new LoginUserDtoRequest("Danilvector", "abs0Lut1789");
        String jsonLoginRequest2 = gson.toJson(loginRequest2);
        ServerResponse serverResponseLogin2 = server.loginUser(jsonLoginRequest2);
        LoginUserDtoResponse loginResponse2 = gson.fromJson(serverResponseLogin2.getResponseData(), LoginUserDtoResponse.class);


        List<String> authors = new ArrayList<>();
        Collections.addAll(authors, "Дмитрий Глуховский, Романов Сергеевич");
        Set<String> categories = new HashSet<>();
        Collections.addAll(categories, "Fantasy", "Horror", "Apocalypse");
        InsertBookDtoRequest bookRequest1 = new InsertBookDtoRequest("Метро 2033", authors, categories);
        String jsonBook1 = gson.toJson(bookRequest1);
        ServerResponse serverResponseBook1 = server.addBook(loginResponse1.getToken(), jsonBook1);
        assertEquals(200, serverResponseBook1.getResponseCode());

        authors.clear();
        categories.clear();
        Collections.addAll(authors, "Лев Толстой");
        Collections.addAll(categories, "History", "Biography", "Romantic");
        InsertBookDtoRequest bookRequest2 = new InsertBookDtoRequest("Война и мир", authors, categories);
        String jsonBook2 = gson.toJson(bookRequest2);
        ServerResponse serverResponseBook2 = server.addBook(loginResponse1.getToken(), jsonBook2);
        assertEquals(200, serverResponseBook2.getResponseCode());

        authors.clear();
        categories.clear();
        Collections.addAll(authors, "Николай Карамзин");
        Collections.addAll(categories, "History");
        InsertBookDtoRequest bookRequest3 = new InsertBookDtoRequest("История государства Российского", authors, categories);
        String jsonBook3 = gson.toJson(bookRequest3);
        ServerResponse serverResponseBook3 = server.addBook(loginResponse2.getToken(), jsonBook3);
        assertEquals(200, serverResponseBook3.getResponseCode());

        ReceiveBookDtoRequest bookRequest4 = new ReceiveBookDtoRequest(1, LocalDate.now().plusDays(10));
        String jsonBook4 = gson.toJson(bookRequest4);
        ServerResponse serverResponseBook4 = server.receiveBook(loginResponse2.getToken(), jsonBook4);
        assertEquals(200, serverResponseBook4.getResponseCode());

        ReceiveBookDtoRequest bookRequest6 = new ReceiveBookDtoRequest(1, LocalDate.now().plusDays(10));
        String jsonBook6 = gson.toJson(bookRequest6);
        ServerResponse serverResponseBook6 = server.receiveBook(loginResponse2.getToken(), jsonBook6);
        assertEquals(ServerErrorCode.BOOK_BUSY, serverResponseBook6.getErrorCode());
        assertEquals(400, serverResponseBook6.getResponseCode());

        ReceiveBookDtoRequest bookRequest7 = new ReceiveBookDtoRequest(2, LocalDate.now().plusDays(15));
        String jsonBook7 = gson.toJson(bookRequest7);
        ServerResponse serverResponseBook7 = server.receiveBook(loginResponse2.getToken(), jsonBook7);
        assertEquals(200, serverResponseBook7.getResponseCode());

        RecallBookDtoRequest bookDtoRequest8 = new RecallBookDtoRequest(1);
        String jsonBook8 = gson.toJson(bookDtoRequest8);
        ServerResponse serverResponseBook8 = server.recallBook(loginResponse1.getToken(), jsonBook8);
        assertEquals(200, serverResponseBook8.getResponseCode());

        ReceiveBookDtoRequest bookRequest9 = new ReceiveBookDtoRequest(1, LocalDate.now().plusDays(20));
        String jsonBook9 = gson.toJson(bookRequest9);
        ServerResponse serverResponseBook9 = server.receiveBook(loginResponse2.getToken(), jsonBook9);
        assertEquals(400, serverResponseBook9.getResponseCode());
        assertEquals(ServerErrorCode.NO_BOOKS, serverResponseBook9.getErrorCode());

        RecallBookDtoRequest bookDtoRequest10 = new RecallBookDtoRequest(3);
        String jsonBook10 = gson.toJson(bookDtoRequest10);
        ServerResponse serverResponseBook10 = server.recallBook(loginResponse1.getToken(), jsonBook10);
        assertEquals(400, serverResponseBook10.getResponseCode());
        assertEquals(ServerErrorCode.RECALL_INCORRECT, serverResponseBook10.getErrorCode());

        ReceiveBookDtoRequest bookRequest11 = new ReceiveBookDtoRequest(3, LocalDate.now().plusDays(20));
        String jsonBook11 = gson.toJson(bookRequest11);
        ServerResponse serverResponseBook11 = server.receiveBook(loginResponse2.getToken(), jsonBook11);
        assertEquals(400, serverResponseBook11.getResponseCode());
        assertEquals(ServerErrorCode.RECEIVE_INCORRECT, serverResponseBook11.getErrorCode());
    }

    @Test
    public void testGetBooks() {
        RegisterUserDtoRequest registerRequest1 = new RegisterUserDtoRequest(
                "Шурик", "Владимирович", "Shurik555", "juSti5ve");
        String jsonRegisterRequest1 = gson.toJson(registerRequest1);
        ServerResponse serverResponseRegister1 = server.registerUser(jsonRegisterRequest1);
        assertEquals(200, serverResponseRegister1.getResponseCode());

        LoginUserDtoRequest loginRequest1 = new LoginUserDtoRequest("Shurik555", "juSti5ve");
        String jsonLoginRequest1 = gson.toJson(loginRequest1);
        ServerResponse serverResponseLogin1 = server.loginUser(jsonLoginRequest1);
        LoginUserDtoResponse loginResponse1 = gson.fromJson(serverResponseLogin1.getResponseData(), LoginUserDtoResponse.class);

        List<String> authors = new ArrayList<>();
        Set<String> categories = new HashSet<>();
        Collections.addAll(authors, "Глуховский Д.", "Романов С.", "Левицкий С.");
        Collections.addAll(categories, "Fantasy", "Horror", "Apocalypse");
        InsertBookDtoRequest bookRequest1 = new InsertBookDtoRequest("Метро 2033", authors, categories);
        String jsonBook1 = gson.toJson(bookRequest1);
        ServerResponse serverResponseBook1 = server.addBook(loginResponse1.getToken(), jsonBook1);
        assertEquals(200, serverResponseBook1.getResponseCode());

        authors.clear();
        categories.clear();
        Collections.addAll(authors, "Лев Толстой");
        Collections.addAll(categories, "History", "Biography", "Romantic");
        InsertBookDtoRequest bookRequest2 = new InsertBookDtoRequest("Война и мир", authors, categories);
        String jsonBook2 = gson.toJson(bookRequest2);
        ServerResponse serverResponseBook2 = server.addBook(loginResponse1.getToken(), jsonBook2);
        assertEquals(200, serverResponseBook2.getResponseCode());

        authors.clear();
        categories.clear();
        Collections.addAll(authors, "Карамзин Н.");
        Collections.addAll(categories, "History", "Education");
        InsertBookDtoRequest bookRequest3 = new InsertBookDtoRequest("История государства Российского", authors, categories);
        String jsonBook3 = gson.toJson(bookRequest3);
        ServerResponse serverResponseBook3 = server.addBook(loginResponse1.getToken(), jsonBook3);
        assertEquals(200, serverResponseBook3.getResponseCode());

        authors.clear();
        categories.clear();
        Collections.addAll(authors, "Левицкий С.", "Зорич А.", "Первушин А.", "Акимов О.");
        Collections.addAll(categories, "Fantasy", "Horror", "Apocalypse");
        InsertBookDtoRequest bookRequest4 = new InsertBookDtoRequest("S.T.A.L.K.E.R.", authors, categories);
        String jsonBook4 = gson.toJson(bookRequest4);
        ServerResponse serverResponseBook4 = server.addBook(loginResponse1.getToken(), jsonBook4);
        assertEquals(200, serverResponseBook4.getResponseCode());


        ServerResponse getAllBooks = server.getAllBooks();
        assertEquals(200, getAllBooks.getResponseCode());
        GetBooksDtoResponse booksResponse1 = gson.fromJson(getAllBooks.getResponseData(), GetBooksDtoResponse.class);
        assertEquals(4, booksResponse1.getBooks().size());

        List<String> categoriesRequest1 = new ArrayList<>();
        categoriesRequest1.add("History");
        GetBooksByCategoriesDtoRequest bookRequest12 = new GetBooksByCategoriesDtoRequest(true, categoriesRequest1);
        String jsonGetBooks2 = gson.toJson(bookRequest12);
        ServerResponse getAllBooksByCategories1 = server.getBooksByCategories(jsonGetBooks2);
        assertEquals(400, getAllBooksByCategories1.getResponseCode());
        assertEquals(ServerErrorCode.NO_BOOKS, getAllBooksByCategories1.getErrorCode());

        List<String> categoriesRequest2 = new ArrayList<>();
        categoriesRequest2.add("History");
        GetBooksByCategoriesDtoRequest bookRequest14 = new GetBooksByCategoriesDtoRequest(false, categoriesRequest2);
        String jsonGetBooks4 = gson.toJson(bookRequest14);
        ServerResponse getAllBooksByCategories2 = server.getBooksByCategories(jsonGetBooks4);
        assertEquals(200, getAllBooksByCategories2.getResponseCode());
        GetBooksDtoResponse booksResponse3 = gson.fromJson(getAllBooksByCategories2.getResponseData(), GetBooksDtoResponse.class);
        assertEquals(2, booksResponse3.getBooks().size());

        List<String> categoriesRequest3 = new ArrayList<>();
        Collections.addAll(categoriesRequest3, "Fantasy", "Horror", "Apocalypse");
        GetBooksByCategoriesDtoRequest bookRequest15 = new GetBooksByCategoriesDtoRequest(true, categoriesRequest3);
        String jsonGetBooks5 = gson.toJson(bookRequest15);
        ServerResponse getAllBooksByCategories3 = server.getBooksByCategories(jsonGetBooks5);
        assertEquals(200, getAllBooksByCategories3.getResponseCode());
        GetBooksDtoResponse booksResponse4 = gson.fromJson(getAllBooksByCategories3.getResponseData(), GetBooksDtoResponse.class);
        assertEquals(2, booksResponse4.getBooks().size());

        List<String> authorsRequest1 = new ArrayList<>();
        Collections.addAll(authorsRequest1, "Левицкий С.");
        GetBooksByAuthorsDtoRequest bookRequest16 = new GetBooksByAuthorsDtoRequest(false, authorsRequest1);
        String jsonGetBooks6 = gson.toJson(bookRequest16);
        ServerResponse getAllBooksByAuthors1 = server.getBooksByAuthors(jsonGetBooks6);
        assertEquals(200, getAllBooksByAuthors1.getResponseCode());
        GetBooksDtoResponse booksResponse5 = gson.fromJson(getAllBooksByAuthors1.getResponseData(), GetBooksDtoResponse.class);
        assertEquals(2, booksResponse5.getBooks().size());

        List<String> authorsRequest2 = new ArrayList<>();
        Collections.addAll(authorsRequest2, "Левицкий С.");
        GetBooksByAuthorsDtoRequest bookRequest17 = new GetBooksByAuthorsDtoRequest(true, authorsRequest2);
        String jsonGetBooks7 = gson.toJson(bookRequest17);
        ServerResponse getAllBooksByAuthors2 = server.getBooksByAuthors(jsonGetBooks7);
        assertEquals(400, getAllBooksByAuthors2.getResponseCode());
        assertEquals(ServerErrorCode.NO_BOOKS, getAllBooksByAuthors2.getErrorCode());

        GetBooksByBookNameDtoRequest bookRequest13 = new GetBooksByBookNameDtoRequest("Hello 777");
        String jsonGetBooks3 = gson.toJson(bookRequest13);
        ServerResponse getAllBooksByName = server.getBooksByBookName(jsonGetBooks3);
        assertEquals(400, getAllBooksByName.getResponseCode());
        assertEquals(ServerErrorCode.NO_BOOKS, getAllBooksByName.getErrorCode());
    }

}
