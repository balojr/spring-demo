package com.example.martin.service;


import com.example.martin.domain.Item;
import com.example.martin.repository.ItemRepository;
import com.example.martin.web.rest.requests.ItemRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    private final Logger log = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public Item createNewItem(ItemRequest itemRequest) {
        log.info("create new item");

        Long id = itemRequest.getId();
        boolean itemExists = itemRepository.findById(id).isPresent();
        if (itemExists) {
            log.info("Item exists!. Creating another one");
        }
        Item newItem = new Item();
        newItem.setItemName(itemRequest.getItemName());

        itemRepository.save(newItem);
        log.info("item Saved");
        return newItem;
    }

    public Optional<Item> getOneItemById(Long id) {
        Optional<Item> item = itemRepository.getItemById(id);

        // check if home isPresent
        if (item.isPresent()) {
            log.info("Getting item");
            return item;
        } else {
            throw new IllegalStateException("Item with Id " + id + " does not exist");
        }
    }

    public Page<Item> getAllItems(int pageNumber, int pageSize, String sortBy, String search) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        if (search != null && !search.isEmpty()) {
            return itemRepository.findByItemNameContainingIgnoreCase(search, pageable);
        }
        return itemRepository.findAll(pageable);
    }
}
