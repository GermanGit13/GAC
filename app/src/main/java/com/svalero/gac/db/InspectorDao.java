package com.svalero.gac.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.gac.domain.Brigde;
import com.svalero.gac.domain.Inspector;

import java.util.List;

@Dao
public interface InspectorDao {

    /**
     * Para realizar una Query de todos los Inspectores
     */
    @Query(value = "SELECT * FROM inspectors")
    List<Inspector> getAll();

    /**
     * Para realizar una busqueda por numero de licencia del inspector
     * @return
     */
    @Query(value = "SELECT * FROM inspectors WHERE numberLicense = :numberLicense")
    Inspector getByNumberLicense(int numberLicense);

    /**
     * Para realizar una busqueda por numero de id del inspector
     * @return
     */
    @Query(value = "SELECT * FROM inspectors WHERE inspector_id = :inspector_id")
    Inspector getById(long inspector_id);

    /**
     * Para insertar en la BBDD
     * Brigde... Significa que le podemos pasar uno o varios objetos de ese tipo
     * Brigde Solo insertar un objeto
     */
    @Insert
    void insert (Inspector... inspectors);

    /**
     * Para borrar en la BBDD
     */
    @Delete
    void delete(Inspector inspector);

    /**
     * Para modificar en la BBDD
     */
    @Update
    void update(Inspector inspector);
}
