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
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Brigde;

public class BrigdeDetailsActivity extends AppCompatActivity {

    FloatingActionButton fabDelete; //Para borrar desde la vista detalle
    FloatingActionButton fabModify; //Para modificar desde la vista detalle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brigde_details);

        fabDelete = findViewById(R.id.fab_delete);
//        fabModify = findViewById(R.id.fab_modify);


        Intent intent = getIntent();
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
            builder.setMessage("¿Seguro que quieres eliminar")
                    .setTitle("Eliminar Puente")
                    .setPositiveButton("Si", (dialog, id) -> { //Añadimos los botones
                        final AppDatabase dbD = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD -< PAsamos el contexto para saber donde estamos
                                .allowMainThreadQueries().build();
                        dbD.brigdeDao().delete(brigde);
                        brigdeList();

//                        dbD.brigdeDao().delete(brigde); //Borramos de la BBDD
                    })
                    .setNegativeButton("No", (dialog, id) -> dialog.dismiss()); //Botones del dialogo que salta
            AlertDialog dialog = builder.create();
            dialog.show();//Importante para que se muestre
        }));
    }

    private void fillData(Brigde brigde) {
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
}