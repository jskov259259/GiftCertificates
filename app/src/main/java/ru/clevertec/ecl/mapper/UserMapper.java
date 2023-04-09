package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToUser(UserDto userDto);

    UserDto userToDto(User user);
}