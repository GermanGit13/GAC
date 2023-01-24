package com.svalero.gac;

import static com.svalero.gac.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Brigde;
import com.svalero.gac.domain.Inspection;
import com.svalero.gac.domain.Inspector;

public class InspectionModifyActivity extends AppCompatActivity {

    private long inspectionId;
    private long bridgeId;
    private long inspectorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_modify);

        Intent intent = new Intent(getIntent());
        inspectionId = getIntent().getLongExtra("inspection_id", 0); //guardamos el id que nos traemos de la vista detalle

        //Instanciamos la BBDD
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Inspection inspection = db.inspectionDao().getById(inspectionId); //creamos la inspeccion por su id
        bridgeId = inspection.getBridgeInspId();
        inspectorId = inspection.getInspectorCreatorId();

        noticeModify();

        fillData(inspection);
    }

    private void fillData(Inspection inspection) {
        //Instanciamos la BBDD para traernos el nombre del puente por el id que sacamos del objeto inspeccion
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Brigde bridge = db.brigdeDao().getById(bridgeId); //Para obtener el puente asociado
        Inspector inspector = db.inspectorDao().getById(inspectorId); // para obtener el inspector asociado

        TextView tvBridge = findViewById(R.id.tv_modify_brige_id_add_inspection); //Para que no se pueda modificar
        TextView tvInspector = findViewById(R.id.tv_modify_inspection_inspector_id); //Para que no se pueda modificar
        CheckBox cbVain = findViewById(R.id.cb_modify_checkBox_vain);
        CheckBox cbStape = findViewById(R.id.cb_modify_checkBox_stapes);
        TextView tcDamage = findViewById(R.id.et_modify_inspection_damage);
        CheckBox cbPlatform = findViewById(R.id.cb_modify_checkBox_platform);
        CheckBox cbCondition = findViewById(R.id.cb_modify_checkBox_condition);
        TextView tvComment = findViewById(R.id.et_modify_inspection_comment);

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
     * Metodo que llama el boton modify_save_inspector_button que tiene definido en onclick modifyButton en el layout activity_inspector_modify.xml
     * @param view
     */
    public void modifyButtonInspection(View view){
//        TextView tvBridge = findViewById(R.id.tv_modify_brige_id_add_inspection); //Para que no se pueda modificar
//        TextView tvInspector = findViewById(R.id.tv_modify_inspection_inspector_id); //Para que no se pueda modificar
        CheckBox cbVain = findViewById(R.id.cb_modify_checkBox_vain);
        CheckBox cbStape = findViewById(R.id.cb_modify_checkBox_stapes);
        TextView tcDamage = findViewById(R.id.et_modify_inspection_damage);
        CheckBox cbPlatform = findViewById(R.id.cb_modify_checkBox_platform);
        CheckBox cbCondition = findViewById(R.id.cb_modify_checkBox_condition);
        TextView tvComment = findViewById(R.id.et_modify_inspection_comment);

        long bridgeIdMo = bridgeId; //recuperamos el id porque no permitimos borrar
        long inspectorIdMo = inspectorId;
        boolean vain = cbVain.isChecked();
        boolean stape = cbStape.isChecked();
        String damageString = tcDamage.getText().toString();
        int damage = Integer.parseInt(damageString);
        boolean platform = cbPlatform.isChecked();
        boolean condition = cbCondition.isChecked();
        String comment = tvComment.getText().toString();

        Inspection inspection = new Inspection(inspectionId, inspectorIdMo, bridgeIdMo, vain, stape, damage, platform, condition, comment); //Creamos un inspector con los datos, le pasamos el id para que sea modificar
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD, la creamos cada vez que necesitemos meter algo en BBDD
                .allowMainThreadQueries().build();

        //TODO REVISAR PORQUE NO ACTUALIZA
        //Controlamos que la tarea no esta ya creada en su campo primary key, controlando la excepcion
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this); //le pasamos el contexto donde estamos
            builder.setMessage(R.string.do_you_want_to_modify)
                    .setTitle(R.string.modify_inspection)
                    .setPositiveButton(R.string.yes, (dialog, id) -> { //Añadimos los botones
                        final AppDatabase dbD = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD -< PAsamos el contexto para saber donde estamos
                                .allowMainThreadQueries().build();
                        dbD.inspectionDao().update(inspection); // Modificamos el objeto dentro de la BBDD

                        Intent intent = new Intent(this, InspectionAllActivity.class); //Lo devuelvo al details del inspector
                        intent.putExtra("inspection_id", inspection.getInspection_id());
                        this.startActivity(intent); //lanzamos el intent que nos lleva al layout correspondiente
                    })
                    .setNegativeButton(R.string.not, (dialog, id) -> dialog.dismiss()); //Botones del dialogo que salta
            AlertDialog dialog = builder.create();
            dialog.show();//Importante para que se muestre

        } catch (SQLiteConstraintException sce) {
            Snackbar.make(tvComment, "Ha ocurrido un error. Comprueba que el dato es válido", BaseTransientBottomBar.LENGTH_LONG);
        }
    }

    /**
     * Metodoque llama el boton back_button que tiene definido en onclick goBackButtonModify en el layout activity_inspector_modify.xml
     * @param view
     * onBackPressed(); VOlver atras
     */
    public void goBackButtonModifyInspection(View view) {
        onBackPressed(); //Volver atrás
    }




    private void noticeModify() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.modication_notice);
        builder.setMessage(R.string.not_modify_bridge_and_inspector);
        builder.setPositiveButton(R.string.Accept, null);
        AlertDialog dialog = builder.create();
        dialog.show();
//                .setNegativeButton(R.string.not, (dialog, id) -> dialog.dismiss()); //Botones del dialogo que salta
//        Intent intent = new Intent(this, InspectorDetailsActivity.class); //Lo devuelvo al details del inspector
//        this.startActivity(intent); //lanzamos el intent que nos lleva al layout correspondiente
//        AlertDialog dialog = builder.create();
//        dialog.show();//Importante para que se muestre
    }



}