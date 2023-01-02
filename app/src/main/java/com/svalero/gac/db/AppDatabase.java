package com.svalero.gac.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.svalero.gac.domain.Brigde;

/**
 * Esta clase nos va a permitir instanciar la BBDD
 * @Database(entities = {Task.class }, version = 1): Le decimimos mediante Anotación que es una BBDD y clase quiero Guardar en este caso la clase TASK.
 * Para mas clases añadir más @Database
 * más la version de la BBDD para poder actualizar mediante este número la apliacion y si existen nuevas tablas no se pierdan los datos mediante una serie de metodos
 * Necesitamos poner en Grandle Scripts/build.grandle la librería y la anotación
 *     implentation "androidx.room:room-runtime:2.4.3"
 *     annotationProcessor "androidx.room:room-runtime:2.4.3"
 * Por cada Tabla que tengamos necesitamos un Dao
 */
@Database(entities = {Brigde.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BrigdeDao brigdeDao();

}
