package net.thumbtack.school.library.server;

import net.thumbtack.school.library.exception.ServerErrorCode;

public class ServerResponse {

    private int responseCode;
    private String responseData;
    private ServerErrorCode errorCode;

    public ServerResponse(int responseCode) {
        this.responseCode = responseCode;
    }

    public ServerResponse(int responseCode, String responseData) {
        this(responseCode);
        this.responseData = responseData;
    }

    public ServerResponse(int responseCode, ServerErrorCode errorCode) {
        this(responseCode);
        this.errorCode = errorCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public ServerErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ServerErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
