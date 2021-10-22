package com.cos.querydsl_study.food;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodStoreDTO {

    private Integer id;
    private String storeName;
    private int rate;
    private String ownerName;
    private String foodTypeName;
    private int foodOrder;

    @QueryProjection
    public FoodStoreDTO(Integer id, String storeName, int rate, String ownerName, String foodTypeName, int foodOrder) {
        this.id = id;
        this.storeName = storeName;
        this.rate = rate;
        this.ownerName = ownerName;
        this.foodTypeName = foodTypeName;
        this.foodOrder = foodOrder;
    }
}
