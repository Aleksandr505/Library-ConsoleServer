package net.thumbtack.school.library.exception;

// Если это исключение, то имя совсем неудачное.
// ServerException, например.
// Никакое это не DTO
// и хранить тут нужно не String, а ServerErrorCode (см. Задания 8, 10)
// и его сделать надо

public class ServerException extends Exception {

    private final ServerErrorCode errorCode;

    public ServerException(ServerErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ServerErrorCode getErrorCode() {
        return errorCode;
    }
}
