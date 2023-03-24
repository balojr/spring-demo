package com.example.martin.service;

import com.example.martin.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ItemService {
    @Autowired
    ItemRepository itemRepository;


}
