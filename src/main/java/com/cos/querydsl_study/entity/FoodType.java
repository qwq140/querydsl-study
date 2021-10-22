package com.cos.querydsl_study.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString(of={"id","foodTypeName", "foodOrder"})
public class FoodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_type_id")
    private Integer id;

    private String foodTypeName;
    private int foodOrder;

    @OneToMany(mappedBy = "foodType")
    List<FoodStore> foodStoreList = new ArrayList<>();

    public FoodType(String foodTypeName, int foodOrder) {
        this.foodTypeName = foodTypeName;
        this.foodOrder = foodOrder;
    }
}
