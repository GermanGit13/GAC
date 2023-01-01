package com.svalero.gac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class BridgeAdapter extends RecyclerView.Adapter<BridgeAdapter.BrigeHolder> {

    private Context context; // Es la activity en la que estamos
    private List<Brigde> bridgeList; //Lista de puentes para pintarlo en el RecyclerView

    /**
     * 1) Constructor que creamos para pasarle los datos que queremos que pinte
     * @param dataList Lista de puentes que le pasamos
     */
    public BridgeAdapter(Context context, List<Brigde> dataList) {
        this.context = context;
        this.bridgeList = dataList;
    }

    /**
     * Metodo con el que Android va a inflar, va a crear cada estructura del layout donde irán los datos de cada puente.
     * Vista detalle de cada puente
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BridgeAdapter.BrigeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    /**
     * Metodo que estamos obligados para hacer corresponder los valores de la lista y pintarlo en cada elemento de layout
     * es para poder recorrer en el bucle por cada elemento de la lista y poder pintarlo
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull BridgeAdapter.BrigeHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * 5) Holder son las estructuras que contienen los datos y los rellenan luego
     * Creamos todos los componentes que tenemos
     */
    public class BrigdeHolder extends RecyclerView.ViewHolder {

    }

    /**
     * 5) Consturctor del Holder
     * @param view
     */
    public BrigdeHolder(View view) {

    }


}
