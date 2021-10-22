package com.cos.querydsl_study.repository;

import com.cos.querydsl_study.entity.FoodStore;
import com.cos.querydsl_study.food.FoodDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodStoreRepository extends JpaRepository<FoodStore, Integer>, FoodDAO {
}
