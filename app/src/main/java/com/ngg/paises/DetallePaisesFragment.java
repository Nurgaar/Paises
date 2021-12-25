package com.ngg.paises;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngg.android.paises.placeholder.PlaceholderContent;
import com.ngg.paises.databinding.FragmentDetallePaisesBinding;



public class DetallePaisesFragment extends Fragment {

   private FragmentDetallePaisesBinding binding;
   private PlaceholderContent.Pais mPais;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPais = getArguments().getParcelable("pais");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetallePaisesBinding.inflate(inflater,container,false);

        ImageView iv = binding.foto;
        iv.setImageResource(getImageId(mPais.foto));
        TextView tv = binding.descripcion;
        tv.setText(mPais.detalles);

        return binding.getRoot();
    }

    private int getImageId(String imagePath){
        String imageName = imagePath.substring(imagePath.lastIndexOf("/")+1,imagePath.lastIndexOf("."));
        return getResources().getIdentifier(imageName,"drawable",getContext().getPackageName());
    }
}