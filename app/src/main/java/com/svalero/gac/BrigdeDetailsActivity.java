package com.svalero.gac;

import static com.svalero.gac.db.Constants.DATABASE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svalero.gac.adapter.BridgeAdapter;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Brigde;

import java.util.concurrent.atomic.AtomicReference;

public class BrigdeDetailsActivity extends AppCompatActivity {

    private BridgeAdapter adapter; //Para poder conectar con la BBDD

    FloatingActionButton fabDelete; //Para borrar desde la vista detalle
    FloatingActionButton fabModify; //Para modificar desde la vista detalle
    FloatingActionButton fab_create_inspection; //Para crear una inspección asociada al puente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brigde_details);

        fabDelete = findViewById(R.id.fab_delete);
        fabModify = findViewById(R.id.fab_modify);


        AtomicReference<Intent> intent = new AtomicReference<>(getIntent());
        //Recuperar el puente por el id
        long brigde_id = getIntent().getLongExtra("brigde_id", 0);
//        long brigde_id = Long.parseLong(intent.getStringExtra("brideg_id"));
//        if (brigde_id == null)
//            return;

        //Instanciamos la BBDD
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Brigde brigde = db.brigdeDao().getById(brigde_id); //creamos el puente por su id
        fillData(brigde);

        //Método onClick para borrar
        fabDelete.setOnClickListener((view -> {
            /**
             * Dialogo para pregunta antes de si quiere borrar -> https://developer.android.com/guide/topics/ui/dialogs?hl=es-419
             */
            AlertDialog.Builder builder = new AlertDialog.Builder(this); //le pasamos el contexto donde estamos
            builder.setMessage(R.string.do_you_want_to_detele)
                    .setTitle(R.string.delete_brigde)
                    .setPositiveButton(R.string.yes, (dialog, id) -> { //Añadimos los botones
                        final AppDatabase dbD = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD -< PAsamos el contexto para saber donde estamos
                                .allowMainThreadQueries().build();
                        dbD.brigdeDao().delete(brigde);
                        brigdeList();

                    })
                    .setNegativeButton(R.string.not, (dialog, id) -> dialog.dismiss()); //Botones del dialogo que salta
            AlertDialog dialog = builder.create();
            dialog.show();//Importante para que se muestre
        }));

        //Método onClick para modificar
        fabModify.setOnClickListener((view -> {
            /**
             * Dialogo para pregunta antes de si quiere modificar -> https://developer.android.com/guide/topics/ui/dialogs?hl=es-419
             */
            AlertDialog.Builder builder = new AlertDialog.Builder(this); //le pasamos el contexto donde estamos
            builder.setMessage(R.string.do_you_want_to_modify)
                    .setTitle(R.string.modify_brigde)
                    .setPositiveButton(R.string.yes, (dialog, id) -> { //Añadimos los botones
                        final AppDatabase dbD = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD -< PAsamos el contexto para saber donde estamos
                                .allowMainThreadQueries().build();
//                        Brigde brigde = bridgeList.get(position); //recuperamos el puente por su posicion

                        intent.set(new Intent(this, BrigdeModifyActivity.class)); //Lo pasamos al activity para pintar el detalle de la tarea
                        intent.get().putExtra("brigde_id", brigde.getBrigde_id()); //Recogemos el id
                        this.startActivity(intent.get()); //lanzamos el intent que nos lleva al layout correspondiente

                    })
                    .setNegativeButton(R.string.not, (dialog, id) -> dialog.dismiss()); //Botones del dialogo que salta
            AlertDialog dialog = builder.create();
            dialog.show();//Importante para que se muestre
        }));

        //Método onClick para crear una inspeccion
//        fab_create_inspection.setOnClickListener((view -> {
//            /**
//             * Dialogo para pregunta antes de si quiere crear una inspeccion -> https://developer.android.com/guide/topics/ui/dialogs?hl=es-419
//             */
//            AlertDialog.Builder builder = new AlertDialog.Builder(this); //le pasamos el contexto donde estamos
//            builder.setMessage("¿Vas a crear una inspección?")
//                    .setTitle("Asignar inspección a este Puente")
//                    .setPositiveButton("Si", (dialog, id) -> { //Añadimos los botones
//                        final AppDatabase dbD = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD -< PAsamos el contexto para saber donde estamos
//                                .allowMainThreadQueries().build();
////                        Brigde brigde = bridgeList.get(position); //recuperamos el puente por su posicion
//
//                        intent.set(new Intent(this, InspectionRegisterActivity.class)); //Lo pasamos al activity registrar una inspeccion
//                        intent.get().putExtra("brigde_id", brigde.getBrigde_id()); //Recogemos el id
//                        this.startActivity(intent.get()); //lanzamos el intent que nos lleva al layout correspondiente
//
//                    })
//                    .setNegativeButton("No", (dialog, id) -> dialog.dismiss()); //Botones del dialogo que salta
//            AlertDialog dialog = builder.create();
//            dialog.show();//Importante para que se muestre
//        }));
    }

        private void fillData (Brigde brigde){
            TextView tvName = findViewById(R.id.tv_brigde_name);
            TextView tvCountry = findViewById(R.id.tv_brigde_country);
            TextView tvCity = findViewById(R.id.tv_brigde_city);
            TextView tvYearBuild = findViewById(R.id.tv_brigde_yearBuild);
            TextView tvNumberVain = findViewById(R.id.tv_brigde_numbervain);
            TextView tvNumberStapes = findViewById(R.id.tv_brigde_numberstapes);
            TextView tvPlatform = findViewById(R.id.tv_brigde_platform);

            tvName.setText(brigde.getName());
            tvCountry.setText(brigde.getCountry());
            tvCity.setText(brigde.getCity());
            tvYearBuild.setText(brigde.getYearBuild());
            tvNumberVain.setText(brigde.getNumberVain());
            tvNumberStapes.setText(brigde.getNumberStapes());
            tvPlatform.setText(brigde.getPlatform());
        }

        /**
         * Para volver al listado de puentes despues de borrar desde la vista de detall
         */
        private void brigdeList () {
            Intent intent = new Intent(this, BridgeAllActivity.class); //Desde la vista que estamos a la vista que queremos ir
            startActivity(intent); //iniciamos el intent
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