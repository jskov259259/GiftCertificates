package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.service.OrderService;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value="/makeOrder", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrderDto> createByUserIdAndCertificateId(@RequestBody Map<String, Long> params) {
        OrderDto order =
                orderService.createByUserIdAndCertificateId(params.get("userId"), params.get("certificateId"));
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }


}
