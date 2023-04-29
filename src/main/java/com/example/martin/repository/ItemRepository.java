package com.example.martin.repository;

import com.example.martin.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> getItemById(Long id);
    Page<Item> findByItemNameContainingIgnoreCase(String name, Pageable pageable);
}
