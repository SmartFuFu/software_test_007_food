package org.tju.food_007.repository.cus.indent;

import org.springframework.data.jpa.repository.JpaRepository;

import org.tju.food_007.model.UserEntity;

public interface GetIndComPositionRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByUserId(int user_ID);
}
