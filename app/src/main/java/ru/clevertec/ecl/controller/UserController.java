package ru.clevertec.ecl.controller;

import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.*;
import ru.clevertec.ecl.service.*;

import java.util.*;

import static ru.clevertec.ecl.controller.config.Constants.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

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

}
