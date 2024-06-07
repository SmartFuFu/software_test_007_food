package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;

import org.tju.food_007.model.StoreEntity;

public interface UserUploadLogoImageStoreRepository extends JpaRepository<StoreEntity,Integer> {
    StoreEntity findByStoId(Integer sto_id);
}
