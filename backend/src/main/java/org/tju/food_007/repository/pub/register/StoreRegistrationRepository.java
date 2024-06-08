package org.tju.food_007.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.StoreEntity;

/**
 * @author WGY
 * @create 2024-03-10-11:28
 */
public interface StoreRegistrationRepository extends JpaRepository<StoreEntity,Integer> {
}
