package com.cos.querydsl_study.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRetrieveCondition {

    private String storeName;
    private int rate;
    private String storeTypeName;
}
