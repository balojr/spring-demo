package com.example.martin.web;

import com.example.martin.domain.Item;
import com.example.martin.dto.RestResponse;
import com.example.martin.service.ItemService;
import com.example.martin.web.requests.ItemRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/item")
@AllArgsConstructor
@Slf4j
public class ItemResource {
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  ItemService itemService;

  @PostMapping
  public ResponseEntity<?> createNewItem(@RequestBody ItemRequest itemRequest) {
    try {
      Item item = itemService.createNewItem(itemRequest);
      RestResponse response = new RestResponse(false, "Item created");
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      log.error("error ", e);
      return new ResponseEntity<>(new RestResponse(true, "Item not created, contact admin"),
        HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @GetMapping(path = "{id}")
  ResponseEntity<?> findById(@PathVariable("id") Long id) {

    try {
      Optional<Item> itemOptional = itemService.getOneItemById(id);

      if (itemOptional.isPresent()) {
        Item item = itemOptional.get();
        ItemRequest response = modelMapper.map(item, ItemRequest.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(new RestResponse(true, "Item not found"), HttpStatus.NOT_FOUND);

      }

    } catch (Exception e) {
      log.error("error ", e);
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
