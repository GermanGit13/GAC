package com.svalero.gac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.svalero.gac.db.AppDatabase;

/**
 * Extiende de AppCompatActivity: donde hay un motón de código para usar por esos sobreescribimos los métodos de esta clase
 * implements AdapterView.OnItemClickListener Implementa el metodo onItemClick que le dice que cuando hagamos click en un elemento de la lista se habra en otra activity
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Método onCreate: mínimo obligatorio, primer método que una activity arranca por primera vez.
     * Método onResume: Para que la activity vuelva de segundo plano del onCreate. No duplicar código entre amas
     * podría ser por ejemplo en onResume refrescar la lista de tareas pendientes en este caso
     * Método onPause: Por ejemplo para programar la pausa de un juego cuando recibes una llamada
     * Atajos teclado: Alt + Insert
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * COmo pasamos tanto al crear la mainActivity principal como al volver de segundo plano, aqui es donde conectamos con
     * la BBDD para actualizar los datos
     */
    @Override
    protected void onResume() {
        super.onResume();
        /**
         * Llamamos a la BBDD mediante la clase AppDatabase
         * AppDatabase.class la clase que tiene la conexion con la BBDD
         * this -> activity en la que estamos
         * "tasks" el nombre de la BBDD
         * .allowMainThreadQueries().build(); -> Es para que android nos deje hacerlo sin tener de concurrencia
         */
    }

    /**
     * PAra crear el menu (el actionBar)
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu); //Inflamos el menu
        return true;
    }

    /**
     * Para cuando elegimos una opcion del menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.register_build) { //Evaluar a que opcion hemos pichado
            Intent intent = new Intent(this, RegisterBrigdeActivity.class); //donde nos manda al pinchar sobre el boton + en el action bar
            startActivity(intent);
            return true;
        }
//        else if (item.getItemId() == R.id.view_map) { //Para cuaando pulsan en la boton del mapa en el actionbar
//            Intent intent = new Intent(this, MapsActivity.class); //donde nos manda al pinchar sobre el boton mapas en el action bar
//            startActivity(intent);
//            return true;
//        }

        return false;
    }



}