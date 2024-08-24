package org.demo.springcrudexample.repository;

import org.demo.springcrudexample.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface CardRepository extends CrudRepository<Card, Long> {

    Page<Card> findAll(Pageable pageable);

}
