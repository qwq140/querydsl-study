package com.cos.querydsl_study.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFoodType is a Querydsl query type for FoodType
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFoodType extends EntityPathBase<FoodType> {

    private static final long serialVersionUID = 1671943272L;

    public static final QFoodType foodType = new QFoodType("foodType");

    public final NumberPath<Integer> foodOrder = createNumber("foodOrder", Integer.class);

    public final ListPath<FoodStore, QFoodStore> foodStoreList = this.<FoodStore, QFoodStore>createList("foodStoreList", FoodStore.class, QFoodStore.class, PathInits.DIRECT2);

    public final StringPath foodTypeName = createString("foodTypeName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QFoodType(String variable) {
        super(FoodType.class, forVariable(variable));
    }

    public QFoodType(Path<? extends FoodType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFoodType(PathMetadata metadata) {
        super(FoodType.class, metadata);
    }

}

