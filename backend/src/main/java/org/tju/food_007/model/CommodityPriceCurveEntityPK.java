package org.tju.food_007.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class CommodityPriceCurveEntityPK implements Serializable {
    @Column(name = "com_ID")
    @Id
    private int comId;
    @Column(name = "com_pc_time")
    @Id
    private Timestamp comPcTime;

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public Timestamp getComPcTime() {
        return comPcTime;
    }

    public void setComPcTime(Timestamp comPcTime) {
        this.comPcTime = comPcTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommodityPriceCurveEntityPK that = (CommodityPriceCurveEntityPK) o;
        return comId == that.comId && Objects.equals(comPcTime, that.comPcTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comId, comPcTime);
    }
}
