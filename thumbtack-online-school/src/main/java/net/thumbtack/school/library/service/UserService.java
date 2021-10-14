package net.thumbtack.school.library.service;

import com.google.gson.Gson;
import net.thumbtack.school.library.dao.UserDao;
import net.thumbtack.school.library.daoimpl.UserDaoImpl;
import net.thumbtack.school.library.dto.request.LoginUserDtoRequest;
import net.thumbtack.school.library.dto.request.RegisterUserDtoRequest;
import net.thumbtack.school.library.dto.response.LoginUserDtoResponse;
import net.thumbtack.school.library.exception.ServerErrorCode;
import net.thumbtack.school.library.exception.ServerException;
import net.thumbtack.school.library.mapstruct.UserMapper;
import net.thumbtack.school.library.model.User;
import net.thumbtack.school.library.server.ServerResponse;

import java.util.UUID;


public class UserService {

    private final UserDao userDao = new UserDaoImpl();
    private final Gson gson = new Gson();

    public ServerResponse registerUser(String requestJsonString) {
        try {
            RegisterUserDtoRequest registerRequest = ServiceUtils.getClassFromJson(requestJsonString, RegisterUserDtoRequest.class);
            registerValidate(registerRequest);

            User user = UserMapper.INSTANCE.toUser(registerRequest);
            userDao.insertUser(user);
            return new ServerResponse(ServiceUtils.CODE_SUCCESS);
        } catch (ServerException e) {
            return new ServerResponse(ServiceUtils.CODE_FAIL, e.getErrorCode());
        }
    }

    public ServerResponse loginUser(String requestJsonString) {
        try {
            LoginUserDtoRequest loginUserDtoRequest = ServiceUtils.getClassFromJson(requestJsonString, LoginUserDtoRequest.class);
            loginValidate(loginUserDtoRequest);

            String token = UUID.randomUUID().toString();
            User user = userDao.getUserByLogin(loginUserDtoRequest.getLogin());
            userDao.setUserToken(token, user);
            LoginUserDtoResponse response = new LoginUserDtoResponse(token);
            return new ServerResponse(ServiceUtils.CODE_SUCCESS, gson.toJson(response));
        } catch (ServerException e) {
            return new ServerResponse(ServiceUtils.CODE_FAIL, e.getErrorCode());
        }
    }

    public ServerResponse logoutUser(String token) {
        try {
            userDao.logoutUserByToken(token);
            return new ServerResponse(ServiceUtils.CODE_SUCCESS);
        } catch (ServerException e) {
            return new ServerResponse(ServiceUtils.CODE_FAIL, e.getErrorCode());
        }
    }

    public ServerResponse deleteLoggedUser(String token) {
        try {
            userDao.deleteUserByToken(token);
            return new ServerResponse(ServiceUtils.CODE_SUCCESS);
        } catch (ServerException e) {
            return new ServerResponse(ServiceUtils.CODE_FAIL, e.getErrorCode());
        }
    }

    public ServerResponse deleteAllUsers() {
        userDao.clearDatabase();
        return new ServerResponse(ServiceUtils.CODE_SUCCESS);
    }

//                         *** валидация данных ***
    private void registerValidate(RegisterUserDtoRequest userRequest) throws ServerException {
        if (userRequest.getFirstName() == null || userRequest.getFirstName().equals("")) {
            throw new ServerException(ServerErrorCode.EMPTY_FIRST_NAME);
        }
        if (userRequest.getLastName() == null || userRequest.getLastName().equals("")) {
            throw new ServerException(ServerErrorCode.EMPTY_LAST_NAME);
        }
        if (userRequest.getLogin() == null || userRequest.getLogin().equals("")) {
            throw new ServerException(ServerErrorCode.EMPTY_LOGIN);
        }
        if (userRequest.getPassword() == null || userRequest.getPassword().equals("")) {
            throw new ServerException(ServerErrorCode.EMPTY_PASSWORD);
        }

        if (userRequest.getPassword().length() < 6) {
            throw new ServerException(ServerErrorCode.PASSWORD_SHORT);
        }
    }

    private void loginValidate(LoginUserDtoRequest userRequest) throws ServerException {
        User user = userDao.getUserByLogin(userRequest.getLogin());
        if (user == null) {
            throw new ServerException(ServerErrorCode.WRONG_LOGIN);
        }
        if (!user.getPassword().equals(userRequest.getPassword())) {
            throw new ServerException(ServerErrorCode.WRONG_PASSWORD);
        }
    }

}
