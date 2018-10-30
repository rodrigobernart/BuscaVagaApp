package com.example.root.buscavagaapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Switch;

import com.example.root.buscavagaapp.dao.PreferenciasUsuarioDAO;
import com.example.root.buscavagaapp.modelo.PreferenciasUsuario;

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
    private PreferenciasUsuario preferenciasUsuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preferencias, container, false);

        preferenciasUsuarioDAO = new PreferenciasUsuarioDAO(getActivity());
        List<PreferenciasUsuario> preferenciaRetornada = preferenciasUsuarioDAO.retornaPreferencias();

        motoSwitch = view.findViewById(R.id.motoSwitch);
        carroSwitch = view.findViewById(R.id.carroSwitch);
        meiaHoraSwitch = view.findViewById(R.id.meiaHoraSwitch);
        umaHoraSwitch = view.findViewById(R.id.umaHoraSwitch);
        diariaSwitch = view.findViewById(R.id.diariaSwitch);
        semanalSwitch = view.findViewById(R.id.semanalSwitch);
        mensalSwitch = view.findViewById(R.id.mensalSwitch);

        if(preferenciasUsuario.isMoto()){
            motoSwitch.setChecked(true);
        }

        if(preferenciasUsuario.isCarro()){
            carroSwitch.setChecked(true);
        }

        if(preferenciasUsuario.isValor_meiahora()){
            meiaHoraSwitch.setChecked(true);
        }

        if(preferenciasUsuario.isValor_umahora()){
            umaHoraSwitch.setChecked(true);
        }

        if(preferenciasUsuario.isValor_diaria()){
            diariaSwitch.setChecked(true);
        }

        if(preferenciasUsuario.isValor_semanal()){
            semanalSwitch.setChecked(true);
        }

        if(preferenciasUsuario.isValor_mensal()){
            mensalSwitch.setChecked(true);
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
                if(motoSwitch.isChecked()){
                    preferenciasUsuario.setMoto(true);
                } else {
                    preferenciasUsuario.setMoto(false);
                }

                if(carroSwitch.isChecked()){
                    preferenciasUsuario.setCarro(true);
                } else {
                    preferenciasUsuario.setCarro(false);
                }

                if(meiaHoraSwitch.isChecked()){
                    preferenciasUsuario.setValor_meiahora(true);
                } else {
                    preferenciasUsuario.setValor_meiahora(false);
                }

                if(umaHoraSwitch.isChecked()){
                    preferenciasUsuario.setValor_umahora(true);
                } else {
                    preferenciasUsuario.setValor_umahora(false);
                }

                if(diariaSwitch.isChecked()){
                    preferenciasUsuario.setValor_diaria(true);
                } else {
                    preferenciasUsuario.setValor_diaria(false);
                }

                if(semanalSwitch.isChecked()){
                    preferenciasUsuario.setValor_semanal(true);
                } else {
                    preferenciasUsuario.setValor_semanal(false);
                }

                if(mensalSwitch.isChecked()){
                    preferenciasUsuario.setValor_mensal(true);
                } else {
                    preferenciasUsuario.setValor_mensal(false);
                }
                preferenciasUsuarioDAO.atualizar(preferenciasUsuario);

            }
        });
        return view;
    }

}
