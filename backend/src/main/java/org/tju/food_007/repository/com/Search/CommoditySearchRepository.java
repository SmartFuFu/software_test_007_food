package org.tju.food_007.repository.com.Search;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tju.food_007.model.CommodityEntity;

import java.util.List;

public interface CommoditySearchRepository extends JpaRepository<CommodityEntity,Integer> {

    @Query(value = "SELECT\n" +
            "  c.COM_ID, c.COM_NAME, c.COM_INTRODUCTION, c.COM_ORIPRICE, c.COM_TYPE,\n" +
            "  c.COM_UPLOADDATE, c.COM_LEFT, c.PRAISE_RATE, c.STO_ID, s.STO_NAME,\n" +
            "  s.STO_OPENINGTIME,s.STO_CLOSINGTIME,rc.COM_EXPIRATIONDATE ,\n" +
            "  GROUP_CONCAT(DISTINCT cc.COM_CATEGORY) AS Categories,\n" +
            "  GROUP_CONCAT(DISTINCT ci.COM_IMAGE) AS Images," +
            "  u.USER_ADDRESS,\n" +
            "  (SELECT GROUP_CONCAT(CONCAT(pc.COM_PC_TIME, '#', pc.COM_PC_PRICE))\n" +
            "   FROM commodityPriceCurve pc\n" +
            "   WHERE pc.COM_ID = c.COM_ID)  As PriceCru ," +
            "   rc.COM_PRODUCEDDATE " +
            " FROM commodity c\n" +
            " JOIN store s ON c.STO_ID = s.STO_ID\n" +
            " RIGHT JOIN  regular_commodity rc ON c.COM_ID = rc.REGULAR_COM_ID\n" +
            " LEFT JOIN commodity_categories cc ON c.COM_ID = cc.COM_ID\n" +
            " LEFT JOIN commodityImage ci ON c.COM_ID = ci.COM_ID\n" +
            " LEFT JOIN user u ON u.USER_ID = s.STO_ID\n" +
            " WHERE c.COM_NAME LIKE %:content% \n" +
            " GROUP BY c.COM_ID \n" +
            " ORDER BY CASE WHEN :sort_by = 'COM_ORIPRICE' AND :sort_order='ASC' THEN c.COM_ORIPRICE END ASC, " +
            "          CASE WHEN :sort_by = 'PRAISE_RATE' AND :sort_order='ASC' THEN c.PRAISE_RATE END ASC, " +
            "          CASE WHEN :sort_by = 'COM_UPLOADDATE' AND :sort_order='ASC' THEN c.COM_UPLOADDATE END ASC," +
            "          CASE WHEN :sort_by = 'COM_ORIPRICE' AND :sort_order='DESC' THEN c.COM_ORIPRICE END DESC," +
            "          CASE WHEN :sort_by = 'PRAISE_RATE' AND :sort_order='DESC' THEN c.PRAISE_RATE END DESC," +
            "          CASE WHEN :sort_by = 'COM_UPLOADDATE' AND :sort_order='DESC' THEN c.COM_UPLOADDATE END DESC"
            ,nativeQuery = true)
    public List<Object[]> searchCommodity(@Param("content") String content,
                                          @Param("sort_by") String sort_by,
                                          @Param("sort_order") String sort_order);

    @Query(value = "SELECT\n" +
            "  c.COM_ID, c.COM_NAME, c.COM_INTRODUCTION, c.COM_ORIPRICE, c.COM_TYPE,\n" +
            "  c.COM_UPLOADDATE, c.COM_LEFT, c.PRAISE_RATE, c.STO_ID, s.STO_NAME,\n" +
            "  s.STO_OPENINGTIME,s.STO_CLOSINGTIME,rc.COM_EXPIRATIONDATE ,\n" +
            "  GROUP_CONCAT(DISTINCT cc.COM_CATEGORY) AS Categories,\n" +
            "  GROUP_CONCAT(DISTINCT ci.COM_IMAGE) AS Images," +
            "  u.USER_ADDRESS,\n" +
            "  (SELECT GROUP_CONCAT(CONCAT(pc.COM_PC_TIME, '#', pc.COM_PC_PRICE))\n" +
            "   FROM commodityPriceCurve pc\n" +
            "   WHERE pc.COM_ID = c.COM_ID)  As PriceCru , " +
            "   rc.COM_PRODUCEDDATE " +
            " FROM commodity c\n" +
            " JOIN store s ON c.STO_ID = s.STO_ID\n" +
            " RIGHT JOIN  regular_commodity rc ON c.COM_ID = rc.REGULAR_COM_ID\n" +
            " LEFT JOIN commodity_categories cc ON c.COM_ID = cc.COM_ID\n" +
            " LEFT JOIN commodityImage ci ON c.COM_ID = ci.COM_ID\n" +
            " LEFT JOIN user u ON u.USER_ID = s.STO_ID\n" +
            " WHERE c.COM_NAME LIKE %:content% \n" +
            " GROUP BY c.COM_ID \n" +
            " HAVING Categories LIKE %:type% \n" +
            " ORDER BY CASE WHEN :sort_by = 'COM_ORIPRICE' AND :sort_order='ASC' THEN c.COM_ORIPRICE END ASC, " +
            "          CASE WHEN :sort_by = 'PRAISE_RATE' AND :sort_order='ASC' THEN c.PRAISE_RATE END ASC, " +
            "          CASE WHEN :sort_by = 'COM_UPLOADDATE' AND :sort_order='ASC' THEN c.COM_UPLOADDATE END ASC," +
            "          CASE WHEN :sort_by = 'COM_ORIPRICE' AND :sort_order='DESC' THEN c.COM_ORIPRICE END DESC," +
            "          CASE WHEN :sort_by = 'PRAISE_RATE' AND :sort_order='DESC' THEN c.PRAISE_RATE END DESC," +
            "          CASE WHEN :sort_by = 'COM_UPLOADDATE' AND :sort_order='DESC' THEN c.COM_UPLOADDATE END DESC"
            ,nativeQuery = true)
    public List<Object[]> searchCommoditybyType(@Param("content") String content,
                                          @Param("sort_by") String sort_by,
                                          @Param("sort_order") String sort_order,
                                                @Param("type") String type);


}
