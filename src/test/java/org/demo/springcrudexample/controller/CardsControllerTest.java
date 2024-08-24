package org.demo.springcrudexample.controller;

import org.demo.springcrudexample.entity.Card;
import org.demo.springcrudexample.repository.CardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
class CardsControllerTest {

    @MockBean
    private CardRepository cardRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("list cards endpoint with pagination and return values")
    void verifyGetAllCards() throws Exception {
        Card card = new Card();
        card.setId(1L);

        given(cardRepository.findAll(
                PageRequest.of(
                        0,
                        20,
                        Sort.by(Sort.Direction.DESC, "name"))))
                .willReturn(new PageImpl<>(Collections.singletonList(card)));


        mockMvc.perform(get("/cards")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id", is(1)));
    }

    @Test
    @DisplayName("list cards endpoint with pagination and return empty")
    void verifyGetAllCardsWithEmptyList() throws Exception {

        mockMvc.perform(get("/cards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("verify create a new card endpoint")
    void verifyPostNewCard() throws Exception {
        Card mockCard = new Card();
        mockCard.setId(1L);

        given(cardRepository.save(any())).willReturn(mockCard);

        mockMvc.perform(post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/cards/1"));
    }

    @Test
    @DisplayName("verify find by id endpoint and return value")
    void givenCard_whenGetCardById_thenReturnJson() throws Exception {

        Card card = new Card();
        card.setId(1L);

        given(cardRepository.findById(Mockito.anyLong())).willReturn(Optional.of(card));

        mockMvc.perform(get("/cards/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(card.getId().intValue())));
    }

    @Test
    @DisplayName("verify find by id endpoint and return not found")
    void givenNoCard_whenGetCardById_thenReturnNotFound() throws Exception {

        given(cardRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());

        mockMvc.perform(get("/cards/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}