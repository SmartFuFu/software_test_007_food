package org.tju.food_007.repository.com.Information;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tju.food_007.model.CommodityEntity;
import java.util.List;

public interface CommodityDetailRepository extends JpaRepository<CommodityEntity,Integer> {
    boolean existsByComId(Integer com_id);

    @Query(value = "SELECT\n" +
            "  c.COM_ID, c.COM_NAME, c.COM_INTRODUCTION, c.COM_ORIPRICE, c.COM_TYPE,\n" +
            "  c.COM_UPLOADDATE, c.COM_LEFT, c.PRAISE_RATE, c.STO_ID, s.STO_NAME,\n" +
            "  s.STO_OPENINGTIME,s.STO_CLOSINGTIME,rc.COM_EXPIRATIONDATE ,\n" +
            "  GROUP_CONCAT(DISTINCT cc.COM_CATEGORY) AS Categories,\n" +
            "  GROUP_CONCAT(DISTINCT ci.COM_IMAGE) AS Images,\n" +
            "  pc.COM_PC_TIME, pc.COM_PC_PRICE ," +
            "  rc.COM_PRODUCEDDATE \n" +
            " FROM commodity c\n" +
            " JOIN store s ON c.STO_ID = s.STO_ID\n" +
            " LEFT JOIN  regular_commodity rc ON c.COM_ID = rc.REGULAR_COM_ID\n" +
            " LEFT JOIN commodity_categories cc ON c.COM_ID = cc.COM_ID\n" +
            " LEFT JOIN commodityImage ci ON c.COM_ID = ci.COM_ID\n" +
            " LEFT JOIN commodityPriceCurve pc ON c.COM_ID = pc.COM_ID\n" +
            " WHERE c.COM_ID = :com_id AND c.COM_TYPE=0\n" +
            " GROUP BY c.COM_ID, pc.COM_PC_TIME, pc.COM_PC_PRICE\n" +
            " ORDER BY pc.COM_PC_TIME ASC",nativeQuery = true)
    public List<Object[]> getCommodityDetail(@Param("com_id") int com_id);


    @Query(value = "SELECT\n" +
            "  c.COM_ID, c.COM_NAME, c.COM_INTRODUCTION, c.COM_ORIPRICE, c.COM_TYPE,\n" +
            "  c.COM_UPLOADDATE, c.COM_LEFT, c.PRAISE_RATE, c.STO_ID, s.STO_NAME,\n" +
            "  s.STO_OPENINGTIME,s.STO_CLOSINGTIME,rc.COM_EXPIRATIONDATE ,\n" +
            "  GROUP_CONCAT(DISTINCT cc.COM_CATEGORY) AS Categories,\n" +
            "  GROUP_CONCAT(DISTINCT ci.COM_IMAGE) AS Images,\n" +
            "  (SELECT GROUP_CONCAT(CONCAT(pc.COM_PC_TIME, '#', pc.COM_PC_PRICE)) \n" +
            "   FROM commodityPriceCurve pc \n" +
            "   WHERE pc.COM_ID = c.COM_ID)  As PriceCru , " +
            "   rc.COM_PRODUCEDDATE " +
            " FROM commodity c\n" +
            " JOIN store s ON c.STO_ID = s.STO_ID\n" +
            " LEFT JOIN  regular_commodity rc ON c.COM_ID = rc.REGULAR_COM_ID\n" +
            " LEFT JOIN commodity_categories cc ON c.COM_ID = cc.COM_ID\n" +
            " LEFT JOIN commodityImage ci ON c.COM_ID = ci.COM_ID\n" +
            " WHERE c.STO_ID = :sto_id AND c.COM_TYPE=0\n" +
            " GROUP BY c.COM_ID \n" +
            " ORDER BY c.COM_UPLOADDATE DESC" ,nativeQuery = true)
    public List<Object[]> getCommodityList(@Param("sto_id") int sto_id);




    @Query(value = "SELECT\n" +
            "  c.COM_ID, c.COM_NAME, c.COM_INTRODUCTION, c.COM_ORIPRICE, c.COM_TYPE,\n" +
            "  c.COM_UPLOADDATE, c.COM_LEFT, c.PRAISE_RATE, c.STO_ID, s.STO_NAME,\n" +
            "  s.STO_OPENINGTIME,s.STO_CLOSINGTIME,rc.COM_EXPIRATIONDATE ,\n" +
            "  GROUP_CONCAT(DISTINCT cc.COM_CATEGORY) AS Categories,\n" +
            "  GROUP_CONCAT(DISTINCT ci.COM_IMAGE) AS Images,\n" +
            "  (SELECT GROUP_CONCAT(CONCAT(pc.COM_PC_TIME, '#', pc.COM_PC_PRICE)) \n" +
            "   FROM commodityPriceCurve pc\n" +
            "   WHERE pc.COM_ID = c.COM_ID)  As PriceCru ," +
            "   rc.COM_PRODUCEDDATE " +
            " FROM commodity c\n" +
            " JOIN store s ON c.STO_ID = s.STO_ID\n" +
            " LEFT JOIN  regular_commodity rc ON c.COM_ID = rc.REGULAR_COM_ID\n" +
            " LEFT JOIN commodity_categories cc ON c.COM_ID = cc.COM_ID\n" +
            " LEFT JOIN commodityImage ci ON c.COM_ID = ci.COM_ID\n" +
            " WHERE c.STO_ID = :sto_id \n" +
            " GROUP BY c.COM_ID \n" +
            " HAVING Categories LIKE %:type% \n" +
            " ORDER BY c.COM_UPLOADDATE DESC" ,nativeQuery = true)
    public List<Object[]> getCommodityListByCat(@Param("sto_id") int sto_ID, @Param("type") String type);


    @Query(value="SELECT DISTINCT" +
            " c.COM_ID, rc.COM_EXPIRATIONDATE, rc.COM_PRODUCEDDATE " +
            " FROM commodity c " +
            " JOIN store s ON c.STO_ID = s.STO_ID " +
            " LEFT JOIN  regular_commodity rc ON c.COM_ID = rc.REGULAR_COM_ID " +
            " WHERE c.STO_ID = :sto_id AND c.COM_TYPE = 0 ",nativeQuery = true)
    public List<Object[]> getCommodityStatistics(@Param("sto_id") int sto_id);

    public CommodityEntity findByComId(Integer com_ID);
}
