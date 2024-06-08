package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;

import org.tju.food_007.model.CommodityEntity;


import java.util.ArrayList;

/**
 * @author WGY
 * @create 2024-04-01-18:53
 */
public interface StoGeComListRepository extends JpaRepository<CommodityEntity,Integer> {
    ArrayList<CommodityEntity> findCommodityEntitiesByStoId(Integer sto_id);
}
