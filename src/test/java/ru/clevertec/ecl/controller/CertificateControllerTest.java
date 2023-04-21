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
import ru.clevertec.ecl.service.CertificateService;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static ru.clevertec.ecl.util.Constants.TEST_ID;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_NO;
import static ru.clevertec.ecl.util.Constants.TEST_PAGE_SIZE;
import static ru.clevertec.ecl.util.Constants.TEST_SEARCH;
import static ru.clevertec.ecl.util.Constants.TEST_SORT_BY;
import static ru.clevertec.ecl.util.TestData.getCertificateDto;
import static ru.clevertec.ecl.util.TestData.getTagNames;

@ExtendWith(MockitoExtension.class)
class CertificateControllerTest {

    @InjectMocks
    private CertificateController certificateController;

    @Mock
    private CertificateService certificateService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(certificateController)
                .build();
    }

    @AfterEach
    public void end() {
        Mockito.verifyNoMoreInteractions(certificateService);
    }

    @Test
    void checkFindAll() throws Exception {
        doReturn(Collections.singletonList(getCertificateDto()))
                .when(certificateService).findAll(anyString(), anyInt(), anyInt(), anyString());

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("search", TEST_SEARCH);
        requestParams.add("pageNo", String.valueOf(TEST_PAGE_NO));
        requestParams.add("pageSize", String.valueOf(TEST_PAGE_SIZE));
        requestParams.add("sortBy", TEST_SORT_BY);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/certificates")
                .params(requestParams)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Certificate1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].duration", Matchers.is(1)));

        Mockito.verify(certificateService).findAll(TEST_SEARCH, TEST_PAGE_NO, TEST_PAGE_SIZE, TEST_SORT_BY);
    }

    @Test
    void checkFindById() throws Exception {
        doReturn(getCertificateDto())
                .when(certificateService).findById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/certificates/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is("Certificate1")))
                .andExpect(MockMvcResultMatchers.jsonPath("price", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("duration", Matchers.is(1)));

        Mockito.verify(certificateService).findById(TEST_ID);
    }

    @Test
    void checkFindAllByTagNames() throws Exception {
        doReturn(Collections.singletonList(getCertificateDto()))
                .when(certificateService).findAllByTagNames(anyInt(), anyInt(), anyString(), anyList());

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("pageNo", String.valueOf(TEST_PAGE_NO));
        requestParams.add("pageSize", String.valueOf(TEST_PAGE_SIZE));
        requestParams.add("sortBy", TEST_SORT_BY);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/certificates/tags")
                .params(requestParams)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getTagNames()))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Certificate1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].duration", Matchers.is(1)));

        Mockito.verify(certificateService).findAllByTagNames(TEST_PAGE_NO, TEST_PAGE_SIZE, TEST_SORT_BY, getTagNames());
    }

    @Test
    void checkCreate() throws Exception {
        doReturn(getCertificateDto())
                .when(certificateService).save(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/certificates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getCertificateDto()))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is("Certificate1")))
                .andExpect(MockMvcResultMatchers.jsonPath("price", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("duration", Matchers.is(1)));

        Mockito.verify(certificateService).save(getCertificateDto());
    }

    @Test
    void checkUpdate() throws Exception {
        doReturn(getCertificateDto())
                .when(certificateService).update(any());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/certificates/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getCertificateDto()))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("name", Matchers.is("Certificate1")))
                .andExpect(MockMvcResultMatchers.jsonPath("price", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("duration", Matchers.is(1)));

        Mockito.verify(certificateService).update(getCertificateDto());
    }

    @Test
    void checkUpdatePrice() throws Exception {
        doNothing().when(certificateService).updatePrice(anyLong(), any());

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/certificates/1/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content("1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(certificateService).updatePrice(TEST_ID, BigDecimal.valueOf(1));
    }

    @Test
    void checkDelete() throws Exception {
        doNothing().when(certificateService).deleteById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/certificates/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(certificateService).deleteById(TEST_ID);
    }
}