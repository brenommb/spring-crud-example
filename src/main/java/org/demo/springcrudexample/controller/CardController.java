package org.demo.springcrudexample.controller;

import lombok.RequiredArgsConstructor;
import org.demo.springcrudexample.entity.Card;
import org.demo.springcrudexample.repository.CardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("cards")
public class CardController {

    private final CardRepository cardRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Card> findById(@PathVariable Long id) {
        Optional<Card> cardOptional = cardRepository.findById(id);

        return cardOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Card>> getAllCards(Pageable pageable) {
        Page<Card> cardsResult = cardRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.DESC, "name"))
                )
        );
        return ResponseEntity.ok(cardsResult);
    }

    @PostMapping
    public ResponseEntity<Void> createCard(@RequestBody Card card) {
        final Card createdCard = cardRepository.save(card);
        return ResponseEntity.created(URI.create("/cards/" + createdCard.getId())).build();
    }
}
