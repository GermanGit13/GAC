package com.svalero.gac;

import static com.svalero.gac.db.Constants.DATABASE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Inspection;

public class InspectionRegisterActivity extends AppCompatActivity {

    private long inspectionId; //Para guardarnos el id de Inspection
    private long brigdeId; //Para guardarnos el id de Brigde a asociar a la inspeccion
    private long inspectorId; //Para guardarnos el id de Inspector a asociar a la inspeccion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_register);
    }

    /**
     * Metodo que llama el boton add_inspector_button que tiene definido en onclick saveButtonInspector en el layout activity_inspector_register.xml
     * @param view
     */
    public void saveButtonInspection(View view){
        //recogemos los datos de las cajas de texto del layout
        EditText etBrigdeId = findViewById(R.id.et_inspection_brigde_id); //Clave Primaria de Brigde
        EditText etInspectionId = findViewById(R.id.et_inspection_inspector_id); //Clave Primaria de Inspector
        CheckBox cbVain = (CheckBox) findViewById(R.id.cb_checkBox_vain);
        CheckBox cbStape = (CheckBox) findViewById(R.id.cb_checkBox_stapes);
        EditText etDamage = findViewById(R.id.et_inspection_damage);
        CheckBox cbPlatform = (CheckBox) findViewById(R.id.cb_checkBox_platform);
        CheckBox cbCondition = (CheckBox) findViewById(R.id.cb_checkBox_condition);
        EditText etComment = findViewById(R.id.et_inspection_comment);

        long brigdeId = convertToLong(etBrigdeId.getText().toString());
        long inspectorId = convertToLong(etInspectionId.getText().toString());
        boolean vain = cbVain.isChecked();
        boolean stapes = cbStape.isChecked();
        String damageString = etDamage.getText().toString();
        int damage = Integer.valueOf(damageString);
        //Para informar de como se valoran los daños
        if (damage != 0 || damage != 25 || damage != 50 || damage != 75 || damage != 100) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Valor no válido");
            builder.setMessage("Introduzca uno de los siguientes valores");
            builder.setMessage("Daños de: 0 , 25 , 50,  75 ó 100");
            builder.setPositiveButton("Aceptar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        boolean platform = cbPlatform.isChecked();
        boolean condition = cbCondition.isChecked();
        String comment = etComment.getText().toString();

        Inspection inspection = new Inspection(brigdeId, inspectorId, vain, stapes, damage, platform, condition, comment); //Creamos una inspeccion con los datos
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD, la creamos cada vez que necesitemos meter algo en BBDD
                .allowMainThreadQueries().build();
        //Controlamos que la tarea no esta ya creada en su campo primary key, controlando la excepcion

        try {
            db.inspectionDao().insert(inspection); // Insertamos el objeto dentro de la BBDD

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Inspeccion Creada con exito");
            builder.setPositiveButton("Aceptar", null);
            AlertDialog dialog = builder.create();
            dialog.show();

//            Snackbar.make(etComment, "Inspección creada con exito", BaseTransientBottomBar.LENGTH_LONG); //etComment porque el Snackbar hay que asociarlo algún componente del layout
            etBrigdeId.setText(""); //Para vaciar las cajas de texto y prepararlas para registrar otra tarea
            etInspectionId.setText("");
            cbVain.setChecked(false);
            cbStape.setChecked(false);
            etDamage.setText("");
            cbPlatform.setChecked(false);
            cbCondition.setChecked(false);
            etComment.setText("");
            etBrigdeId.requestFocus(); //recuperamos el foco
        } catch (SQLiteConstraintException sce) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ha ocurrido un error. Comprueba que el dato es válido");
            builder.setPositiveButton("Aceptar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
//            Toast.makeText(this, "Ha ocurrido un error. Comprueba que el dato es válido", Toast.LENGTH_LONG).show();
//            Snackbar.make(etComment, "Ha ocurrido un error. Comprueba que el dato es válido", BaseTransientBottomBar.LENGTH_LONG);
        }
    }

    /**
     * Metodoque llama el boton back_button que tiene definido en onclick goBackButton en el layout activity_register_inspector.xml
     * @param view
     * onBackPressed(); VOlver atras
     */
    public void goBackButton(View view) {
        onBackPressed(); //Volver atrás
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
        }

        return false;
    }

    /**
     * Para poder pasar el id que recibo como string de la TextView a Long
     * @param string
     * @return
     */
    public static long convertToLong(String string) {
        long id;
        try {
            id = Long.parseLong(string);
        } catch (NumberFormatException | NullPointerException nfe) {
            return 0; //Valor default en caso de no poder convertir  a Long
        }
        return id;
    }
}
