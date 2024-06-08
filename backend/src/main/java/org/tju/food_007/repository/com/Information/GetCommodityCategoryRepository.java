package org.tju.food_007.repository.com.Information;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CommoditiesCategoriesEntity;


/**
 * @author WGY
 * @create 2024-03-24-20:38
 */
public interface GetCommodityCategoryRepository extends JpaRepository<CommoditiesCategoriesEntity,Integer> {
}
