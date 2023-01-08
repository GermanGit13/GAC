package com.svalero.gac.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.gac.domain.Inspection;

import java.util.List;

/**
 * Contiene los metodos que queremos publicar, es como el repository en Java
 * Solamente los Query Methods
 * Por cada tabla de BBDD tenemos que tener un Dao
 *
 */
@Dao
public interface InspectionDao {

    /**
     * Para realizar una Query de todos los Inspecciones
     */
    @Query(value = "SELECT * FROM inspections")
    List<Inspection> getAll();

    /**
     * Para realizar una Query de todos las Inspecciones por id
     */
    @Query(value = "SELECT * FROM inspections WHERE inspection_id = :inspection_id")
    Inspection getById(long inspection_id);

    /**
     * Para realizar una Query de todos las Inspecciones por id de Inspector
     */
    @Query(value = "SELECT * FROM inspections WHERE inspectorCreatorId = :inspectorCreatorId")
    Inspection getByInspectorId(long inspectorCreatorId);

    /**
     * Para realizar una Query de todos las Inspecciones por id del puente
     */
    @Query(value = "SELECT * FROM inspections WHERE bridgeInspId = :bridgeInspId")
    Inspection getByBridgeId(long bridgeInspId);

    /**
     * Para insertar en la BBDD
     * Inspection... Significa que le podemos pasar uno o varios objetos de ese tipo
     * Inspection Solo insertar un objeto
     */
    @Insert
    void insert (Inspection inspection);

    /**
     * Para borrar en la BBDD
     */
    @Delete
    void delete(Inspection inspection);

    /**
     * Para modificar en la BBDD
     */
    @Update
    void update(Inspection inspection);
}
