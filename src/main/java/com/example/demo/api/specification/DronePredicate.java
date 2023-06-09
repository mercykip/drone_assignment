package com.example.demo.api.specification;

import com.example.demo.api.domain.Drones;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class DronePredicate <T> implements Specification<Drones> {
    private String state,serialNumber;

    private Integer id;
    public DronePredicate(String state) {
        this.state = state;
    }

    public DronePredicate(Integer id) {
        this.id = id;
    }

    @Override
    public Predicate toPredicate(Root<Drones> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Predicate p = cb.and();

        if (id != null) {
            p.getExpressions().add(cb.equal(root.get("dronesId"), id));
        }

        if (state != null && !state.trim().isEmpty()) {
            p.getExpressions().add(cb.equal(cb.upper(root.get("state")), state.trim().toUpperCase()));
        }

        p.getExpressions().add(cb.equal(root.get("deleted"), false));

        return p;
    }
}



