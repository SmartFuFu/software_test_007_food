package org.tju.food_007.repository.pub.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.UserEntity;

/**
 * @author WGY
 * @create 2024-03-10-16:53
 */
public interface UserLoginRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByUserPhone(String user_phone);

    UserEntity findByUserId(Integer user_id);

}
