package com.svalero.gac;

import static com.svalero.gac.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import com.svalero.gac.adapter.InspectionAdapter;
import com.svalero.gac.adapter.InspectorAdapter;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Inspection;
import com.svalero.gac.domain.Inspector;

import java.util.ArrayList;
import java.util.List;

public class InspectionAllActivity extends AppCompatActivity {

    private List<Inspection> inspectionList; //Lista de inspecciones para obtener los todos las inspecciones de la BBDD
    private InspectionAdapter adapter; //Para poder conectar con la BBDD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_all);

        inspectionList = new ArrayList<>();//Instanciamos la lista a vacio la seguimos usando como referencia pero la llenamos con la BBDD

        /**
         * Pauta generales para trabajar con recyclerView. Para que se ajuste al layout y nos haga caso
         */
        RecyclerView recyclerView = findViewById(R.id.rc_inspection_all); //Nos hacemos con el RecyclerView que en el layout activity_inspection_all.xml le hemos llamado rc_inspection_all
        recyclerView.setHasFixedSize(true); //para decirle que tiene un tamaño fijo y ocupe xtodo lo que tiene asigando
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); //Para decirle al activity que lo va a gestionar un layoutManager
        recyclerView.setLayoutManager(layoutManager); // para que el reciclerView se ciña al layoutManager
        /**
         * FIN Pauta generales para trabajar con recyclerView
         */

        adapter = new InspectionAdapter(this, inspectionList);  //creamos el adapter y le pasamos la vista actual y la lista de puentes
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
         * "DATABASE_NAME" el nombre de la BBDD
         * .allowMainThreadQueries().build(); -> Es para que android nos deje hacerlo sin tener de concurrencia
         */
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();

        inspectionList.clear(); //Vaciamos la taskList por si tuviera algo
        inspectionList.addAll(db.inspectionDao().getAll()); //Añadimos xtodo lo que la BBDD nos devuelve
        adapter.notifyDataSetChanged(); //Para que actualice desde la BBDD
    }
}
