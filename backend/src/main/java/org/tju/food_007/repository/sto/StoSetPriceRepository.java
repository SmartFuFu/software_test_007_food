package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;

import org.tju.food_007.model.CommodityPriceCurveEntity;

public interface StoSetPriceRepository extends JpaRepository<CommodityPriceCurveEntity,Integer> {
}


