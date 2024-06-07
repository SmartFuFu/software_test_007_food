package org.tju.food_007.repository.pub;

import org.springframework.data.jpa.repository.JpaRepository;

import org.tju.food_007.model.UserEntity;

/**
 * @author WGY
 * @create 2024-04-26-14:10
 */
public interface BalanceChangeRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findUserEntityByUserId(Integer user_id);
}
