package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.service.OrderService;
import ru.clevertec.ecl.service.UserService;

import java.util.List;

import static ru.clevertec.ecl.controller.config.Constants.DEFAULT_PAGE_NO;
import static ru.clevertec.ecl.controller.config.Constants.DEFAULT_PAGE_SIZE;
import static ru.clevertec.ecl.controller.config.Constants.DEFAULT_SORT_BY;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserDto>> findAll(
            @RequestParam(defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = DEFAULT_SORT_BY) String sortBy) {
        List<UserDto> users = userService.findAll(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        UserDto user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/orders", produces = "application/json")
    public ResponseEntity<List<OrderDto>> findAllOrdersByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = DEFAULT_SORT_BY) String sortBy) {
        List<OrderDto> orders = orderService.findAllOrdersByUserId(userId, pageNo, pageSize, sortBy);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/orders/{orderId}", produces = "application/json")
    public ResponseEntity<OrderDto> findOrderByUserIdAndOrderId(@PathVariable Long userId, @PathVariable Long orderId) {
        OrderDto order = orderService.findOrderByUserIdAndOrderId(userId, orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping(value = "/highestCostOfAllOrders", produces = "application/json")
    public ResponseEntity<UserDto> findUserByHighestCostOfAllOrders() {
        UserDto user = userService.findUserByHighestCostOfAllOrders();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
