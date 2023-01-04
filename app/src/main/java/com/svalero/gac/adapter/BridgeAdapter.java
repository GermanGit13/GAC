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

import com.svalero.gac.BrigdeDetailsActivity;
import com.svalero.gac.R;
import com.svalero.gac.db.AppDatabase;
import com.svalero.gac.domain.Brigde;

import java.util.List;

/**
 * BrigdeAdapter: Es la clase en la que le explicamos a Android como pintar cada elemento en el RecyclerView
 * Patron Holder: 1) Constructor - 2) onCreateViewHolder - 3) onBindViewHolder - 4) getItemCount - 5) Y la estructura SuperheroHolder
 * al extender de la clase RecyclerView los @Override los añadira automáticamente para el patron Holder, solo añadiremos nosotros el 5)
 *
 */
public class BridgeAdapter extends RecyclerView.Adapter<BridgeAdapter.BrigdeHolder> {

    private Context context; // Es la activity en la que estamos
    private List<Brigde> bridgeList; //Lista de puentes para pintarlo en el RecyclerView
    private Brigde brigde;

    /**
     * 1) Constructor que creamos para pasarle los datos que queremos que pinte
     *
     * @param dataList Lista de puentes que le pasamos
     */
    public BridgeAdapter(Context context, List<Brigde> dataList) {
        this.context = context; //El contexto
        this.bridgeList = dataList; //La lista de los puentes
    }

//    public BridgeAdapter(Context context, Brigde brigde) {
//        this.context = context; //El contexto
//        this.brigde = brigde;
//    }


    /**
     * Metodo con el que Android va a inflar, va a crear cada estructura del layout donde irán los datos de cada puente.
     * Vista detalle de cada puente
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BrigdeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brigde_item, parent, false); // el layout task_item para cada tarea
        return new BrigdeHolder(view); //Creamos un holder para cada una de las estructuras que infla el layout
    }

    /**
     * Metodo que estamos obligados para hacer corresponder los valores de la lista y pintarlo en cada elemento de layout
     * es para poder recorrer en el bucle por cada elemento de la lista y poder pintarlo
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BrigdeHolder holder, int position) {
        holder.brigdeName.setText(bridgeList.get(position).getName());
        holder.brigdeCountry.setText(bridgeList.get(position).getYearBuild());
        holder.brigdeYearBuild.setText(bridgeList.get(position).getYearBuild());
    }

    /**
     * Metodo que estamos obligados a hacer para que devuelva el número de elementos y android pueda hacer sus calculos y pintar xtodo en base a esos calculos
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return bridgeList.size(); //devolvemos el tamaño de la lista
    }

    /**
     * 5) Holder son las estructuras que contienen los datos y los rellenan luego
     * Creamos todos los componentes que tenemos
     */
    public class BrigdeHolder extends RecyclerView.ViewHolder {
        public TextView brigdeName;
        public TextView brigdeCountry;
        public TextView brigdeYearBuild;
        public Button detailsBrigdeButton;
        public Button modifyBrigdeButton;
        public Button deleteBrigdeButton;
        public Button mapBrigdeButton;
        public View parentView; //vista padre - como el recyclerView


        /**
         * 5) Consturctor del Holder
         *
         * @param view
         */
        public BrigdeHolder(View view) {
            super(view); //Vista padre
            parentView = view; //Guardamos el componente padre

            brigdeName = view.findViewById(R.id.brigde_name);
            brigdeCountry = view.findViewById(R.id.brigde_country);
            brigdeYearBuild = view.findViewById(R.id.bridgde_year);
            detailsBrigdeButton = view.findViewById(R.id.details_brigde_button);
//            modifyBrigdeButton = view.findViewById(R.id.modify_brigde_button); //De momento en está vista no voy a modificar
            deleteBrigdeButton = view.findViewById(R.id.delete_brigde_button);
            mapBrigdeButton = view.findViewById(R.id.map_brigde_button);

            //Para decirle que hace el boton cuando pulsamos sobre el
            // Ver detalles de un puente
            detailsBrigdeButton.setOnClickListener(v -> detailsBrigdeButton(getAdapterPosition())); //al pulsar lo llevamos al método detailsBrigdeButton
//            // Modificar un puente
//            modifyBrigdeButton.setOnClickListener(v -> modifyBrigdeButton(getAdapterPosition()));
            // Eliminar un puente
            deleteBrigdeButton.setOnClickListener(v -> deleteBrigdeButton(getAdapterPosition()));
            //Ver en el mapa el puente
            mapBrigdeButton.setOnClickListener(v -> mapBrigdeButton(getAdapterPosition()));
        }

        /**
         * Métodos de los botones del layout que se pinta en el recyclerView
         */
        private void detailsBrigdeButton(int position) {
            Brigde brigde = bridgeList.get(position); //recuperamos el puente por su posicion

            Intent intent = new Intent(context, BrigdeDetailsActivity.class); //Lo pasamos al activity para pintar el detalle de la tarea
            intent.putExtra("brigde_id", brigde.getBrigde_id()); //Recogemos el id
            context.startActivity(intent); //lanzamos el intent que nos lleva al layout correspondiente

        }

        /**
         * De momento modificamos desde la vista general
         * @param position
         */
//        private void modifyBrigdeButton(int position) {
//
//        }

        private void deleteBrigdeButton(int position) {
            /**
             * Dialogo para pregunta antes de si quiere borrar -> https://developer.android.com/guide/topics/ui/dialogs?hl=es-419
             */
            AlertDialog.Builder builder = new AlertDialog.Builder(context); //le pasamos el contexto donde estamos
            builder.setMessage("¿Seguro que quieres eliminar")
                    .setTitle("Eliminar Puente")
                    .setPositiveButton("Si", (dialog, id) -> { //Añadimos los botones
                        final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME) //Instanciamos la BBDD -< PAsamos el contexto para saber donde estamos
                                .allowMainThreadQueries().build();
                        Brigde brigde = bridgeList.get(position); //Recuperamos el objeto po su posicion para pasarselo al delete
                        db.brigdeDao().delete(brigde); //Borramos de la BBDD

                        bridgeList.remove(position); //Borra solo de la lista que muestra no de la BBDD
                        notifyItemRemoved(position); // Para notificar a Android que hemos borrado algo y refrescar la lista
                    })
                    .setNegativeButton("No", (dialog, id) -> dialog.dismiss()); //Botones del dialogo que salta
            AlertDialog dialog = builder.create();
            dialog.show();//Importante para que se muestre
        }

        private void mapBrigdeButton (int position) {

        }
    }
}
