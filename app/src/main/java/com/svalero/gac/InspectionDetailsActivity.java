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
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svalero.gac.adapter.InspectionAdapter;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Brigde;
import com.svalero.gac.domain.Inspection;
import com.svalero.gac.domain.Inspector;

import java.util.concurrent.atomic.AtomicReference;

public class InspectionDetailsActivity extends AppCompatActivity {

    private InspectionAdapter inspectionAdapter;

    FloatingActionButton fabDelete; //Para borrar desde la vista detalle
    FloatingActionButton fabModify; //Para modificar desde la vista detalle
    private long bridgeId;
    private long inspectorId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);

        fabDelete = findViewById(R.id.fab_modify_inspection);
        fabModify = findViewById(R.id.fab_modify_inspection);

        AtomicReference<Intent> intent = new AtomicReference<>(getIntent()); //Porque tenemos dos distintos botones que nos lleva a dos zonas distintas
        //Recuperar la inspeccion por el id
        long inspection_id = getIntent().getLongExtra("inspection_id", 0);

        //Instanciamos la BBDD
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Inspection inspection = db.inspectionDao().getById(inspection_id); //creamos la inspeccion por su id
        bridgeId = inspection.getBridgeInspId();
        inspectorId = inspection.getInspectorCreatorId();

        fillData(inspection); //Rellenamos los campos con el método creado

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
                        dbD.inspectionDao().delete(inspection);
                        inspectionList(); // Volvemos a la activity InspectorAllActivity despues de borrar

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
                    .setTitle(R.string.modify_inspectio)
                    .setPositiveButton(R.string.yes, (dialog, id) -> { //Añadimos los botones
                        final AppDatabase dbD = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD -< PAsamos el contexto para saber donde estamos
                                .allowMainThreadQueries().build();

                        intent.set(new Intent(this, InspectionModifyActivity.class)); //Lo pasamos al activity para pintar el detalle de la tarea
                        intent.get().putExtra("inspection_id", inspection.getInspection_id()); //Recogemos el id
                        this.startActivity(intent.get()); //lanzamos el intent que nos lleva al layout correspondiente

                    })
                    .setNegativeButton(R.string.not, (dialog, id) -> dialog.dismiss()); //Botones del dialogo que salta
            AlertDialog dialog = builder.create();
            dialog.show();//Importante para que se muestre
        }));
    }

    private void fillData(Inspection inspection) {
        //Instanciamos la BBDD para traernos el nombre del puente por el id que sacamos del objeto inspeccion
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Brigde bridge = db.brigdeDao().getById(bridgeId); //Para obtener el puente asociado
        Inspector inspector = db.inspectorDao().getById(inspectorId); // para obtener el inspector asociado

        TextView tvBridge = findViewById(R.id.tv_details_brige_id_add_inspection);
        TextView tvInspector = findViewById(R.id.tv_details_inspection_inspector_id);
        CheckBox cbVain = findViewById(R.id.cb_details_checkBox_vain);
        CheckBox cbStape = findViewById(R.id.cb_details_checkBox_stapes);
        TextView tcDamage = findViewById(R.id.tv_details_inspection_damage);
        CheckBox cbPlatform = findViewById(R.id.cb_details_checkBox_platform);
        CheckBox cbCondition = findViewById(R.id.cb_details_checkBox_condition);
        TextView tvComment = findViewById(R.id.tv_details_inspection_comment);

        tvBridge.setText(bridge.getName());
        tvInspector.setText(inspector.getName());
        cbVain.setChecked(inspection.isVain());
        cbStape.setChecked(inspection.isStapes());
        tcDamage.setText(String.valueOf(inspection.getDamage()));
        cbPlatform.setChecked(inspection.isPlatformIns());
        cbCondition.setChecked(inspection.isCondition());
        tvComment.setText(inspection.getComment());
    }

    /**
     * Para volver al listado de inspectores despues de borrar desde la vista de detall
     */
    private void inspectionList () {
        Intent intent = new Intent(this, InspectorAllActivity.class); //Desde la vista que estamos a la vista que queremos ir
        startActivity(intent); //iniciamos el intent
    }

    /**
     * PAra crear el menu (el actionBar)
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_inspector_all, menu); //Inflamos el menu
        return true;
    }

    /**
     * Para cuando elegimos una opcion del menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.register_inspector) {
            Intent intent    = new Intent(this, InspectorRegisterActivity.class);
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