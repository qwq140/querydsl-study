package com.cos.querydsl_study;

import com.cos.querydsl_study.entity.FoodStore;
import com.cos.querydsl_study.entity.FoodType;
import com.cos.querydsl_study.entity.QFoodStore;
import com.cos.querydsl_study.entity.QFoodType;
import com.cos.querydsl_study.food.FoodStoreDTO;
import com.cos.querydsl_study.food.QFoodStoreDTO;
import com.cos.querydsl_study.repository.FoodStoreRepository;
import com.cos.querydsl_study.repository.FoodTypeRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

import static com.cos.querydsl_study.entity.QFoodStore.*;
import static com.cos.querydsl_study.entity.QFoodType.foodType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

@SpringBootTest
@Transactional
public class QueryDSLTest {

    @Autowired
    FoodTypeRepository foodTypeRepository;

    @Autowired
    FoodStoreRepository foodStoreRepository;

    @Autowired
    JPAQueryFactory query;


    public void setData(){
        FoodType korean = new FoodType("한식", 1);
        FoodType western = new FoodType("양식", 2);
        FoodType chinese = new FoodType("중식", 3);

        foodTypeRepository.saveAll(List.of(korean, western, chinese));

        FoodStore foodStore1 = new FoodStore("삼겹살", 9, "sangmessi", korean);
        FoodStore foodStore2 = new FoodStore("닭갈비", 2, "sangmessi", korean);
        FoodStore foodStore3 = new FoodStore("부대찌개", 3, "lake", korean);
        FoodStore foodStore4 = new FoodStore("순대국밥", 4, "lake", korean);
        FoodStore foodStore5 = new FoodStore("소고기", 5, "lake", korean);
        FoodStore foodStore6 = new FoodStore("스파게티", 6, "sangmessi", western);
        FoodStore foodStore7 = new FoodStore("피자", 7, "sangmessi", western);
        FoodStore foodStore8 = new FoodStore("중국집", 8, "hong", chinese);
        FoodStore foodStore9 = new FoodStore("중국집2", 9, "hong", chinese);
        FoodStore foodStore10 = new FoodStore("중국집3", 10, "hong", chinese);

        foodStoreRepository.saveAll(List.of(foodStore1, foodStore2, foodStore3, foodStore4, foodStore5, foodStore6, foodStore7, foodStore8, foodStore9, foodStore10));


    }

    // 기본쿼리
    @Test
    public void 기본쿼리(){
        setData();
        List<FoodStore> result = query
                .selectFrom(foodStore)
                .fetch();

        assertThat(result.size()).isEqualTo(10);
    }

    @Test
    public void 기본쿼리_조건절(){
        setData();
        List<FoodStore> result = query
                .selectFrom(foodStore)
                .where(foodStore.rate.goe(5))
                .fetch();

        assertThat(result.size()).isEqualTo(7);
    }

    @Test
    public void 기본쿼리_조건절2(){
        setData();
        List<FoodStore> result = query
                .selectFrom(foodStore)
                .where(
                        foodStore.rate.goe(5),
                        foodStore.storeName.startsWith("삼"))
                .fetch();

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void 기본쿼리_정렬(){
        setData();
        List<FoodStore> results = query
                .selectFrom(foodStore)
                .orderBy(foodStore.rate.desc())
                .fetch();

        assertThat(results.size()).isEqualTo(10);
        assertThat(results.get(0).getRate()).isEqualTo(10);
    }

    @Test
    public void 기본쿼리_페이징(){
        setData();
        QueryResults<FoodStore> fetchResults = query
                .selectFrom(foodStore)
                .offset(1) // 시작하는 지점
                .limit(3)
                .fetchResults(); // fetchResults : 조회한 리스트 + 전체 개수를 포함한 QueryResults 반환, count 쿼리가 추가로 실행

        List<FoodStore> results = fetchResults.getResults();
        long limit = fetchResults.getLimit();
        long offset = fetchResults.getOffset();
        long total = fetchResults.getTotal();

        System.out.println("total = "+total);
        System.out.println("offset = "+offset);
        System.out.println("limit = "+limit);


        assertThat(results.size()).isEqualTo(3);


    }

    @Test
    public void 서브쿼리(){
        setData();
        List<FoodStore> fetch = query
                .selectFrom(foodStore)
                .where(foodStore.rate.in(
                        JPAExpressions
                                .select(foodType.foodOrder.max())
                                .from(foodType)
                ))
                .fetch();

        assertThat(fetch.size()).isEqualTo(1);
    }

    @Test
    public void caseQuery(){
        setData();
        List<String> fetch = query
                .select(
                        foodStore.rate
                                .when(10).then("존맛탱")
                                .when(9).then("맛남")
                                .otherwise("그럭저럭"))
                .from(foodStore)
                .orderBy(foodStore.rate.desc())
                .fetch();

        System.out.println("fetch : " + fetch);

        List<String> fetch2 = query
                .select(new CaseBuilder()
                        .when(foodStore.rate.goe(7)).then("존맛탱")
                        .when(foodStore.rate.goe(5)).then("맛남")
                        .otherwise("그럭저럭"))
                .from(foodStore)
                .orderBy(foodStore.rate.desc())
                .fetch();

        System.out.println("fetch2 : "+fetch2);

    }

    @Test
    public void 원하는_객체리턴(){
        setData();
        List<FoodStoreDTO> foodStoreDTOList = query
                .select(new QFoodStoreDTO(
                        foodStore.id,
                        foodStore.storeName,
                        foodStore.rate,
                        foodStore.ownerName,
                        foodType.foodTypeName,
                        foodType.foodOrder))
                .from(foodStore)
                .join(foodStore.foodType, foodType)
                .fetch();

        foodStoreDTOList.forEach(System.out::println);
    }

    @Test
    public void dynamicQuery(){
        setData();
        List<FoodStoreDTO> 삼겹살 = 동적쿼리만들기("삼", 0);
        List<FoodStoreDTO> lessThanFive = 동적쿼리만들기("대", 5);

        assertThat(삼겹살.size()).isEqualTo(1);
        assertThat(lessThanFive.size()).isEqualTo(2);
        assertThat(삼겹살).extracting("storeName").containsExactly("삼겹살");
    }

    private List<FoodStoreDTO> 동적쿼리만들기(String storeName, int rate){

        BooleanBuilder builder = new BooleanBuilder();
        if (!Objects.isNull(storeName)){
            builder.and(foodStore.storeName.contains(storeName));
        }
        if(rate>0){
            builder.and(foodStore.rate.loe(rate));
        }

        return query
                .select(new QFoodStoreDTO(
                        foodStore.id,
                        foodStore.storeName,
                        foodStore.rate,
                        foodStore.ownerName,
                        foodType.foodTypeName,
                        foodType.foodOrder))
                .from(foodStore)
                .join(foodStore.foodType, foodType)
                .where(builder)
                .fetch();
    }

    @Test
    public void dynamicQuery2(){
        setData();
        List<FoodStoreDTO> 삼겹살 = 동적쿼리만들기2("삼", 0);
        List<FoodStoreDTO> lessThanFive = 동적쿼리만들기2("대", 5);

        assertThat(삼겹살.size()).isEqualTo(1);
        assertThat(lessThanFive.size()).isEqualTo(2);
        assertThat(삼겹살).extracting("storeName").containsExactly("삼겹살");
    }

    private List<FoodStoreDTO> 동적쿼리만들기2(String storeName, int rate){
        return query
                .select(new QFoodStoreDTO(
                        foodStore.id,
                        foodStore.storeName,
                        foodStore.rate,
                        foodStore.ownerName,
                        foodType.foodTypeName,
                        foodType.foodOrder))
                .from(foodStore)
                .join(foodStore.foodType, foodType)
                .where(storeNameContains(storeName),
                        lessThan(rate))
                .fetch();
    }

    private BooleanExpression storeNameContains(String storeName){
        return Objects.isNull(storeName) ? null : foodStore.storeName.contains(storeName);
    }

    private BooleanExpression lessThan(int rate){
        return rate <= 0 ? null :foodStore.rate.loe(rate);
    }

    @Test
    public void 집합() {
        setData();
        List<Tuple> fetch = query
                .select(
                        foodStore.rate.max().as("max"),
                        foodStore.rate.avg().as("avg"),
                        foodStore.rate.sum().as("sum"))
                .from(foodStore)
                .fetch();

        Tuple tuple = fetch.get(0);
        System.out.println("tuple = "+tuple);

    }

    @Autowired
    EntityManager em;

    @Test
    //@Commit
    public void update(){
        setData();

        List<FoodStore> fetch = query.selectFrom(foodStore).fetch();

        long count = query
                .update(foodStore)
                .set(foodStore.ownerName, "dal")
                .execute();
        System.out.println("count = "+count);

        // 영속성 컨텍스트 비워줌
        em.flush();
        em.clear();

        // DB는 update가 되었지만 영속성 컨텍스트가 update전을 가지고 있어서 update되기전 데이터를 출력
        List<FoodStore> fetch2 = query.selectFrom(foodStore).fetch();
        fetch2.forEach(System.out::println);
    }

}
