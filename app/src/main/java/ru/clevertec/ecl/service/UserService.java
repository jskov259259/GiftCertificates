package ru.clevertec.ecl.service;

import ru.clevertec.ecl.dto.*;

import java.util.*;

public interface UserService {

    List<UserDto> findAll(Integer pageNo, Integer pageSize, String sortBy);

    UserDto findById(Long id);

}
