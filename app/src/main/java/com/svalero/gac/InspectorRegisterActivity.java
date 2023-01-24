package com.svalero.gac;

import static com.svalero.gac.db.Constants.DATABASE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Brigde;
import com.svalero.gac.domain.Inspector;

public class InspectorRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspector_register);
    }

    /**
     * Metodo que llama el boton add_inspector_button que tiene definido en onclick saveButtonInspector en el layout activity_inspector_register.xml
     * @param view
     */
    public void saveButtonInspector(View view){
        EditText etName = findViewById(R.id.et_inspector_name); //recogemos los datos de las cajas de texto del layout
        EditText etSurname = findViewById(R.id.et_inspector_surname);
        EditText etNumberLicense = findViewById(R.id.et_inspector_number_license);
        EditText etDni = findViewById(R.id.et_inspector_dni);
        EditText etCompany = findViewById(R.id.et_inspector_company);

        String name = etName.getText().toString(); //Pasamos la cajas de texto a un String
        String surname = etSurname.getText().toString();
        String numberLicense = etNumberLicense.getText().toString();
        String dni = etDni.getText().toString();
        String company = etCompany.getText().toString();

        Inspector inspector = new Inspector(name, surname, numberLicense,dni, company); //Creamos un inspector con los datos
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD, la creamos cada vez que necesitemos meter algo en BBDD
                .allowMainThreadQueries().build();
        //Controlamos que la tarea no esta ya creada en su campo primary key, controlando la excepcion

        try {
            db.inspectorDao().insert(inspector); // Insertamos el objeto dentro de la BBDD

            Snackbar.make(etName, R.string.inspector_create_ok, BaseTransientBottomBar.LENGTH_LONG); //etName porque el Snackbar hay que asociarlo algún componente del layout
            etName.setText(""); //Para vaciar las cajas de texto y prepararlas para registrar otra tarea
            etSurname.setText("");
            etNumberLicense.setText("");
            etDni.setText("");
            etCompany.setText("");
            etName.requestFocus(); //recuperamos el foco
        } catch (SQLiteConstraintException sce) {
            Snackbar.make(etName, R.string.inspector_error, BaseTransientBottomBar.LENGTH_LONG);
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