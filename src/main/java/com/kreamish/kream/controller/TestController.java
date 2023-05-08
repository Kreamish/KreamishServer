package com.kreamish.kream.controller;

import com.kreamish.kream.dto.TestDto;
import com.kreamish.kream.entity.Item;
import com.kreamish.kream.repository.ItemRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@EnableWebMvc
public class TestController {

    private final ItemRepository itemRepository;

    @GetMapping(value = "/item", produces = "application/json")
    @Operation(
            summary = "TEST 이름으로 상품 찾기",
            description = "TEST 요청 받은 이름과 일치하는 상품의 List 반환."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204-0", description = "존재하지 않는 리소스"),
            @ApiResponse(responseCode = "200", description = "정상 반환")
    })
    public ResponseEntity<List<Item>> getItem(@RequestParam String name) {
        List<Item> findItem = itemRepository.findItemsWhereLikes(name);
        if (findItem == null || findItem.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(findItem);
    }

    @PostMapping(value = "/item", produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "TEST 이름으로 상품 등록",
            description = "TEST 요청 받은 이름으로 상품 등록."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "400-0", description = "이미 존재하는 이름"),
            @ApiResponse(responseCode = "201-0", description = "정상 생성")
    })
    public ResponseEntity<?> registerItem(@RequestBody TestDto req) {
        List<Item> findItem = itemRepository.findItemsWhereLikes(req.getName());
        if (findItem != null && findItem.size() != 0) {
            return new ResponseEntity<>("400-0", HttpStatus.BAD_REQUEST);
        }

        Item item = new Item();
        item.setName(req.getName());

        itemRepository.save(item);
        return ResponseEntity.created(URI.create("/" + item.getItemId())).body(item);
    }
}
