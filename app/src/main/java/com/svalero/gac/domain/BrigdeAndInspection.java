package com.svalero.gac.domain;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * Como definir relacion entre dos tablas en este caso 1:N -> https://developer.android.com/training/data-storage/room/relationships?hl=es-419
 */
public class BrigdeAndInspection {

    @Embedded
    public Brigde brigde; //Instancia Principal
    @Relation(
            parentColumn = "brigde_id",
            entityColumn = "bridgeInspId"
    )
    public List<Inspection> inspections;

}
