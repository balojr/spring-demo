package com.example.martin.repository;

import com.example.martin.domain.Item;
import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item,Long>, QuerydslPredicateExecutor<Item> {
    Optional<Item> findById(Long id);
}
