package com.svalero.gac;

import static com.svalero.gac.db.Constants.DATABASE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.svalero.gac.adapter.BridgeAdapter;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Brigde;

import java.util.ArrayList;
import java.util.List;

public class BridgeAllActivity extends AppCompatActivity {

    private List<Brigde> brigdeList; //Lista de puentes para obtener los todos los puentes de la BBDD
    private BridgeAdapter adapter; //Para poder conectar con la BBDD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brige_all);

        brigdeList = new ArrayList<>(); //Instanciamos la lista a vacio la seguimos usando como referencia pero la llenamos con la BBDD

        /**
         * Pauta generales para trabajar con recyclerView. Para que se ajuste al layout y nos haga caso
         */
        RecyclerView recyclerView = findViewById(R.id.rc_brigde_all); //Nos hacemos con el RecyclerView que en el layout activity_main.xml le hemos llamado brigde_list
        recyclerView.setHasFixedSize(true); //para decirle que tiene un tamaño fijo y ocupe xtodo lo que tiene asigando
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); //Para decirle al activity que lo va a gestionar un layoutManager
        recyclerView.setLayoutManager(layoutManager); // para que el reciclerView se ciña al layoutManager
        /**
         * FIN Pauta generales para trabajar con recyclerView
         */

        adapter = new BridgeAdapter(this, brigdeList);  //creamos el adapter y le pasamos la vista actual y la lista de puentes
        recyclerView.setAdapter(adapter); //el adaptador que sabe como poblar de datos la lista en android
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
            final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();

            brigdeList.clear(); //Vaciamos la brigdeList por si tuviera algo
            brigdeList.addAll(db.brigdeDao().getAll()); //Añadimos xtodo lo que la BBDD nos devuelve
            adapter.notifyDataSetChanged(); //Para que actualice desde la BBDD
    }

    /**
     * PAra crear el menu (el actionBar)
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_brigde_all, menu); //Inflamos el menu
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