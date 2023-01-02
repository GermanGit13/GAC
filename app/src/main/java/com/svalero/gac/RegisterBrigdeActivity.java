package com.svalero.gac;

import static com.svalero.gac.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Brigde;

public class RegisterBrigdeActivity extends AppCompatActivity {

    private MapView brigdeMap; //Porque en el layout de registrar Brigde tenemos un mapa
    private Point point; //Guardamos el point para gestionar la latitu y longuitud
    private PointAnnotationManager pointAnnotationManager; //Para anotar el point así es común para todos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_brigde);

//        brigdeMap = findViewById(R.id.brigdeMap); //le pasamos el mapa creado en el layout activity_register_brigde y lo metemos en el constrait de abajo
//
//        GesturesPlugin gesturesPlugin = GesturesUtils.getGestures(brigdeMap);
//        gesturesPlugin.addOnMapClickListener(point -> {
//            removeAllMarkers(); //Método creado para borrar los anteriores antes de seleccionar alguna para no tener problemas con los point
//            this.point = point; //Ese point lo guardamos para tener la longuitud y latitude
//
//        } );
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
        EditText etNumberVain = findViewById(R.id.edit_text_numbervain);
        EditText etNumberStapes = findViewById(R.id.edit_text_numberstapes);
        EditText etPlatform = findViewById(R.id.edit_text_platform);

        String name = etName.getText().toString(); //Pasamos la cajas de texto a un String
        String country = etCountry.getText().toString();
        String city = etCity.getText().toString();
        String yearBuild = etYearBuild.getText().toString();
        int numberVain = Integer.parseInt(etNumberVain.getText().toString());
        int numberStapes = Integer.parseInt(etNumberStapes.getText().toString());
        String platform = etPlatform.getText().toString();

        Brigde brigde = new Brigde(name, country, city, yearBuild, /*latitude, longitude,*/ numberVain, numberStapes, platform); //Creamos un puente con los datos, recogemos de point los datos con el clik del usuario sobre el map
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD, la creamos cada vez que necesitemos meter algo en BBDD
                .allowMainThreadQueries().build();
        //Controlamos que la tarea no esta ya creada en su campo primary key, controlando la excepcion

        try {
            db.brigdeDao().insert(brigde); // Insertamos el objeto dentro de la BBDD

            Snackbar.make(etName, "Puente Registrado", BaseTransientBottomBar.LENGTH_LONG); //etName porque el Snackbar hay que asociarlo algún componente del layout
            etName.setText(""); //Para vaciar las cajas de texto y prepararlas para registrar otra tarea
            etCountry.setText("");
            etCity.setText("");
            etYearBuild.setText("");
//            etLatitude.setText("");
//            etLongitude.setText("");
            etNumberVain.setText("");
            etNumberStapes.setText("");
            etPlatform.setText("");
            etName.requestFocus(); //recuperamos el foco
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

    /**
     * Método para añadir un Marker sobre un mapa
     * @param point le pasamos el point con los datos de latitude y longuitude
     * @param "String" le podemos pasar un titulo para que aparezca en el mapa min 54 webinar 4 de hay se puede sacar
     */
//    private void addMarker(Point point) {
//        PointAnnotationManager pointAnnotationManager = new PointAnnotationManager()
//                .withPoint(point)
//                .wi
//    }

    /**
     * Para borrar el marker anterior y no aparezcan todos en el mapa
     */
    private void removeAllMarkers() {
        pointAnnotationManager.deleteAll(); // Se Podria borra uno en concreto pasandole el point exacto
    }
}