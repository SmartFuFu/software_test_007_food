package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;

import org.tju.food_007.model.RegularCommodityEntity;

/**
 * @author WGY
 * @create 2024-03-20-11:20
 */
public interface StoUploadRegularCommodityRepository extends JpaRepository<RegularCommodityEntity,Integer> {
}
