package com.tr.eren.tradedataservice;

import com.tr.eren.tradedataservice.api.dto.CandlestickDTO;
import com.tr.eren.tradedataservice.api.service.CandlestickService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CandlesticksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandlestickService candlestickService;

    // Couldn't get it to return the desired list, hence 404 not found
    @Test
    public void givenCandlesticks_whenGetCandlesticks_thenStatus200() throws Exception {
        CandlestickDTO candlestickDTO = new CandlestickDTO(LocalDateTime.of(2021, 12, 5, 20, 30),
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(1500),
                BigDecimal.valueOf(900),
                BigDecimal.valueOf(1500),
                LocalDateTime.of(2021,12,5,20,31));
        List<CandlestickDTO> candlestickDTOS = new ArrayList<>();
        candlestickDTOS.add(candlestickDTO);

        Mockito.when(candlestickService.getCandlesticks("TEST")).thenReturn(candlestickDTOS);

        mockMvc.perform(get("/candlesticks").param("isin", "TEST"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].openPrice", Matchers.equalTo("1000")));
    }
}
