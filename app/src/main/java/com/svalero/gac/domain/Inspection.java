package com.svalero.gac.domain;

import static androidx.room.ForeignKey.SET_NULL;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "inspections",
        foreignKeys = {
                @ForeignKey(entity = Inspector.class, parentColumns = "inspector_id", childColumns = "inspectorCreatorId", onDelete = SET_NULL),
                @ForeignKey(entity = Brigde.class, parentColumns = "brige_id", childColumns = "bridgeInspId", onDelete = SET_NULL)
        })
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
    private boolean platformIns;
    @ColumnInfo
    private boolean condition;
    @ColumnInfo
    private String Comment;
    //Todo campo fecha

    public Inspection() {

    }

    public Inspection(long inspection_id, long inspectorCreatorId, long bridgeInspId, boolean vain, boolean stapes, int damage, boolean platformIns, boolean condition, String comment) {
        this.inspection_id = inspection_id;
        this.inspectorCreatorId = inspectorCreatorId;
        this.bridgeInspId = bridgeInspId;
        this.vain = vain;
        this.stapes = stapes;
        this.damage = damage;
        this.platformIns = platformIns;
        this.condition = condition;
        Comment = comment;
    }

    public Inspection(long inspectorCreatorId, long bridgeInspId, boolean vain, boolean stapes, int damage, boolean platformIns, boolean condition, String comment) {
        this.inspectorCreatorId = inspectorCreatorId;
        this.bridgeInspId = bridgeInspId;
        this.vain = vain;
        this.stapes = stapes;
        this.damage = damage;
        this.platformIns = platformIns;
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

    public void setInspectorCreatorId(int inspectorCreatorId) {
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

    public boolean isPlatformIns() {
        return platformIns;
    }

    public void setPlatformIns(boolean platformIns) {
        this.platformIns = platformIns;
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
