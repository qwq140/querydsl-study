package com.cos.querydsl_study.repository;

import com.cos.querydsl_study.entity.FoodStore;
import com.cos.querydsl_study.entity.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTypeRepository extends JpaRepository<FoodType, Integer> {
}
