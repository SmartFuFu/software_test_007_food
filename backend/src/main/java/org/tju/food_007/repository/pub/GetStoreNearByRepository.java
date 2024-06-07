package org.tju.food_007.repository.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import org.tju.food_007.model.StoreEntity;

import java.util.List;

/**
 * @author WGY
 * @create 2024-03-10-15:04
 */
public interface GetStoreNearByRepository extends JpaRepository<StoreEntity,Integer> {

    public List<StoreEntity>findAll();
}
