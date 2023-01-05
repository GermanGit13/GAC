package com.svalero.gac.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * BrigdeAdapter: Es la clase en la que le explicamos a Android como pintar cada elemento en el RecyclerView
 * Patron Holder: 1) Constructor - 2) onCreateViewHolder - 3) onBindViewHolder - 4) getItemCount - 5) Y la estructura SuperheroHolder
 * al extender de la clase RecyclerView los @Override los añadira automáticamente para el patron Holder, solo añadiremos nosotros el 5)
 *
 */
public class InspectionAdapter extends RecyclerView.Adapter<InspectorAdapter.InspectorHolder> {
    @NonNull
    @Override
    public InspectorAdapter.InspectorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull InspectorAdapter.InspectorHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
