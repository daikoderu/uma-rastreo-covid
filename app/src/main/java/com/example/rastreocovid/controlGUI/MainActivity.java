package com.example.rastreocovid.controlGUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.rastreocovid.R;
import com.example.rastreocovid.modelo.Debug;
import com.example.rastreocovid.modelo.Persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linealPersonas, linealAmigos, linealDisponibles;
    private List<Integer> listaPersonas = new ArrayList<>();
    private Button btnQuitar, btnAniadir;
    private List<TableLayout> tablas = new ArrayList<>();

    private int numeroPersonas = 0;
    private int idSeleccionado = -1;
    private int idAnterior = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enlazarControles();
        mostrarInfoDebug();
        mostrarListaPersonas();
    }

    private void mostrarListaPersonas() {
        List<Persona> listaPersonas = Persona.getPersonas(this);
        for(Persona p : listaPersonas) {
            mostrarPersona(p);
        }
    }

    private void mostrarPersona(Persona p) { // En progreso
        View vistaP = getLayoutInflater().inflate(R.layout.activity_persona, null);

        vistaP.setId(p.getId());
        //vistaP.setId(numeroPersonas);

        TextView id = (TextView) vistaP.findViewById(R.id.txtPersonaID);
        id.setText(" "+p.getId());

        TextView nombre = (TextView) vistaP.findViewById(R.id.txtPersonaNombre);
        nombre.setText(p.toString());

        TableLayout t = (TableLayout) vistaP.findViewById(R.id.tlPersonas);
        tablas.add(t);

        t.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                t.setBackgroundColor(Color.CYAN);

                if(idSeleccionado > -1) { // Solución Temporal
                    //tablas.get(idAnterior).setBackgroundColor(Color.WHITE);
                    for(TableLayout tAux : tablas) {
                        if (!tAux.equals(t)){
                            tAux.setBackgroundColor(Color.WHITE);
                        }
                    }
                }

                idAnterior = idSeleccionado;
                idSeleccionado = p.getId();
                // mostrar amigos y disponibles
                //mostrarRelaciones();
                // borrar seleccion del anterior

            }

        });

        linealPersonas.addView(vistaP);
        listaPersonas.add(vistaP.getId());
        numeroPersonas++;
    }

    private void enlazarControles() {
        btnQuitar = findViewById(R.id.btnQuitar);
        btnAniadir = findViewById(R.id.btnAniadir);
        linealPersonas = findViewById(R.id.linealPersonas);
        linealAmigos = findViewById(R.id.linealAmigos);
        linealDisponibles = findViewById(R.id.linealDisponibles);
    }

    private void mostrarInfoDebug() {
        Context context = getApplicationContext();
        Debug.mostrarPersonasPorConsola(context);
        Debug.mostrarAmigosPorConsola(context);
    }

}