package com.ngg.android.paises;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ngg.paises.R;
import com.ngg.android.paises.placeholder.PlaceholderContent;

/**
 * A fragment representing a list of Items.
 */
public class PaisFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pais_list, container, false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            String tipoVisualizacion = prefs.getString("tipo_visualizacion", "listado");
            if (tipoVisualizacion.equals("listado")) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else { //si fuera rejilla
                recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            }


            //INVOCA AL CONSTRUCTOR Y HACE QUE EL ARRAYLIST DE PAISES ESTÉ LLENO

            boolean useDivider = prefs.getBoolean("linea", false);
            if(useDivider){
                DividerItemDecoration verticalDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        LinearLayout.VERTICAL);
                recyclerView.addItemDecoration(verticalDecoration);
            }
            PlaceholderContent placeholderContent = new PlaceholderContent(getResources(),
                    getContext().getPackageName());

            recyclerView.setAdapter(new PaisRecyclerViewAdapter(PlaceholderContent.PAISES));
        }
        return view;
    }
}