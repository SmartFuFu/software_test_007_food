package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CustomerEntity;


public interface UserUploadLogoImageCusRepository extends JpaRepository<CustomerEntity,Integer> {
    CustomerEntity findByCusId(Integer cus_id);
}
