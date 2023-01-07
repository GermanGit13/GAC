package com.svalero.gac;

import static com.svalero.gac.db.Constants.DATABASE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.svalero.gac.adapter.BridgeAdapter;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Brigde;
import com.svalero.gac.domain.Inspection;

public class InspectionRegisterActivity extends AppCompatActivity {

    private long inspectionId; //Para guardarnos el id de Inspection
    private long brigdeId; //Para guardarnos el id de Brigde a asociar a la inspeccion
    private long inspectorId; //Para guardarnos el id de Inspector a asociar a la inspeccion
    private Brigde brigde; //para tener el puente a mano
    private BridgeAdapter adapterBrigde; //Para poder conectar con la BBDD
    Button btnCamera; //Boton que al pulsar abrira la camara en el layout de registrar inspeccion
    ImageView imageView; //Para que aparezca la foto aquí
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_register);

        Intent intent = new Intent(getIntent());
        brigdeId = getIntent().getLongExtra("brigde_id", 0); //guardamos el id que nos traemos de la vista detalle

        //Instanciamos la BBDD
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        brigde = db.brigdeDao().getById(brigdeId); //creamos el puente por su id

        TextView tvName = findViewById(R.id.tv_brige_id_add_inspection);
        tvName.setText(brigde.getName());

        btnCamera = findViewById(R.id.camera_inspetion_button); //Asignamos los elementos al elemento del layout
        imageView = findViewById(R.id.imv_inspection_add); //Asignamos el elemenot dodn ira la imagen

        //Implemento el Onclik para que escuche al presionar el boton de la foto
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera();
            }
        });

    }

    /**
     * Metodo que llama el boton add_inspector_button que tiene definido en onclick saveButtonInspector en el layout activity_inspector_register.xml
     * @param view
     */
    public void saveButtonInspection(View view){

        //recogemos los datos de las cajas de texto del layout

        EditText etInspectionId = findViewById(R.id.et_inspection_inspector_id); //Clave Primaria de Inspector
        CheckBox cbVain = (CheckBox) findViewById(R.id.cb_checkBox_vain);
        CheckBox cbStape = (CheckBox) findViewById(R.id.cb_checkBox_stapes);
        EditText etDamage = findViewById(R.id.et_inspection_damage);
        CheckBox cbPlatform = (CheckBox) findViewById(R.id.cb_checkBox_platform);
        CheckBox cbCondition = (CheckBox) findViewById(R.id.cb_checkBox_condition);
        EditText etComment = findViewById(R.id.et_inspection_comment);

        String inspectorIdString = etInspectionId.getText().toString();
        long inspectorId = Long.parseLong(inspectorIdString);
        boolean vain = cbVain.isChecked();
        boolean stapes = cbStape.isChecked();
        String damageString = etDamage.getText().toString();
        int damage = Integer.parseInt(damageString);
        //Para informar de como se valoran los daños
//        if (damage != 0 || damage != 25 || damage != 50 || damage != 75 || damage != 100) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Valor no válido");
//            builder.setMessage("Introduzca uno de los siguientes valores");
//            builder.setMessage("Daños de: 0 , 25 , 50,  75 ó 100");
//            builder.setPositiveButton("Aceptar", null);
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        }
        boolean platform = cbPlatform.isChecked();
        boolean condition = cbCondition.isChecked();
        String comment = etComment.getText().toString();

        Inspection inspection = new Inspection(brigdeId, inspectorId, vain, stapes, damage, platform, condition, comment); //Creamos una inspeccion con los datos
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD, la creamos cada vez que necesitemos meter algo en BBDD
                .allowMainThreadQueries().build();
        //Controlamos que la tarea no esta ya creada en su campo primary key, controlando la excepcion

//        try {
            db.inspectionDao().insert(inspection); // Insertamos el objeto dentro de la BBDD

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Inspeccion Creada con exito");
            builder.setMessage("Asignada al puente " + brigde.getName() + ", podrás registrar más inspecciones sobre este puente hasta que pulsar cancelar");
            builder.setPositiveButton("Aceptar", null);
            AlertDialog dialog = builder.create();
            dialog.show();

//            Snackbar.make(etComment, "Inspección creada con exito", BaseTransientBottomBar.LENGTH_LONG); //etComment porque el Snackbar hay que asociarlo algún componente del layout
            etInspectionId.setText("");
            cbVain.setChecked(false);
            cbStape.setChecked(false);
            etDamage.setText("");
            cbPlatform.setChecked(false);
            cbCondition.setChecked(false);
            etComment.setText("");
            etInspectionId.requestFocus(); //recuperamos el foco
//        } catch (SQLiteConstraintException sce) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Ha ocurrido un error. Comprueba que el dato es válido");
//            builder.setPositiveButton("Aceptar", null);
//            AlertDialog dialog = builder.create();
//            dialog.show();
////            Toast.makeText(this, "Ha ocurrido un error. Comprueba que el dato es válido", Toast.LENGTH_LONG).show();
////            Snackbar.make(etComment, "Ha ocurrido un error. Comprueba que el dato es válido", BaseTransientBottomBar.LENGTH_LONG);
//        }
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
     * Metodo para abrir la camara mediante un intent
     */
    private void camera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * Recuperar la foto creada
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
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

}
