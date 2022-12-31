package com.svalero.gac.domain;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

/**
 * Contiene los metodos que queremos publicar, es como el repository en Java
 * Solamente los Query Methods
 * Por cada tabla de BBDD tenemos que tener un Dao
 *
 */
@Dao
public interface BrigdeDao {

    @Query("SElECT * FROM brigdes")
    List<Brigde> getAll;
}
