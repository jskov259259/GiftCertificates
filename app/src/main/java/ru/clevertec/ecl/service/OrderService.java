package ru.clevertec.ecl.service;

import ru.clevertec.ecl.dto.OrderDto;
import java.util.List;

public interface OrderService {

    OrderDto createByUserIdAndCertificateId(Long userId, Long certificateId);

    List<OrderDto> findAllOrdersByUserId(Long userId, Integer pageNo, Integer pageSize, String sortBy);

    OrderDto findOrderByUserIdAndOrderId(Long userId, Long orderId);

}
