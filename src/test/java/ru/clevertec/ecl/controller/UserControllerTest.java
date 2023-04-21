package ru.clevertec.ecl.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import ru.clevertec.ecl.service.OrderService;
import ru.clevertec.ecl.service.UserService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static ru.clevertec.ecl.util.Constants.TEST_ID;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_NO;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_SIZE;
import static ru.clevertec.ecl.util.Constants.TEST_SORT_BY;
import static ru.clevertec.ecl.util.TestData.getOrderDto;
import static ru.clevertec.ecl.util.TestData.getUserDto;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private OrderService orderService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @AfterEach
    public void end() {
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    void checkFindAll() throws Exception {
        doReturn(Collections.singletonList(getUserDto()))
                .when(userService).findAll(anyInt(), anyInt(), anyString());

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("pageNo", String.valueOf(TEST_PAGE_NO));
        requestParams.add("pageSize", String.valueOf(TEST_PAGE_SIZE));
        requestParams.add("sortBy", TEST_SORT_BY);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                .params(requestParams)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("User1")));

        Mockito.verify(userService).findAll(TEST_PAGE_NO, TEST_PAGE_SIZE, TEST_SORT_BY);
    }

    @Test
    void checkFindById() throws Exception {
        doReturn(getUserDto())
                .when(userService).findById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is("User1")));

        Mockito.verify(userService).findById(TEST_ID);
    }

    @Test
    void checkFindAllOrdersByUserId() throws Exception {
        doReturn(Collections.singletonList(getOrderDto()))
                .when(orderService).findAllOrdersByUserId(anyLong(), anyInt(), anyInt(), anyString());

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("pageNo", String.valueOf(TEST_PAGE_NO));
        requestParams.add("pageSize", String.valueOf(TEST_PAGE_SIZE));
        requestParams.add("sortBy", TEST_SORT_BY);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1/orders")
                .params(requestParams)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].certificateId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId", Matchers.is(1)));

        Mockito.verify(orderService).findAllOrdersByUserId(TEST_ID, TEST_PAGE_NO, TEST_PAGE_SIZE, TEST_SORT_BY);
    }

    @Test
    void checkFindOrderByUserIdAndOrderId() throws Exception {
        doReturn(getOrderDto())
                .when(orderService).findOrderByUserIdAndOrderId(anyLong(), anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1/orders/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("certificateId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("userId", Matchers.is(1)));

        Mockito.verify(orderService).findOrderByUserIdAndOrderId(TEST_ID, TEST_ID);
    }

    @Test
    void findUserByHighestCostOfAllOrders() throws Exception {
        doReturn(getUserDto())
                .when(userService).findUserByHighestCostOfAllOrders();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/highestCostOfAllOrders")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is("User1")));

        Mockito.verify(userService).findUserByHighestCostOfAllOrders();
    }
}