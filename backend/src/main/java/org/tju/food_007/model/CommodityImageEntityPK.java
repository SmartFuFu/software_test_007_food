package org.tju.food_007.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class CommodityImageEntityPK implements Serializable {
    @Column(name = "com_ID")
    @Id
    private int comId;
    @Column(name = "com_image")
    @Id
    private String comImage;

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public String getComImage() {
        return comImage;
    }

    public void setComImage(String comImage) {
        this.comImage = comImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommodityImageEntityPK that = (CommodityImageEntityPK) o;
        return comId == that.comId && Objects.equals(comImage, that.comImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comId, comImage);
    }
}
