package com.svalero.gac.adapter;

import static com.svalero.gac.db.Constants.DATABASE_NAME;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.svalero.gac.InspectionDetailsActivity;
import com.svalero.gac.InspectorDetailsActivity;
import com.svalero.gac.R;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Brigde;
import com.svalero.gac.domain.Inspection;
import com.svalero.gac.domain.Inspector;

import java.util.List;

/**
 * BrigdeAdapter: Es la clase en la que le explicamos a Android como pintar cada elemento en el RecyclerView
 * Patron Holder: 1) Constructor - 2) onCreateViewHolder - 3) onBindViewHolder - 4) getItemCount - 5) Y la estructura SuperheroHolder
 * al extender de la clase RecyclerView los @Override los añadira automáticamente para el patron Holder, solo añadiremos nosotros el 5)
 *
 */
public class InspectionAdapter extends RecyclerView.Adapter<InspectionAdapter.InspectionHolder> {

    private Context context;
    private List<Inspection> inspectionList;
    private Inspection inspection;
    private Brigde brigde;
    private Inspector inspector;

    /**
     * 1) Constructor que creamos para pasarle los datos que queremos que pinte
     * Contexto y lista de inspecciones
     * @param dataList Lista de inspectores que le pasamos
     */
    public InspectionAdapter(Context context, List<Inspection> dataList) {
        this.context = context;
        this.inspectionList = dataList;

    }

    /**
     * Metodo con el que Android va a inflar, va a crear cada estructura del layout donde irán los datos de cada inspector.
     * Vista detalle de cada inspector
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public InspectionAdapter.InspectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inspection_item, parent, false); // el layout inspector_item para cada inspeccion
        return new InspectionAdapter.InspectionHolder(view); //Creamos un holder para cada una de las estructuras que infla el layout
    }

    /**
     * Metodo que estamos obligados para hacer corresponder los valores de la lista y pintarlo en cada elemento de layout
     * es para poder recorrer en el bucle por cada elemento de la lista y poder pintarlo
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(InspectionAdapter.InspectionHolder holder, int position) {

        /**
         * Me conecto a la BBDD creo el puente y el inspector
         */
        final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Brigde brigde = db.brigdeDao().getById(inspectionList.get(position).bridgeInspId); //creamos el puente por su id
        Inspector inspector = db.inspectorDao().getById(inspectionList.get(position).inspectorCreatorId); //Creamos el inspector por su id

        holder.inspectionBrigdeName.setText(brigde.getName());
        holder.inspectionInspectorName.setText(inspector.getName());
        holder.inspectionComment.setText(inspectionList.get(position).getComment());
        holder.imageInspection.setImageURI(Uri.parse(inspectionList.get(position).getImagePath()));
    }

    /**
     * Metodo que estamos obligados a hacer para que devuelva el número de elementos y android pueda hacer sus calculos y pintar xtodo en base a esos calculos
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return inspectionList.size();
    }

    /**
     * 5) Holder son las estructuras que contienen los datos y los rellenan luego
     * Creamos todos los componentes que tenemos
     */
    public class InspectionHolder extends RecyclerView.ViewHolder {
        public TextView inspectionBrigdeName;
        public TextView inspectionInspectorName;
        public TextView inspectionComment;
        public ImageView imageInspection;
        public Button detailsInspectionButton;
        public Button deleteInspectionButton;

        public View parentView; //vista padre - como el recyclerView

        /**
         * 5) Consturctor del Holder
         *
         * @param view
         */
        public InspectionHolder(View view) {
            super(view); //Vista padre
            parentView = view; //Guardamos el componente padre

            inspectionBrigdeName = view.findViewById(R.id.it_inspection_brigdename);
            inspectionInspectorName = view.findViewById(R.id.it_inspection_inspectorname);
            inspectionComment = view.findViewById(R.id.it_inspectioncomment);
            imageInspection = view.findViewById(R.id.imv_inspection_details);
            deleteInspectionButton = view.findViewById(R.id.it_delete_inspection_button);
            detailsInspectionButton = view.findViewById(R.id.it_details_inspection_button);

            //Para decirle que hace el boton cuando pulsamos sobre el
            // Ver detalles de un inspector
            detailsInspectionButton.setOnClickListener(v -> detailsInspectionButton(getAdapterPosition())); //al pulsar lo llevamos al método detailsInspectionButton
//            // Modificar un inspector
//            modifyBrigdeButton.setOnClickListener(v -> modifyBrigdeButton(getAdapterPosition()));
            // Eliminar un inspector
            deleteInspectionButton.setOnClickListener(v -> deleteInspectionButton(getAdapterPosition()));
        }

        /**
         * Métodos de los botones del layout que se pinta en el recyclerView
         */
        private void detailsInspectionButton(int position) {
            Inspection inspection = inspectionList.get(position); //recuperamos el puente por su posicion

            Intent intent = new Intent(context, InspectionDetailsActivity.class); //Lo pasamos al activity para pintar el detalle de la tarea
            intent.putExtra("inspector_id", inspection.getInspection_id()); //Recogemos el id
            context.startActivity(intent); //lanzamos el intent que nos lleva al layout correspondiente
        }

        private void deleteInspectionButton(int position) {
            /**
             * Dialogo para pregunta antes de si quiere borrar -> https://developer.android.com/guide/topics/ui/dialogs?hl=es-419
             */
            AlertDialog.Builder builder = new AlertDialog.Builder(context); //le pasamos el contexto donde estamos
            builder.setMessage(R.string.do_you_want_to_detele)
                    .setTitle(R.string.delete_inspection)
                    .setPositiveButton(R.string.yes, (dialog, id) -> { //Añadimos los botones
                        final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD -> Pasamos el contexto para saber donde estamos
                                .allowMainThreadQueries().build();
                        Inspection inspection = inspectionList.get(position); //Recuperamos el objeto po su posicion para pasarselo al delete
                        db.inspectionDao().delete(inspection); //Borramos de la BBDD

                        inspectionList.remove(position); //Borra solo de la lista que muestra no de la BBDD
                        notifyItemRemoved(position); // Para notificar a Android que hemos borrado algo y refrescar la lista
                    })
                    .setNegativeButton(R.string.not, (dialog, id) -> dialog.dismiss()); //Botones del dialogo que salta
            AlertDialog dialog = builder.create();
            dialog.show();//Importante para que se muestre
        }
    }
}
