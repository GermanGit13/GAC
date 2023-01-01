package com.svalero.gac.domain;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * Como definir relacion entre dos tablas en este caso 1:N -> https://developer.android.com/training/data-storage/room/relationships?hl=es-419
 */
public class InspectorAndInspection {

    @Embedded public Inspector inspector; //Instancia Principal
    @Relation(
            parentColumn = "inspector_id",
            entityColumn = "inspectorCreatorId"
    )
    public List<Inspection> inspections;
}
