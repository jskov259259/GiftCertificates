package ru.clevertec.ecl.service.impl;

import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import ru.clevertec.ecl.dto.*;
import ru.clevertec.ecl.exceptions.*;
import ru.clevertec.ecl.mapper.*;
import ru.clevertec.ecl.model.*;
import ru.clevertec.ecl.repository.*;
import ru.clevertec.ecl.service.*;

import java.util.*;
import java.util.stream.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> pagedResult = userDao.findAll(paging);

        return pagedResult.getContent().stream()
                .map(userMapper::userToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.userToDto(user);
    }

}
