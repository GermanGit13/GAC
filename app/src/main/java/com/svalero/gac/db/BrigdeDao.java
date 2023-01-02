package com.svalero.gac.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.gac.domain.Brigde;

import java.util.List;

/**
 * Contiene los metodos que queremos publicar, es como el repository en Java
 * Solamente los Query Methods
 * Por cada tabla de BBDD tenemos que tener un Dao
 *
 */
@Dao
public interface BrigdeDao {

    /**
     * Para realizar una Query de todos los Puentes
    */
    @Query(value = "SELECT * FROM bridges")
    List<Brigde> getAll();

    /**
     * Para realizar una busqueda por nombre del puente
     * @param name buscar por campo nombre
     * @return
     */
    @Query(value = "SELECT * FROM bridges WHERE name = :name")
    Brigde getByName(String name);

    /**
     * Para realizar una Query de todos los Puentes por ciudad
     */
    @Query(value = "SELECT * FROM bridges WHERE city = :city")
    List<Brigde> getAllCity(String city);

    /**
     * Para realizar una Query de todos los Puentes por id
     */
    @Query(value = "SELECT * FROM bridges WHERE brigde_id = :brigde_id")
    Brigde getById(long brigde_id);

    /**
     * Para insertar en la BBDD
     * Brigde... Significa que le podemos pasar uno o varios objetos de ese tipo
     * Brigde Solo insertar un objeto
     */
    @Insert
    void insert (Brigde... brigde);

    /**
     * Para borrar en la BBDD
     */
    @Delete
    void delete(Brigde brigde);

    /**
     * Para modificar en la BBDD
     */
    @Update
    void update(Brigde brigde);
}
