package net.thumbtack.school.library.mapstruct;

import javax.annotation.processing.Generated;
import net.thumbtack.school.library.dto.request.RegisterUserDtoRequest;
import net.thumbtack.school.library.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-13T00:41:27+0600",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(RegisterUserDtoRequest registerUserDtoRequest) {
        if ( registerUserDtoRequest == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;
        String login = null;
        String password = null;

        firstName = registerUserDtoRequest.getFirstName();
        lastName = registerUserDtoRequest.getLastName();
        login = registerUserDtoRequest.getLogin();
        password = registerUserDtoRequest.getPassword();

        int id = 0;

        User user = new User( id, firstName, lastName, login, password );

        return user;
    }
}
