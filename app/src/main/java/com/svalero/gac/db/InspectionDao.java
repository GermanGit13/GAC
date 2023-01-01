package com.svalero.gac.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.gac.domain.Inspection;

import java.util.List;

@Dao
public interface InspectionDao {

    /**
     * Para realizar una Query de todos los Inspecciones
     */
    @Query(value = "SELECT * FROM inspections")
    List<Inspection> getAll();

    /**
     * Para realizar una busqueda de inspecciones por el id del puente
     * @return
     */
    @Query(value = "SELECT * FROM inspections WHERE bridgeInspId = :bridgeInspId")
    List<Inspection> getAllBridgeInspId(long bridgeInspId);

    /**
     * Para realizar una busqueda de inspecciones por el id del inspector
     * @return
     */
    @Query(value = "SELECT * FROM inspections WHERE inspectorCreatorId = :inspectorCreatorId")
    List<Inspection> getAllInspectorCreatorId(long inspectorCreatorId);

    /**
     * Para realizar una busqueda de inspecciones por el id
     * @return
     */
    @Query(value = "SELECT * FROM inspections WHERE inspection_id = :inspection_id")
    Inspection getByid(long inspection_id);

    /**
     * Para insertar en la BBDD
     * Inspection... Significa que le podemos pasar uno o varios objetos de ese tipo
     * Inspection Solo insertar un objeto
     */
    @Insert
    void insert (Inspection... inspections);

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
