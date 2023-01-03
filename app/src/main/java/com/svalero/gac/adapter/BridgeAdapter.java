package com.svalero.gac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    /**
     * 1) Constructor que creamos para pasarle los datos que queremos que pinte
     * @param dataList Lista de puentes que le pasamos
     */
    public BridgeAdapter(Context context, List<Brigde> dataList) {
        this.context = context; //El contexto
        this.bridgeList = dataList; //La lista de los puentes
    }

    /**
     * Metodo con el que Android va a inflar, va a crear cada estructura del layout donde irán los datos de cada puente.
     * Vista detalle de cada puente
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BrigdeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    /**
     * Metodo que estamos obligados para hacer corresponder los valores de la lista y pintarlo en cada elemento de layout
     * es para poder recorrer en el bucle por cada elemento de la lista y poder pintarlo
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BrigdeHolder holder, int position) {
        holder.brigdeName.setText(bridgeList.get(position).getName());
        holder.brigdeCountry.setText(bridgeList.get(position).getYearBuild());
        holder.brigdeCity.setText(bridgeList.get(position).getCity());
        holder.brigdeYearBuild.setText(bridgeList.get(position).getYearBuild());
        holder.brigdeNumerVain.setText(bridgeList.get(position).getYearBuild());
        holder.brigdeNumberStapes.setText(bridgeList.get(position).getNumberStapes());
        holder.brigdePlatform.setText(bridgeList.get(position).getPlatform());
    }

    /**
     * Metodo que estamos obligados a hacer para que devuelva el número de elementos y android pueda hacer sus calculos y pintar xtodo en base a esos calculos
     * @return
     */
    @Override
    public int getItemCount() {
        return  bridgeList.size(); //devolvemos el tamaño de la lista
    }

    /**
     * 5) Holder son las estructuras que contienen los datos y los rellenan luego
     * Creamos todos los componentes que tenemos
     */
    public class BrigdeHolder extends RecyclerView.ViewHolder {
        public TextView brigdeName;
        public TextView brigdeCountry;
        public TextView brigdeCity;
        public TextView brigdeYearBuild;
//        public TextView brigdeLatitude; //Quitar cuanto tenga el point creado
//        public TextView brigdeLongitude; //Quitar cuanto tenga el point creado
        public TextView brigdeNumerVain;
        public TextView brigdeNumberStapes;
        public TextView brigdePlatform;
        public View parentView; //vista padre - como el recyclerView


        /**
         * 5) Consturctor del Holder
         * @param view
         */
        public BrigdeHolder(View view) {
            super(view); //Vista padre
            parentView = view; //Guardamos el componente padre


        }
    }
}
