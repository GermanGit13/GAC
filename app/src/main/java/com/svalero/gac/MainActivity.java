package com.svalero.gac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.svalero.gac.adapter.BridgeAdapter;
import com.svalero.gac.domain.Brigde;

import java.util.ArrayList;
import java.util.List;

/**
 * Extiende de AppCompatActivity: donde hay un motón de código para usar por esos sobreescribimos los métodos de esta clase
 * implements AdapterView.OnItemClickListener Implementa el metodo onItemClick que le dice que cuando hagamos click en un elemento de la lista se habra en otra activity
 */
public class MainActivity extends AppCompatActivity {

    private List<Brigde> brigdeList; //Lista de puentes para obtener los todos los puentes de la BBDD
    private BridgeAdapter adapter; //Para poder conectar con la BBDD
    Button listBrigde; // Creamos el objeto button que esta en mainactivity
    Button addBrige;
    Button listInspector;
    Button addInspector;
    Button listInspection;
    Button addInspection;


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

        brigdeList = new ArrayList<>(); //Instanciamos la lista a vacio la seguimos usando como referencia pero la llenamos con la BBDD

        listBrigde = findViewById(R.id.list_brigde_main_button);
        listBrigde.setOnClickListener(view -> {
            Intent intent = new Intent(this, BridgeAllActivity.class); //donde nos manda al pinchar sobre el boton mapas en el action bar
            startActivity(intent);
        });

        addBrige = findViewById(R.id.add_brigde_main_button);
        addBrige.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterBrigdeActivity.class); //donde nos manda al pinchar sobre el boton mapas en el action bar
            startActivity(intent);
        });

        listInspector = findViewById(R.id.list_inspector_main_button);
        listInspector.setOnClickListener(view -> {
            Intent intent = new Intent(this, InspectorAllActivity.class); //donde nos manda al pinchar sobre el boton mapas en el action bar
            startActivity(intent);
        });

        addInspector = findViewById(R.id.add_inspector_main_button);
        addInspector.setOnClickListener(view -> {
            Intent intent = new Intent(this, InspectorRegisterActivity.class); //donde nos manda al pinchar sobre el boton mapas en el action bar
            startActivity(intent);
        });

        listInspection = findViewById(R.id.list_inspection_main_button);
        listInspection.setOnClickListener(view -> {
            Intent intent = new Intent(this, InspectionAllActivity.class); //donde nos manda al pinchar sobre el boton mapas en el action bar
            startActivity(intent);
        });

//        addInspection.setOnClickListener(view -> {
//            Intent intent = new Intent(this, InspectionRegisterActivity.class); //donde nos manda al pinchar sobre el boton mapas en el action bar
//            startActivity(intent);
//        });

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
//            final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
//                    .allowMainThreadQueries().build();
//
//            brigdeList.clear(); //Vaciamos la taskList por si tuviera algo
//            brigdeList.addAll(db.brigdeDao().getAll()); //Añadimos xtodo lo que la BBDD nos devuelve
//            adapter.notifyDataSetChanged(); //Para que actualice desde la BBDD
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
        else if (item.getItemId() == R.id.view_map) { //Para cuando pulsan en la boton del mapa en el actionbar
            Intent intent = new Intent(this, MapsActivity.class); //donde nos manda al pinchar sobre el boton mapas en el action bar
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.register_inspector) {
            Intent intent    = new Intent(this, InspectorRegisterActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.go_init) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.ab_back) {
            onBackPressed();
            return true;
        }

        return false;
    }
}