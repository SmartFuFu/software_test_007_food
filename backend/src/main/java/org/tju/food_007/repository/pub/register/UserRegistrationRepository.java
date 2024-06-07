package org.tju.food_007.repository.pub.register;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.UserEntity;

/**
 * @author WGY
 * @create 2024-03-05-14:58
 */
public interface UserRegistrationRepository extends JpaRepository <UserEntity,Integer>{
    public boolean existsByUserPhone(String user_phone);
    public boolean existsByUserId(Integer cus_id);
}
