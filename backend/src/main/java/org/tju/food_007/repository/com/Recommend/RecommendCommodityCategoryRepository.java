package org.tju.food_007.repository.com.Recommend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CommodityCategoriesEntity;
import org.tju.food_007.model.CommodityCategoriesEntityPK;

import java.util.List;

public interface RecommendCommodityCategoryRepository  extends JpaRepository<CommodityCategoriesEntity, CommodityCategoriesEntityPK> {
    boolean existsByComId(Integer com_ID);
    List<CommodityCategoriesEntity> findAllByComId(Integer com_ID);
    List<CommodityCategoriesEntity> findAllByComCategory(String category);
}
