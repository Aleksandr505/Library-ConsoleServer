package net.thumbtack.school.library.exception;

public enum ServerErrorCode {

    EMPTY_FIRST_NAME("Поле с именем пустое"),
    EMPTY_LAST_NAME("Поле с фамилией пустое"),
    EMPTY_LOGIN("Поле с логином пустое"),
    EMPTY_PASSWORD("Поле с паролем пустое"),
    LOGIN_BUSY("Пользователь с таким логином уже существует"),
    PASSWORD_SHORT("Пароль должен быть длинной как минимум в 6 символов"),
    WRONG_LOGIN("Неверный логин или такого пользователя не существует"),
    WRONG_PASSWORD("Неверный пароль"),
    ABSENT_TOKEN("Токен пользователя отсутствует"),
    ABSENT_BOOK_NAME("Название книги отсутствует"),
    ABSENT_AUTHORS("Авторы книги отсутствуют"),
    ABSENT_CATEGORIES("Категории книги отсутствуют"),
    ABSENT_DEADLINE("Срок сдачи книги отсутствует"),
    USER_OFFLINE("Этот пользователь не в сети, либо введён неверный токен"),
    BOOK_BUSY("Книга занята"),
    WRONG_JSON("Неверный json"),
    INCORRECT_GET_BOOKS_REQUEST("Данные для получения книг введены неверно"),
    NO_BOOKS("Книги по заданному параметру отсутствуют"),
    EXPIRED_DEADLINE("Срок взятия книги истёк"),
    RECEIVE_INCORRECT("Данный пользователь не может получить книгу"),
    RECALL_INCORRECT("Данный пользователь не может отозвать книгу");


    private final String message;

    ServerErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
