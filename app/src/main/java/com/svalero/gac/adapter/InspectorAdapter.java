package com.svalero.gac.adapter;

import static com.svalero.gac.db.Constants.DATABASE_NAME;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.svalero.gac.InspectorDetailsActivity;
import com.svalero.gac.R;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Inspector;

import java.util.List;

/**
 * BrigdeAdapter: Es la clase en la que le explicamos a Android como pintar cada elemento en el RecyclerView
 * Patron Holder: 1) Constructor - 2) onCreateViewHolder - 3) onBindViewHolder - 4) getItemCount - 5) Y la estructura SuperheroHolder
 * al extender de la clase RecyclerView los @Override los añadira automáticamente para el patron Holder, solo añadiremos nosotros el 5)
 *
 */
public class InspectorAdapter extends RecyclerView.Adapter<InspectorAdapter.InspectorHolder> {

    private Context context; // Es la activity en la que estamos
    private List<Inspector> inspectorList; //Lista de inspectores para pintarlo en el RecyclerView
    private Inspector inspector; // Objeto inspector

    /**
     * 1) Constructor que creamos para pasarle los datos que queremos que pinte
     *
     * @param dataList Lista de inspectores que le pasamos
     */
    public InspectorAdapter(Context context, List<Inspector> dataList) {
        this.context = context; //El contexto
        this.inspectorList = dataList; //La lista de los puentes
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
    public InspectorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inspector_item, parent, false); // el layout inspector_item para cada inspector
        return new InspectorHolder(view); //Creamos un holder para cada una de las estructuras que infla el layout
    }

    /**
     * Metodo que estamos obligados para hacer corresponder los valores de la lista y pintarlo en cada elemento de layout
     * es para poder recorrer en el bucle por cada elemento de la lista y poder pintarlo
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(InspectorAdapter.InspectorHolder holder, int position) {
        holder.inspectorId.setText(String.valueOf(inspectorList.get(position).getInspector_id()));
        holder.inspectorName.setText(inspectorList.get(position).getName());
        holder.inspectorSurname.setText(inspectorList.get(position).getSurname());
        holder.inspectorLicense.setText(inspectorList.get(position).getNumberLicense());
    }

    /**
     * Metodo que estamos obligados a hacer para que devuelva el número de elementos y android pueda hacer sus calculos y pintar xtodo en base a esos calculos
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return inspectorList.size();
    }

    /**
     * 5) Holder son las estructuras que contienen los datos y los rellenan luego
     * Creamos todos los componentes que tenemos
     */
    public class InspectorHolder extends RecyclerView.ViewHolder {
        public TextView inspectorName;
        public TextView inspectorSurname;
        public TextView inspectorLicense;
        public TextView inspectorId;
        public Button detailsInspectorButton;
        public Button deleteInspectorButton;

        public View parentView; //vista padre - como el recyclerView

        /**
         * 5) Consturctor del Holder
         *
         * @param view
         */
        public InspectorHolder(View view) {
            super(view); //Vista padre
            parentView = view; //Guardamos el componente padre

            inspectorId = view.findViewById(R.id.it_inspector_id);
            inspectorName = view.findViewById(R.id.it_inspector_name);
            inspectorSurname = view.findViewById(R.id.it_inspector_surname);
            inspectorLicense = view.findViewById(R.id.it_inspector_license);
            deleteInspectorButton = view.findViewById(R.id.it_delete_inspector_button);
            detailsInspectorButton = view.findViewById(R.id.it_details_inspector_button);

            //Para decirle que hace el boton cuando pulsamos sobre el
            // Ver detalles de un inspector
            detailsInspectorButton.setOnClickListener(v -> detailsInspectorButton(getAdapterPosition())); //al pulsar lo llevamos al método detailsBrigdeButton
//            // Modificar un inspector
//            modifyBrigdeButton.setOnClickListener(v -> modifyBrigdeButton(getAdapterPosition()));
            // Eliminar un inspector
            deleteInspectorButton.setOnClickListener(v -> deleteInspectorButton(getAdapterPosition()));
        }

        /**
         * Métodos de los botones del layout que se pinta en el recyclerView
         */
        private void detailsInspectorButton(int position) {
            Inspector inspector = inspectorList.get(position); //recuperamos el puente por su posicion

            Intent intent = new Intent(context, InspectorDetailsActivity.class); //Lo pasamos al activity para pintar el detalle de la tarea
            intent.putExtra("inspector_id", inspector.getInspector_id()); //Recogemos el id
            context.startActivity(intent); //lanzamos el intent que nos lleva al layout correspondiente

        }

        private void deleteInspectorButton(int position) {
            /**
             * Dialogo para pregunta antes de si quiere borrar -> https://developer.android.com/guide/topics/ui/dialogs?hl=es-419
             */
            AlertDialog.Builder builder = new AlertDialog.Builder(context); //le pasamos el contexto donde estamos
            builder.setMessage(R.string.do_you_want_to_detele)
                    .setTitle(R.string.delete_inspector)
                    .setPositiveButton(R.string.yes, (dialog, id) -> { //Añadimos los botones
                        final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD -< PAsamos el contexto para saber donde estamos
                                .allowMainThreadQueries().build();
                        Inspector inspector = inspectorList.get(position); //Recuperamos el objeto po su posicion para pasarselo al delete
                        db.inspectorDao().delete(inspector); //Borramos de la BBDD

                        inspectorList.remove(position); //Borra solo de la lista que muestra no de la BBDD
                        notifyItemRemoved(position); // Para notificar a Android que hemos borrado algo y refrescar la lista
                    })
                    .setNegativeButton(R.string.not, (dialog, id) -> dialog.dismiss()); //Botones del dialogo que salta
            AlertDialog dialog = builder.create();
            dialog.show();//Importante para que se muestre
        }
    }
}