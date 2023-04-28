package com.example.martin.repository;

import com.example.martin.domain.Item;

import com.example.martin.dto.ItemDto;
import com.example.martin.dto.UserDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, PagingAndSortingRepository<Item, Long> {
  Optional<Item> findById(Long id);
  Optional<Item> getItemById(Long id);
  Page<Item> findByItemNameContainingIgnoreCase(String name, Pageable pageable);
  Page<Item> findAll(Pageable pageable);

}
