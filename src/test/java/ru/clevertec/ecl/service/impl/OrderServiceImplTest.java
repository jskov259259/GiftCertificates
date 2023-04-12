package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.model.Order;
import ru.clevertec.ecl.repository.CertificateDao;
import ru.clevertec.ecl.repository.OrderDao;
import ru.clevertec.ecl.repository.UserDao;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.clevertec.ecl.util.Constants.TEST_ID;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_NO;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_SIZE;
import static ru.clevertec.ecl.util.Constants.TEST_SORT_BY;
import static ru.clevertec.ecl.util.TestData.getCertificate;
import static ru.clevertec.ecl.util.TestData.getOrder;
import static ru.clevertec.ecl.util.TestData.getOrderDto;
import static ru.clevertec.ecl.util.TestData.getOrders;
import static ru.clevertec.ecl.util.TestData.getUser;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private CertificateDao certificateDao;

    @Mock
    private UserDao userDao;

    @Mock
    private OrderDao orderDao;

    @Mock
    private OrderMapper orderMapper;

    @Test
    void checkCreateByUserIdAndCertificateId() {
        Order order = getOrder();
        OrderDto orderDto = getOrderDto();

        doReturn(Optional.of(getUser()))
                .when(userDao).findById(TEST_ID);
        doReturn(Optional.of(getCertificate()))
                .when(certificateDao).findById(TEST_ID);
        doReturn(order)
                .when(orderDao).save(any());
        doReturn(orderDto)
                .when(orderMapper).orderToDto(order);

        OrderDto result = orderService.createByUserIdAndCertificateId(TEST_ID, TEST_ID);

        verify(userDao).findById(anyLong());
        verify(certificateDao).findById(anyLong());
        verify(orderDao).save(any());
        verify(orderMapper).orderToDto(order);
        assertThat(result).isEqualTo(orderDto);
    }

    @Test
    void checkFindAllOrdersByUserId() {
        Order order = getOrder();
        OrderDto orderDto = getOrderDto();

        doReturn(new PageImpl<>(getOrders()))
                .when(orderDao).findAllByUserId(TEST_ID, PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE, Sort.by(TEST_SORT_BY)));
        doReturn(orderDto)
                .when(orderMapper).orderToDto(order);

        List<OrderDto> orders = orderService.findAllOrdersByUserId(TEST_ID, TEST_PAGE_NO, TEST_PAGE_SIZE, TEST_SORT_BY);

        verify(orderDao).findAllByUserId(TEST_ID, PageRequest.of(TEST_PAGE_NO, TEST_PAGE_SIZE, Sort.by(TEST_SORT_BY)));
        verify(orderMapper, times(3)).orderToDto(any());

        assertThat(orders.get(0)).isEqualTo(orderDto);
    }

    @Test
    void checkFindOrderByUserIdAndOrderId() {
        Order order = getOrder();
        OrderDto orderDto = getOrderDto();

        doReturn(Optional.of(order))
                .when(orderDao).findByIdAndUserId(TEST_ID, TEST_ID);
        doReturn(orderDto)
                .when(orderMapper).orderToDto(order);

        OrderDto result = orderService.findOrderByUserIdAndOrderId(TEST_ID, TEST_ID);

        verify(orderDao).findByIdAndUserId(anyLong(), anyLong());
        verify(orderMapper).orderToDto(any());
        assertThat(result).isEqualTo(orderDto);
    }

}