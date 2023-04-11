package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.exceptions.UserNotFoundException;
import ru.clevertec.ecl.mapper.UserMapper;
import ru.clevertec.ecl.model.User;
import ru.clevertec.ecl.repository.UserDao;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_NO;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_SIZE;
import static ru.clevertec.ecl.util.Constants.TEST_SORT_BY;
import static ru.clevertec.ecl.util.TestData.getUser;
import static ru.clevertec.ecl.util.TestData.getUserDto;
import static ru.clevertec.ecl.util.TestData.getUsers;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Mock
    private UserMapper userMapper;

    @Test
    void checkFindAll() {
        User user = getUser();
        UserDto userDto = getUserDto();

        doReturn(new PageImpl<>(getUsers()))
                .when(userDao).findAll(PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE, Sort.by(TEST_SORT_BY)));
        doReturn(userDto)
                .when(userMapper).userToDto(user);

        List<UserDto> users = userService.findAll(TEST_PAGE_NO, TEST_PAGE_SIZE, TEST_SORT_BY);

        verify(userDao).findAll(PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE, Sort.by(TEST_SORT_BY)));
        verify(userMapper, times(3)).userToDto(any());

        assertThat(users.get(0)).isEqualTo(userDto);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L})
    void checkFindById(Long id) {
        User user = getUser();
        UserDto userDto = getUserDto();

        doReturn(Optional.of(user))
                .when(userDao).findById(id);
        doReturn(userDto)
                .when(userMapper).userToDto(user);

        UserDto result = userService.findById(id);

        verify(userDao).findById(anyLong());
        verify(userMapper).userToDto(any());
        assertThat(result).isEqualTo(userDto);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L})
    void checkFindByIdShouldThrowUserNotFoundException(Long id) {
        doThrow(UserNotFoundException.class)
                .when(userDao).findById(anyLong());
        assertThrows(UserNotFoundException.class, () -> userService.findById(id));
        verify(userDao).findById(anyLong());
    }

    @Test
    void checkFindUserByHighestCostOfAllOrders() {
        User user = getUser();
        UserDto userDto = getUserDto();

        doReturn(user)
                .when(userDao).findUserByHighestCostOfAllOrders();
        doReturn(userDto)
                .when(userMapper).userToDto(user);

        UserDto result = userService.findUserByHighestCostOfAllOrders();
        assertThat(result).isEqualTo(userDto);
    }

}