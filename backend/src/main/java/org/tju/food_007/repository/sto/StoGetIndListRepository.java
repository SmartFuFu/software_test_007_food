package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.IndentCommodityEntity;


import java.util.ArrayList;
import java.util.List;

public interface StoGetIndListRepository extends JpaRepository<IndentCommodityEntity,Integer> {
    List<IndentCommodityEntity> findByComIdIn(ArrayList<Integer> comids);
}
