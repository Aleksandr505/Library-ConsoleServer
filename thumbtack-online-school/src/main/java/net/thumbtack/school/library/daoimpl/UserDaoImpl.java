package net.thumbtack.school.library.daoimpl;

import net.thumbtack.school.library.dao.UserDao;
import net.thumbtack.school.library.database.DataBase;
import net.thumbtack.school.library.exception.ServerException;
import net.thumbtack.school.library.model.User;

public class UserDaoImpl implements UserDao {

    private final DataBase dataBase = DataBase.getDataBase();

    @Override
    public void insertUser(User user) throws ServerException {
        dataBase.insertUser(user);
    }

    @Override
    public User getUserByToken(String token) throws ServerException {
        return dataBase.getUserByToken(token);
    }

    @Override
    public User getUserByLogin(String login) {
        return dataBase.getUserByLogin(login);
    }

    @Override
    public User getUserById(int id) {
        return dataBase.getUserById(id);
    }

    @Override
    public void setUserToken(String token, User user) {
        dataBase.setUserToken(token, user);
    }

    @Override
    public void logoutUserByToken(String token) throws ServerException {
        dataBase.logoutUserByToken(token);
    }

    @Override
    public void deleteUserByToken(String token) throws ServerException {
        dataBase.deleteUserByToken(token);
    }

    @Override
    public void clearDatabase() {
        dataBase.clearDatabase();
    }


}
