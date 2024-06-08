package org.tju.food_007.repository.cus.info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CustomerEntity;


/**
 * @author WGY
 * @create 2024-04-01-17:38
 */
public interface GetCusInfoRepository extends JpaRepository<CustomerEntity,Integer> {
    CustomerEntity findByCusId(Integer cus_id);
}

