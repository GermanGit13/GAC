package com.svalero.gac;

import static com.svalero.gac.db.Constants.DATABASE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.IOException;

public class InspectionRegisterActivity extends AppCompatActivity {

    private long inspectionId; //Para guardarnos el id de Inspection
    private long brigdeId; //Para guardarnos el id de Brigde a asociar a la inspeccion
    private long inspectorId; //Para guardarnos el id de Inspector a asociar a la inspeccion
    private Brigde brigde; //para tener el puente a mano
    private BridgeAdapter adapterBrigde; //Para poder conectar con la BBDD
    Button btnCamera; //Boton que al pulsar abrira la camara en el layout de registrar inspeccion
    ImageView imageView; //Para que aparezca la foto aquí
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String pathImage; //Para almacenar la ruta de la imagen

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

        noticeId();

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

        Inspection inspection = new Inspection(brigdeId, inspectorId, vain, stapes, damage, platform, condition, comment, pathImage); //Creamos una inspeccion con los datos
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD, la creamos cada vez que necesitemos meter algo en BBDD
                .allowMainThreadQueries().build();
        //Controlamos que la tarea no esta ya creada en su campo primary key, controlando la excepcion

//        try {
            db.inspectionDao().insert(inspection); // Insertamos el objeto dentro de la BBDD

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.inspection_created);
            builder.setMessage(getString(R.string.assigned_to_brigde) + brigde.getName() + getString(R.string.more_register_inspection));
            builder.setPositiveButton(R.string.ok, null);
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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {

            File imageFile = null; //Creamos un fichero y lo declaramos a null

            try {
                imageFile = saveImage(); //Le pasamos el metodo de salvar fichero al fichero creado antes
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            if (imageFile !=null) { //Para verificar que creo el archivo temporal
                Uri photoUri = FileProvider.getUriForFile(this, "com.svalero.gac.fileprovider", imageFile); //El contexto y el package de la app y el fichero de la imagen
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE); //
            }
        }
    }

    /**
     * Recuperar la foto creada, recupera el resultado de la actividad que realiza el método de la cámara
     * @param requestCode : para indicarle cual es el que tiene que recoger porque podriamos tener más ActiviyResult
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            Bitmap imageBitmap = BitmapFactory.decodeFile(pathImage); //Le indicamos que archivo hay que decodificar
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

    /**
     * Método para guardar las fotos creadas
     * @return
     */
    private File saveImage() throws IOException {
        String nameImage = getString(R.string.photo_) ; //Para asignarle un nombre a la foto
        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES); //donde vamos a guardar las imagenes
        File imageTemp = File.createTempFile(nameImage, getString(R.string.jpeg), directory); //Para salvar el archivo temporal, nombre de la foto, extension y directorio donde ira

        pathImage = imageTemp.getAbsolutePath(); //guardamos la ruta absoluta de la imagen
        return imageTemp;
    }

    private void noticeId() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.register_inspection_with_id);
            builder.setMessage(R.string.shearch_id_inspector);
            builder.setPositiveButton(R.string.Accept, null);
            AlertDialog dialog = builder.create();
            dialog.show();
//            .setNegativeButton(R.string.not, (dialog, id) -> dialog.dismiss()); //Botones del dialogo que salta
//            Intent intent = new Intent(this, InspectorAllActivity.class); //Lo devuelvo al details del inspector
//            this.startActivity(intent); //lanzamos el intent que nos lleva al layout correspondiente
//            AlertDialog dialog = builder.create();
//            dialog.show();//Importante para que se muestre
    }
}
