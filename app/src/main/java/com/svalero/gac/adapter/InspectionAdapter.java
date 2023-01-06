package com.svalero.gac.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
public class InspectionAdapter extends RecyclerView.Adapter<InspectorAdapter.InspectorHolder> {

    private Context context; // Es la activity en la que estamos
    private List<Inspection> inspectionList;
    private Inspection inspection;
//    private Brigde brigde;
//    private Inspector inspector;

    /**
     * 1) Constructor que creamos para pasarle los datos que queremos que pinte
     *
     * @param dataList Lista de inspectores que le pasamos
     */
    public InspectionAdapter(Context context, List<Inspection> dataList) {
        this.context = context; //El contexto
        this.inspectionList = dataList; //La lista de los puentes
    }

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
