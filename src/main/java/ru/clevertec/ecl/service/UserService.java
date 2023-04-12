package ru.clevertec.ecl.service;

import ru.clevertec.ecl.dto.UserDto;
import java.util.List;

public interface UserService {

    List<UserDto> findAll(Integer pageNo, Integer pageSize, String sortBy);

    UserDto findById(Long id);

    UserDto findUserByHighestCostOfAllOrders();

}
