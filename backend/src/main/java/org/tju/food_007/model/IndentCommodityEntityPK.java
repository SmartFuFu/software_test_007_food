package org.tju.food_007.model;

import jakarta.persistence.Column;

import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class IndentCommodityEntityPK implements Serializable {
    @Column(name = "ind_ID")
    @Id
    private int indId;
    @Column(name = "com_ID")
    @Id
    private int comId;

    public int getIndId() {
        return indId;
    }

    public void setIndId(int indId) {
        this.indId = indId;
    }

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndentCommodityEntityPK that = (IndentCommodityEntityPK) o;
        return indId == that.indId && comId == that.comId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(indId, comId);
    }
}
