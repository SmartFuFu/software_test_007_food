package org.tju.food_007.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Objects;

/**
 * @author WGY
 * @create 2024-04-19-11:20
 */
@Entity
@Table(name = "store", schema = "food_007", catalog = "")
public class StoreEntity {
    @Id
    @Column(name = "sto_ID")
    private Integer stoId;
    @Basic
    @Column(name = "sto_name")
    private String stoName;
    @Basic
    @Column(name = "sto_introduction")
    private String stoIntroduction;
    @Basic
    @Column(name = "sto_state")
    private Integer stoState;
    @Basic
    @Column(name = "sto_openingTime")
    private Time stoOpeningTime;
    @Basic
    @Column(name = "sto_closingTime")
    private Time stoClosingTime;
    @Basic
    @Column(name = "sto_latitude")
    private String stoLatitude;
    @Basic
    @Column(name = "sto_longitude")
    private String stoLongitude;
    @Basic
    @Column(name = "sto_rating")
    private BigDecimal stoRating;
    @Basic
    @Column(name = "sto_logo")
    private String stoLogo;

    public Integer getStoId() {
        return stoId;
    }

    public void setStoId(Integer stoId) {
        this.stoId = stoId;
    }

    public String getStoName() {
        return stoName;
    }

    public void setStoName(String stoName) {
        this.stoName = stoName;
    }

    public String getStoIntroduction() {
        return stoIntroduction;
    }

    public void setStoIntroduction(String stoIntroduction) {
        this.stoIntroduction = stoIntroduction;
    }

    public Integer getStoState() {
        return stoState;
    }

    public void setStoState(Integer stoState) {
        this.stoState = stoState;
    }

    public Time getStoOpeningTime() {
        return stoOpeningTime;
    }

    public void setStoOpeningTime(Time stoOpeningTime) {
        this.stoOpeningTime = stoOpeningTime;
    }

    public Time getStoClosingTime() {
        return stoClosingTime;
    }

    public void setStoClosingTime(Time stoClosingTime) {
        this.stoClosingTime = stoClosingTime;
    }

    public String getStoLatitude() {
        return stoLatitude;
    }

    public void setStoLatitude(String stoLatitude) {
        this.stoLatitude = stoLatitude;
    }

    public String getStoLongitude() {
        return stoLongitude;
    }

    public void setStoLongitude(String stoLongitude) {
        this.stoLongitude = stoLongitude;
    }

    public BigDecimal getStoRating() {
        return stoRating;
    }

    public void setStoRating(BigDecimal stoRating) {
        this.stoRating = stoRating;
    }

    public String getStoLogo() {
        return stoLogo;
    }

    public void setStoLogo(String stoLogo) {
        this.stoLogo = stoLogo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreEntity that = (StoreEntity) o;
        return Objects.equals(stoId, that.stoId) && Objects.equals(stoName, that.stoName) && Objects.equals(stoIntroduction, that.stoIntroduction) && Objects.equals(stoState, that.stoState) && Objects.equals(stoOpeningTime, that.stoOpeningTime) && Objects.equals(stoClosingTime, that.stoClosingTime) && Objects.equals(stoLatitude, that.stoLatitude) && Objects.equals(stoLongitude, that.stoLongitude) && Objects.equals(stoRating, that.stoRating) && Objects.equals(stoLogo, that.stoLogo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stoId, stoName, stoIntroduction, stoState, stoOpeningTime, stoClosingTime, stoLatitude, stoLongitude, stoRating, stoLogo);
    }
}
