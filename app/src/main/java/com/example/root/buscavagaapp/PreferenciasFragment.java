package com.example.root.buscavagaapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.root.buscavagaapp.dao.PreferenciasUsuarioDAO;
import com.example.root.buscavagaapp.modelo.PreferenciasUsuario;

import java.util.ArrayList;
import java.util.List;

public class PreferenciasFragment extends Fragment {

    private Switch motoSwitch;
    private Switch carroSwitch;
    private Switch meiaHoraSwitch;
    private Switch umaHoraSwitch;
    private Switch diariaSwitch;
    private Switch semanalSwitch;
    private Switch mensalSwitch;
    private Button btSalvar;
    private Button btLimpar;

    private PreferenciasUsuarioDAO preferenciasUsuarioDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preferencias, container, false);

        motoSwitch = view.findViewById(R.id.motoSwitch);
        carroSwitch = view.findViewById(R.id.carroSwitch);
        meiaHoraSwitch = view.findViewById(R.id.meiaHoraSwitch);
        umaHoraSwitch = view.findViewById(R.id.umaHoraSwitch);
        diariaSwitch = view.findViewById(R.id.diariaSwitch);
        semanalSwitch = view.findViewById(R.id.semanalSwitch);
        mensalSwitch = view.findViewById(R.id.mensalSwitch);

        PreferenciasUsuario preferenciasUsuario = new PreferenciasUsuario();

        preferenciasUsuarioDAO = new PreferenciasUsuarioDAO(getActivity());

        Globais.listaPreferencias = preferenciasUsuarioDAO.retornaPreferencias();

        if(Globais.listaPreferencias.get(0).getCarro().equals("SIM")){
            carroSwitch.setChecked(true);
        } else {
            carroSwitch.setChecked(false);
        }

        if(Globais.listaPreferencias.get(0).getMoto().equals("SIM")){
            motoSwitch.setChecked(true);
        } else {
            motoSwitch.setChecked(false);
        }

        if(Globais.listaPreferencias.get(0).getValor_meiahora().equals("SIM")){
            meiaHoraSwitch.setChecked(true);
        } else {
            meiaHoraSwitch.setChecked(false);
        }

        if(Globais.listaPreferencias.get(0).getValor_umahora().equals("SIM")){
            umaHoraSwitch.setChecked(true);
        } else {
            umaHoraSwitch.setChecked(false);
        }

        if(Globais.listaPreferencias.get(0).getValor_diaria().equals("SIM")){
            diariaSwitch.setChecked(true);
        } else {
            diariaSwitch.setChecked(false);
        }

        if(Globais.listaPreferencias.get(0).getValor_semanal().equals("SIM")){
            semanalSwitch.setChecked(true);
        } else {
            semanalSwitch.setChecked(false);
        }

        if(Globais.listaPreferencias.get(0).getValor_mensal().equals("SIM")){
            mensalSwitch.setChecked(true);
        } else {
            mensalSwitch.setChecked(false);
        }

        btLimpar = view.findViewById(R.id.btLimpar);
        btLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motoSwitch.setChecked(false);
                carroSwitch.setChecked(false);
                meiaHoraSwitch.setChecked(false);
                umaHoraSwitch.setChecked(false);
                diariaSwitch.setChecked(false);
                semanalSwitch.setChecked(false);
                mensalSwitch.setChecked(false);
            }
        });

        btSalvar = view.findViewById(R.id.btSalvar);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PreferenciasUsuario p = new PreferenciasUsuario();

                if(carroSwitch.isChecked()){
                    p.setCarro("SIM");
                } else {
                    p.setCarro("NÃO");
                }

                if(motoSwitch.isChecked()){
                    p.setMoto("SIM");
                } else {
                    p.setMoto("NÃO");
                }

                if(meiaHoraSwitch.isChecked()){
                    p.setValor_meiahora("SIM");
                } else {
                    p.setValor_meiahora("NÃO");
                }

                if(umaHoraSwitch.isChecked()){
                    p.setValor_umahora("SIM");
                } else {
                    p.setValor_umahora("NÃO");
                }

                if(diariaSwitch.isChecked()){
                    p.setValor_diaria("SIM");
                } else {
                    p.setValor_diaria("NÃO");
                }

                if(semanalSwitch.isChecked()){
                    p.setValor_semanal("SIM");
                } else {
                    p.setValor_semanal("NÃO");
                }

                if(mensalSwitch.isChecked()){
                    p.setValor_mensal("SIM");
                } else {
                    p.setValor_mensal("NÃO");
                }

                preferenciasUsuarioDAO.atualizar(p);
                Toast.makeText(getActivity(),  "Preferências alteradas!!", Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }

}
