package com.example.martin.service;



import com.example.martin.dto.ItemDto;
import com.example.martin.dto.ListResponse;
import com.example.martin.repository.ItemRepository;
import com.example.martin.domain.Item;
import com.example.martin.web.requests.ItemRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

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

