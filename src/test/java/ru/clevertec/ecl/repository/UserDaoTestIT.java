package ru.clevertec.ecl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.clevertec.ecl.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.clevertec.ecl.util.Constants.TEST_ID;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_NO;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_SIZE;
import static ru.clevertec.ecl.util.Constants.TEST_SORT_BY;

@DataJpaTest
class UserDaoTestIT {

    @Autowired
    private UserDao userDao;

    @Test
    void checkFindAll() {
        Page<User> pagedResult = userDao.findAll(PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE,
                Sort.by(TEST_SORT_BY)));
        assertThat(pagedResult.getContent().size()).isEqualTo(7);
    }

    @Test
    void checkFindById() {
        Optional<User> userData = userDao.findById(TEST_ID);
        assertThat(userData.get().getId()).isEqualTo(TEST_ID);
        assertThat(userData.get().getName()).isEqualTo("Stan");
    }

    @Test
    void checkFindUserByHighestCostOfAllOrders() {
        User user = userDao.findUserByHighestCostOfAllOrders();
        assertThat(user.getId()).isEqualTo(3L);
        assertThat(user.getName()).isEqualTo("Eric");
    }

}