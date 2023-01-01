package com.svalero.gac;

import static com.svalero.gac.db.Constants.DATABASE_BRIGDES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Brigde;

public class RegisterBrigdeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_brigde);
    }

    /**
     * Metodo que llama el boton add_brigde_button que tiene definido en onclick saveButtonBrigde en el layout activity_register_brigde.xml
     * @param view
     */
    public void saveButtonBrigde(View view){
        EditText etName = findViewById(R.id.edit_text_name); //recogemos los datos de las cajas de texto del layout
        EditText etCountry = findViewById(R.id.edit_text_country);
        EditText etCity = findViewById(R.id.edit_text_city);
        EditText etYearBuild = findViewById(R.id.edit_text_yearbuild);
        EditText etLatitude = findViewById(R.id.edit_text_latitude);
        EditText etLongitude = findViewById(R.id.edit_text_longuitud);
        EditText etNumberVain = findViewById(R.id.edit_text_numbervain);
        EditText etNumberStapes = findViewById(R.id.edit_text_numberstapes);
        EditText etPlatform = findViewById(R.id.edit_text_platform);

        String name = etName.getText().toString(); //Pasamos la cajas de texto a un String
        String country = etCountry.getText().toString();
        String city = etCity.getText().toString();
        String yearBuild = etYearBuild.getText().toString();
        double latitude = Integer.parseInt(etLatitude.toString());
        double longitude = Integer.parseInt(etLongitude.toString());
        int numberVain = Integer.parseInt(etNumberVain.toString());
        int numberStapes = Integer.parseInt(etNumberStapes.toString());
        String platform = etPlatform.getText().toString();

        Brigde brigde = new Brigde(name, country, city, yearBuild, latitude, longitude, numberVain, numberStapes, platform); //Creamos un puente con los datos, recogemos de point los datos con el clik del usuario sobre el map
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_BRIGDES) //Instanciamos la BBDD, la creamos cada vez que necesitemos meter algo en BBDD
                .allowMainThreadQueries().build();
        //Controlamos que la tarea no esta ya creada en su campo primary key, controlando la excepcion
        try {
            db.brigdeDao().insert(brigde); // Insertamos el objeto dentro de la BBDD

            Snackbar.make(etName, "Puente Registrado", BaseTransientBottomBar.LENGTH_LONG); //etName porque el Snackbar hay que asociarlo algún componente del layout

        } catch (SQLiteConstraintException sce) {
            Snackbar.make(etName, "Ha ocurrido un error. Comprueba que el dato es válido", BaseTransientBottomBar.LENGTH_LONG);
        }

    }

    /**
     * Metodoque llama el boton back_button que tiene definido en onclick goBackButton en el layout activity_register_brigde.xml
     * @param view
     * onBackPressed(); VOlver atras
     */
    public void goBackButton(View view) {
        onBackPressed(); //Volver atrás
    }
}