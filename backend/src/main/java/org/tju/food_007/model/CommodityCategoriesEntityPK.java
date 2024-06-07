package org.tju.food_007.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class CommodityCategoriesEntityPK implements Serializable {
    @Column(name = "com_ID")
    @Id
    private int comId;
    @Column(name = "com_category")
    @Id
    private String comCategory;

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public String getComCategory() {
        return comCategory;
    }

    public void setComCategory(String comCategory) {
        this.comCategory = comCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommodityCategoriesEntityPK that = (CommodityCategoriesEntityPK) o;
        return comId == that.comId && Objects.equals(comCategory, that.comCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comId, comCategory);
    }
}
