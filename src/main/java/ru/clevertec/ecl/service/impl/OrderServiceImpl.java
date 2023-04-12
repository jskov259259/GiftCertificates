package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.exceptions.CertificateNotFoundException;
import ru.clevertec.ecl.exceptions.OrderNotFoundException;
import ru.clevertec.ecl.exceptions.UserNotFoundException;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.model.Order;
import ru.clevertec.ecl.model.User;
import ru.clevertec.ecl.repository.CertificateDao;
import ru.clevertec.ecl.repository.OrderDao;
import ru.clevertec.ecl.repository.UserDao;
import ru.clevertec.ecl.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final CertificateDao certificateDao;
    private final UserDao userDao;
    private final OrderDao orderDao;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto createByUserIdAndCertificateId(Long userId, Long certificateId) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        GiftCertificate certificate = certificateDao.findById(certificateId)
                .orElseThrow(() -> new CertificateNotFoundException(certificateId));

        Order order = buildOrder(user, certificate);
        Order createdOrder = orderDao.save(order);
        return orderMapper.orderToDto(createdOrder);
    }

    @Override
    public List<OrderDto> findAllOrdersByUserId(Long userId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Order> pagedResult = orderDao.findAllByUserId(userId, paging);

        return pagedResult.getContent().stream()
                .map(orderMapper::orderToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto findOrderByUserIdAndOrderId(Long userId, Long orderId) {
        Order order = orderDao.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        OrderDto orderDto = orderMapper.orderToDto(order);
        setNullsToUnnecessaryFields(orderDto);
        return orderDto;
    }

    private Order buildOrder(User user, GiftCertificate certificate) {
        return Order.builder()
                .user(user)
                .certificate(certificate)
                .purchaseTime(LocalDateTime.now())
                .totalPrice(certificate.getPrice())
                .build();
    }

    private void setNullsToUnnecessaryFields(OrderDto order) {
        order.setId(null);
        order.setUserId(null);
        order.setCertificateId(null);
    }
}
