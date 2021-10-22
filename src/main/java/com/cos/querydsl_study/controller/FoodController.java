package com.cos.querydsl_study.controller;

import com.cos.querydsl_study.food.FoodRetrieveCondition;
import com.cos.querydsl_study.food.FoodStoreDTO;
import com.cos.querydsl_study.repository.FoodStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodStoreRepository foodStoreRepository;

    @GetMapping("/foodStores")
    public Page<FoodStoreDTO> getFoodStores(FoodRetrieveCondition condition, Pageable pageable){
        return foodStoreRepository.retrieveStores(condition,pageable);
    }

    @GetMapping("/foodStoresBetter")
    public Page<FoodStoreDTO> getFoodStoresBetter(FoodRetrieveCondition condition, Pageable pageable){
        return foodStoreRepository.retrieveStoresBetter(condition,pageable);
    }
}
