package net.thumbtack.school.library.mapstruct;

import net.thumbtack.school.library.dto.request.RegisterUserDtoRequest;
import net.thumbtack.school.library.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(RegisterUserDtoRequest registerUserDtoRequest);
}
