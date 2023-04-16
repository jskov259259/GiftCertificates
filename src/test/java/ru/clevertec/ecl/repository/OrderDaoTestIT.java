package ru.clevertec.ecl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.clevertec.ecl.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.clevertec.ecl.util.Constants.TEST_ID;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_NO;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_SIZE;
import static ru.clevertec.ecl.util.Constants.TEST_SORT_BY;
import static ru.clevertec.ecl.util.TestData.getOrder;

@DataJpaTest
class OrderDaoTestIT {

    @Autowired
    private OrderDao orderDao;

    @Test
    void checkSave() {
        Order order = getOrder();
        order.setPurchaseTime(LocalDateTime.now());
        order.setTotalPrice(new BigDecimal(1));
        Order createdOrder = orderDao.save(order);
        System.out.println(createdOrder);
        assertThat(createdOrder.getTotalPrice()).isEqualTo(BigDecimal.valueOf(1));
        assertThat(createdOrder.getCertificate().getId()).isEqualTo(1L);
        assertThat(createdOrder.getUser().getId()).isEqualTo(1L);
    }

    @Test
    void findAllByUserId() {
        Page<Order> pagedResult = orderDao.findAllByUserId(TEST_ID,
                PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE, Sort.by(TEST_SORT_BY)));
        assertThat(pagedResult.getContent().size()).isEqualTo(2);
    }

    @Test
    void findByIdAndUserId() {
        Optional<Order> orderData = orderDao.findByIdAndUserId(TEST_ID, TEST_ID);
        assertThat(orderData.get().getId()).isEqualTo(TEST_ID);
        assertThat(orderData.get().getUser().getId()).isEqualTo(TEST_ID);
        assertThat(orderData.get().getCertificate().getId()).isEqualTo(TEST_ID);
        assertThat(orderData.get().getTotalPrice()).isEqualTo(BigDecimal.valueOf(10));
    }
}