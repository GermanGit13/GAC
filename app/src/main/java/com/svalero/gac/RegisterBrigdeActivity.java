package com.svalero.gac;

import static com.svalero.gac.db.Constants.DATABASE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
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

        brigdeMap = findViewById(R.id.brigdeMap); //le pasamos el mapa creado en el layout activity_register_brigde y lo metemos en el constrait de abajo

        GesturesPlugin gesturesPlugin = GesturesUtils.getGestures(brigdeMap);
        gesturesPlugin.addOnMapClickListener(point -> { //Cuando hacemos click en el mapa devolvemos un point
            removeAllMarkers(); //Método creado para borrar los anteriores antes de seleccionar alguna para no tener problemas con los point
            this.point = point; //Ese point lo guardamos para tener la longuitud y latitude
            addMarker(point);
            return true;
        });

        initializePointManager();// Para que se cree nada más arrancar
    }

    /**
     * Metodo que llama el boton add_brigde_button que tiene definido en onclick saveButtonBrigde en el layout activity_register_brigde.xml
     * @param view
     */
    public void saveButton(View view){
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
        String numberVain = etNumberVain.getText().toString();
        String numberStapes = etNumberStapes.getText().toString();
        String platform = etPlatform.getText().toString();

        //If por si acaso el point no está creado, el usuario no ha selecionado nada en el mapa, asi no da error al crear la tarea porque falte latitude y longuitude
        if (point == null) {
              Toast.makeText(this, R.string.select_location_message, Toast.LENGTH_LONG).show();
//            Snackbar.make(etName, R.string.select_location_message, BaseTransientBottomBar.LENGTH_LONG); //etName porque el Snackbar hay que asociarlo algún componente del layout
            return;
        }

        Brigde brigde = new Brigde(name, country, city, yearBuild, point.latitude(), point.longitude(), numberVain, numberStapes, platform); //Creamos un puente con los datos, recogemos de point los datos con el clik del usuario sobre el map
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD, la creamos cada vez que necesitemos meter algo en BBDD
                .allowMainThreadQueries().build();
        //Controlamos que la tarea no esta ya creada en su campo primary key, controlando la excepcion

        try {
            db.brigdeDao().insert(brigde); // Insertamos el objeto dentro de la BBDD

            Snackbar.make(etName, R.string.register_brigde, BaseTransientBottomBar.LENGTH_LONG); //etName porque el Snackbar hay que asociarlo algún componente del layout
            etName.setText(""); //Para vaciar las cajas de texto y prepararlas para registrar otra tarea
            etCountry.setText("");
            etCity.setText("");
            etYearBuild.setText("");
            etNumberVain.setText("");
            etNumberStapes.setText("");
            etPlatform.setText("");
            etName.requestFocus(); //recuperamos el foco
        } catch (SQLiteConstraintException sce) {
            Snackbar.make(etName, R.string.brigde_error, BaseTransientBottomBar.LENGTH_LONG);
        }

    }

    /**
     * Para inicializar el Pointmanager y asi la podemos dejar inicializada nada más arracar en onCreate
     */
    private void initializePointManager() {
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(brigdeMap);
        AnnotationConfig annotationConfig = new AnnotationConfig();
        pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, annotationConfig);
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
    private void addMarker(Point point) {
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(point)
                .withIconImage(BitmapFactory.decodeResource(getResources(), R.mipmap.red_marker)); //le pasamos el dibujo que queremos que pinte como icono, los podemos crea webinar 4 min 54
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    /**
     * Para borrar el marker anterior y no aparezcan todos en el mapa
     */
    private void removeAllMarkers() {
        pointAnnotationManager.deleteAll(); // Se Podria borra uno en concreto pasandole el point exacto
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