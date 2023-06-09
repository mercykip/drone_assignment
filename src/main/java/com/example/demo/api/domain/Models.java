package com.example.demo.api.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public enum Models {
    Lightweight(1,"Lightweight"), Middleweight(2,"Middleweight"), Cruiserweight(3,"Cruiserweight"), Heavyweight(4,"Heavyweight");
    private final Integer id;
    private final  String name;

    Models(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
