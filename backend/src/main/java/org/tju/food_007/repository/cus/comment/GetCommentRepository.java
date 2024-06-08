package org.tju.food_007.repository.cus.comment;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tju.food_007.model.CommentEntity;

import java.util.List;

public interface GetCommentRepository extends JpaRepository<CommentEntity,Integer> {
    @Query(value = "SELECT " +
            "DISTINCT cmt.CMT_ID , cmt.CMT_CONTENT , cmt.CMT_TIME ,cmt.IND_ID," +
            "cus.CUS_ID , cus.CUS_NICKNAME , cus.USER_LOGO " +
            "FROM comment AS cmt " +
            "LEFT JOIN customer AS cus ON cmt.USER_ID = cus.CUS_ID " +
            "LEFT JOIN indent_commodity AS ind ON cmt.IND_ID = ind.IND_ID " +
            "LEFT JOIN commodity AS com ON ind.COM_ID = com.COM_ID " +
            "WHERE com.STO_ID = :sto_ID " +
            "ORDER BY cmt.CMT_TIME DESC",nativeQuery = true)
    public List<Object[]> searchComment(@Param("sto_ID") Integer sto_ID);
}
