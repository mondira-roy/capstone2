package com.company.capstone2.retailapi.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.Objects;

public class Levelup {
    private int levelupId;
    private int customerId;
    private int point;
    // generates "yyyy-MM-dd" output
    @JsonSerialize(using = LocalDateSerializer.class)
    // handles "yyyy-MM-dd" input just fine (note: "yyyy-M-d" format will not work)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate memberdate; // create memberdate when create customer

    @Override
    public String toString() {
        return "Levelup{" +
                "levelupId=" + levelupId +
                ", customerId=" + customerId +
                ", point=" + point +
                ", memberdate=" + memberdate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Levelup levelup = (Levelup) o;
        return levelupId == levelup.levelupId &&
                customerId == levelup.customerId &&
                point == levelup.point &&
                memberdate.equals(levelup.memberdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(levelupId, customerId, point, memberdate);
    }

    public int getLevelupId() {
        return levelupId;
    }

    public void setLevelupId(int levelupId) {
        this.levelupId = levelupId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public LocalDate getMemberdate() {
        return memberdate;
    }

    public void setMemberdate(LocalDate memberdate) {
        this.memberdate = memberdate;
    }
}