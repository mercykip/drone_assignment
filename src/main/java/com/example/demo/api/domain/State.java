package com.example.demo.api.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public enum State {

    IDLE(1,"IDLE"), LOADING(2,"LOADING"), LOADED(3,"LOADED"), DELIVERING(4,"DELIVERING"), DELIVERED(5,"DELIVERED"), RETURNING(6,"RETURNING");

    private final Integer id;
    private final  String name;


    State(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
