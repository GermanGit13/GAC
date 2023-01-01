package com.svalero.gac.domain;

import androidx.annotation.BoolRes;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "inspections")
public class Inspection {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long inspection_id;
    @ColumnInfo
    public long inspectorCreatorId; //Será el Id del Inspector para saber cuales son sus inspecciones
    @ColumnInfo
    public long bridgeInspId; //Será el id del puente al que se asocia la inspeccion

    @ColumnInfo
    private boolean vain;
    @ColumnInfo
    private boolean stapes;
    @ColumnInfo
    private int damage;
    @ColumnInfo
    private boolean platform;
    @ColumnInfo
    private boolean condition;
    @ColumnInfo
    private String Comment;
    //Todo campo fecha

    public Inspection(){

    }

    public Inspection(long inspectorCreatorId, long bridgeInspId, boolean vain, boolean stapes, int damage, boolean platform, boolean condition, String comment) {
        this.inspectorCreatorId = inspectorCreatorId;
        this.bridgeInspId = bridgeInspId;
        this.vain = vain;
        this.stapes = stapes;
        this.damage = damage;
        this.platform = platform;
        this.condition = condition;
        Comment = comment;
    }

    public long getInspection_id() {
        return inspection_id;
    }

    public void setInspection_id(long inspection_id) {
        this.inspection_id = inspection_id;
    }

    public long getInspectorCreatorId() {
        return inspectorCreatorId;
    }

    public void setInspectorCreatorId(long inspectorCreatorId) {
        this.inspectorCreatorId = inspectorCreatorId;
    }

    public long getBridgeInspId() {
        return bridgeInspId;
    }

    public void setBridgeInspId(long bridgeInspId) {
        this.bridgeInspId = bridgeInspId;
    }

    public boolean isVain() {
        return vain;
    }

    public void setVain(boolean vain) {
        this.vain = vain;
    }

    public boolean isStapes() {
        return stapes;
    }

    public void setStapes(boolean stapes) {
        this.stapes = stapes;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isPlatform() {
        return platform;
    }

    public void setPlatform(boolean platform) {
        this.platform = platform;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
