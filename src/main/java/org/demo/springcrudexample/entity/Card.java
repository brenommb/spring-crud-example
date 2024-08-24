package org.demo.springcrudexample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Card {

    @Id
    private Long id;

    private String name;
}
