package com.svalero.gac;

import static com.svalero.gac.db.Constants.DATABASE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Brigde;

import java.util.List;

public class MapsActivity extends AppCompatActivity {

    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;
    private FloatingActionButton btLotacion;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapView = findViewById(R.id.mapView); //cargamos el mapa
        initializePointManager(); // inicializamos el pointmanager
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        /**
         * No traemos todas los puentes que hay para pintar todas en un mapa
         */
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        List<Brigde> brigdes = db.brigdeDao().getAll(); //Lista de la tareas
        addBrigdeToMap(brigdes); // se lo pasamos al metodo
    }

    /**
     * Metodo para sacar una lista de puuentes con un for para crear un point por cada puente con la longitude y latitude mas el nombre del puente
     * @param
     */
    private void addBrigdeToMap(List<Brigde> brigdes) {
        for (Brigde brigde : brigdes) {
            Point point = Point.fromLngLat(brigde.getLongitude(), brigde.getLatitude());
            addMarker(point, brigde.getName()); //le pasamos el metodo que crea el marker y ponemos el point y nombre del puente
        }

        Brigde lastBrigde = brigdes.get(brigdes.size() - 1); // recogemos el ultimo puente
        setCameraPosition(Point.fromLngLat(lastBrigde.getLongitude(), lastBrigde.getLatitude())); //Fijamos la camara del mapa en el ultimo puente
    }

    /**
     * Inicializamos el pointmanager
     */
    private void initializePointManager() {
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
        AnnotationConfig annotationConfig = new AnnotationConfig();
        pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, annotationConfig);
    }

    /**
     * Para poder crear un marker y que lo pinte por cada puente
     * @param point Pasamos el point
     * @param title el nombre del puente
     */
    private void addMarker(Point point, String title) {
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(point)
                .withTextField(title) //asi aparece el nombre en el mapa
                .withIconImage(BitmapFactory.decodeResource(getResources(), R.mipmap.red_marker));
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    /**
     * Fija la camara del mapa donde nosotros queramos, asi el mapa arranca desde ese punto
     * @param point
     */
    private void setCameraPosition(Point point) {
        CameraOptions cameraPosition = new CameraOptions.Builder()
                .center(point)
                .pitch(0.0)
                .zoom(13.5)
                .bearing(-17.6)
                .build();
        mapView.getMapboxMap().setCamera(cameraPosition);
    }

    /**
     * Para ubicar al usuario en el mapa
     */
//    private void locationUser() {
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        btLotacion =(FloatingActionButton) findViewById(R.id.btLocation);
//        btLotacion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mapView !=null) {
//                    Location lastLocation = fusedLocationClient.getLastLocation();
//
//                }
//            }
//        });
//    }

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