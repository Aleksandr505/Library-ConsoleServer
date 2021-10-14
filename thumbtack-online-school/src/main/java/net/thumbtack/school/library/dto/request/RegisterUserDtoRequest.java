package net.thumbtack.school.library.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserDtoRequest {

    private String firstName;
    private String lastName;
    private String login;
    private String password;

}
