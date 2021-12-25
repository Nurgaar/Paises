package com.ngg.paises;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.ngg.paises.databinding.FragmentJuegoBanderasBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class JuegoBanderasFragment extends Fragment {

    List<PreguntaPais> preguntas = new ArrayList<>();
    List<String> todasLasRespuestas = new ArrayList<>();
    private final int NUM_RESPUESTAS = 3;
    String respuestaCorrecta;

    FragmentJuegoBanderasBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtenerPreguntasYRespuestas(this.preguntas, this.todasLasRespuestas);
    }

    private void obtenerPreguntasYRespuestas(List<PreguntaPais> preguntas, List<String> todasLasRespuestas) {
        String[] paises = getResources().getStringArray(R.array.paises);
        for(String idPais : paises) {
            int RIdPais = getResources().getIdentifier(idPais, "array", getContext().getPackageName());
            String [] datosPais  = getResources().getStringArray(RIdPais);
            PreguntaPais p = new PreguntaPais(idPais, datosPais[0], datosPais[1]);
            preguntas.add(p);

            todasLasRespuestas.add(datosPais[0]);
        }
        Collections.shuffle(preguntas);
        Collections.shuffle(todasLasRespuestas);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJuegoBanderasBinding.inflate(inflater,container,false);

        presentarPregunta();

        //funcion para que el boton haga cosas cuadno hagamos click en el
            binding.aceptar.setOnClickListener(view -> {

                //saber el ID del boton que tengo seleccionado
            int radioID = binding.radioGroup.getCheckedRadioButtonId();

            if(radioID != -1){

                RadioButton radio = getActivity().findViewById(radioID);
                CharSequence respuesta = radio.getText();

                //Es lo mismo que un if/else, en plan, si la respuesta es correcta, el mensaje es igual a acertaste, si no, mensaje = fallaste

                CharSequence mensaje = respuesta.equals(respuestaCorrecta) ? "¡ACERTASTE!": "¡FALLASTE!";

                //Snackbar

               Snackbar snackbar= Snackbar.make(view, mensaje, Snackbar.LENGTH_INDEFINITE);

               //Una ccion para que solo si pulsas en el texto (siguiente), te pase de pregunta
                snackbar.setAction("Siguiente", view1 -> presentarPregunta());
                snackbar.show();

                //vamos a inhabilitar cuando selecciones una opción el botón de aceptar y ponemos indefinido el snackbar para que no se deshabilite también
                view.setEnabled(false);


                //Esto se sustituye por la linea de arriba
                /*
                if(respuesta.equals(respuestaCorrecta)){
                    Toast.makeText(getContext(), "Correcta!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Incorrecta!", Toast.LENGTH_SHORT).show();
                }*/

                } else{
                Toast.makeText(getContext(),"Selecciona una opción", Toast.LENGTH_SHORT).show();
            }

            });


        return binding.getRoot();
    }

    private void presentarPregunta() {
        if(preguntas.size() > 0) {

            //habilita el botón aceptar cuando pasas de pregunta
            binding.aceptar.setEnabled(true);
            binding.radioGroup.clearCheck();
            PreguntaPais pregunta = preguntas.remove(0);

            binding.bandera.setImageResource(getImageId(pregunta.bandera));
            List<String> respuestas = generarResuestasPosibles(pregunta);


            for(int i=0; i < binding.radioGroup.getChildCount();i++){
                RadioButton radio = (RadioButton) binding.radioGroup.getChildAt(i);
                radio.setText(respuestas.get(i));

            }


        }
    }

    private List<String> generarResuestasPosibles(PreguntaPais pregunta) {
         respuestaCorrecta = pregunta.nombrePais;
        List<String> todasLasRespuestas = new ArrayList<>(this.todasLasRespuestas);
        todasLasRespuestas.remove(respuestaCorrecta);


        List<String> respuestas = new ArrayList<>();
        respuestas.add(respuestaCorrecta);

        //Elimino una respuesta de la lista y se la añado a otra

        for(int i=0; i<binding.radioGroup.getChildCount()-1;i++){
            respuestas.add(todasLasRespuestas.remove(new Random().nextInt(todasLasRespuestas.size())));
        }

        //barajar las respuestas y que salgan aleatorias
        Collections.shuffle(respuestas);

        return respuestas;

    }

   /* private presentarPregunta() {
        binding.bandera.setImageResource();
    }*/

    class PreguntaPais {
        private String idPais;
        private String nombrePais;
        private String bandera;
        private List<String> respuestas;

        public PreguntaPais(String idPais, String nombrePais, String bandera) {
            this.idPais = idPais;
            this.nombrePais = nombrePais;
            this.bandera = bandera;
        }
    }

    private int getImageId(String imagePath) {
        String imageName = "bandera_" + imagePath.substring(imagePath.lastIndexOf("/")+1, imagePath.lastIndexOf("."));
        return getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());
    }
}