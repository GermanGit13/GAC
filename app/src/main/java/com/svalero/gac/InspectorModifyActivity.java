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
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Inspector;

public class InspectorModifyActivity extends AppCompatActivity {

    private long inspectorId; //Para guardarnos el id del Inspector a modificar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspector_modify);

        Intent intent = new Intent(getIntent());
        inspectorId = getIntent().getLongExtra("inspector_id", 0); //guardamos el id que nos traemos de la vista detalle

        //Instanciamos la BBDD
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Inspector inspector = db.inspectorDao().getById(inspectorId); //creamos el inspector por su id
        fillData(inspector); //rellenamos los datos con el método
    }

    /**
     * Metodo que llama el boton modify_save_inspector_button que tiene definido en onclick modifyButton en el layout activity_inspector_modify.xml
     * @param view
     */
    public void modifyButtonInspector(View view){
        EditText etName = findViewById(R.id.ed_inspector_modify_name); //recogemos los datos de las cajas de texto del layout
        EditText etSurname = findViewById(R.id.ed_inspector_modify_surname);
        EditText etNumberLicense = findViewById(R.id.ed_inspector_modify_license);
        EditText etDni = findViewById(R.id.ed_inspector_modify_dni);
        EditText etCompany = findViewById(R.id.ed_inspector_modify_company);

        String name = etName.getText().toString(); //Pasamos la cajas de texto a un String
        String surname = etSurname.getText().toString();
        String numberLicense = etNumberLicense.getText().toString();
        String dni = etDni.getText().toString();
        String company = etCompany.getText().toString();

        Inspector inspector = new Inspector(inspectorId, name, surname, numberLicense, dni, company); //Creamos un inspector con los datos, le pasamos el id para que sea modificar
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD, la creamos cada vez que necesitemos meter algo en BBDD
                .allowMainThreadQueries().build();

        //Controlamos que la tarea no esta ya creada en su campo primary key, controlando la excepcion
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this); //le pasamos el contexto donde estamos
            builder.setMessage(R.string.do_you_want_to_modify)
                    .setTitle(R.string.modify_inspector)
                    .setPositiveButton(R.string.yes, (dialog, id) -> { //Añadimos los botones
                        final AppDatabase dbD = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD -< PAsamos el contexto para saber donde estamos
                                .allowMainThreadQueries().build();

                        db.inspectorDao().update(inspector); // Modificamos el objeto dentro de la BBDD

                        Intent intent = new Intent(this, InspectorDetailsActivity.class); //Lo devuelvo al details del inspector
                        intent.putExtra("inspector_id", inspector.getInspector_id());
                        this.startActivity(intent); //lanzamos el intent que nos lleva al layout correspondiente
                    })
                    .setNegativeButton(R.string.not, (dialog, id) -> dialog.dismiss()); //Botones del dialogo que salta
            AlertDialog dialog = builder.create();
            dialog.show();//Importante para que se muestre

        } catch (SQLiteConstraintException sce) {
            Snackbar.make(etName, "Ha ocurrido un error. Comprueba que el dato es válido", BaseTransientBottomBar.LENGTH_LONG);
        }
    }

    /**
     * Metodoque llama el boton back_button que tiene definido en onclick goBackButtonModify en el layout activity_inspector_modify.xml
     * @param view
     * onBackPressed(); VOlver atras
     */
    public void goBackButtonModifyInspector(View view) {
        onBackPressed(); //Volver atrás
    }

    private void fillData(Inspector inspector) {
        EditText etName = findViewById(R.id.ed_inspector_modify_name);
        EditText etSurname = findViewById(R.id.ed_inspector_modify_surname);
        EditText etNumberLicense = findViewById(R.id.ed_inspector_modify_license);
        EditText etDni = findViewById(R.id.ed_inspector_modify_dni);
        EditText etCompany = findViewById(R.id.ed_inspector_modify_company);

        etName.setText(inspector.getName());
        etSurname.setText(inspector.getSurname());
        etNumberLicense.setText(inspector.getNumberLicense());
        etDni.setText(inspector.getDni());
        etCompany.setText(inspector.getCompany());

    }

    /**
     * Para crear el menu (el actionBar)
     * @param menu
     * @return
     */
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