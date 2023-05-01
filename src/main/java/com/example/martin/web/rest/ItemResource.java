package com.example.martin.web.rest;

import com.example.martin.domain.Item;
import com.example.martin.service.ItemService;
import com.example.martin.service.dto.RestResponse;
import com.example.martin.web.rest.requests.ItemRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/item")
public class ItemResource {

    private final Logger log = LoggerFactory.getLogger(ItemResource.class);

    private final ItemService itemService;

    public ItemResource(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<?> createNewItem(@RequestBody ItemRequest itemRequest) {
        try {
            Item item = itemService.createNewItem(itemRequest);
            return ResponseEntity.ok().body(item);
        } catch (Exception e) {
            log.error("error {} ", e.getMessage());
            return new ResponseEntity<>(new RestResponse(true, "Item not created, contact admin"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<?> findById(@PathVariable("id") Long id) {

        try {
            Optional<Item> itemOptional = itemService.getOneItemById(id);

            if (itemOptional.isPresent()) {
                Item item = itemOptional.get();
                return new ResponseEntity<>(item, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new RestResponse(true, "Item not found"), HttpStatus.NOT_FOUND);

            }

        } catch (Exception e) {
            log.debug("error {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse(true, "Item not found, contact admin"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Item>> getAllItems(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(required = false) String search) {
        Page<Item> products = itemService.getAllItems(pageNumber, pageSize, sortBy, search);
        return ResponseEntity.ok(products);
    }

}
