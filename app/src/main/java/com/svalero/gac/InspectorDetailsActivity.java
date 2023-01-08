package com.svalero.gac;

import static com.svalero.gac.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svalero.gac.adapter.BridgeAdapter;
import com.svalero.gac.adapter.InspectorAdapter;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Brigde;
import com.svalero.gac.domain.Inspector;

import java.util.concurrent.atomic.AtomicReference;

public class InspectorDetailsActivity extends AppCompatActivity {

    private InspectorAdapter adapter; //Para poder conectar con la BBDD

    FloatingActionButton fabDelete; //Para borrar desde la vista detalle
    FloatingActionButton fabModify; //Para modificar desde la vista detalle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspector_details); //Layout que vamos a cargar

        fabDelete = findViewById(R.id.fab_delete_inspector); //Asigamos el boton al boton del layout
        fabModify = findViewById(R.id.fab_modify_inspector);


        AtomicReference<Intent> intent = new AtomicReference<>(getIntent()); //Porque tenemos dos distintos botones que nos lleva a dos zonas distintas
        //Recuperar el inspector por el id
        long inspector_id = getIntent().getLongExtra("inspector_id", 0);

        //Instanciamos la BBDD
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Inspector inspector = db.inspectorDao().getById(inspector_id); //creamos el inspector por su id
        fillData(inspector); //Rellenamos los campos con el método creado

        //Método onClick para borrar
        fabDelete.setOnClickListener((view -> {
            /**
             * Dialogo para pregunta antes de si quiere borrar -> https://developer.android.com/guide/topics/ui/dialogs?hl=es-419
             */
            AlertDialog.Builder builder = new AlertDialog.Builder(this); //le pasamos el contexto donde estamos
            builder.setMessage(R.string.do_you_want_to_detele)
                    .setTitle(R.string.delete_inspector)
                    .setPositiveButton(R.string.yes, (dialog, id) -> { //Añadimos los botones
                        final AppDatabase dbD = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD -< PAsamos el contexto para saber donde estamos
                                .allowMainThreadQueries().build();
                        dbD.inspectorDao().delete(inspector);
                        inspectorList(); // Volvemos a la activity InspectorAllActivity despues de borrar

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
                    .setTitle(R.string.modify_inspector)
                    .setPositiveButton(R.string.yes, (dialog, id) -> { //Añadimos los botones
                        final AppDatabase dbD = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD -< PAsamos el contexto para saber donde estamos
                                .allowMainThreadQueries().build();

                        intent.set(new Intent(this, InspectorModifyActivity.class)); //Lo pasamos al activity para pintar el detalle de la tarea
                        intent.get().putExtra("inspector_id", inspector.getInspector_id()); //Recogemos el id
                        this.startActivity(intent.get()); //lanzamos el intent que nos lleva al layout correspondiente

                    })
                    .setNegativeButton(R.string.not, (dialog, id) -> dialog.dismiss()); //Botones del dialogo que salta
            AlertDialog dialog = builder.create();
            dialog.show();//Importante para que se muestre
        }));
    }

    private void fillData(Inspector inspector) {
        TextView tvName = findViewById(R.id.tv_inspector_name_modify);
        TextView tvSurname = findViewById(R.id.tv_inspector_surname_modify);
        TextView tvNumberLicense = findViewById(R.id.tv_inspector_number_license_modify);
        TextView tvDni = findViewById(R.id.tv_inspector_dni_modify);
        TextView tvCompany = findViewById(R.id.tv_inspector_company_modify);

        tvName.setText(inspector.getName());
        tvSurname.setText(inspector.getSurname());
        tvNumberLicense.setText(inspector.getNumberLicense());
        tvDni.setText(inspector.getDni());
        tvCompany.setText(inspector.getCompany());
    }

    /**
     * Para volver al listado de inspectores despues de borrar desde la vista de detall
     */
    private void inspectorList () {
        Intent intent = new Intent(this, InspectorAllActivity.class); //Desde la vista que estamos a la vista que queremos ir
        startActivity(intent); //iniciamos el intent
    }
}