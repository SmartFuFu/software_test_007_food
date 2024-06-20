package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.UserEntity;

public interface UserInformatonDetailRepository extends JpaRepository<UserEntity,Integer> {
    boolean existsByUserId(Integer id);
    UserEntity findByUserId(Integer id);
}
