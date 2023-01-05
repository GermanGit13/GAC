package com.svalero.gac.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.gac.domain.Brigde;
import com.svalero.gac.domain.Inspector;

import java.util.List;

/**
 * Contiene los metodos que queremos publicar, es como el repository en Java
 * Solamente los Query Methods
 * Por cada tabla de BBDD tenemos que tener un Dao
 *
 */
@Dao
public interface InspectorDao {

    /**
     * Para realizar una Query de todos los Inspectores
     */
    @Query(value = "SELECT * FROM inspectors")
    List<Inspector> getAll();

    /**
     * Para realizar una busqueda por nombre del inspector
     * @param name buscar por campo nombre
     * @return
     */
    @Query(value = "SELECT * FROM inspectors WHERE name = :name")
    Inspector getByName(String name);

     /**
     * Para realizar una Query de todos los Inpectores por id
     */
    @Query(value = "SELECT * FROM inspectors WHERE inspector_id = :inspector_id")
    Inspector getById(long inspector_id);

    /**
     * Para insertar en la BBDD
     * Inspector... Significa que le podemos pasar uno o varios objetos de ese tipo
     * Inspector Solo insertar un objeto
     */
    @Insert
    void insert (Inspector inspector);

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
