package net.thumbtack.school.library.dao;

import net.thumbtack.school.library.exception.ServerException;
import net.thumbtack.school.library.model.User;

public interface UserDao {
    void insertUser(User user) throws ServerException;
    User getUserByToken(String token) throws ServerException;
    User getUserByLogin(String login);
    User getUserById(int id);
    void setUserToken(String token, User user);
    void logoutUserByToken(String token) throws ServerException;
    void deleteUserByToken(String token) throws ServerException;
    void clearDatabase();
}
