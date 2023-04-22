package ru.clevertec.ecl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import ru.clevertec.ecl.service.TagService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static ru.clevertec.ecl.util.Constants.TEST_ID;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_NO;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_SIZE;
import static ru.clevertec.ecl.util.Constants.TEST_SEARCH;
import static ru.clevertec.ecl.util.Constants.TEST_SORT_BY;
import static ru.clevertec.ecl.util.TestData.getTagDto;

@ExtendWith(MockitoExtension.class)
class TagControllerTest {

    @InjectMocks
    private TagController tagController;

    @Mock
    private TagService tagService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(tagController)
                .build();
    }

    @AfterEach
    public void end() {
        Mockito.verifyNoMoreInteractions(tagService);
    }

    @Test
    void checkFindAll() throws Exception {
        doReturn(Collections.singletonList(getTagDto()))
                .when(tagService).findAll(anyInt(), anyInt(), anyString());

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("search", TEST_SEARCH);
        requestParams.add("pageNo", String.valueOf(TEST_PAGE_NO));
        requestParams.add("pageSize", String.valueOf(TEST_PAGE_SIZE));
        requestParams.add("sortBy", TEST_SORT_BY);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tags")
                .params(requestParams)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Tag1")));

        Mockito.verify(tagService).findAll(TEST_PAGE_NO, TEST_PAGE_SIZE, TEST_SORT_BY);
    }

    @Test
    void checkFindById() throws Exception {
        doReturn(getTagDto())
                .when(tagService).findById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tags/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is("Tag1")));

        Mockito.verify(tagService).findById(TEST_ID);
    }


    @Test
    void checkCreate() throws Exception {
        doReturn(getTagDto())
                .when(tagService).save(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getTagDto()))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is("Tag1")));

        Mockito.verify(tagService).save(getTagDto());
    }

    @Test
    void checkUpdate() throws Exception {
        doReturn(getTagDto())
                .when(tagService).update(any());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/tags/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getTagDto()))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is("Tag1")));

        Mockito.verify(tagService).update(getTagDto());
    }

    @Test
    void checkDelete() throws Exception {
        doNothing().when(tagService).deleteById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/tags/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(tagService).deleteById(TEST_ID);
    }

}