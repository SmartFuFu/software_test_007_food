package org.tju.food_007.repository.cus.indent;

import org.springframework.data.jpa.repository.JpaRepository;

import org.tju.food_007.model.IndentCommodityEntity;


import java.util.ArrayList;

/**
 * @author WGY
 * @create 2024-03-17-15:43
 */
public interface GenerateIndentComRepository extends JpaRepository<IndentCommodityEntity,Integer> {
    ArrayList<IndentCommodityEntity> findByIndId(Integer ind_id);

}
